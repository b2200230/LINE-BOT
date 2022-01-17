package com.example.linebot.value;

import com.example.linebot.replier.Intent;
import com.linecorp.bot.model.event.PostbackEvent;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReminderSlot {

//    private final LocalTime pushAt;
    private final Timestamp pushAt;
    private final String pushText;

    public ReminderSlot(String dateAndTime, String text) {
        // Slotにあたる部分を取り出す正規表現の仕組み(Matcher)を作る
//        String regexp = Intent.REMINDER.getRegexp();
//        Pattern pattern = Pattern.compile(regexp);
//        Matcher matcher = pattern.matcher(text);
//        if(matcher.matches()) {
        if(text.length() <= 32) {
            // 32文字以下
//            int hours = Integer.parseInt(matcher.group(1));
//            int minutes = Integer.parseInt(matcher.group(2));
//            this.pushAt = LocalTime.of(hours, minutes);
//            this.pushText = matcher.group(3);

            this.pushAt = Timestamp.valueOf(dateAndTime);
            this.pushText = text;
        } else {
            // 正規表現にマッチしない場合、実行時例外を throw する
            throw new IllegalArgumentException("text をスロットに分けられません");
        }
    }

//    public LocalTime getPushAt() {
//        return pushAt;
//    }

    public Timestamp getPushAt() {
        return pushAt;
    }

    public String getPushText() {
        return pushText;
    }
}
