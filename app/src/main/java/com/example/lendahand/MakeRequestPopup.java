package com.example.lendahand;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.UUID;

public class MakeRequestPopup extends AppCompatActivity {
    String donorName;
    int qtyRequested = 1;
    int qtyMaximum;

    UUID offerID;
    String itemName;

    double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_request_popup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Initialise members
        donorName = getIntent().getStringExtra("donorName");
        offerID = UUID.fromString(getIntent().getStringExtra("offerID"));
        itemName = getIntent().getStringExtra("itemName");
        distance = getIntent().getDoubleExtra("distance", 0);
        qtyMaximum = getIntent().getIntExtra("qtyMaximum", 0);

        //Fill in textviews
        TextView textViewTitle = findViewById(R.id.make_request_popup_title);
        TextView textViewDistance = findViewById(R.id.make_request_distance);
        TextView textViewNameAndQuantity = findViewById(R.id.make_request_item_qty);

        textViewTitle.setText(String.format("Request to %s", donorName));
        textViewDistance.setText(String.format("%.1fkm away", distance));
        textViewNameAndQuantity.setText(String.format("Requesting %d of %s", qtyRequested, itemName));
    }

    public void onPlusClick(View v) {
        if (qtyRequested < qtyMaximum) {
            qtyRequested ++;
            TextView textViewNameAndQuantity = findViewById(R.id.make_request_item_qty);
            textViewNameAndQuantity.setText(String.format("Requesting %d of %s", qtyRequested, itemName));
        } else {
            Toast.makeText(this, String.format("There are only %d item%s available", qtyMaximum, ((qtyMaximum != 1) ? "s" : "")), Toast.LENGTH_SHORT).show();
        }
    }

    public void onMinusClick(View v) {
        if (qtyRequested > 1) {
            qtyRequested --;
            TextView textViewNameAndQuantity = findViewById(R.id.make_request_item_qty);
            textViewNameAndQuantity.setText(String.format("Requesting %d of %s", qtyRequested, itemName));
        }
    }

    public void onOutsideClick(View v) {
        finish();
    }

    public void onRequestClick(View v) {
        DataManager.getInstance(this).APIRespondToOffer(offerID, qtyRequested, new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });

    }
}