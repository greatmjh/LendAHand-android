package com.example.lendahand;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lendahand.apiclasses.DonationOffer;
import com.example.lendahand.apiclasses.LogInResponse;
import com.example.lendahand.apiclasses.LoginRequest;
import com.example.lendahand.apiclasses.ProfileInfo;
import com.example.lendahand.apiclasses.RawApiNotification;
import com.example.lendahand.apiclasses.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


//Giant class to manage sessionkey storage, API connections, and caching
public class DataManager {
    //Singleton instance getter
    public static DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    //==== Sessionkey/login management ====
    public String getSessionKey() {
        return sessionKey;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void logIn(String sessionKey) {
        loggedIn = true;
        this.sessionKey = sessionKey;
        updatePrefs();
    }

    public void logOut() {
        sessionKey = "";
        loggedIn = false;
        updatePrefs();
        Intent intent = new Intent(applicationContext, WelcomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        applicationContext.startActivity(intent);
    }

    //==== API functions ====
    /*
    Note that most of these functions run asynchronously with output send via callbacks.
    Note that these callbacks aren't on the UI thread so you have to do that before updating elements onscreen.
     */

    //Sign-up endpoint -- callback with no parameters (called on success)
    public void APIRegister(RegisterRequest req, Runnable callback) {
        //Convert input into JSON
        String payloadJson = gson.toJson(req);
        makeApiRequest(payloadJson, "register.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //Deserialise our response
                LogInResponse decodedResponse = gson.fromJson(responseBody, LogInResponse.class);
                //Log in
                handleLogInResponse(decodedResponse, callback);
            }
        });
    }

