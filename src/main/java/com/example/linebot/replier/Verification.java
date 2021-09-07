package com.example.linebot.replier;


import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;


// 予定確認
public class Verification implements Replier {

    @Override
    public Message reply() {
        List scheduleList = new List();
        String text = "予定";
        for (List schedules : scheduleList){
            text = String.format(text + "\n" + schedules.getScheduleList());
            /*予定
            * ○/○ □□:□□
            * 　バイト
            * ○/○ □□:□□
            * 　課題締め切り
            *
            * みたいに登録されている予定をもとに予定表を表示させたいが、うまくいかない*/
        }
        return new TextMessage(text);
    }

}


