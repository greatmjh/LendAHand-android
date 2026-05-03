package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AppLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionState sessionState = SessionState.getInstance(this);
        if (sessionState.isLoggedIn()) {
            startActivity(new Intent(this, topDonors.class));
        } else {
            startActivity(new Intent(this, WelcomePage.class));
        }
        finish();
    }
}