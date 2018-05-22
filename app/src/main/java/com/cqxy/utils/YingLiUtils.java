package com.cqxy.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.cqxy.constants.ProfitType;
import com.cqxy.fyb.R;

/**
 * Created by wx on 2017/10/27.
 */

public class YingLiUtils {
    public static void selectTextViews(Context context,TextView[] textViews, int index, EditText edittext,TextView unit)
    {
        for (int i = 0; i < textViews.length; i++) {
            if (i==index)
            {
                textViews[i].setTextColor(context.getResources().getColor(R.color.colorRed));
                if (i==0) {
                    unit.setText("%");
                }else {
                    unit.setText("元");
                }
                edittext.setText("");
            }else {
                textViews[i].setTextColor(context.getResources().getColor(R.color.black9a));
            }
        }
    }

    public static void selectTextViews(Context context, TextView textView, String leixing, EditText edittext, TextView unit) {
        textView.setTextColor(context.getColor(R.color.black9a));
        edittext.setText("");
        switch (leixing) {
            case ProfitType.YONGJIN:
                textView.setTextColor(context.getColor(R.color.colorRed));
                unit.setText("%");
                break;
            case ProfitType.JINGDE:
                textView.setTextColor(context.getColor(R.color.colorRed));
                unit.setText("元");
                break;
            case ProfitType.FANFEI:
                textView.setTextColor(context.getColor(R.color.colorRed));
                unit.setText("元");
                break;
        }
    }
}
