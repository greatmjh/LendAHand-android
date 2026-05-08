package com.example.lendahand.apiclasses;

public class RawApiNotification {
    public String heading, text, onClick, id, time;
    public boolean isRead;

    public RawApiNotification(String heading, String text, String onClick, String id, String time, boolean isRead) {
        this.heading = heading;
        this.text = text;
        this.onClick = onClick;
        this.id = id;
        this.time = time;
        this.isRead = isRead;
    }
}
