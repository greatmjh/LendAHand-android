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
import java.util.UUID;

public class findDonations extends AppCompatActivity {
    ArrayList<RVLevelItem> visibleSubcategories; //takes in parentIds

    ArrayList<findDonationsItem> itemList = new ArrayList<>(); //TODO: receive available items from server
    ArrayList<findDonationsItem> filteredItemList = new ArrayList<>();

    findDonationsSubcategoryAdapter adapter;
    findDonationsAdapter itemsAdapter;

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

        visibleSubcategories.add(new RVLevelItem(UUID.fromString("20eca463-47f1-401c-b978-bfd2eb68548c"))); //essentials
        visibleSubcategories.add(new RVLevelItem(UUID.fromString("b41df440-fb36-4936-b289-caca4965762a"))); //non-essentials

        RecyclerView recyclerView = findViewById(R.id.allCategoriesRecyclerView);

        rvAdapter = new FindDonationsRVAdapter(visibleSubcategories, this);

        //set on click listener
        rvAdapter.setOnSubcategoryClickListener(this::updateAllCategoriesRV);

        recyclerView.setAdapter(adapter);
        //--------------------------

        //ITEM LIST RECYCLERVIEW
        {
            RecyclerView itemRecyclerView = findViewById(R.id.findDonationsRecyclerView);
            itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            //add sample data
            {
                itemList.add(new findDonationsItem("Baked Beans", "Essential", "Food", 3, 5, UUID.randomUUID(), "Richard Klein"));
                itemList.add(new findDonationsItem("T-shirt", "Essential", "Clothes", 1, 2, UUID.randomUUID(), "Richard Klein"));
                itemList.add(new findDonationsItem("Toothbrush", "Essential", "Hygiene", 3, 5, UUID.randomUUID(), "Richard Klein"));
                itemList.add(new findDonationsItem("Chair", "Essential", "Furniture", 3, 5, UUID.randomUUID(), "Richard Klein"));
                itemList.add(new findDonationsItem("Kettle", "Essential", "Appliances", 3, 5, UUID.randomUUID(), "Richard Klein"));
                itemList.add(new findDonationsItem("Airtime Voucher", "NonEssential", "Airtime", 1, 5, UUID.randomUUID(), "Richard Klein"));
            }

            itemsAdapter = new findDonationsAdapter(itemList);

            itemRecyclerView.setAdapter(itemsAdapter);
        }

    }

    public void updateAllCategoriesRV(RVLevelItem subcategory){
        //TODO: make this do something effective?
    }

//    public void updateMainCategoryItemList(String mainCategory){
//        filteredItemList.clear();
//
//        for (findDonationsItem item:itemList){
//            if (item.getMainCategory().equals(mainCategory)){
//                filteredItemList.add(item);
//            }
//        }
//
//        adapterItems.updateData(filteredItemList);
//    }
//    public void updateSubcategoryItemList(String subcategory){
//        filteredItemList.clear();
//
//        for (findDonationsItem item:itemList){
//            if (item.getSubcategory().equals(subcategory)){
//                filteredItemList.add(item);
//            }
//        }
//
//        adapterItems.updateData(filteredItemList);
//    }

}