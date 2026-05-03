package com.example.lendahand.apiclasses;

public class ProfileInfo {
    public String fullName, email, phoneNumber, bio;
    public double homeLat, homeLong;

    public ProfileInfo(String fullname, String email, String phoneNumber, String bio, double homeLat, double homeLong) {
        this.fullName = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.homeLat = homeLat;
        this.homeLong = homeLong;
    }


}
