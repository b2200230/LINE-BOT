package com.example.linebot;

import com.example.linebot.service.ReminderService;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;

import com.example.linebot.repository.ReminderRepository;

//-------
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
//-------

import com.linecorp.bot.model.message.TextMessage;

//-------
import com.linecorp.bot.model.message.template.ConfirmTemplate;
//-------

import com.linecorp.bot.model.response.BotApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class Push {

    private static final Logger log = LoggerFactory.getLogger(Push.class);

    // push先のユーザID（本来は、友達登録をした瞬間にDBなどに格納しておく）
    private String userId = "U705d764ea32a7748b138b56914cdee93";

    private final LineMessagingClient client;

    private final ReminderService reminderService;

    private final ReminderRepository reminderRepository;

    @Autowired
    public Push(LineMessagingClient lineMessagingClient, ReminderService reminderService,
                ReminderRepository reminderRepository) {
        this.client = lineMessagingClient;
        this.reminderService = reminderService;
        this.reminderRepository = reminderRepository;
    }

//    @Autowired
//    public Push(LineMessagingClient lineMessagingClient) {
//        this.client = lineMessagingClient;
//    }

//    // テスト
//    @GetMapping("test")
//    public String hello(HttpServletRequest request) {
//        return "Get from " + request.getRequestURL();
//    }

    // -------------------
    // 時報をpushする
    // https://xxxxxx.jp.ngrok.io/timetone にアクセスするとpush
    @GetMapping("timetone")
    // 1分ごとpush
    // @Scheduled(cron = "0 */1 * * * *", zone = "Asia/Tokyo")
    public String pushTimeTone() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("a K:mm");
        String text = DateTimeFormatter.ofPattern("a K:mm").format(LocalDateTime.now());
        try {
            PushMessage pMsg
                    = new PushMessage(userId, new TextMessage(text));
            BotApiResponse resp = client.pushMessage(pMsg).get();
            log.info("Sent messages: {}", resp);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return text;
    }
    // -------------------

    // 予定時刻にリマインド
    @Scheduled(cron = "0 */1 * * * *", zone = "Asia/Tokyo")
    public void pushReminder() {
        try{
            List<PushMessage> messages = reminderService.doPushReminderItems();
            for(PushMessage message : messages) {
                BotApiResponse resp = client.pushMessage(message).get();
                log.info("Sent messages: {}", resp);

                // 削除
                reminderRepository.deletePreviousItems();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }



    // 確認メッセージをpush
    // http://localhost:8080/confirm
    @GetMapping("confirm")
    public String pushConfirm() {
        String text = "質問だよ";
        try {
            Message msg = new TemplateMessage(text,
                    new ConfirmTemplate("いいかんじ？",
                            new PostbackAction("おけまる", "CY"),
                            new PostbackAction("やばたん", "CN")));
            PushMessage pMsg = new PushMessage(userId, msg);
            BotApiResponse resp = client.pushMessage(pMsg).get();
            log.info("Sent messages: {}", resp);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

}