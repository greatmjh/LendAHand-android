package com.example.lendahand;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;

public class IncomingReqItem implements Parcelable {
    private String itemName, requesterName, requesterPhoneNumber, requestID, requesterBio;
    double requesterDistanceKm;
    boolean fulfilled;

    int itemQty;

    public IncomingReqItem(String itemName, int itemQty, String doneeName, String requesterPhoneNumber, String doneeBio, String requestID, double distAway, boolean open) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.requesterName = doneeName;
        this.requesterPhoneNumber = requesterPhoneNumber;
        this.requesterBio = doneeBio;
        this.requestID = requestID;
        this.fulfilled = !open;
        this.requesterDistanceKm = distAway;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDoneeName() { return requesterName; }

    public String getDoneeBio() { return requesterBio; }

    public double getDoneeDistance() { return requesterDistanceKm; }

    public String getDoneePhone() { return requesterPhoneNumber; }

    public int getItemQty() { return itemQty; }


    public SpannableString getSubtext() {
        String init = String.format("Requested by %s, %.1f km away", requesterName, requesterDistanceKm);
        SpannableString spannable = new SpannableString(init);
        spannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                13,
                (13+ requesterName.length()),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return spannable;
    }

    public boolean isOpen() {
        return !fulfilled;
    }

    // to allow this to be passed between activities:

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(requesterName);
        dest.writeString(requesterPhoneNumber);
        dest.writeString(requestID);
        dest.writeDouble(requesterDistanceKm);
        dest.writeString(requesterBio);
        dest.writeByte((byte) (fulfilled ? 1 : 0));
    }

    protected IncomingReqItem(Parcel in) {
        itemName = in.readString();
        requesterName = in.readString();
        requesterPhoneNumber = in.readString();
        requestID = in.readString();
        requesterDistanceKm = in.readDouble();
        requesterBio = in.readString();
        fulfilled = in.readByte() != 0;
    }

    public static final Creator<IncomingReqItem> CREATOR = new Creator<IncomingReqItem>() {
        @Override
        public IncomingReqItem createFromParcel(Parcel in) {
            return new IncomingReqItem(in);
        }

        @Override
        public IncomingReqItem[] newArray(int size) {
            return new IncomingReqItem[size];
        }
    };




}
