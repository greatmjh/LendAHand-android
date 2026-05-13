package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class manageMyDonations extends AppCompatActivity {

    public void menuBtnClick(View v){
        previousView.setPrevView(manageMyDonations.class);

        Intent intent = new Intent(this, menuActivity.class);
        startActivity(intent);
    }

    public void viewProfileOnClick(View v){
        Intent intent = new Intent(this, viewProfile.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_my_donations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerViewManageDonations);
        DataManager.getInstance(this).APIGetMyDonations(new DataManager.ManageMyDonationsCallback() {
            @Override
            public void onSuccess(ArrayList<manageMyDonationsItem> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //set adapter
                        manageMyDonationsAdapter adapter = new manageMyDonationsAdapter(result);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    public void newDonationButtonClick(View v) {
        Intent intent = new Intent(this, makeDonation.class);
        startActivity(intent);
    }
    public void viewWhatPeopleNeedClick(View v) {
        startActivity(new Intent(this, HighlyRequestedItems.class));
    }
}