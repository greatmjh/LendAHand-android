package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menuActivity extends AppCompatActivity {

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
        //TODO: save prev view before moving onto this page, then when you click back it takes you there
        //openScreen(prevView);
    }

    public void menuClick(View view){
        int id = view.getId();

        if (id == R.id.notificationMenu){
            openScreen(notifications.class);
        }else if (id  ==  R.id.reqReceivedMenu) {
            openScreen(requestsReceived.class);
        }else if (id  ==  R.id.manageRequestsMenu) {
            openScreen(manageRequests.class);
        }else if (id  ==  R.id.manageDonationsMenu) {
            openScreen(manageMyDonations.class);
        }else if (id  ==  R.id.findDonationsMenu) {
            //TODO: make find donations page and link here
        }else if (id  ==  R.id.topDonorsMenu) {
            openScreen(topDonors.class);
        }else if (id  ==  R.id.logOutMenu) {
            //TODO: log user out
            openScreen(MainActivity.class);
        }
    }

}
