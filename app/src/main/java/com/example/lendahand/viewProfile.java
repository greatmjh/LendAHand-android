package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.apiclasses.ProfileInfo;

public class viewProfile extends AppCompatActivity {

    public void menuBtnClick(View v){
        previousView.setPrevView(viewProfile.class);

        Intent intent = new Intent(this, menuActivity.class);
        startActivity(intent);
    }

    public void editProfileClick(View v)    {
        Intent intent = new Intent(this, profileSettings.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Load profile info from server
        DataManager.getInstance(this).APIGetProfileInfo(new DataManager.APIProfileInfoCallback() {
            @Override
            public void success(ProfileInfo p) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView usersName = findViewById(R.id.usersName);
                        TextView usersBio = findViewById(R.id.userBio);
                        TextView usersEmail = findViewById(R.id.userEmailAddress);
                        TextView usersPhone = findViewById(R.id.userPhoneNum);

                        usersName.setText(p.fullName);
                        usersBio.setText(p.bio);
                        usersEmail.setText(p.email);
                        usersPhone.setText(p.phoneNumber);
                    }
                });
            }
        });
    }
}