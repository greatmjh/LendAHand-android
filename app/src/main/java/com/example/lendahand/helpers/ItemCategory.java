package com.example.lendahand.helpers;

import java.util.HashSet;
import java.util.UUID;

public class ItemCategory {
    private final UUID itemID;
    private final String itemName;
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
    public static void loadTreeFromServer(Runnable callback) {
        DataManager.getInstance(null).APILoadItemTree(new DataManager.ItemTreeCallback() {
            @Override
            public void onSuccess(ItemCategory[] response) {
                roots = response;
                callback.run();
            }
        });
    }

    public HashSet<UUID> getAllChildren() {
        HashSet<UUID> result = new HashSet<>();
        getAllChildrenRecurse(result);
        return result;
    }

    private void getAllChildrenRecurse(HashSet<UUID> result) {
        result.add(this.itemID);
        for (ItemCategory child: itemChildren) {
            child.getAllChildrenRecurse(result);
        }
    }


}
