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
import java.util.Arrays;
import java.util.UUID;

public class findDonations extends AppCompatActivity {
    ArrayList<RVLevelItem> visibleLevels = new ArrayList<>();
    ItemCategory[] visibleSubcategoriesPerLvl;

    ArrayList<DonationOffer> itemList = new ArrayList<>(Arrays.asList(
            new DonationOffer(
                    UUID.fromString("99075aab-2948-43e1-af8d-a22fc7283ad0"),
                    UUID.fromString("3a5080c9-c4b5-41c1-9804-adb3767c44e0"),
                    "PS5",
                    "Mel Higgs",
                    15,
                    135.99797099070514
            ),

            new DonationOffer(
                    UUID.fromString("93b9b4c0-5bd9-4fdd-9ab0-7e00fe6ee8cb"),
                    UUID.fromString("6c673ea5-a028-447e-b17d-cd9c2f66c694"),
                    "Wagyu Beef",
                    "Mel Higgs",
                    14,
                    135.99797099070514
            ),

            new DonationOffer(
                    UUID.fromString("dbd2d22f-4cc9-4101-821f-947a6df45825"),
                    UUID.fromString("e88f6e97-eb9e-4184-b013-83af3bb3b983"),
                    "R1 million rand Vodacom airtime voucher ",
                    "Mel Higgs",
                    35,
                    135.99797099070514
            ),

            new DonationOffer(
                    UUID.fromString("4105c72c-1fdc-460a-8ccb-a1eea6fe21da"),
                    UUID.fromString("7ac906f3-bed7-4c8b-a17e-417a86bbefb7"),
                    "Designer soap",
                    "Mel Higgs",
                    5,
                    135.99797099070514
            ),

            new DonationOffer(
                    UUID.fromString("24b89a0d-3a70-4064-96e9-491bae22ba03"),
                    UUID.fromString("5f55cecc-9449-4697-b670-12881d4ce800"),
                    "Gold plated condoms ",
                    "Mel Higgs",
                    16,
                    135.99797099070514
            ),

            new DonationOffer(
                    UUID.fromString("f88fbe22-f4e0-44cd-b279-74f394ca3d3d"),
                    UUID.fromString("f8d42427-7bb1-4e8f-bd9c-ef5533442cb0"),
                    "Champagne ",
                    "Mel Higgs",
                    9,
                    135.99797099070514
            )
    ));
    ArrayList<DonationOffer> filteredItemList = new ArrayList<>();

    FindDonationsRVAdapter rvAdapter;

    findDonationsSubcategoryAdapter subcategoryAdapter;


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
            visibleLevels.add(new RVLevelItem());
            visibleLevels.get(0).initialSet();

            RecyclerView recyclerView = findViewById(R.id.allCategoriesRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            subcategoryAdapter = new findDonationsSubcategoryAdapter(visibleLevels.get(0).subcategories);

            rvAdapter = new FindDonationsRVAdapter(visibleLevels, this, this);

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

    public void setInnerRecyclerView(RecyclerView subcategory){
        subcategory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        );

        subcategory.setAdapter(subcategoryAdapter);
    }

}