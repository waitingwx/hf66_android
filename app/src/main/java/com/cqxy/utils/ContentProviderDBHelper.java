package com.cqxy.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wx on 2018/1/2.
 */

public class ContentProviderDBHelper extends SQLiteOpenHelper implements IContent {

    private static final String TAG = "ContentProviderDBHelper";

    public ContentProviderDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLESQL = "CREATE TABLE IF NOT EXISTS "
                + UserLocalData.TABLE_NAME + " ("
                + UserLocalData.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserLocalData.USER_NAME + " VARCHAR,"
                + UserLocalData.USER_GENDER + " VARCHAR,"
                + UserLocalData.USER_LOCALAVATAR + " VARCHAR,"
                + UserLocalData.USER_PHONENUM + " VARCHAR,"
                + UserLocalData.USER_QIANMING + " VARCHAR,"
                + UserLocalData.USER_RECOMM_NAME + " VARCHAR,"
                + UserLocalData.USER_RECOMMEND + " VARCHAR,"
                + UserLocalData.USER_SHANGQUAN + " VARCHAR,"
                + UserLocalData.USER_TOKEN + " VARCHAR,"
                + UserLocalData.USERID + " VARCHAR,"
                + UserLocalData.EASE_TOKEN + " VARCHAR,"
                + UserLocalData.USER_ASSET + " VARCHAR)";
        db.execSQL(TABLESQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + "to"
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }
}
