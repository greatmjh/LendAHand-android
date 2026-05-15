package com.example.lendahand.apiclasses;

public class RawApiNotification {
    public final String heading;
    public final String text;
    public final String onClick;
    public final String id;
    public final String time;
    public final boolean isRead;

    public RawApiNotification(String heading, String text, String onClick, String id, String time, boolean isRead) {
        this.heading = heading;
        this.text = text;
        this.onClick = onClick;
        this.id = id;
        this.time = time;
        this.isRead = isRead;
    }
}
