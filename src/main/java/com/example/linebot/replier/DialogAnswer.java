package com.example.linebot.replier;

import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;


public class DialogAnswer implements Replier {

    private PostbackEvent event;
    public MakeList makeList;
    private String text;

    public DialogAnswer(PostbackEvent event,MakeList makeList,String text) {
        this.event = event;
        this.makeList = makeList;
        this.text = text;
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
                String dt = this.event.getPostbackContent().getParams().get("datetime");
                String[] date_link = dt.split("T"); // 日付と時刻をTで分割（date_link[0]:日付　date_link[1]:時間）
                String[] date = date_link[0].split("-");
                String dd = date[0] + "年" + date[1] + "月" + date[2] + "日";
                String time = date_link[1]; // 時間部分のみ抽出
            // ----------------------------------

                makeList.schedule_add(dd + " " + time,text); // MakeList で入力された日付と予定を紐づけ
            return new TextMessage(makeList.Add_schedule + "\nを追加しました");
        }
        return new TextMessage("?");
    }
}