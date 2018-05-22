package com.cqxy.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

/**
 * Created by wx on 2017/11/13.
 */

public class FileUtil {
    /**
     * 项目根目录
     */
    public static final String APP_DIR = "hf66";

    /**
     * 获取 hf66/image 目录
     */
    public static final File DIR_IMAGE = getDir("image");

    /**
     * 获取 hf66/image_transformation 目录
     */
    public static final File DIR_IMAGE_TRANSFORMATION = getDir("image_transformation");


    /**
     * 音频目录
     */
    public static final File DIR_AUDIO = getDir("audio");

    /**
     * 视频目录
     */
    public static final File DIR_VIDEO = getDir("video");

    /**
     * m3u8目录
     */
    public static final File DIR_M3U8 = getDir("m3u8");

    /**
     * 获取sd卡根目录
     *
     * @return
     */
    public static File getSdCardDir()
    {
        //获取sd卡的状态
        String state = Environment.getExternalStorageState();
        //如果sd卡已挂载
        if (Environment.MEDIA_MOUNTED.equals(state))
        {
            //获取根目录
            File dir = Environment.getExternalStorageDirectory();

            return dir;
        }

        LogUtil.e("没有sd卡");
        return null;
    }

    /**
     * 获取sd卡下 hf66 目录
     *
     * @return
     */
    public static File getAppDir()
    {
        File dir = new File(getSdCardDir(), APP_DIR);

        if (!dir.exists())
        {
            dir.mkdir();
        }

        return dir;
    }

    /**
     * 获取 hf66的子目录
     *
     * @param dirName
     * @return
     */
    public static File getDir(String dirName)
    {
        File dir = new File(getAppDir(), dirName);

        if (!dir.exists())
        {
            dir.mkdir();
        }

        return dir;
    }


    /**
     * 获取指定的SharedPreferences文件里指定标签的内容
     *
     * @param context 上下文
     * @param fileName 文件名
     * @param flagName 标签名
     * @return 指定的json
     */
    public static String getCacheFromPreference(Context context, String fileName, String flagName)
    {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        String value = preferences.getString(flagName, "");

        return value;
    }

    /**
     * 获取指定的SharedPreferences文件里指定标签内容保存的时间
     *
     * @param context 上下文
     * @param fileName 文件名
     * @param flagName 标签名
     * @return 指定的json
     */
    public static long getCacheTimeFromPreference(Context context, String fileName, String flagName)
    {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        long value = preferences.getLong(flagName, 0);

        return value;
    }

    /**
     * 保存json到指定的SharedPreferences 文件中
     *
     * @param context 上下文
     * @param fileName 文件名
     * @param flagName 标签名
     * @param json 要保存的内容
     */
    public static void saveCacheToPreference(Context context, String fileName, String flagName, String json)
    {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(flagName, json);

        editor.commit();
    }

    /**
     * 保存请求json服务器的时间到指定的SharedPreferences 文件中
     *
     * @param context 上下文
     * @param fileName 文件名
     * @param flagName 标签名
     * @param time 服务器时间
     */
    public static void saveCacheTimeToPreference(Context context, String fileName, String flagName, long time)
    {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(flagName, time);

        editor.commit();
    }


    /**
     * 删除缓存文件
     */
    public static void deleteCacheFile(File dir, String rename)
    {
        File target = new File(dir, rename);

        if (target.exists())
        {
            target.delete();
        }
    }

    /**
     * 清空缓存
     *
     * @param dir
     */
    public static void clearCacheFromDir(File dir)
    {
        if (!dir.exists())
        {
            return;
        }
        //清空该目录下面的所有子文件
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }

    public  static void installApk(Context context, File apk) {
        Uri uri = Uri.fromFile(apk);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri,"application/vnd.package-archive");
        context.startActivity(intent);
    }


}
