package com.example.lendahand;

import android.text.format.DateUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class notificationItem {
    private LocalDateTime time;
    private String title;
    private String body;
    private String onclick;

    boolean read;

    public notificationItem(LocalDateTime time, String title, String body, String onclick, boolean read) {
        this.time = time;
        this.title = title;
        this.body = body;
        this.onclick = onclick;
        this.read = read;
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
}
