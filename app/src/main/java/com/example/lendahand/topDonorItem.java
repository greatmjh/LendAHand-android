package com.example.lendahand;

public class topDonorItem {

    private String name;
    private int itemCount;

    public topDonorItem(String name, int itemCount){
        this.name = name;
        this.itemCount = itemCount;
    }

    public String getName(){ return name; }

    public int getItemCount() { return itemCount; }
}
