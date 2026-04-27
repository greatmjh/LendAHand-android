package com.example.lendahand;

import android.content.ClipData;

import java.util.ArrayList;
import java.util.UUID;

public class ItemCategory {
    private UUID itemId;
    private String itemName;
    private ArrayList<ItemCategory> children;

    private ItemCategory(UUID itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
        children = new ArrayList<>();
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public ArrayList<ItemCategory> getChildren() {
        return children;
    }

    static ArrayList<ItemCategory> roots = null;

    public static ArrayList<ItemCategory> getRoots() {
        if (roots == null) {
            loadTreeFromServer();
        }
        return roots;
    }

    private static void loadTreeFromServer() {
        //TODO: actually load it from the server instead of hardcoding lol
        roots = new ArrayList<>();
        ItemCategory essential = new ItemCategory(UUID.randomUUID(), "Essential");
        ItemCategory food = new ItemCategory(UUID.randomUUID(), "Food");
        ItemCategory clothes = new ItemCategory(UUID.randomUUID(), "Clothes");
        essential.children.add(food);
        essential.children.add(clothes);
        ItemCategory nonessential = new ItemCategory(UUID.randomUUID(), "Non essential");
        ItemCategory mobileData = new ItemCategory(UUID.randomUUID(), "Mobile data");
        nonessential.children.add(mobileData);

        roots.add(essential);
        roots.add(nonessential);
    }
}
