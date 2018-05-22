package com.cqxy.utils;

import android.content.Context;
import android.util.Log;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.EaseTokenBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lpl on 17-9-27.
 */

public class OkHttpUtils {
    public static void okhttpGetNeedHead(String token, String phone_num, String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Authorization", "Token " + token + ",phone_num=" + phone_num)
                .add("Accept", "application/json; q=0.5")
                .add("Accept", "application/vnd.github.v3+json").build();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .headers(headers)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static String okhttpGetNeedHead(String token, String phone_num, String url) {
        String responseBody = "";
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Authorization", "Token " + token + ",phone_num=" + phone_num)
                .add("Accept", "application/json; q=0.5")
                .add("Accept", "application/vnd.github.v3+json").build();
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .headers(headers)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    public static String okhttpGet(String url) {
        String responseBody = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
    public static String okhttpPost(FormBody formBody,String url) {
        String responseBody = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .post(formBody)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBody;
    }
    public static void okhttpPost(MultipartBody multipartBody, String url,Callback callback) {
        String responseBody = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .post(multipartBody)
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public static void okhttpPostEaseToken(final Context mContext) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"grant_type\": \"client_credentials\",\"client_id\": \"YXA6ULMuYL0hEeeUTDcts2J9xA\",\"client_secret\": \"YXA6kadiIRds67UHZfwcQkv1aZHU_Gg\"}";
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request1 = new Request.Builder()
                .post(requestBody)
                .url(BaseUrl.EASE_TOKEN)
                .cacheControl(new CacheControl.Builder().maxStale(1, TimeUnit.MINUTES).build())
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Content-Type", "application/json")
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call1 = client.newCall(request1);

        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OkhttpUtil", "Error------------->" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                EaseTokenBean easeTokenBean = EaseTokenBean.objectFromData(result);
                Log.d("OkhttpUtil", "onResponse: --------------------putSp");
                SpUtil.putString(mContext, SpUtil.SP_USER, SpUtil.EASE_TOKEN, easeTokenBean.getAccess_token());
                SpUtil.putInt(mContext, SpUtil.SP_USER, SpUtil.EASE_TOKEN_TIME, easeTokenBean.getExpires_in());
            }
        });
    }

    public static void okhttpPost(FormBody formBody, String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .post(formBody)
                .url(url)
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public static void okhttpPostWithHead(String token, String userPhone, FormBody formBody, String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Authorization", "Token " + token + ",phone_num=" + userPhone)
                .add("Accept", "application/json; q=0.5")
                .add("Content-Type", "application/json")
                .add("Accept", "application/vnd.github.v3+json").build();
        Request request = new Request.Builder()
                .post(formBody)
                .headers(headers)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public static void okhttpPostWithHead(String token, String userPhone, MultipartBody formBody, String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Authorization", "Token " + token + ",phone_num=" + userPhone)
                .add("Accept", "application/json; q=0.5")
                .add("Accept", "application/vnd.github.v3+json").build();
        Request request = new Request.Builder()
                .post(formBody)
                .headers(headers)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public static void okhttpGet(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void okhttpPut(String url, FormBody formBody, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .put(formBody)
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }



    public static void okhttpPutNeedHead(Context context, FormBody formBody, String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Authorization", "Token " + UserInf.getUserToken(context) + ",phone_num=" + UserInf.getUserPhonenum(context)).build();
        Request request = new Request.Builder()
                .put(formBody)
                .url(url)
                .headers(headers)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public static void okhttpPutNeedHead(String usertoken,String phonenum, FormBody formBody, String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder()
                .add("Authorization", "Token " + usertoken + ",phone_num=" + phonenum).build();
        Request request = new Request.Builder()
                .put(formBody)
                .url(url)
                .headers(headers)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
    public static void okhttpPutNeedHead(Context context, MultipartBody multipartBody, String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Headers headers = new Headers.Builder().add("Authorization", "Token " + UserInf.getUserToken(context) + ",phone_num=" + UserInf.getUserPhonenum(context)).build();
        Request request = new Request.Builder()
                .put(multipartBody)
                .url(url)
                .headers(headers)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }


    public static void uploadImg(String postUrl, ArrayList<String> mImgUrls) {
        OkHttpClient client = new OkHttpClient();
        // mImgUrls为存放图片的url集合
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i < mImgUrls.size(); i++) {
            File imgFile = new File(mImgUrls.get(i));
            if (imgFile != null) {
                builder.addFormDataPart("img",
                        imgFile.getName(),
                        RequestBody.create(MediaType.parse("image/png"), imgFile));
            }
        }
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(postUrl)//地址
                .post(requestBody)//添加请求体
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("上传照片成功：response = " + response.body().string());
            }

        });

    }


}
