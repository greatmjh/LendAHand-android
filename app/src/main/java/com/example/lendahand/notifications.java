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

import java.time.LocalDateTime;
import java.util.ArrayList;

public class notifications extends AppCompatActivity {

    public void menuBtnClick(View v){
        previousView.setPrevView(notifications.class);

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
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotifications);

        //Populate with test data
        ArrayList<notificationItem> sampleNotifs = new ArrayList<>();
        sampleNotifs.add(new notificationItem(LocalDateTime.parse("2026-04-25T12:34:56"),
                "Request accepted",
                "Jenna Smith has accepted your request for Tinned tuna",
                "outgoingRequests",
                false));

        sampleNotifs.add(new notificationItem(LocalDateTime.parse("2026-04-24T12:34:56"),
                "Request received",
                "Mark Gibbons would like 2 R12 airtime vouchers",
                "incomingRequests",
                true));

        sampleNotifs.add(new notificationItem(LocalDateTime.parse("2026-03-07T12:34:56"),
                "Request rejected",
                "Gavin Greef rejected your request for Blanket",
                "incomingRequests",
                true));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter adapter = new notificationAdapter(sampleNotifs);
        recyclerView.setAdapter(adapter);
    }


}