package com.example.lendahand;

import java.util.UUID;

public class HighlyRequestedItem {
    String itemTitle;
    int quantity;
    UUID itemId;

    public HighlyRequestedItem(String itemName, int qtyNeeded, UUID itemId) {
        this.itemTitle = itemName;
        this.quantity = qtyNeeded;
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemTitle;
    }

    public int getQtyNeeded() {
        return quantity;
    }

    public UUID getItemId() { return itemId; }
}
