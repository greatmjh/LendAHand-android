package com.example.lendahand.apiclasses;

public class LogInResponse {
    public final boolean success;
    public final String sessionKey;
    public final String errorMessage;

    public LogInResponse(boolean success, String sessionKey, String errorMessage) {
        this.success = success;
        this.sessionKey = sessionKey;
        this.errorMessage = errorMessage;
    }

}
