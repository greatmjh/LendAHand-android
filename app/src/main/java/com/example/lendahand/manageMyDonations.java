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

import java.util.ArrayList;

public class manageMyDonations extends AppCompatActivity {

    public void menuBtnClick(View v){
        previousView.setPrevView(manageMyDonations.class);

        Intent intent = new Intent(this, menuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_my_donations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerViewManageDonations);
        ArrayList<manageMyDonationsItem> itemList = new ArrayList<>();

        //sample data
        itemList.add(new manageMyDonationsItem("Baked beans", 3));
        itemList.add(new manageMyDonationsItem("Shirt", 1));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter
        manageMyDonationsAdapter adapter = new manageMyDonationsAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }
}