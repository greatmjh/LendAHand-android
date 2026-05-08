package com.example.lendahand;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ActivityViewModelLazyKt;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.apiclasses.ProfileInfo;
import com.example.lendahand.apiclasses.RegisterRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

public class profileSettings extends AppCompatActivity {

    ProfileInfo serverSideProfile;
    public void changePassClick(View v) {
        previousView.setPrevView(profileSettings.class);

        Intent intent = new Intent(this, changePassword.class);
        startActivity(intent);
    }

    public void viewProfileOnClick(View v){
        Intent intent = new Intent(this, viewProfile.class);
        startActivity(intent);
    }

    public void updateAddressClick(View v) {
        //Check if we have location perms (and request them if we don't)
        if (!requestLocationPermission()) {
            //don't have location permission
            Toast.makeText(this, "Please enable location permission.", Toast.LENGTH_SHORT).show();
            return; //when the user clicks the button next we should have permission if they said yes
        }

        Activity parent = this; //for callback
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Now that we have location, proceed
                    try {
                        ProfileInfo newProfile = new ProfileInfo(serverSideProfile.fullName, serverSideProfile.email, serverSideProfile.phoneNumber, serverSideProfile.bio, location.getLatitude(), location.getLongitude());
                        DataManager.getInstance(parent).APIUpdateProfileInfo(newProfile);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Toast.makeText(parent, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Please enable location permission.", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelClick(View v) {
        Intent intent = new Intent(this, viewProfile.class);
        startActivity(intent);
    }

    public void menuBtnClick(View v){
        Intent intent = new Intent(this, menuActivity.class);
        startActivity(intent);
    }

    public void saveChangesClick(View v){
        //get stuff from screen
        EditText fullNameInput = findViewById(R.id.fullNameEntry);
        EditText emailInput = findViewById(R.id.emailEntry);
        EditText phoneInput = findViewById(R.id.phoneEntry);
        EditText bioInput = findViewById(R.id.bioEntry);

        String fullName = fullNameInput.getText().toString();
        String email = emailInput.getText().toString();
        String phoneUnformatted = phoneInput.getText().toString();
        String bio = bioInput.getText().toString();

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

        try {
            ProfileInfo newProfile = new ProfileInfo(fullName, email, phoneFormatted, bio, serverSideProfile.homeLat, serverSideProfile.homeLong);
            DataManager.getInstance(this).APIUpdateProfileInfo(newProfile);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }
        //make the previous screen update with the new data
        Intent intent = new Intent(this, viewProfile.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Load current profile from server
        DataManager.getInstance(this).APIGetProfileInfo(new DataManager.APIProfileInfoCallback() {
            @Override
            public void success(ProfileInfo p) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        serverSideProfile = p;
                        ((EditText)findViewById(R.id.fullNameEntry)).setText(p.fullName);
                        ((EditText)findViewById(R.id.emailEntry)).setText(p.email);
                        ((EditText)findViewById(R.id.phoneEntry)).setText(p.phoneNumber);
                        ((EditText)findViewById(R.id.bioEntry)).setText(p.bio);

                    }
                });
            }
        });
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