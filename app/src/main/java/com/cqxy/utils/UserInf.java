package com.cqxy.utils;

import android.content.Context;

import static com.cqxy.utils.SpUtil.USER_ID;

/**
 * Created by lpl on 17-9-28.
 */
public class UserInf {

    public static String getUserToken(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_TOKEN);
    }
    public static int getUserId(Context context)
    {
        return SpUtil.getInt(context,SpUtil.SP_USER, USER_ID,-1);
    }
    public static String getUserPhonenum(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_PHONENUM);
    }
    public static String getUserQianming(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_QIANMING);
    }
    public static String getUserShangquan(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_SHANGQUAN);
    }
    public static String getUserGender(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_GENDER);
    }

    public static String getUserLocalAvatar(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_LOCALAVATAR);
    }

    public static String getUserRemoteAvatar(Context context) {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_ASSET);
    }
    public static String getUserName(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_NAME);
    }
    public static String getEaseToken(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.EASE_TOKEN);
    }
    public static int getEaseTokenTime(Context context)
    {
        return SpUtil.getInt(context,SpUtil.SP_USER,SpUtil.EASE_TOKEN_TIME,-1);
    }
    public static String getUserRecommend(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_RECOMMEND);
    }    public static String getUserRecommName(Context context)
    {
        return SpUtil.getString(context,SpUtil.SP_USER,SpUtil.USER_RECOMM_NAME);
    }
}
