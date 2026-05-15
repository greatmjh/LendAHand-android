package com.example.lendahand.apiclasses;

public class TopDonorItem {

    private final String name;
    private final int itemCount;

    public TopDonorItem(String name, int itemCount){
        this.name = name;
        this.itemCount = itemCount;
    }

    public String getName(){ return name; }

    public int getItemCount() { return itemCount; }
}
