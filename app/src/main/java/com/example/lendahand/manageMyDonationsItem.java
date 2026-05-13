package com.example.lendahand;

import java.util.UUID;

public class manageMyDonationsItem {

    private String itemDesc;
    private int qty;

    private UUID offerID, itemId;

    public manageMyDonationsItem(String itemName, int qty, UUID offerID){
        this.itemDesc = itemName;
        this.qty = qty;
        this.offerID = offerID;
    }

    public String getItemDesc() { return itemDesc; }

    public int getQty()  { return qty; }

    public UUID getOfferID() { return offerID; }
}
