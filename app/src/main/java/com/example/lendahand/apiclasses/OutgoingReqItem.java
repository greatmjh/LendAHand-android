package com.example.lendahand.apiclasses;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.UUID;

public class OutgoingReqItem {
    private final String itemName;
    private final String donorName;
    private final String donorPhoneNumber;
    private final String state;
    private final UUID requestID;
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
                return String.format("Accepted by %s (%s)", donorName, getFormattedPhone());
            case "rejected":
                return String.format("Rejected by %s", donorName);
            default:
                return "";
        }
    }

    public String getFormattedPhone() {
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber parsed = phoneUtil.parse(donorPhoneNumber, null);
            return phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            e.printStackTrace();
            return donorPhoneNumber;
        }
    }

    public boolean isOpen() {
        return state.equals("open");
    }

    public UUID getRequestID() { return requestID; }
}
