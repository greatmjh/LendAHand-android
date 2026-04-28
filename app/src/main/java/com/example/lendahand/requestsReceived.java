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

    public void menuBtnClick(View v){
        previousView.setPrevView(requestsReceived.class);

        Intent intent = new Intent(this, menuActivity.class);
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

        openReqs = new LinkedList<>();
        openReqs.add(new IncomingReqItem("Baked beans", "Greg Owen",
                "", "", true));
        openReqs.add(new IncomingReqItem( "R12 Airtime Voucher","Mark Gibbons",
                "", "", true));
        openReqs.add(new IncomingReqItem( "Shirt", "Dirk Schutte",
                "", "", true));

        fulfilledReqs = new LinkedList<>();
        fulfilledReqs.add(new IncomingReqItem( "Baked beans", "Greg Owen",
                "+27 83 123 8718", "", false));
        fulfilledReqs.add(new IncomingReqItem( "R12 Airtime Voucher","Mark Gibbons",
                "", "", false));
        fulfilledReqs.add(new IncomingReqItem( "Shirt", "Dirk Schutte",
                "+27 62 817 1281", "", false));

        Chip openChip = findViewById(R.id.chipOpen);
        if (openChip.isChecked()) {
            adapter = new requestsRecievedAdapter(openReqs);
        } else {
            adapter = new requestsRecievedAdapter(fulfilledReqs);
        }

        RecyclerView rv = findViewById(R.id.recyclerViewRequestsReceived);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}