package com.example.linebot.replier;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;

// フォローされた時用の返信クラス
public class Follow implements Replier {

    private FollowEvent event;
    private ArrayList<String> userIdList = new ArrayList<>();

    public Follow(FollowEvent event) {
        this.event = event;
    }

    @Override
    public Message reply() {
        String userId = this.event.getSource().getUserId();

        //ユーザーID記録
        userIdList.add(userId);

        String text = String.format(
                "こんにちは！\n" +
                "スケジュール管理をするBotです。\n" +
                "\n" +
                "登録したい予定を送信した後、「日付選択」で日付を選択することで予定を登録できます。\n\n" +
                "「予定確認」で登録されたスケジュールを確認できます。\n\n" +
                "「予定削除」で登録されている予定を削除することができます。");
        return new TextMessage(text);
    }

}