package com.example.linebot.replier;


import java.util.ArrayList;
import java.util.Collections;

// 予定を追加（記録）＆予定リストの表示(『予定確認』から)
public class MakeList{
    private ArrayList<String> scheduleList = new ArrayList<>();
    private String add_schedule;

    public MakeList(){

    }

    public void schedule_add(String time, String text){
        add_schedule = (time + "\n　" + text);
        /*
        * ○年○月○日 ○:○
        * 　入力された予定(scheduleText.schedule)
        *
        * となるように Add_schedule に保存　*/
        scheduleList.add(add_schedule); // 予定リストの中に追加(「予定確認」で見られるように)
        Collections.sort(scheduleList); // 日付順でソート
    }

    public String getList(){
        String str = "予定リスト📅";
        for (String ele : scheduleList){
            str = (str + "\n\n" + ele);
            /*
             * ○年○月○日 ○:○
             * 　予定
             *
             * ○年○月○日 ○:○
             * 　予定
             *
             * となるように表示　*/
        }
        return str;
    }

    public String delete(String text){
        int i = 0;
        int de = -1;
        int f = 0;
        String str;
        for(String ele : scheduleList){
            if (ele.contains(text)){
                de = i;
                f = 1;
            }
            i++;
        }
        if(f == 1) {
            str = scheduleList.get(de);
            scheduleList.remove(de);
            return (str + "\nを削除しました");
        }
        return text + " という予定は登録されていませんでした";


    }

    public String getAdd_schedule () {
        return add_schedule;
    }

}

