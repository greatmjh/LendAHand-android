package com.example.lendahand;

public class topDonorItem {

    private String userName;
    private int numItemsDonated;

    public topDonorItem(String userName, int numItemsDonated){
        this.userName = userName;
        this.numItemsDonated = numItemsDonated;
    }

    public String getUserName(){ return userName; }

    public int getNumItemsDonated() { return numItemsDonated; }
}
