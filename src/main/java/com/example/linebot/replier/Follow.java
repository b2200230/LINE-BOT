package com.example.linebot.replier;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;

// フォローされた時用の返信クラス
public class Follow implements Replier {

    private FollowEvent event;

    public Follow(FollowEvent event) {
        this.event = event;
    }

    @Override
    public Message reply() {
        String userId = this.event.getSource().getUserId();

        String text = String.format(
                "こんにちは！\n" +
                "スケジュール管理をするBotです。\n" +
                "\n" +
                "「日付選択」で日時を選択することで予定を登録できます。\n\n" +
                "「予定確認」で登録されたスケジュールを確認できます。\n\n" +
                "「予定削除」で登録されている予定を削除することができます。");
        return new TextMessage(text);
    }

}