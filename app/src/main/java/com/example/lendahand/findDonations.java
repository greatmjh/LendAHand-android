package com.example.lendahand;

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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.ArrayDeque;

public class findDonations extends AppCompatActivity {

    ArrayList<String> essentialSubcategories;
    ArrayList<String> nonEssentialSubcategories;

    ArrayList<String> visibleSubcategories;

    ArrayList<findDonationsItem> itemList = new ArrayList<>();; //TODO: receive available items from server
    ArrayList<findDonationsItem> filteredItemList = new ArrayList<>();  ;

    findDonationsSubcategoryAdapter adapter;
    findDonationsAdapter adapterItems;

    public void essentialsOnClick(View v)   {
        essentialList();
        visibleSubcategories = essentialSubcategories;
        adapter.updateData(visibleSubcategories);
    }

    public void nonEssentialsOnClick(View v) {
        nonEssentialList();
        visibleSubcategories = nonEssentialSubcategories;
        adapter.updateData(visibleSubcategories);
    }


    public void menuBtnClick(View v){
        previousView.setPrevView(findDonations.class);

        Intent intent = new Intent(this, menuActivity.class);
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
        adapter.setOnSubcategoryClickListener(this::updateMainItemList);

        recyclerView.setAdapter(adapter);
                //--------------------------

                //ITEM LIST RECYCLERVIEW
        RecyclerView itemRecyclerView = findViewById(R.id.findDonationsRecyclerView);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add sample data
        {
            itemList.add(new findDonationsItem("Baked Beans", "Food",3,5));
            itemList.add(new findDonationsItem("T-shirt", "Clothes",1,2));
            itemList.add(new findDonationsItem("Toothbrush", "Hygiene",3,5));
            itemList.add(new findDonationsItem("Chair", "Furniture",3,5));
            itemList.add(new findDonationsItem("Kettle", "Appliances",3,5));
        }

        adapterItems = new findDonationsAdapter(itemList);

        itemRecyclerView.setAdapter(adapterItems);
                //----------------------------

    }

    public void essentialList() {
        essentialSubcategories = new ArrayList<>();

        essentialSubcategories.add("Food");
        essentialSubcategories.add("Clothes");
        essentialSubcategories.add("Hygiene");
        essentialSubcategories.add("Furniture");
        essentialSubcategories.add("Appliances");
    }

    public void nonEssentialList()  {
        nonEssentialSubcategories = new ArrayList<>();

        nonEssentialSubcategories.add("Data");
        nonEssentialSubcategories.add("Airtime");
        nonEssentialSubcategories.add("Recreation + Sports");
        nonEssentialSubcategories.add("Decor");
        nonEssentialSubcategories.add("Electronics");
    }

    public void updateMainItemList(String subcategory){ //TODO: make the vert recyclerview display items that fit the selected categories
        filteredItemList.clear();

        for (findDonationsItem item:itemList){
            if (item.getSubcategory().equals(subcategory)){
                filteredItemList.add(item);
            }
        }

        adapterItems.updateData(filteredItemList);
    }


}