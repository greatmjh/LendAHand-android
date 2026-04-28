package com.example.lendahand;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HighlyRequestedItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_highly_requested_items);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewHighlyReq);
        ArrayList<HighlyRequestedItem> sampleData = new ArrayList<>();
        sampleData.add(new HighlyRequestedItem("Tinned Tuna", 18));
        sampleData.add(new HighlyRequestedItem("R12 Airtime voucher", 15));
        sampleData.add(new HighlyRequestedItem("Blanket", 10));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter
        HighlyRequestedItemsAdapter adapter = new HighlyRequestedItemsAdapter(sampleData);
        recyclerView.setAdapter(adapter);
    }
}