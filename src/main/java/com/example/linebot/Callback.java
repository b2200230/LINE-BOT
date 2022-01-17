package com.example.linebot;

import com.example.linebot.replier.*;
import com.example.linebot.replier.Omikuji;

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

// Intent Enum
import com.example.linebot.replier.Intent;
import com.example.linebot.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler //LineBotのコントローラー部
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);

    private MakeList makeList = new MakeList();
    private boolean addFlag = false;
    private boolean deleteFlag = false;
    private String dateAndTime;
    private String scheduleTime;

//-------
    private final ReminderService reminderService;

    @Autowired
    public Callback(ReminderService reminderService) {
        this.reminderService = reminderService;
    }
//-------

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

        // 送信されたtextのパターンからIntentを得る
        Intent intent = Intent.whichIntent(text);

        switch (intent) {
            // 「やあ」
            case GREET:
                //時間帯に合わせて返信
                Greet greet = new Greet();
                return greet.reply();
            // 「おみくじ」
            case OMIKUJI:
                Omikuji omikuji = new Omikuji();
                return omikuji.reply();
            // 「予定確認」
            case KAKUNIN:
                // リッチメニューから送信
                return new TextMessage(makeList.getList());
            // 「予定削除」
            case SAKUZYO:
                // リッチメニューから送信
                deleteFlag = true;   // deleteFlag が false の時は予定登録、deleteFlag が true の時は予定削除
                addFlag = false;
                return new TextMessage("削除したい予定の内容を送信してください");
            default:
                // 予定追加
                if(addFlag) {
                    reminderService.doReplyOfNewItem(event,dateAndTime);

                    makeList.scheduleAdd(scheduleTime, text);
                    addFlag = false;
                    deleteFlag = false;
                    return makeList.reply();
                }
                // 予定削除
                if(deleteFlag){
                    String str = makeList.delete(text);
                    deleteFlag = false;
                    addFlag = false;
                    return new TextMessage(str);
                }
                Parrot parrot = new Parrot(event);
                return parrot.reply();
        }

    }

    // PostBackEventに対応する（ユーザーの回答に反応）
    @EventMapping
    public Message handlePostBack(PostbackEvent event) {

//        DialogAnswer dialogAnswer = new DialogAnswer(event,makeList,texts);
        // 日時が選択されたら次に送られたメッセージを予定として追加する
        DialogAnswer dialogAnswer = new DialogAnswer(event);

        String dt = event.getPostbackContent().getParams().get("datetime");
        String[] dateLink = dt.split("T"); // 日付と時刻をTで分割（dateLink[0]:日付　dateLink[1]:時間）
        String[] date = dateLink[0].split("-");
        String dd = date[0] + "年" + date[1] + "月" + date[2] + "日";
        String time = dateLink[1]; // 時間部分のみ抽出

        scheduleTime = dd + " " + time;
        dateAndTime = dateLink[0] + " " + dateLink[1] + ":00";

        addFlag = true;
        deleteFlag = false;
        return dialogAnswer.reply();

    }
}