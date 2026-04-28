package com.example.lendahand;

public class OutgoingReqItem {
    private String itemName, donorName, phoneNumber, requestID, state;

    public OutgoingReqItem(String itemName, String donorName, String phoneNumber, String requestID, String state) {
        this.itemName = itemName;
        this.donorName = donorName;
        this.phoneNumber = phoneNumber;
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
                return String.format("Accepted by %s (%s)", donorName, phoneNumber);
            case "rejected":
                return String.format("Rejected by %s", donorName);
            default:
                return "";
        }
    }

    public boolean isOpen() {
        return state.equals("open");
    }
}
