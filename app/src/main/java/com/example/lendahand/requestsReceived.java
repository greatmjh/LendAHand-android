package com.example.lendahand;

import android.app.Activity;
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

import com.google.android.material.chip.Chip;

import java.util.LinkedList;

public class requestsReceived extends AppCompatActivity {

    LinkedList<IncomingReqItem> openReqs, fulfilledReqs;

    requestsRecievedAdapter adapter;
    public void openOnClick(View v)  {
        adapter.setItemList(openReqs);
        adapter.notifyDataSetChanged();
    }

    public void fulfillOnClick(View v)  {
        adapter.setItemList(fulfilledReqs);
        adapter.notifyDataSetChanged();
    }


    public void menuBtnClick(View v){
        previousView.setPrevView(requestsReceived.class);

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
        setContentView(R.layout.activity_requests_received);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Activity parent = this;
        //Load data from backend
        DataManager.getInstance(this).APIGetRequestsReceived(new DataManager.RequestsReceivedCallback() {
            @Override
            public void onSuccess(LinkedList<IncomingReqItem> open, LinkedList<IncomingReqItem> fulfilled) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        openReqs = open;
                        fulfilledReqs = fulfilled;
                        Chip openChip = findViewById(R.id.chipOpen);
                        if (openChip.isChecked()) {
                            adapter = new requestsRecievedAdapter(openReqs);
                        } else {
                            adapter = new requestsRecievedAdapter(fulfilledReqs);
                        }

                        RecyclerView rv = findViewById(R.id.recyclerViewRequestsReceived);
                        rv.setLayoutManager(new LinearLayoutManager(parent));
                        rv.setAdapter(adapter);


                    }
                });
            }
        });
    }
}