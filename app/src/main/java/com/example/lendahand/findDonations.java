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

import com.example.lendahand.apiclasses.DonationOffer;

import java.util.ArrayList;
import java.util.UUID;

public class findDonations extends AppCompatActivity {
    ArrayList<RVLevelItem> visibleSubcategories; //takes in parentIds

    ArrayList<DonationOffer> itemList = new ArrayList<>(); //TODO: receive available items from server
    ArrayList<DonationOffer> filteredItemList = new ArrayList<>();

    FindDonationsRVAdapter rvAdapter;


    public void menuBtnClick(View v){
        previousView.setPrevView(findDonations.class);

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
        setContentView(R.layout.activity_find_donations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ///MAIN TREE ITEM RECYCLERVIEW
        {
            visibleSubcategories.add(new RVLevelItem());

            RecyclerView recyclerView = findViewById(R.id.allCategoriesRecyclerView);

            rvAdapter = new FindDonationsRVAdapter(visibleSubcategories, this);

            //set on click listener
            rvAdapter.setOnSubcategoryClickListener(this::updateAllCategoriesRV);

            recyclerView.setAdapter(rvAdapter);
        }

        //ITEM LIST RECYCLERVIEW
        {
            RecyclerView itemRecyclerView = findViewById(R.id.findDonationsRecyclerView);
            itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            //add sample data
            {

            }

            //TODO: make + set adapter
        }

    }

    public void updateAllCategoriesRV(RVLevelItem subcategory){
        //TODO: make this do something effective?
    }

}