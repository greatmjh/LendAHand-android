package com.example.lendahand;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import java.util.List;
import java.util.UUID;

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


        //Load data from server
        DataManager.getInstance(this).APIGetNotifications(new DataManager.NotificationsCallback() {
            @Override
            public void onSuccess(List<notificationItem> items) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        notificationAdapter adapter = new notificationAdapter(items);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotifications);
        try {
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (NullPointerException ignored) {

        }

    }
}