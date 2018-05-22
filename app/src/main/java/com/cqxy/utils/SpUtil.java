package com.cqxy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static android.R.attr.value;

/**
 * Created by lpl on 17-10-25.
 */

public class SpUtil {

    public final static String SP_CALENDAR = "spcalendar";
    public final static String CALENDAR_SIGNED = "signedday";

    public final static String SP_USER = "user";
    public final static String USER_ID = "userid";
    public final static String USER_TOKEN = "usertoken";
    public final static String USER_PHONENUM = "phonenum";
    public final static String USER_GENDER = "gender";
    public final static String USER_QIANMING = "qianming";
    public final static String USER_SHANGQUAN = "shangquan";
    public final static String USER_LOCALAVATAR = "localavatar";
    public final static String USER_ASSET = "asset";
    public final static String USER_NAME = "name";
    public final static String USER_RECOMMEND = "recommend";
    public final static String USER_RECOMM_NAME = "recommname";

    public final static String EASE_TOKEN = "easetoken";
    public final static String EASE_TOKEN_TIME = "easetokentime";
    private static SharedPreferences sp;

    private static SharedPreferences getSp(Context context, String sharedKey) {
        if (sp == null) {
            sp = context.getSharedPreferences(sharedKey, Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void clearSp(Context context, String sharedKey) {
        SharedPreferences sp = getSp(context, sharedKey);
        sp.edit().clear().commit();
    }

    private static String TAG = "SpUtil";

    /**
     * 存入字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @param value   字符串的值
     */
    public static void putString(Context context, String sharedKey, String key, String value) {
        Log.d(TAG, "putString: --------------sharedkey = " + sharedKey + "\nkey = " + key + "\nvalue = " + value);
        SharedPreferences preferences = getSp(context, sharedKey);
        //存入数据
        SharedPreferences.Editor editor = preferences.edit();
        boolean commit = editor.putString(key, value).commit();
        Log.d(TAG, "putString: commit = " + commit);
    }

    /**
     * 获取字符串
     *
     * @param context 上下文
     * @param key     字符串的键
     * @return 得到的字符串
     */
    public static String getString(Context context, String sharedKey, String key) {
        SharedPreferences preferences = getSp(context, sharedKey);
        String value = preferences.getString(key, "");
        Log.d(TAG, "getString: --------------sharedkey = " + sharedKey + "\nkey = " + key + "\nvalue = " + value);
        return value;
    }

    /**
     * 获取字符串
     *
     * @param context      上下文
     * @param key          字符串的键
     * @param defaultValue 字符串的默认值
     * @return 得到的字符串
     */
    public static String getString(Context context, String sharedKey, String key, String defaultValue) {
        SharedPreferences preferences = getSp(context, sharedKey);
        return preferences.getString(key, defaultValue);
    }

    /**
     * 保存布尔值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putBoolean(Context context, String sharedKey, String key, boolean value) {
        SharedPreferences sp = getSp(context, sharedKey);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取布尔值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static boolean getBoolean(Context context, String sharedKey, String key, boolean defValue) {
        SharedPreferences sp = getSp(context, sharedKey);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存long值
     *
     * @param context      上下文
     * @param key          键
     * @param defaultValue 默认值
     */
    public static void putLong(Context context, String sharedKey, String key, long defaultValue) {
        SharedPreferences sp = getSp(context, sharedKey);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static long getLong(Context context, String sharedKey, String key, long defValue) {
        SharedPreferences sp = getSp(context, sharedKey);
        return sp.getLong(key, defValue);
    }

    /**
     * 保存int值
     *
     * @param context 上下文
     * @param key     键
     * @param value   值
     */
    public static void putInt(Context context, String sharedKey, String key, int value) {
        Log.d(TAG, "putInt: --------------sharedkey = " + sharedKey + "\nkey = " + key + "\nvalue = " + value);
        SharedPreferences sp = getSp(context, sharedKey);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取long值
     *
     * @param context  上下文
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static int getInt(Context context, String sharedKey, String key, int defValue) {
        SharedPreferences sp = getSp(context, sharedKey);
        return sp.getInt(key, defValue);
    }

    /**
     * 保存对象
     *
     * @param context 上下文
     * @param key     键
     * @param obj     要保存的对象（Serializable的子类）
     * @param <T>     泛型定义
     */
    public static <T extends Serializable> void putObject(Context context, String sharedKey, String key, T obj) {
        try {
            put(context, sharedKey, key, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象
     *
     * @param context 上下文
     * @param key     键
     * @param <T>     指定泛型
     * @return 泛型对象
     */
    public static <T extends Serializable> T getObject(Context context, String sharedKey, String key) {
        try {
            return (T) get(context, sharedKey, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储List集合
     *
     * @param context 上下文
     * @param key     存储的键
     * @param list    存储的集合
     */
    public static void putList(Context context, String sharedKey, String key, List<? extends Serializable> list) {
        try {
            put(context, sharedKey, key, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List集合
     *
     * @param context 上下文
     * @param key     键
     * @param <E>     指定泛型
     * @return List集合
     */
    public static <E extends Serializable> List<E> getList(Context context, String sharedKey, String key) {
        try {
            return (List<E>) get(context, sharedKey, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储Map集合
     *
     * @param context 上下文
     * @param key     键
     * @param map     存储的集合
     * @param <K>     指定Map的键
     * @param <V>     指定Map的值
     */
    public static <K extends Serializable, V extends Serializable> void putMap(Context context,
                                                                               String sharedKey, String key, Map<K, V> map) {
        try {
            put(context, sharedKey, key, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <K extends Serializable, V extends Serializable> Map<K, V> getMap(Context context, String sharedKey, String key) {
        try {
            return (Map<K, V>) get(context, sharedKey, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//    public static Map<String, Boolean> getMap(Context context, String key)
//    {
//        try {
//            return (Map<String, Boolean>) get(context,key);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 存储对象
     */
    private static void put(Context context, String sharedKey, String key, Object obj)
            throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putString(context, sharedKey, key, objectStr);
    }

    /**
     * 获取对象
     */
    private static Object get(Context context, String sharedKey, String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getString(context, sharedKey, key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }

}
