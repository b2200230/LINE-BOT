package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;


// 入力された予定(日付なし)を保存
public class Schedule implements Replier {

    private MessageEvent<TextMessageContent> event;
    private String schedule;

    public Schedule(MessageEvent<TextMessageContent> event) {
        this.event = event;
        this.schedule = this.event.getMessage().getText();
    }

    @Override
    public Message reply() {
        TextMessageContent tmc = this.event.getMessage();
        schedule = tmc.getText();   // schedule に入力された予定を保存して、リッチメニューから入力された日付と紐づける
        return new TextMessage(schedule + " はいつの予定ですか？\n(『日付選択』から日時を選択してください)");
    }

}
