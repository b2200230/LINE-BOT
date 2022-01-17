package com.example.linebot.replier;

import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

// リッチメニューを押した時の処理
public class DialogAnswer implements Replier {

    private PostbackEvent event;

    public DialogAnswer(PostbackEvent event) {
        this.event = event;
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
            case "DT":
            // ---------- 時間を取り出す ----------
                // datetime = 2018-08-15T22:16
                String dt = this.event.getPostbackContent().getParams().get("datetime");
                String[] dateLink = dt.split("T"); // 日付と時刻をTで分割（dateLink[0]:日付　dateLink[1]:時間）
                String[] date = dateLink[0].split("-");
                String dd = date[0] + "年" + date[1] + "月" + date[2] + "日";
                String time = dateLink[1]; // 時間部分のみ抽出

                //    private String dateAndTime;
                String scheduleTime = dd + " " + time;
//                this.dateAndTime = dateLink[0] + " " + dateLink[1];
            // ----------------------------------

                return new TextMessage(scheduleTime + " にどんな予定を追加しますか？");
        }
        return new TextMessage("?");
    }

}