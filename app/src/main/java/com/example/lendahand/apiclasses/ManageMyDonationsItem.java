package com.example.lendahand.apiclasses;

import java.util.UUID;

public class ManageMyDonationsItem {

    private String itemDesc;
    private int qty;

    private UUID offerID, itemId;

    public ManageMyDonationsItem(String itemName, int qty, UUID offerID){
        this.itemDesc = itemName;
        this.qty = qty;
        this.offerID = offerID;
    }

    public String getItemDesc() { return itemDesc; }

    public int getQty()  { return qty; }

    public UUID getOfferID() { return offerID; }
}
