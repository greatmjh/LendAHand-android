package com.example.lendahand;

public class findDonationsItem {
    String itemName, mainCategory, subcategory;
    int numUnits;
    double distance;


    public findDonationsItem(String itemName, String mainCategory, String subcategory, int numUnits, double distance){
        this.itemName = itemName;
        this.numUnits = numUnits;
        this.distance = distance;
        this.subcategory = subcategory;
        this.mainCategory = mainCategory;
    }

    public double getDistance() { return distance; }

    public String getItemName(){ return itemName; }

    public int getNumUnits(){ return numUnits; }

    public String getSubcategory() { return subcategory; }

    public String getMainCategory() { return mainCategory; }

}
