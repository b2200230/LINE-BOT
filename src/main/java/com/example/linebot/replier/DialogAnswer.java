package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class DialogAnswer implements Replier {

    private PostbackEvent event;
    private MessageEvent<TextMessageContent> eventT;

    public DialogAnswer(PostbackEvent event) {
        this.event = event;
    }
    public DialogAnswer(PostbackEvent event,MessageEvent<TextMessageContent> eventT){
        this.eventT = eventT;
    }

    // PostBackEventに対応する
    @Override
    public Message reply() {
        String actionLabel = this.event.getPostbackContent().getData();
        switch (actionLabel)  {
            case "CY":
                return new TextMessage("イイね！");
            case "CN":
                return new TextMessage("つらたん");
            // ---------- ここから変更 -----------
            case "DT":
                /* 入力された日付と送信された予定を sheduleList に登録したいけど、うまくいかない。
                *  this.event~~~ がどういうことなのかよくわからない                           */
                List scheduleList = new List(this.event.getPostbackContent().getParams().toString());
                scheduleList.add(this.eventT.getMessage());
                return new TextMessage("予定を追加しました");
            // ---------- ここまで変更 -----------
        }
        return new TextMessage("?");
    }
}