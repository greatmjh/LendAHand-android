package com.example.lendahand.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lendahand.helpers.DataManager;
import com.example.lendahand.R;
import com.example.lendahand.screens.notifications.Notifications;
import com.example.lendahand.screens.requests_received.RequestsReceived;
import com.example.lendahand.screens.find_donations.FindDonations;
import com.example.lendahand.screens.highly_requested_items.HighlyRequestedItems;
import com.example.lendahand.screens.make_gen_requests.MakeGenRequest;
import com.example.lendahand.screens.manage_my_donations.ManageMyDonations;
import com.example.lendahand.screens.manage_requests.ManageRequests;
import com.example.lendahand.screens.top_donors.TopDonors;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void openScreen(Class<?> screen){
        Intent intent = new Intent(this, screen);
        startActivity(intent);
    }

    public void backOnClick(View view){
        finish();
    }

    public void menuClick(View view){
        int id = view.getId();

        if (id == R.id.notificationMenu){
            openScreen(Notifications.class);
        }else if (id  ==  R.id.reqReceivedMenu) {
            openScreen(RequestsReceived.class);
        }else if (id  ==  R.id.manageRequestsMenu) {
            openScreen(ManageRequests.class);
        }else if (id  ==  R.id.manageDonationsMenu) {
            openScreen(ManageMyDonations.class);
        }else if (id  ==  R.id.findDonationsMenu) {
            openScreen(FindDonations.class);
        }else if (id  ==  R.id.topDonorsMenu) {
            openScreen(TopDonors.class);
        } else if (id  ==  R.id.logOutMenu) {
            DataManager.getInstance(this).logOut();
        } else if (id == R.id.highlyRequestedItemsMenu) {
            openScreen(HighlyRequestedItems.class);
        } else if (id == R.id.manageUniversalRequestsMenu) {
            openScreen(MakeGenRequest.class);
        }
    }

}
