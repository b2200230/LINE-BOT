package com.example.linebot.replier;


import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.List;

// 予定確認
public class Verification implements Replier {

    @Override
    public Message reply() {
        MakeList scheduleMakeList = new MakeList();
        String text = "予定リスト\n";
//        for (MakeList schedules : scheduleMakeList){
//            text = String.format(text + "\n" + schedules.getList());
//            /*予定
//            * ○/○ □□:□□
//            * 　バイト
//            * ○/○ □□:□□
//            * 　課題締め切り
//            *
//            * みたいに登録されている予定をもとに予定表を表示させたいが、うまくいかない*/
//        }
        return new TextMessage(text);
    }

}


