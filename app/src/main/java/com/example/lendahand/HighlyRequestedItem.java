package com.example.lendahand;

public class HighlyRequestedItem {
    String itemName;
    int qtyNeeded;

    public HighlyRequestedItem(String itemName, int qtyNeeded) {
        this.itemName = itemName;
        this.qtyNeeded = qtyNeeded;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQtyNeeded() {
        return qtyNeeded;
    }
}
