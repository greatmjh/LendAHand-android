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

import java.util.ArrayList;
import java.util.LinkedList;

public class manageRequests extends AppCompatActivity {

    LinkedList<OutgoingReq> openReqs, closedReqs;
    ManageRequestAdapter adapter;
    public void menuBtnClick(View v){
        previousView.setPrevView(manageRequests.class);

        Intent intent = new Intent(this, menuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        openReqs = new LinkedList<>();
        openReqs.add(new OutgoingReqItem("Baked beans", "Greg Owen", "", "", "open"));
        openReqs.add(new OutgoingReqItem( "R12 Airtime Voucher","Mark Gibbons",  "", "", "open"));
        openReqs.add(new OutgoingReqItem( "Shirt", "Dirk Schutte","", "", "open"));

        closedReqs = new LinkedList<>();
        closedReqs.add(new OutgoingReqItem( "Baked beans", "Greg Owen","+27 83 123 8718", "", "accepted"));
        closedReqs.add(new OutgoingReqItem( "R12 Airtime Voucher","Mark Gibbons", "", "", "rejected"));
        closedReqs.add(new OutgoingReqItem( "Shirt", "Dirk Schutte","+27 62 817 1281", "", "accepted"));

        Chip openChip = findViewById(R.id.chipOpen);
        if (openChip.isChecked()) {
            adapter = new ManageRequestAdapter(openReqs);
        } else {
            adapter = new ManageRequestAdapter(closedReqs);
        }

        RecyclerView rv = findViewById(R.id.recyclerViewManageRequests);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onOpenClick(View v) {
        adapter.setItemList(openReqs);
        adapter.notifyDataSetChanged();
    }

    public void onCloseClick(View v) {
        adapter.setItemList(closedReqs);
        adapter.notifyDataSetChanged();
    }
}