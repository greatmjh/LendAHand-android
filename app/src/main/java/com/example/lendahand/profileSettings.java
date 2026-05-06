package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.apiclasses.ProfileInfo;

public class profileSettings extends AppCompatActivity {

    public void changePassClick(View v) {
        previousView.setPrevView(profileSettings.class);

        Intent intent = new Intent(this, changePassword.class);
        startActivity(intent);
    }

    public void updateAddressClick(View v) {

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

        Intent intent = new Intent(this, viewProfile.class);
        startActivity(intent);
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
}