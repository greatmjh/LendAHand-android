package com.example.lendahand.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.helpers.ItemCategory;
import com.example.lendahand.screens.top_donors.TopDonors;

public class AppLauncher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager dataManager = DataManager.getInstance(this);
        if (dataManager.isLoggedIn()) {
            DataManager.getInstance(this); //make sure data manager is up
            ItemCategory.getRoots(); // load these from server so we have them when necessary
            startActivity(new Intent(this, TopDonors.class));
        } else {
            startActivity(new Intent(this, WelcomePage.class));
        }

        finish();
    }
}