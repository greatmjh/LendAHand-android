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

    ArrayList<String> essentialSubcategories;
    ArrayList<String> nonEssentialSubcategories;

    ArrayList<String> visibleSubcategories;

    ArrayList<findDonationsItem> itemList = new ArrayList<>(); //TODO: receive available items from server
    ArrayList<findDonationsItem> filteredItemList = new ArrayList<>();

    findDonationsSubcategoryAdapter adapter;
    findDonationsAdapter adapterItems;

    public void essentialsOnClick(View v)   {
        essentialList();
        adapter.updateData(essentialSubcategories);
        //"deselect" subcategories
        adapter.resetPosition();
        //also reset item filter
        updateMainCategoryItemList("Essential");
    }

    public void nonEssentialsOnClick(View v) {
        nonEssentialList();
        adapter.updateData(nonEssentialSubcategories);
        //"deselect" subcategories
        adapter.resetPosition();
        //also reset item filter
        updateMainCategoryItemList("NonEssential");
    }


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

        essentialList();
        visibleSubcategories = essentialSubcategories;

        //SUBCATEGORIES RECYCLERVIEW
        RecyclerView recyclerView = findViewById(R.id.subcategoryRecyclerView);
        //make view horizontal
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );


        adapter = new findDonationsSubcategoryAdapter(visibleSubcategories);

        //set on click listener
        adapter.setOnSubcategoryClickListener(this::updateSubcategoryItemList);

        recyclerView.setAdapter(adapter);
        //--------------------------

        //ITEM LIST RECYCLERVIEW
        RecyclerView itemRecyclerView = findViewById(R.id.findDonationsRecyclerView);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add sample data
        {
            itemList.add(new findDonationsItem("Baked Beans", "Essential", "Food",3,5, UUID.randomUUID(), "Richard Klein"));
            itemList.add(new findDonationsItem("T-shirt", "Essential", "Clothes",1,2, UUID.randomUUID(), "Richard Klein"));
            itemList.add(new findDonationsItem("Toothbrush","Essential", "Hygiene",3,5, UUID.randomUUID(), "Richard Klein"));
            itemList.add(new findDonationsItem("Chair", "Essential", "Furniture",3,5, UUID.randomUUID(), "Richard Klein"));
            itemList.add(new findDonationsItem("Kettle","Essential", "Appliances",3,5, UUID.randomUUID(), "Richard Klein"));
            itemList.add(new findDonationsItem("Airtime Voucher","NonEssential", "Airtime",1,5, UUID.randomUUID(), "Richard Klein"));
        }

        adapterItems = new findDonationsAdapter(itemList);

        itemRecyclerView.setAdapter(adapterItems);
        //----------------------------

    }

    public void updateSubcategoryItemList(String subcategory){
        filteredItemList.clear();

        for (findDonationsItem item:itemList){
            if (item.getSubcategory().equals(subcategory)){
                filteredItemList.add(item);
            }
        }

        adapterItems.updateData(filteredItemList);
    }

    public void updateMainCategoryItemList(String mainCategory){
        filteredItemList.clear();

        for (findDonationsItem item:itemList){
            if (item.getMainCategory().equals(mainCategory)){
                filteredItemList.add(item);
            }
        }

        adapterItems.updateData(filteredItemList);
    }


}