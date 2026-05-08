package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AppLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager dataManager = DataManager.getInstance(this);
        if (dataManager.isLoggedIn()) {
            startActivity(new Intent(this, topDonors.class));
        } else {
            startActivity(new Intent(this, WelcomePage.class));
        }
        DataManager.getInstance(this); //make sure data manager is up
        ItemCategory.getRoots(); // load these from server so we have them when necessary
        finish();
    }
}