    //Log in endpoint -- callback with no parameters (called on success)
    public void APILogin(LoginRequest req, Runnable callback) {
        String payloadJson = gson.toJson(req);

        makeApiRequest(payloadJson, "login.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //Deserialise our response
                LogInResponse decodedResponse = gson.fromJson(responseBody, LogInResponse.class);
                //Log in
                handleLogInResponse(decodedResponse, callback);
            }
        });
    }

    //Change password endpoint -- callback with no parameters called on success
    public void APIChangePassword(String oldPassword, String newPassword, Runnable callback) {
        try {
            //encode data
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("sessionKey", getSessionKey());
            jsonObj.put("oldPassword", oldPassword);
            jsonObj.put("newPassword", newPassword);

            String payloadJson = jsonObj.toString();

            makeApiRequest(payloadJson, "change_password.php", new ApiRequestCallback() {
                @Override
                public void onSuccessfulResponse(String responseBody) {
                    //Deserialise our response
                    LogInResponse decodedResponse = gson.fromJson(responseBody, LogInResponse.class);

                    //Check success
                    if (decodedResponse.success) {
                        //save the new session key we got
                        sessionKey = decodedResponse.sessionKey;
                        updatePrefs();
                        callback.run();
                    } else {
                        //display the error
                        toast(decodedResponse.errorMessage);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //get profile info endpoint
    public void APIGetProfileInfo(APIProfileInfoCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "get_profile.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //Deserialise our response
                ProfileInfo decodedResponse = gson.fromJson(responseBody, ProfileInfo.class);

                //Return our response to the callback
                callback.success(decodedResponse);
            }
        });
    }

    //Update profile endpoint -- No callback as no data comes back with it

    public void APIUpdateProfileInfo(ProfileInfo newProfileInfo) {
        APIUpdateProfileInfo(newProfileInfo, new Runnable() {
            @Override
            public void run() {
                //pass
            }
        });
    }
    //Override that has a callback so we know its done
    public void APIUpdateProfileInfo(ProfileInfo newProfileInfo, Runnable callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("sessionKey", JsonParser.parseString(sessionKey));
        jsonObject.add("profileInfo", gson.toJsonTree(newProfileInfo));

        String payloadJson = jsonObject.toString();

        makeApiRequest(payloadJson, "update_profile.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                callback.run();
            }
        });
    }

    public interface APIProfileInfoCallback{
        public void success(ProfileInfo p);
    }

    //Get top donors endpoint
    public void APIGetTopDonors(TopDonorsCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "top_donors.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //Deserialise
                Type listType = new TypeToken<ArrayList<topDonorItem>>(){}.getType();
                List<topDonorItem> decodedResponse = gson.fromJson(responseBody, listType);
                callback.onSuccess(decodedResponse);
            }
        });
    }
    public interface TopDonorsCallback {
        public void onSuccess(List<topDonorItem> items);
    }

    //Load incoming notifications
    public void APIGetNotifications(NotificationsCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "load_notifications.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //Deserialise into array of raw notifications
                RawApiNotification[] decodedResponse = gson.fromJson(responseBody, RawApiNotification[].class);
                //Go through and properly construct notification items
                ArrayList<notificationItem> result = new ArrayList<>();
                for (RawApiNotification n : decodedResponse) {
                    result.add(new notificationItem(UUID.fromString(n.id) ,LocalDateTime.parse(n.time, DateTimeFormatter.ISO_DATE_TIME), n.heading, n.text, n.onClick, n.isRead));
                }
                //Return the result
                callback.onSuccess(result);
            }
        });
    }
    public interface NotificationsCallback {
        public void onSuccess(List<notificationItem> items);
    }

    //Mark notification as read
    public void APIMarkAsRead(UUID notifId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("sessionKey", JsonParser.parseString(sessionKey));
        jsonObject.add("notificationId", JsonParser.parseString(notifId.toString()));
        String request = jsonObject.toString();
        makeApiRequest(request, "mark_as_read.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //pass
            }
        });
    }

    //Mark all notifications as read
    public void APIMarkAllAsRead() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("sessionKey", JsonParser.parseString(sessionKey));
        jsonObject.add("notificationId", JsonParser.parseString("all"));
        String request = jsonObject.toString();
        makeApiRequest(request, "mark_as_read.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //pass
            }
        });
    }

    //Load global item tree
    public void APILoadItemTree(ItemTreeCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "load_global_items.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                ItemCategory[] decodedResponse = gson.fromJson(responseBody, ItemCategory[].class);
                callback.onSuccess(decodedResponse);
            }
        });
    }
    public interface ItemTreeCallback {
        void onSuccess(ItemCategory[] roots);
    }

    //Get all general requests
    public void APIGetAllGeneralRequests(GenRequestCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "get_universal_requests.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                try {
                    //Decode response into correct type
                    HighlyRequestedItem[] resultArr = gson.fromJson(responseBody, HighlyRequestedItem[].class);
                    //Return in arraylist form
                    callback.onSuccess(new ArrayList<>(Arrays.asList(resultArr)));
                } catch (JsonSyntaxException e) {
                    toast("Error processing response from server");
                    e.printStackTrace();
                }
            }
        });
    }

    //Get my general requests
    public void APIGetMyGeneralRequests(GenRequestCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "get_my_universal_requests.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                try {
                    //Decode response into correct type
                    HighlyRequestedItem[] resultArr = gson.fromJson(responseBody, HighlyRequestedItem[].class);
                    //Return in arraylist form
                    callback.onSuccess(new ArrayList<>(Arrays.asList(resultArr)));
                } catch (JsonSyntaxException e) {
                    toast("Error processing response from server");
                    e.printStackTrace();
                }

            }
        });
    }

    //Used for both above
    public interface GenRequestCallback {
        public void onSuccess(ArrayList<HighlyRequestedItem> result);
    }

    //Make/update general request
    public void APIMakeGeneralRequest(UUID itemId, int quantity) {
        //Formulate request body
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("sessionKey", JsonParser.parseString(sessionKey));
        jsonObject.add("itemId", JsonParser.parseString(itemId.toString()));
        jsonObject.add("qty", JsonParser.parseString(Integer.toString(quantity)));

        String reqBody = jsonObject.toString();

        //Send request
        makeApiRequest(reqBody, "make_universal_request.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //pass
            }
        });
    }

    //Get requests received
    public void APIGetRequestsReceived(RequestsReceivedCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "incoming_requests.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                IncomingReqItem[] decodedResponse = gson.fromJson(responseBody, IncomingReqItem[].class);
                //Split into open and fulfilled;
                LinkedList<IncomingReqItem> open = new LinkedList<>();
                LinkedList<IncomingReqItem> fulfilled = new LinkedList<>();
                for (IncomingReqItem item: decodedResponse) {
                    if (item.isOpen()) {
                        open.add(item);
                    } else {
                        fulfilled.add(item);
                    }
                }
                //return to callback
                callback.onSuccess(open, fulfilled);
            }
        });
    }
    public interface RequestsReceivedCallback {
        void onSuccess(LinkedList<IncomingReqItem> open, LinkedList<IncomingReqItem> fulfilled);
    }

    //Make a new donation
    public void APIMakeDonation(UUID itemCategory, String itemDesc, int newQty) {
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("sessionKey", sessionKey);
        reqBody.addProperty("itemId", itemCategory.toString());
        reqBody.addProperty("itemDesc", itemDesc);
        reqBody.addProperty("newQty", newQty);

        String reqJson = reqBody.toString();
        makeApiRequest(reqJson, "edit_my_donation.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //pass
            }
        });
    }

    //Edit donation quantity, i.e delete
    public void APIEditDonationQty(UUID offerID, int newQty) {
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("sessionKey", sessionKey);
        reqBody.addProperty("offerID", offerID.toString());
        reqBody.addProperty("itemId", "");
        reqBody.addProperty("itemDesc", "");
        reqBody.addProperty("newQty", newQty);

        String reqJson = reqBody.toString();
        makeApiRequest(reqJson, "edit_my_donation.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //pass
            }
        });
    }

    //Get your items up for donation
    public void APIGetMyDonations(ManageMyDonationsCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "get_my_donations.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                manageMyDonationsItem[] decodedResponse = gson.fromJson(responseBody, manageMyDonationsItem[].class);
                callback.onSuccess(new ArrayList<>(Arrays.asList(decodedResponse)));
            }
        });
    }
    public interface ManageMyDonationsCallback {
        void onSuccess(ArrayList<manageMyDonationsItem> result);
    }

    //Get outgoing requests
    public void APIGetOutgoingRequests(OutgoingRequestsCallback callback) {
        makeApiRequest(getAuthenticatedRequestJSON(), "get_outgoing_requests.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                OutgoingReqItem[] decodedResponse = gson.fromJson(responseBody, OutgoingReqItem[].class);
                LinkedList<OutgoingReqItem> open = new LinkedList<>();
                LinkedList<OutgoingReqItem> closed = new LinkedList<>();
                for (OutgoingReqItem item : decodedResponse) {
                    if (item.isOpen()) {
                        open.add(item);
                    } else {
                        closed.add(item);
                    }
                }
                callback.onSuccess(open, closed);
            }
        });
    }
    public interface OutgoingRequestsCallback {
        void onSuccess(LinkedList<OutgoingReqItem> open, LinkedList<OutgoingReqItem> closed);
    }

    //Cancel outgoing requests
    public void APICancelOutgoingRequest(UUID requestID) {
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("sessionKey", sessionKey);
        reqBody.addProperty("requestID", requestID.toString());
        String reqJson = reqBody.toString();
        makeApiRequest(reqJson, "cancel_outgoing_request.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //pass
            }
        });
    }

    //Respond to request
    public void APIRespondToRequest(UUID requestID, boolean accepted) {
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("sessionKey", sessionKey);
        reqBody.addProperty("accepted", accepted);
        reqBody.addProperty("requestID", requestID.toString());
        String reqJson = reqBody.toString();
        makeApiRequest(reqJson, "respond_to_request.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                //pass
            }
        });
    }

    //Load donation offers
    public void APILoadDonationOffers(double latitude, double longitude, DonationOffersCallback callback) {
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("sessionKey", sessionKey);
        reqBody.addProperty("latitude", latitude);
        reqBody.addProperty("longitude", longitude);

        String reqJson = reqBody.toString();
        makeApiRequest(reqJson, "load_donation_offers.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                DonationOffer[] decodedResponse = gson.fromJson(responseBody, DonationOffer[].class);
                callback.onSuccess(new ArrayList<>(Arrays.asList(decodedResponse)));
            }
        });
    }
    public interface DonationOffersCallback {
        void onSuccess(ArrayList<DonationOffer> result);
    }

    public void APIRespondToOffer(UUID offerID, int qty, Runnable callback) {
        JsonObject reqBody = new JsonObject();
        reqBody.addProperty("sessionKey", sessionKey);
        reqBody.addProperty("qty", qty);
        reqBody.addProperty("offerID", offerID.toString());

        String reqJson = reqBody.toString();
        makeApiRequest(reqJson, "respond_to_offer.php", new ApiRequestCallback() {
            @Override
            public void onSuccessfulResponse(String responseBody) {
                callback.run();
            }
        });
    }

    //==== Class internals ====
    static DataManager instance;
    final SharedPreferences sharedPreferences;
    final Context applicationContext;
    String sessionKey;
    boolean loggedIn;
    final Gson gson;
    final OkHttpClient httpClient;

    private DataManager(Context context) {
        //Initialise members
        gson = new Gson();
        httpClient = new OkHttpClient();
        applicationContext = context.getApplicationContext();
        sharedPreferences = applicationContext.getSharedPreferences("com.example.lendahand.PREFERENCE_FILE", Context.MODE_PRIVATE);
        //Pull from storage
        sessionKey = sharedPreferences.getString("sessionKey", "");
        loggedIn = !sessionKey.isEmpty();
    }

    private void updatePrefs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("sessionKey", sessionKey);
                editor.commit(); //for some reason apply doesn't work in all cases so this works in its own thread
            }
        }).start();

    }

    //Show a toast when not on UI thread
    private void toast(String toast) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(applicationContext, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handleLogInResponse(LogInResponse decodedResponse, Runnable callback) {
        //Check if the response was successful and if so log in
        if (!decodedResponse.success) {
            //Check if signup worked
            toast(decodedResponse.errorMessage);
        } else {
            //Update session key store and then log in!
            logIn(decodedResponse.sessionKey);
            callback.run();
        }
    }

    private String getAuthenticatedRequestJSON() {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("sessionKey", getSessionKey());
            return jsonObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void makeApiRequest(String reqJson, String endpointName, ApiRequestCallback callback) {
        //Create OkHttp Request
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(reqJson, MediaType.parse("application/json"));
        Request httpReq = new Request.Builder()
                .url(HttpUrl.parse(applicationContext.getString(R.string.apiServerAddr)).newBuilder().
                        addPathSegment(endpointName).build())
                .post(body)
                .build();

        //Run the request
        client.newCall(httpReq).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                toast("Please check your internet connection");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseBody = response.body().string();
                    //Handle errors from server
                    if (!response.isSuccessful()) {
                        if (response.code() == 500) {
                            toast("Internal server error");
                        } else if (response.code() == 400) {
                            toast("Bad input: " + responseBody);
                        } else if (response.code() == 401) {
                            toast("You have been logged out.");
                            logOut();

                        }
                        else {
                            toast("Unexpected HTTP error:" + response.code());
                        }
                    } else {
                        //Send the successful response back
                        callback.onSuccessfulResponse(responseBody);
                    }
                } catch (IOException e) { //shouldn't really happen the way we are doing this
                    e.printStackTrace();
                }

            }
        });
    }
    private interface ApiRequestCallback {
        void onSuccessfulResponse(String responseBody);
    }

}
