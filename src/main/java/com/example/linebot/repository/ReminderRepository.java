package com.example.linebot.repository;

import com.example.linebot.value.ReminderItem;
import com.example.linebot.value.ReminderItemTuple;
import com.example.linebot.value.ReminderSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

// @Repository アノテーション ⇒ Spring がインスタンス化を管理する
// このクラスをインスタンス化する時に、JdbcTemplateにデータベースの設定を行っている
@Repository
public class ReminderRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public ReminderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    // -------
    public void insert(ReminderItem item) {
        // language = sql
        // sql はテーブルにデータを追加するクエリ
        String sql = "insert into reminder_item "
                + "(user_id, push_at, push_text) "
                + "values (?, ?, ?)";

        String userId = item.getUserId();
        ReminderSlot slot = item.getSlot();
        // SQLの？のところを順番に設定し、SQLを実行している
        jdbc.update(sql, userId, slot.getPushAt(), slot.getPushText());
    }
    // ------

    // リマインダを検索するメソッド
    public List<ReminderItemTuple> findPreviousItems() {
        // language = sql
        // ? を時間として、ある時間より前にリマインダを設定されているタプルを取り出すSQL
        String sql = "select user_id, push_at, push_text " +
                "from reminder_item " +
                "where push_at <= ? ";

        // 現在時刻のインスタンス
//        LocalTime now = LocalTime.now();
        long millis = System.currentTimeMillis();
        Timestamp now = new Timestamp(millis);
        // SQLの ? に現在時刻をあてはめ、結果を ReminderItem インスタンスの (Array)List で取得する。
        List<ReminderItemTuple> list =
                jdbc.query(sql, new DataClassRowMapper<>(ReminderItemTuple.class), now);
        return list;
    }

    public void deletePreviousItems(){
        // language = sql
        String sql = "delete from reminder_item " +
                "where push_at <= ? ";

//        LocalTime now = LocalTime.now();
        long millis = System.currentTimeMillis();
        Timestamp now = new Timestamp(millis);
        jdbc.update(sql, now);
    }

}
