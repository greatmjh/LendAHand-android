package com.example.lendahand;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lendahand.apiclasses.DonationOffer;
import com.example.lendahand.apiclasses.ProfileInfo;
import com.example.lendahand.databinding.FindDonationsNestedRvBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class findDonations extends AppCompatActivity {
    ArrayList<RVLevelItem> visibleLevels = new ArrayList<>();
    ItemCategory[] visibleSubcategoriesPerLvl;

    ArrayList<DonationOffer> itemList = new ArrayList<>();
    ArrayList<DonationOffer> filteredItemList = new ArrayList<>();

    FindDonationsRVAdapter rvAdapter;

    ArrayList<findDonationsSubcategoryAdapter> subcategoryAdapters = new ArrayList<>();
    FindDonationsItemAdapter itemAdapter;


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
        loadDataFromServer();

    }

    public void loadDataFromServer() {
        TextView statusTV = findViewById(R.id.findDonationsStatus);
        Activity parent = this;

        //Load item categories
        ItemCategory.loadTreeFromServer(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Check location permission
                        if (!requestLocationPermission()) {
                            //don't have location permission
                            statusTV.setText("Please enable location permission.");
                            return; //when the user clicks the button next we should have permission if they said yes
                        }

                        //Get location
                        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(parent);
                        try {
                            fusedLocationClient.getLastLocation().addOnSuccessListener(parent, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    //Get item list from server
                                    DataManager.getInstance(parent).APILoadDonationOffers(location.getLatitude(), location.getLongitude(), new DataManager.DonationOffersCallback() {
                                        @Override
                                        public void onSuccess(ArrayList<DonationOffer> result) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    statusTV.setVisibility(View.GONE);
                                                    itemList = result;
                                                    loadRecyclerViews();
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            statusTV.setText("Please enable location permission.");
                        }
                    }
                });

            }
        });
    }

    public void loadRecyclerViews() {
        ///MAIN TREE ITEM RECYCLERVIEW
        {
            visibleLevels.add(new RVLevelItem());
            visibleLevels.get(0).initialSet();

            RecyclerView recyclerView = findViewById(R.id.allCategoriesRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            subcategoryAdapters.add(new findDonationsSubcategoryAdapter(visibleLevels.get(0).subcategories, 0));

            rvAdapter = new FindDonationsRVAdapter(visibleLevels, this, this);

            //set on click listener
            //rvAdapter.setOnSubcategoryClickListener(this::updateAllCategoriesRV);

            recyclerView.setAdapter(rvAdapter);
        }


        //ITEM LIST RECYCLERVIEW
        {
            RecyclerView itemRecyclerView = findViewById(R.id.findDonationsRecyclerView);
            itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            itemAdapter = new FindDonationsItemAdapter(itemList, this);
            itemRecyclerView.setAdapter(itemAdapter);
        }
    }

    public void updateAllCategoriesRV(ItemCategory subcategory, int parentPosition){
        //TODO: make this do something effective?
        if (subcategory.getItemChildren().length > 0){
            RVLevelItem toAdd = new RVLevelItem();
            toAdd.setSubcategories(subcategory.getItemChildren());
            visibleLevels.subList(parentPosition+1, visibleLevels.size()).clear();
            visibleLevels.add(toAdd);

            rvAdapter.updateData(visibleLevels);
            findDonationsSubcategoryAdapter newSCA =  new findDonationsSubcategoryAdapter(subcategory.getItemChildren(), parentPosition + 1);
            subcategoryAdapters.subList(parentPosition+1, subcategoryAdapters.size()).clear();
            subcategoryAdapters.add(newSCA);
        }
        filterItems(subcategory);
        itemAdapter.updateData(filteredItemList);
    }

    public void setInnerRecyclerView(RecyclerView subcategory, int position){
        subcategory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        );
        subcategoryAdapters.get(position).setOnSubcategoryClickListener(this::updateAllCategoriesRV);
        subcategory.setAdapter(subcategoryAdapters.get(position));
    }

    public void filterItems(ItemCategory parentCategory) {
        HashSet<UUID> childrenIds = parentCategory.getAllChildren();
        filteredItemList = new ArrayList<>();
        for (DonationOffer item: itemList) {
            if (childrenIds.contains(item.getItemID())) {
                filteredItemList.add(item);
            }
        }
    }

    private boolean requestLocationPermission() {
        // Check if permissions are already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already granted, so return true
            return true;
        } else {
            // Request Coarse location (Recommended for Android 12+)
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    100);
            return false;
        }
    }

}