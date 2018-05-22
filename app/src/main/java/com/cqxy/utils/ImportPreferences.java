package com.cqxy.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import net.grandcentrix.tray.TrayPreferences;
import net.grandcentrix.tray.core.SharedPreferencesImport;
import net.grandcentrix.tray.core.TrayStorage;

/**
 * Created by wx on 2018/1/2.
 */

public class ImportPreferences extends TrayPreferences {

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

    private Context mContext;

    public ImportPreferences(@NonNull Context context, @NonNull String module, int version, TrayStorage.Type type) {
        super(context, module, version, type);
        this.mContext = context;
    }

    @Override
    protected void onCreate(int initialVersion) {
        super.onCreate(initialVersion);
        SharedPreferencesImport sharedImport1 = new SharedPreferencesImport(mContext, SP_USER, USER_ID, USER_ID);
        SharedPreferencesImport sharedImport2 = new SharedPreferencesImport(mContext, SP_USER, USER_TOKEN, USER_TOKEN);
        SharedPreferencesImport sharedImport3 = new SharedPreferencesImport(mContext, SP_USER, USER_PHONENUM, USER_PHONENUM);
        SharedPreferencesImport sharedImport4 = new SharedPreferencesImport(mContext, SP_USER, USER_GENDER, USER_GENDER);
        SharedPreferencesImport sharedImport5 = new SharedPreferencesImport(mContext, SP_USER, USER_QIANMING, USER_QIANMING);
        SharedPreferencesImport sharedImport6 = new SharedPreferencesImport(mContext, SP_USER, USER_SHANGQUAN, USER_SHANGQUAN);
        SharedPreferencesImport sharedImport7 = new SharedPreferencesImport(mContext, SP_USER, USER_LOCALAVATAR, USER_LOCALAVATAR);
        SharedPreferencesImport sharedImport8 = new SharedPreferencesImport(mContext, SP_USER, USER_NAME, USER_NAME);
        SharedPreferencesImport sharedImport9 = new SharedPreferencesImport(mContext, SP_USER, USER_RECOMMEND, USER_RECOMMEND);
        SharedPreferencesImport sharedImport10 = new SharedPreferencesImport(mContext, SP_USER, USER_ASSET, USER_ASSET);
        SharedPreferencesImport sharedImport11 = new SharedPreferencesImport(mContext, SP_USER, USER_RECOMM_NAME, USER_RECOMM_NAME);
        SharedPreferencesImport sharedImport12 = new SharedPreferencesImport(mContext, SP_USER, EASE_TOKEN, EASE_TOKEN);
        migrate(sharedImport1,
                sharedImport2,
                sharedImport3,
                sharedImport4,
                sharedImport5,
                sharedImport6,
                sharedImport7,
                sharedImport8,
                sharedImport9,
                sharedImport10,
                sharedImport11,
                sharedImport12);
    }
}
