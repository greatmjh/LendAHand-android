package com.example.lendahand;

public class findDonationsItem {
    String itemName, subcategory;
    int numUnits;
    double distance;

    public findDonationsItem(String itemName, String subcategory, int numUnits, double distance){
        this.itemName = itemName;
        this.numUnits = numUnits;
        this.distance = distance;
        this.subcategory = subcategory;
    }

    public double getDistance() { return distance; }

    public String getItemName(){ return itemName; }

    public int getNumUnits(){ return numUnits; }

    public String getSubcategory() { return subcategory; }

}
