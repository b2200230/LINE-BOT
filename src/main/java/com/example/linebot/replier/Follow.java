package com.example.linebot.replier;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;

// フォローされた時用の返信クラス
public class Follow implements Replier {

    private FollowEvent event;
    public ArrayList<String> useridList = new ArrayList<>();

    public Follow(FollowEvent event) {
        this.event = event;
    }

    @Override
    public Message reply() {
        String userId = this.event.getSource().getUserId();

        //ユーザーID記録
        useridList.add(userId);

        String text = String.format(
                "こんにちは！\n" +
                "スケジュール管理をするBotです。\n" +
                "\n" +
                "「予定登録」で予定を登録します。日付選択後、予定を入力してください。\n" +
                "「予定確認」で登録されたスケジュールを確認できます。");
        return new TextMessage(text);
    }

}