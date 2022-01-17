package com.example.linebot.replier;


import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import java.util.ArrayList;
import java.util.Collections;

// 予定を追加（記録）＆予定リストの表示(『予定確認』から)
public class MakeList implements Replier{
    private ArrayList<String> scheduleList = new ArrayList<>();
    private String addSchedule;

    @Override
    public Message reply() {
        return new TextMessage(addSchedule + "\nを登録しました");
    }

    public void scheduleAdd(String scheduleTime, String text){
        addSchedule = (scheduleTime + "\n　" + text);
        /*
        * ○年○月○日 ○:○
        * 　入力された予定
        *
        * となるように addSchedule に保存　*/
        scheduleList.add(addSchedule); // 予定リストの中に追加(「予定確認」で見られるように)
        Collections.sort(scheduleList); // 日付順でソート
    }

    public String getList(){
        StringBuilder str = new StringBuilder("予定リスト📅");
        for (String ele : scheduleList){
            // str = (str + "\n\n" + ele);
            str.append("\n\n").append(ele);
            /*
             * ○年○月○日 ○:○
             * 　予定
             *
             * ○年○月○日 ○:○
             * 　予定
             *
             * となるように表示　*/
        }
        return str.toString();
    }

    public String delete(String text){
        int i = 0;
        int de = -1;
        boolean f = false;
        String str;
        for(String ele : scheduleList){
            if (ele.contains(text)){
                de = i;
                f = true;
            }
            i++;
        }
        if(f) {
            str = scheduleList.get(de);
            scheduleList.remove(de);
            return (str + "\nを削除しました");
        }
        return text + " という予定は登録されていませんでした";


    }

}

