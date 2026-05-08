package com.example.lendahand;

import java.util.UUID;

public class ItemCategory {
    private UUID itemID;
    private String itemName;
    private ItemCategory[] itemChildren;

    private ItemCategory(UUID itemID, String itemName) {
        this.itemID = itemID;
        this.itemName = itemName;
    }

    public UUID getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public ItemCategory[] getItemChildren() {
        return itemChildren;
    }

    static ItemCategory[] roots = null;

    public static ItemCategory[] getRoots() {
        if (roots == null) {
            loadTreeFromServer();
        }
        return roots;
    }

    private static void loadTreeFromServer() {
        DataManager.getInstance(null).APILoadItemTree(new DataManager.ItemTreeCallback() {
            @Override
            public void onSuccess(ItemCategory[] response) {
                roots = response;
            }
        });
    }


}
