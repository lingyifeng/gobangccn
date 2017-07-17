package com.jiang.common.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiang on 2017/5/2.
 */

public class InputFilterUtil {

    /**
     * 给edittext设置过滤器 过滤emoji
     */
    public static void setEmojiFilter(EditText et) {
        InputFilter emojiFilter = new InputFilter() {
            Pattern pattern = Pattern
                    .compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher matcher = pattern.matcher(source);
                if (matcher.find()) {
                    return "";
                }
                return null;
            }
        };
        et.setFilters(new InputFilter[]{emojiFilter});
    }
}
