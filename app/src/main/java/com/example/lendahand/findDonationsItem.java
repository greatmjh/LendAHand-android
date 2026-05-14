package com.example.lendahand;

import java.util.UUID;

public class findDonationsItem {
    String itemName, mainCategory, subcategory, donorName;
    int numUnits;
    double distance;

    UUID offerID;

    public findDonationsItem(String itemName, String mainCategory, String subcategory, int numUnits, double distance, UUID offerID, String donorName){
        this.itemName = itemName;
        this.numUnits = numUnits;
        this.distance = distance;
        this.subcategory = subcategory;
        this.mainCategory = mainCategory;
        this.offerID = offerID;
        this.donorName = donorName;
    }

    public double getDistance() { return distance; }

    public String getItemName(){ return itemName; }

    public int getNumUnits(){ return numUnits; }

    public String getSubcategory() { return subcategory; }

    public String getMainCategory() { return mainCategory; }

    public UUID getOfferID() { return offerID; }

    public String getDonorName() { return donorName; }

}
