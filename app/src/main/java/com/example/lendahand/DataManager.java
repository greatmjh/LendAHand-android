package com.example.lendahand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lendahand.apiclasses.LogInResponse;
import com.example.lendahand.apiclasses.LoginRequest;
import com.example.lendahand.apiclasses.ProfileInfo;
import com.example.lendahand.apiclasses.RegisterRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

        //Create OkHttp Request
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(payloadJson, MediaType.parse("application/json"));
        Request httpReq = new Request.Builder()
                .url(HttpUrl.parse(applicationContext.getString(R.string.apiServerAddr)).newBuilder().
                        addPathSegment("register.php").build())
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
                        handleHttpError(response, responseBody);
                    }
                    //Deserialise our response
                    LogInResponse decodedResponse = gson.fromJson(responseBody, LogInResponse.class);

                    //Log in
                    handleLogInResponse(decodedResponse, callback);
                } catch (IOException e) { //shouldn't really happen the way we are doing this
                    e.printStackTrace();
                }


            }
        });
    }

    //Log in endpoint -- callback with no parameters (called on success)
    public void APILogin(LoginRequest req, Runnable callback) {
        String payloadJson = gson.toJson(req);

        //Create OkHttp Request
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(payloadJson, MediaType.parse("application/json"));
        Request httpReq = new Request.Builder()
                .url(HttpUrl.parse(applicationContext.getString(R.string.apiServerAddr)).newBuilder().
                        addPathSegment("login.php").build())
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
                        handleHttpError(response, responseBody);
                    }
                    //Deserialise our response
                    LogInResponse decodedResponse = gson.fromJson(responseBody, LogInResponse.class);

                    //Log in
                    handleLogInResponse(decodedResponse, callback);
                } catch (IOException e) { //shouldn't really happen the way we are doing this
                    e.printStackTrace();
                }
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

            //Create OkHttp Request
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(payloadJson, MediaType.parse("application/json"));
            Request httpReq = new Request.Builder()
                    .url(HttpUrl.parse(applicationContext.getString(R.string.apiServerAddr)).newBuilder().
                            addPathSegment("change_password.php").build())
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
                            handleHttpError(response, responseBody);
                        }
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
                    } catch (IOException e) { //shouldn't really happen the way we are doing this
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void APIGetProfileInfo(APIProfileInfoCallback callback) {
        String payloadJson = getAuthenticatedRequestJSON();
        //Create OkHttp Request
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(payloadJson, MediaType.parse("application/json"));
        Request httpReq = new Request.Builder()
                .url(HttpUrl.parse(applicationContext.getString(R.string.apiServerAddr)).newBuilder().
                        addPathSegment("get_profile.php").build())
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
                        handleHttpError(response, responseBody);
                    }
                    //Deserialise our response
                    ProfileInfo decodedResponse = gson.fromJson(responseBody, ProfileInfo.class);

                    //Return our response to the callback
                    callback.success(decodedResponse);
                } catch (IOException e) { //shouldn't really happen the way we are doing this
                    e.printStackTrace();
                }
            }
        });
    }

    public interface APIProfileInfoCallback{
        public void success(ProfileInfo p);
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

    private void handleHttpError(Response response, String responseBody) {
        if (response.code() == 500) {
            toast("Internal server error");
        } else if (response.code() == 400) {
            toast("Bad input: " + responseBody);
        } else if (response.code() == 401) {
            toast("You have been logged out.");
            //run logout on a new thread as to not break okhttp
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logOut();
                }
            }).start();

        }
        else {
            toast("Unexpected HTTP error:" + response.code());
        }
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

}
