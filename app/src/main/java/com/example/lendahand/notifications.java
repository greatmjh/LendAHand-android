package com.example.lendahand;

import android.os.Bundle;

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
        ArrayList<Notification> sampleNotifs = new ArrayList<>();
        sampleNotifs.add(new Notification(LocalDateTime.parse("2026-04-25T12:34:56"),
                "Request accepted",
                "Jenna Smith has accepted your request for Tinned tuna",
                "",
                false));

        sampleNotifs.add(new Notification(LocalDateTime.parse("2026-04-24T12:34:56"),
                "Request received",
                "Mark Gibbons would like 2 R12 airtime vouchers",
                "",
                true));

        sampleNotifs.add(new Notification(LocalDateTime.parse("2026-03-07T12:34:56"),
                "Request rejected",
                "Gavin Greef rejected your request for Blanket",
                "",
                true));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter adapter = new notificationAdapter(sampleNotifs);
        recyclerView.setAdapter(adapter);
    }
}