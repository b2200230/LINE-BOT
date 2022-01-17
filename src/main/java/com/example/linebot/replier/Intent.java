package com.example.linebot.replier;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// ユーザは何をしたいのか(インデント)判別
public enum Intent {

    // メッセージの正規表現パターンに対応するやりとり状態の定義
    GREET("やあ"),
    OMIKUJI("おみくじ"),
    KAKUNIN("予定確認"),
    SAKUZYO("予定削除"),
    REMINDER(".+");     // それ以外はすべて追加したい予定と判断
//    REMINDER("^(\\d{1,2}):(\\d{1,2})に(.{1,32})$"),
//    UNKNOWN(".+");

    private final String regexp;

    private Intent(String regexp) {
        this.regexp = regexp;
    }

    // メッセージからやりとり状態を判断
    public static Intent whichIntent(String text) {
        // 全てのIntent(REMINDERなど)を取得
        EnumSet<Intent> set = EnumSet.allOf(Intent.class);
        // 引数 text が、パターンに当てはまるかチェック
        // 当てはまったものを戻り値とする
        for (Intent intent : set) {
            if (Pattern.matches(intent.regexp, text)) {
                return intent;
            }
        }
        return REMINDER;
    }

    public String getRegexp() {
        return regexp;
    }

}
