package com.example.linebot.replier;


import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;

// 予定を追加（記録）
public class MakeList implements Replier{
    public ArrayList<String> scheduleList = new ArrayList<>();
    public String Add_schedule;

    public MakeList(){
    }

    public void schedule_add(String time){
        Schedule scheduleText = new Schedule(MessageEvent.<TextMessageContent>builder().build());
        Add_schedule = (time + "\n　" + scheduleText.schedule);
        scheduleList.add(Add_schedule);
    }

    public String getList(){
        String str = "予定リスト\n";
        for (String ele : scheduleList){
            str = (str + "\n" + ele);
        }
        return str;
    }

    @Override
    public Message reply() {
        return new TextMessage("予定を追加しました。");
    }

}

