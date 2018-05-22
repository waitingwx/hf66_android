package com.cqxy.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;

/**
 * Created by wx on 2017/11/1.
 */

public class ImageTask extends AsyncTask<Object,Void,Object> {
    private IRequest iRequest;
    private IDownloadImage iDownloadBitmap;
    private IRequestCallback iRequestCallback;
    private IDownloadFile iDownloadFile;
    //下载json数据使用的构造方法
    public ImageTask(IRequest iRequest, IRequestCallback iRequestCallback) {
        //如果传进来的值为空，那么就抛出异常
        if(iRequest == null || iRequestCallback == null) {
            throw new NullPointerException("IRequest and IRequestCallback can not be null");
        }
        this.iRequest = iRequest;
        this.iRequestCallback = iRequestCallback;
    }

    //下载Bitmap使用的构造方法
    public ImageTask(IDownloadImage iDownloadBitmap, IRequestCallback iRequestCallback) {
        //如果传进来的值为空，那么就抛出异常
        if(iDownloadBitmap == null || iRequestCallback == null) {
            throw new NullPointerException("IDownloadImage and IRequestCallback can not be null");
        }
        this.iDownloadBitmap = iDownloadBitmap;
        this.iRequestCallback = iRequestCallback;
    }

    //下载文件使用的构造方法
    public ImageTask(IDownloadFile iDownloadFile,IRequestCallback iRequestCallback){
        //如果传进来的值为空，那么就抛出异常
        if(iDownloadBitmap == null || iRequestCallback == null) {
            throw new NullPointerException("IDownloadFile and IRequestCallback can not be null");
        }
        this.iDownloadFile = iDownloadFile;
        this.iRequestCallback = iRequestCallback;
    }

    /**
     *执行耗时操作
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(Object... params) {

        //如果iDownloadImage接口对象不为空认为是要下载bitmap
        if(null != iDownloadBitmap) {
            return iDownloadBitmap.downloadBitmap();
        }

        //如果直接传进来的值，认为是本地json,可以直接解析
        if( params != null && params.length > 0 ) {
            return iRequest.doPraseResult(params[0]);

        }

        Object result = iRequest.doRequest();
        //如果请求失败，那么直接返回
        if(result == null) {
            return result;
        }
        //返回解析后的json数据
        return iRequest.doPraseResult(result);
    }

    @Override
    protected void onPostExecute(Object o) {
        //如果解析后的结果为空，那么就调用onerror()方法；
        if(o == null) {
            iRequestCallback.onError();
        }else {
            //请求成功并返回结果
            iRequestCallback.onSuccess(o);
        }
    }


    /**
     * 网络请求数据接口
     */
    public interface IRequest {
        //请求网络，返回json数据
        Object doRequest();
        //解析json数据
        Object doPraseResult(Object object);
    }

    /**
     * 当下载json数据成功解析后调用
     */
    public interface IRequestCallback {
        void onSuccess(Object object);
        void onError();
    }

    /**
     * 下载图片的接口
     */
    public interface IDownloadImage {
        Bitmap downloadBitmap();

    }

    public interface IDownloadFile {
        File downloadFile();
    }
}
