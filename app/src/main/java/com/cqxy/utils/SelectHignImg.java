package com.cqxy.utils;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxy.fyb.R;

/**
 * Created by PeLon on 2017/10/4.
 */

public class SelectHignImg {
    public static void SelectHigh(int []img_hs, int []img_ns, LinearLayout layouts[],int index, Context context)
    {
        for (int i = 0; i < img_hs.length; i++) {
            if (i==index)
            {
                layouts[i].getChildAt(0).setBackgroundResource(img_hs[i]);
                ((TextView)layouts[i].getChildAt(1)).setTextColor(context.getResources().getColor(R.color.colorBlue));

            }else {
                layouts[i].getChildAt(0).setBackgroundResource(img_ns[i]);
                ((TextView)layouts[i].getChildAt(1)).setTextColor(context.getResources().getColor(R.color.gray_normal));
            }
        }
    }

}
