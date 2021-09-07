package com.example.linebot.replier;

import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;

// 予定を追加（記録）
public class List  implements Replier{
    private  String date;
    private ArrayList<String> scheduleList = new ArrayList<>();

    public List(String date){
        this.date = date;
    }
    public List(){
    }

    public void add(TextMessageContent schedule){
        String text = String.format(date + "\n　" + schedule);
        scheduleList.add(text);
    }

    public ArrayList<String> getScheduleList(){
        return scheduleList;
    }

    @Override
    public Message reply() {
        return new TextMessage("予定を追加しました。");
    }

}

