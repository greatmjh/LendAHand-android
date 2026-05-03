package com.example.lendahand.apiclasses;

public class RegisterRequest {
    public ProfileInfo profileInfo;
    public String password;

    public RegisterRequest(ProfileInfo profileInfo, String password) {
        this.profileInfo = profileInfo;
        this.password = password;
    }

}
