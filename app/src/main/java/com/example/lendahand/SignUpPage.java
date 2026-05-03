package com.example.lendahand;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.apiclasses.LogInResponse;
import com.example.lendahand.apiclasses.ProfileInfo;
import com.example.lendahand.apiclasses.RegisterRequest;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpPage extends AppCompatActivity {

    public void logInClick(View v)  {
        Intent intent = new Intent(this, loginPage.class);
        startActivity(intent);
    }

    public void signUpClick(View v) {
        final Gson gson = new Gson();
        //Load data from screen
        EditText fullNameInput = findViewById(R.id.fullNameEntry);
        EditText emailInput = findViewById(R.id.emailEntry);
        EditText phoneInput = findViewById(R.id.phoneEntry);
        EditText bioInput = findViewById(R.id.bioEntry);
        EditText passwordInput = findViewById(R.id.passwordEntry);
        EditText confirmPasswordInput = findViewById(R.id.confirmPasswordEntry);
        String fullName = fullNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String bio = bioInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        //TODO: input validation confirm password checking

        //Build the JSON request
        //TODO: gps coordinates
        RegisterRequest payloadData = new RegisterRequest(new ProfileInfo(fullName, email, phone, bio, 0.1, 0.1), password);
        String payloadJson = gson.toJson(payloadData);

        //Create OkHttp Request
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(payloadJson, MediaType.parse("application/json"));
        Request signUpRequest = new Request.Builder()
                .url(HttpUrl.parse(getString(R.string.apiServerAddr)).newBuilder().
                        addPathSegment("register.php").build())
                .post(body)
                .build();

        //Run the request
        Context context = this;
        client.newCall(signUpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Sign-up failed!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //Handle errors from server
                if (!response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.code() == 500) {
                                Toast.makeText(context, "Internal server error on sign-up", Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 400) {
                                Toast.makeText(context, "Bad input: " + response.body().charStream(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Unexpected code occured: " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    return;
                }
                //Deserialise our response
                LogInResponse decodedResponse = gson.fromJson(response.body().charStream(), LogInResponse.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!decodedResponse.success) {
                            //Check if signup worked
                            Toast.makeText(getApplicationContext(), decodedResponse.errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            //Update session key store and then log in!
                            SessionState.getInstance(getApplicationContext()).logIn(decodedResponse.sessionKey);
                            Intent intent = new Intent(context, topDonors.class);
                            startActivity(intent);
                        }
                    }
                });

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}