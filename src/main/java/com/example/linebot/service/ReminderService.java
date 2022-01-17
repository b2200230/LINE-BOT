package com.example.linebot.service;

import com.example.linebot.value.ReminderItem;
import com.example.linebot.value.ReminderItemTuple;
import com.example.linebot.value.ReminderSlot;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.PushMessage;

import java.util.ArrayList;
import java.util.List;

import com.example.linebot.repository.ReminderRepository;

// 主な処理を行う
@Service
public class ReminderService {

    // データベース登録
    private final ReminderRepository repository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository) {
        this.repository = reminderRepository;
    }

    //--------
//    public RemindOn doReplyOfNewItem(MessageEvent<TextMessageContent> event) {
//    public RemindOn doReplyOfNewItem(MessageEvent<TextMessageContent> event, String dateAndTime) {
      public void doReplyOfNewItem(MessageEvent<TextMessageContent> event, String dateAndTime) {
        String userId = event.getSource().getUserId();
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        ReminderSlot slot = new ReminderSlot(dateAndTime, text);
        ReminderItem item = new ReminderItem(userId, slot);

        //----
        // データベースに追加
        repository.insert(item);
        //----

//        return new RemindOn(text);
    }
    //----------


    // PUSH通知
    public List<PushMessage> doPushReminderItems(){
        // ReminderRepository がデータを検索した結果（組）を用いる:
        // Serviceが上位モジュール、Repositoryが下位モジュールとして、
        // ServiceがRepositoryの労力を得る。
        List<ReminderItemTuple> previousItems =
                repository.findPreviousItems();
        List<PushMessage> pushMessages = new ArrayList<>();

        // 	検索結果（検索された組）の分だけ繰り返し、
        // 	通知用のメッセージデータ（ PushMessage ）のリストを作る。
        for (ReminderItemTuple item : previousItems) {
            PushMessage pushMessage = toPushMessage(item);
            pushMessages.add(pushMessage);
        }
        return pushMessages;
    }

    // 検索結果から通知用メッセージデータを作り出す。
    public PushMessage toPushMessage(ReminderItemTuple item) {
        String userId = item.getUserId();
        String pushText = item.getPushText();
        String body = String.format("%s の時間です！", pushText);
        return new PushMessage(userId, new TextMessage(body));
    }
}
