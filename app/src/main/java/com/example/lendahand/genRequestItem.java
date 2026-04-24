package com.example.lendahand;

public class genRequestItem {
    private String itemName;
    private int image = R.drawable.delete;

    public genRequestItem(String itemName){
        this.itemName = itemName;
    }

    public String getItemName(){
        return itemName;
    }

    public int getImage(){
        return image;
    }
}
