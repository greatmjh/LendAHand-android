package com.example.lendahand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class profileSettings extends AppCompatActivity {

    public void changePassClick(View v) {
        previousView.setPrevView(profileSettings.class);

        Intent intent = new Intent(this, changePassword.class);
        startActivity(intent);
    }

    public void updateAddressClick(View v) {
        //Check if we have location perms (and request them if we don't)
        if (!requestLocationPermission()) {
            //don't have location permission
            Toast.makeText(this, "Please enable location permission.", Toast.LENGTH_SHORT).show();
            return; //when the user clicks the button next we should have permission if they said yes
        }

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //Now that we have location, proceed
                    //TODO: implement update profile endpoint
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
        //TODO: update user profile and reflect changes, then go back to view profile screen

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