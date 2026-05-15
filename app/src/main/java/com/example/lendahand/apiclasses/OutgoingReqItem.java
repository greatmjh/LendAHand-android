package com.example.lendahand.apiclasses;

import java.util.UUID;

public class OutgoingReqItem {
    private String itemName, donorName, donorPhoneNumber, state;
    private UUID requestID;
    int itemQty;

    public OutgoingReqItem(String itemName, String donorName, String phoneNumber, UUID requestID, String state) {
        this.itemName = itemName;
        this.donorName = donorName;
        this.donorPhoneNumber = phoneNumber;
        this.requestID = requestID;
        this.state = state;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSubtext() {
        switch (state) {
            case "open":
                return String.format("Requested to %s", donorName);
            case "accepted":
                return String.format("Accepted by %s (%s)", donorName, donorPhoneNumber);
            case "rejected":
                return String.format("Rejected by %s", donorName);
            default:
                return "";
        }
    }

    public boolean isOpen() {
        return state.equals("open");
    }

    public UUID getRequestID() { return requestID; }
}
