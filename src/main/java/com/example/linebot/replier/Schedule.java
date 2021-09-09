package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.List;

// おうむ返し用の返信クラス
public class Schedule implements Replier {

    private MessageEvent<TextMessageContent> event;
    public String schedule;

    public Schedule(MessageEvent<TextMessageContent> event) {
        this.event = event;
    }
    public Schedule(){}

    @Override
    public Message reply() {
        TextMessageContent tmc = this.event.getMessage();
        schedule = tmc.getText();   // schedule に入力された予定を保存して、リッチメニューから入力された日付と紐づける
        return new TextMessage(schedule + " はいつの予定ですか？\n(『予定追加』から日時を選択してください)");
    }

}
