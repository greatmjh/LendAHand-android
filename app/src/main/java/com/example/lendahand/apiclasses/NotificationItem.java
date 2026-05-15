package com.example.lendahand.apiclasses;

import android.text.format.DateUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class NotificationItem {
    private LocalDateTime time;
    private String title;
    private String body;
    private String onclick;

    private UUID uuid;
    public boolean read;

    public NotificationItem(UUID id, LocalDateTime time, String title, String body, String onclick, boolean read) {
        this.time = time;
        this.title = title;
        this.body = body;
        this.onclick = onclick;
        this.read = read;
        this.uuid = id;
    }

    public String getRelativeTime() {
        long notifTimeMillis = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(notifTimeMillis, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS);
        return relativeTime.toString();
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getOnclick() {
        return onclick;
    }

    public boolean isRead() {
        return read;
    }
    public UUID getUuid() { return uuid; }
}
