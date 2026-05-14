package com.example.lendahand;

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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HighlyRequestedItems extends AppCompatActivity {

    public void goBackOnClick(View v)  { finish(); }

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
        TextView statusTV = findViewById(R.id.highlyRequestedItemsStatus);
        DataManager.getInstance(this).APIGetAllGeneralRequests(new DataManager.GenRequestCallback() {
            @Override
            public void onSuccess(ArrayList<HighlyRequestedItem> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //set adapter
                        HighlyRequestedItemsAdapter adapter = new HighlyRequestedItemsAdapter(result);
                        recyclerView.setAdapter(adapter);
                        if (result.isEmpty()) {
                            statusTV.setText("There are no highly requested items");
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            statusTV.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

}