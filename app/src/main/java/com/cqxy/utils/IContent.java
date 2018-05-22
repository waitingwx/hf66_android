package com.cqxy.utils;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wx on 2018/1/2.
 */

public interface IContent {

    public static final String AUTHORITY = "com.cqxy.fyb";

    public static final String DB_NAME = "hf66.db";

    public static final int VERSION = 1;

    public interface UserLocalData extends BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final Uri CONTENT_URI = Uri.parse("content://"
                + AUTHORITY + "/" + TABLE_NAME);

        public static final String USER_ID = "_id";
        public static final String USER_NAME = "name";
        public final static String SP_CALENDAR = "spcalendar";
        public final static String CALENDAR_SIGNED = "signedday";

        public final static String SP_USER = "user";
        public final static String USERID = "userid";
        public final static String USER_TOKEN = "usertoken";
        public final static String USER_PHONENUM = "phonenum";
        public final static String USER_GENDER = "gender";
        public final static String USER_QIANMING = "qianming";
        public final static String USER_SHANGQUAN = "shangquan";
        public final static String USER_LOCALAVATAR = "localavatar";
        public final static String USER_ASSET = "asset";
//        public final static String USER_NAME = "name";
        public final static String USER_RECOMMEND = "recommend";
        public final static String USER_RECOMM_NAME = "recommname";

        public final static String EASE_TOKEN = "easetoken";
        public final static String EASE_TOKEN_TIME = "easetokentime";
    }
}
