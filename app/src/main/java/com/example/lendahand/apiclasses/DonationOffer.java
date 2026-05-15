package com.example.lendahand.apiclasses;

import java.util.UUID;

public class DonationOffer {
    final UUID offerID;
    final UUID itemID;
    final String itemName;
    final String donorName;
    final int qty;
    final double distanceKm;

    public DonationOffer(UUID offerID, UUID itemID, String itemName, String donorName, int qty, double distanceKm) {
        this.offerID = offerID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.donorName = donorName;
        this.qty = qty;
        this.distanceKm = distanceKm;
    }

    public UUID getOfferID() {
        return offerID;
    }

    public UUID getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDonorName() {
        return donorName;
    }

    public int getQty() {
        return qty;
    }

    public double getDistanceKm() {
        return distanceKm;
    }
}
