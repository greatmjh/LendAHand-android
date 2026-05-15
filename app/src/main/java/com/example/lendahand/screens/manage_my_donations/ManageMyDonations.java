package com.example.lendahand.screens.manage_my_donations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.R;
import com.example.lendahand.apiclasses.ManageMyDonationsItem;
import com.example.lendahand.screens.MenuActivity;
import com.example.lendahand.helpers.PreviousView;
import com.example.lendahand.screens.highly_requested_items.HighlyRequestedItems;
import com.example.lendahand.screens.ViewProfile;

import java.util.ArrayList;

public class ManageMyDonations extends AppCompatActivity {

    public void menuBtnClick(View v){
        PreviousView.setPrevView(ManageMyDonations.class);

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void viewProfileOnClick(View v){
        Intent intent = new Intent(this, ViewProfile.class);
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
        TextView statusTV = findViewById(R.id.manageMyDonationsStatus);
        DataManager.getInstance(this).APIGetMyDonations(new DataManager.ManageMyDonationsCallback() {
            @Override
            public void onSuccess(ArrayList<ManageMyDonationsItem> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //set adapter
                        ManageMyDonationsAdapter adapter = new ManageMyDonationsAdapter(result);
                        recyclerView.setAdapter(adapter);
                        if (result.isEmpty()) {
                            statusTV.setText("You don't have any items up for donation.");
                        } else {
                            statusTV.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }

    public void newDonationButtonClick(View v) {
        Intent intent = new Intent(this, MakeDonation.class);
        startActivity(intent);
    }
    public void viewWhatPeopleNeedClick(View v) {
        startActivity(new Intent(this, HighlyRequestedItems.class));
    }
}