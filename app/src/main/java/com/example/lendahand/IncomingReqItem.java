package com.example.lendahand;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

public class IncomingReqItem {
    private String itemName, donorName, phoneNumber, requestID;
    double distAway;
    boolean state;

    public IncomingReqItem(String itemName, String donorName, String phoneNumber, String requestID, double distAway, boolean state) {
        this.itemName = itemName;
        this.donorName = donorName;
        this.phoneNumber = phoneNumber;
        this.requestID = requestID;
        this.state = state;
        this.distAway = distAway;
    }

    public String getItemName() {
        return itemName;
    }

    public SpannableString getSubtext() {
        String init = String.format("Requested by %s, %f km away", donorName, distAway);
        SpannableString spannable = new SpannableString(init);
        spannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                13,
                (13+donorName.length()),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return spannable;
    }

    public boolean isOpen() {
        return state;
    }
}
