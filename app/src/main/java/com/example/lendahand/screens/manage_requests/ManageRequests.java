package com.example.lendahand.screens.manage_requests;

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
import com.example.lendahand.apiclasses.OutgoingReqItem;
import com.example.lendahand.R;
import com.example.lendahand.screens.MenuActivity;
import com.example.lendahand.helpers.PreviousView;
import com.example.lendahand.screens.make_gen_requests.MakeGenRequest;
import com.example.lendahand.screens.ViewProfile;
import com.google.android.material.chip.Chip;

import java.util.LinkedList;

public class ManageRequests extends AppCompatActivity {

    LinkedList<OutgoingReqItem> openReqs, closedReqs;
    ManageRequestAdapter adapter;
    public void menuBtnClick(View v){
        PreviousView.setPrevView(ManageRequests.class);

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void makeGenRequestButton(View v){
        Intent intent = new Intent(this, MakeGenRequest.class);
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
        setContentView(R.layout.activity_manage_requests);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rv = findViewById(R.id.recyclerViewManageRequests);
        TextView statusTV = findViewById(R.id.manageRequestsStatus);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DataManager.getInstance(this).APIGetOutgoingRequests(new DataManager.OutgoingRequestsCallback() {
            @Override
            public void onSuccess(LinkedList<OutgoingReqItem> open, LinkedList<OutgoingReqItem> closed) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        openReqs = open;
                        closedReqs = closed;

                        Chip openChip = findViewById(R.id.chipOpen);
                        if (openChip.isChecked()) {
                            adapter = new ManageRequestAdapter(openReqs);
                            if (open.isEmpty()) {
                                statusTV.setText("You do not have any open requests.");
                            } else {
                                statusTV.setVisibility(View.GONE);
                            }
                        } else {
                            adapter = new ManageRequestAdapter(closedReqs);
                            if (closed.isEmpty()) {
                                statusTV.setText("You do not have any closed requests.");
                            } else {
                                statusTV.setVisibility(View.GONE);
                            }
                        }


                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }

    public void onOpenClick(View v) {
        adapter.setItemList(openReqs);
        adapter.notifyDataSetChanged();
        TextView statusTV = findViewById(R.id.manageRequestsStatus);
        if (openReqs.isEmpty()) {
            statusTV.setVisibility(View.VISIBLE);
            statusTV.setText("You do not have any open requests.");
        } else {
            statusTV.setVisibility(View.GONE);
        }
    }

    public void onCloseClick(View v) {
        adapter.setItemList(closedReqs);
        adapter.notifyDataSetChanged();
        TextView statusTV = findViewById(R.id.manageRequestsStatus);
        if (closedReqs.isEmpty()) {
            statusTV.setVisibility(View.VISIBLE);
            statusTV.setText("You do not have any closed requests.");
        } else {
            statusTV.setVisibility(View.GONE);
        }
    }
}