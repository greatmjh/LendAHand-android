package com.example.lendahand.screens.notifications;

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
import com.example.lendahand.apiclasses.NotificationItem;
import com.example.lendahand.helpers.PreviousView;
import com.example.lendahand.screens.MenuActivity;
import com.example.lendahand.screens.ViewProfile;

import java.util.List;

public class Notifications extends AppCompatActivity {

    public void menuBtnClick(View v){
        PreviousView.setPrevView(Notifications.class);

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
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotifications);
        TextView statusTV = findViewById(R.id.notificationsStatus);

        //Load data from server
        DataManager.getInstance(this).APIGetNotifications(new DataManager.NotificationsCallback() {
            @Override
            public void onSuccess(List<NotificationItem> items) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (items.size() > 0) {
                            NotificationAdapter adapter = new NotificationAdapter(items);
                            recyclerView.setAdapter(adapter);
                            statusTV.setVisibility(View.GONE);
                        } else {
                            statusTV.setText("You have not received any notifications yet.");
                        }

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