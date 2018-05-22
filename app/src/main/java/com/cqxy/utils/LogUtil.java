package com.cqxy.utils;

import android.util.Log;

/**
 * Created by wx on 2017/11/13.
 */
public class LogUtil {
    /**
     * 是否是调试版本
     */
    public static final boolean isDebug = true;

    private static final String TAG = "LogUtil";

    public static void d(String msg)
    {
        if (isDebug)
        {
            Log.d(TAG, msg);
        }
    }

    public static void w(String msg)
    {
        if (isDebug)
        {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg)
    {
        if (isDebug)
        {
            Log.e(TAG, msg);
        }
    }
}
