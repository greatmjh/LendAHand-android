package com.example.lendahand;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.apiclasses.LogInResponse;
import com.example.lendahand.apiclasses.ProfileInfo;
import com.example.lendahand.apiclasses.RegisterRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
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
        String phoneUnformatted = phoneInput.getText().toString();
        String bio = bioInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        //TODO: input validation confirm password checking

        //Reformat phone number
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String phoneFormatted = "";
        try {
            Phonenumber.PhoneNumber parsed = phoneUtil.parse(phoneUnformatted, Locale.getDefault().getCountry());
            phoneFormatted = phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            Toast.makeText(this, "Phone number incorrectly formatted", Toast.LENGTH_SHORT).show();
            return;
        }

        Activity parent = this; //so we can do intents from the callback
        //Make a request to get location
        //Check if we have location perms (and request them if we don't)
        if (!requestLocationPermission()) {
            //don't have location permission
            Toast.makeText(this, "Please enable location permission.", Toast.LENGTH_SHORT).show();
            return; //when the user clicks the button next we should have permission if they said yes
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            String finalPhoneFormatted = phoneFormatted;
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Now that we have location, proceed
                    RegisterRequest payloadData = new RegisterRequest(new ProfileInfo(fullName, email, finalPhoneFormatted, bio, location.getLatitude(), location.getLongitude()), password);
                    //Run the request
                    DataManager.getInstance(parent).APIRegister(payloadData, new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(parent, topDonors.class);
                                    //make it so you can't go back from here
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    parent.startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    });
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Please enable location permission.", Toast.LENGTH_SHORT).show();
        }




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

        //Phone number formatting as you type
        EditText phoneNumberField = findViewById(R.id.phoneEntry);
        phoneNumberField.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); //i know it's deprecated but it does what we want
    }

    private boolean requestLocationPermission() {
        // Check if permissions are already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, so return true
            return true;
        } else {
            // Request Coarse location (Recommended for Android 12+)
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    100);
            return false;
        }
    }
}