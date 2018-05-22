package com.cqxy.utils;

import android.widget.TextView;

/**
 * Created by lpl on 17-9-29.
 */

public class TextContentUtils {
    public static String getViewContent(TextView view) {
        String text = view.getText().toString().trim();
        if (null == text || text == "")
            return "";
        else
            return text;
    }
}
