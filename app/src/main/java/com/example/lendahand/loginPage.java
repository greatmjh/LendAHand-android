package com.example.lendahand;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.apiclasses.LogInResponse;
import com.example.lendahand.apiclasses.LoginRequest;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class loginPage extends AppCompatActivity {

    public void signUpClick(View v)  {
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    public void forgotPassClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot password");
        builder.setMessage("If you forgot your password, please contact our team for assistance at 2955114@students.wits.ac.za");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void logInClick(View v)  {
        final Gson gson = new Gson();
        //Load data from screen
        EditText emailInput = findViewById(R.id.emailEntryLogin);
        EditText passwordInput = findViewById(R.id.passwordEntryLogin);
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        //TODO: input validation confirm password checking

        //Build the JSON request
        LoginRequest payloadData = new LoginRequest(email, password);

        Activity parent = this; //to run intents from within a callback

        //Send request to server
        DataManager.getInstance(this).APILogin(payloadData, new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(parent, topDonors.class);
                        parent.startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}