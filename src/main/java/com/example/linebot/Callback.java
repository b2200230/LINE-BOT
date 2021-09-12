package com.example.linebot;

import com.example.linebot.replier.*;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.MessageEvent;

//ユーザーの回答に反応
import com.linecorp.bot.model.event.PostbackEvent;



@LineMessageHandler //LineBotのコントローラー部
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    public MakeList makeList = new MakeList();
    public String texts = "a";
    private int flag = 0;

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
                return new TextMessage(makeList.getList());
            case "予定削除":
                // リッチメニューから送信
                flag = 1;   // flag が 0 の時は予定登録、flag が 1 の時は予定削除
                return new TextMessage("削除したい予定の内容を送信してください");
            default:
                texts = event.getMessage().getText();
                // 予定削除
                if(flag == 1){
                    String str = makeList.delete(text);
                    flag = 0;
                    return new TextMessage(str);
                }

                // 予定登録
                // 「予定削除」が入力されていない状態で「やあ」「予定確認」以外の文章を送信したらそれは全部予定だということにする
                // 日付は予定を入力した後にリッチメニューから入力
                Schedule schedule = new Schedule(event);
                return schedule.reply();
        }
    }

    // PostBackEventに対応する（ユーザーの回答に反応）
    @EventMapping
    public Message handlePostBack(PostbackEvent event) {
        DialogAnswer dialogAnswer = new DialogAnswer(event,makeList,texts);
        return dialogAnswer.reply();
    }
}