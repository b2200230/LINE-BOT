package com.example.linebot.value;

import java.sql.Timestamp;
import java.time.LocalTime;

// データベースの ReminderItem の 1件分を表すクラス
public class ReminderItemTuple {

    private final String userId;
    private final Timestamp pushAt;
    private final String pushText;

    public ReminderItemTuple(String userId, Timestamp pushAt, String pushText) {
        this.userId = userId;
        this.pushAt = pushAt;
        this.pushText = pushText;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getPushAt() {
        return pushAt;
    }

    public String getPushText() {
        return pushText;
    }
}
