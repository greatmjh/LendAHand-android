package com.example.lendahand.apiclasses;

import java.util.UUID;

public class HighlyRequestedItem {
    final String itemTitle;
    final int quantity;
    final UUID itemId;

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
