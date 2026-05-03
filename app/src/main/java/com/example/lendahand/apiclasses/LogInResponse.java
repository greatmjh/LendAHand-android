package com.example.lendahand.apiclasses;

public class LogInResponse {
    public boolean success;
    public String sessionKey, errorMessage;

    public LogInResponse(boolean success, String sessionKey, String errorMessage) {
        this.success = success;
        this.sessionKey = sessionKey;
        this.errorMessage = errorMessage;
    }

}
