package com.example.lendahand;

public class manageMyDonationsItem {

    private String itemName;
    private int numItemsAvailable;

    public manageMyDonationsItem(String itemName, int numItemsAvailable){
        this.itemName = itemName;
        this.numItemsAvailable = numItemsAvailable;
    }

    public String getItemName() { return itemName; }

    public int getNumItemsAvailable()  { return numItemsAvailable; }
}
