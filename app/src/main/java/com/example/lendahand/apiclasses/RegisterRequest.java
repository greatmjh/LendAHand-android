package com.example.lendahand.apiclasses;

public class RegisterRequest {
    public final ProfileInfo profileInfo;
    public final String password;

    public RegisterRequest(ProfileInfo profileInfo, String password) {
        this.profileInfo = profileInfo;
        this.password = password;
    }

}
