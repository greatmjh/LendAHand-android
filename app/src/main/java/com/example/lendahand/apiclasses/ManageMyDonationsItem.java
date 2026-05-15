package com.example.lendahand.apiclasses;

import java.util.UUID;

public class ManageMyDonationsItem {

    private final String itemDesc;
    private final int qty;

    private UUID itemId;
    private final UUID offerID;

    public ManageMyDonationsItem(String itemName, int qty, UUID offerID){
        this.itemDesc = itemName;
        this.qty = qty;
        this.offerID = offerID;
    }

    public String getItemDesc() { return itemDesc; }

    public int getQty()  { return qty; }

    public UUID getOfferID() { return offerID; }
}
