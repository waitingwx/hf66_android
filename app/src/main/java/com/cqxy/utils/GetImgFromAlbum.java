package com.cqxy.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by lpl on 17-9-22.
 */

public class GetImgFromAlbum {
    //根据自己需求更改对应到Activity或者Fragment
    public static int i = 0;
    public static ArrayList<String > imgUrls = new ArrayList<>();

    public static void selectPicFromLocal(Fragment fragment, int REQUEST_CODE_LOCAL)
    {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        fragment.startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }
    public static void selectPicFromLocal(AppCompatActivity activity, int REQUEST_CODE_LOCAL)
    {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        activity.startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    public static  String CodeActivityResult(int requestCode, int resultCode, Intent data, int REQUEST_CODE_LOCAL, ImageView imageView, Fragment fragment,ImageView []selectimgs,int index )
    {
        i++;
        String picUrl = null;
        //获取图片路径,没有点返回时
        if (requestCode == REQUEST_CODE_LOCAL && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                showPhotos(selectimgs,index);
                picUrl = sendPicByUri(selectedImage, imageView, fragment);
                imgUrls.add(picUrl);
            }
        }

        Log.d("ImgFromAlbum", "CodeActivityResult: 执行了"+i);
        return picUrl;
    }
    public static  String CodeActivityResult(int requestCode, int resultCode, Intent data, int REQUEST_CODE_LOCAL, ImageView imageView, AppCompatActivity activity )
    {
        String picUrl = null;
        //获取图片路径,没有点返回时
        if (requestCode == REQUEST_CODE_LOCAL && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                picUrl = sendPicByUri(selectedImage, imageView, activity);
            }
        }
        return picUrl;
    }
    public static  void CodeActivityResult(int requestCode, int resultCode, Intent data, int REQUEST_CODE_LOCAL, ImageView imageView, Fragment fragment)
    {
        //获取图片路径,没有点返回时
        if (requestCode == REQUEST_CODE_LOCAL && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String picUrl = sendPicByUri(selectedImage, imageView, fragment);
            }
        }


    }

    public static String sendPicByUri(Uri selectedImage,ImageView imageview,Fragment fragment) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        String picturePath = null;
        Cursor cursor = fragment.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("-----path",picturePath+"");
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(fragment.getActivity(), com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;
            }
            showImage(picturePath,imageview);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(fragment.getActivity(), com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;

            }
            showImage(file.getAbsolutePath(),imageview);
        }
        return picturePath;

    }
    public static String sendPicByUri(Uri selectedImage,ImageView imageview,AppCompatActivity activity) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        String picturePath = null;
        Cursor cursor = activity.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("-----path",picturePath);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(activity, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;
            }
            showImage(picturePath,imageview);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(activity, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return null;

            }
            showImage(file.getAbsolutePath(),imageview);
        }
        return picturePath;

    }

    //加载图片
    public  static  void showImage(String imaePath, ImageView imageView) {
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(bm, 100, 100);
        imageView.setImageBitmap(bitmap);
    }


    public static void showPhotos(ImageView []selectphotos,int index)
    {
        for (int i = 0; i < selectphotos.length; i++) {
            if (i==index&&i<7)
            {
                selectphotos[i+1].setVisibility(View.VISIBLE);
            }
        }
    }


}
