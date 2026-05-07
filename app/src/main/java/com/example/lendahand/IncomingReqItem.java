package com.example.lendahand;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;

import androidx.annotation.NonNull;

public class IncomingReqItem implements Parcelable {
    private String itemName, doneeName, phoneNumber, requestID, doneeBio;
    double distAway;
    boolean state;

    public IncomingReqItem(String itemName, String doneeName, String phoneNumber, String doneeBio, String requestID, double distAway, boolean state) {
        this.itemName = itemName;
        this.doneeName = doneeName;
        this.phoneNumber = phoneNumber;
        this.doneeBio = doneeBio;
        this.requestID = requestID;
        this.state = state;
        this.distAway = distAway;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDoneeName() { return doneeName; }

    public String getDoneeBio() { return doneeBio; }

    public double getDoneeDistance() { return distAway; }

    public String getDoneePhone() { return phoneNumber; }

    public SpannableString getSubtext() {
        String init = String.format("Requested by %s, %.1f km away", doneeName, distAway);
        SpannableString spannable = new SpannableString(init);
        spannable.setSpan(
                new StyleSpan(Typeface.BOLD),
                13,
                (13+ doneeName.length()),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        return spannable;
    }

    public boolean isOpen() {
        return state;
    }

    // to allow this to be passed between activities:

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(doneeName);
        dest.writeString(phoneNumber);
        dest.writeString(requestID);
        dest.writeDouble(distAway);
        dest.writeString(doneeBio);
        dest.writeByte((byte) (state ? 1 : 0));
    }

    protected IncomingReqItem(Parcel in) {
        itemName = in.readString();
        doneeName = in.readString();
        phoneNumber = in.readString();
        requestID = in.readString();
        distAway = in.readDouble();
        doneeBio = in.readString();
        state = in.readByte() != 0;
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
