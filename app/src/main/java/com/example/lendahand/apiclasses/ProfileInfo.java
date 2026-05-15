package com.example.lendahand.apiclasses;

public class ProfileInfo {
    public final String fullName;
    public final String email;
    public final String phoneNumber;
    public final String bio;
    public final double homeLat;
    public final double homeLong;

    public ProfileInfo(String fullname, String email, String phoneNumber, String bio, double homeLat, double homeLong) {
        this.fullName = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.homeLat = homeLat;
        this.homeLong = homeLong;
    }


}
