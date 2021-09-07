package com.example.linebot;

import com.example.linebot.replier.*;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//オウム返し
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.MessageEvent;

//ユーザーの回答に反応
import com.linecorp.bot.model.event.PostbackEvent;

@LineMessageHandler //LineBotのコントローラー部
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    // フォローイベントに対応する
    @EventMapping
    public Message handleFollow(FollowEvent event) {
        // 実際はこのタイミングでフォロワーのユーザIDをデータベースにに格納しておくなど
        Follow follow = new Follow(event);
        return follow.reply();
    }

    // 文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    // MessageEvent<TextMessageContent> は、LineBotに送られたテキスト文章を表すクラス
    public Message handleMessage(MessageEvent<TextMessageContent> event) {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        switch (text) {
            case "やあ":
                //時間帯に合わせて返信
                Greet greet = new Greet();
                return greet.reply();
            case "予定確認":
                // リッチメニューから送信
                Verification verification = new Verification();
                return verification.reply();
            default:
                //オウム返し
                Parrot parrot = new Parrot(event);
                return parrot.reply();
        }
    }

    // PostBackEventに対応する（ユーザーの回答に反応）
    @EventMapping
    public Message handlePostBack(PostbackEvent event) {
        DialogAnswer dialogAnswer = new DialogAnswer(event);
        return dialogAnswer.reply();
    }
}