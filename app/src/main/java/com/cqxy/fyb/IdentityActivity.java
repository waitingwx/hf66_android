package com.cqxy.fyb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HideLodingEvent;
import com.cqxy.bean.ShowLoadingEvent;
import com.cqxy.bean.Users;
import com.cqxy.utils.GetImgFromAlbum;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.cqxy.view.LoadingView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IdentityActivity extends AppCompatActivity {


    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.iv_idfront)
    ImageView ivfront;
    @BindView(R.id.iv_idback)
    ImageView ivback;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tv_stateb)
    TextView tvState;
    @BindView(R.id.tv_adda)
    TextView tva;
    @BindView(R.id.tv_addb)
    TextView tvb;
    @BindView(R.id.btn_upload)
    Button btnUpload;
    private int index = 0;
    private ImageView[] imgs = new ImageView[]{ivfront, ivback};
    private ArrayList<String> picStr = new ArrayList<>();

    //调用系统相册-选择图片
    protected static final int REQUEST_CODE_LOCAL = 1;

    private static String TAG = "IdentityActivity";
    private LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        ButterKnife.bind(this);
        titleText.setText("身份信息");
        EventBus.getDefault().register(this);
        loadingView = ((LoadingView) findViewById(R.id.lv_loading));
        loadInfo();
    }

    private void loadInfo() {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(this),
                UserInf.getUserPhonenum(this), BaseUrl.URL + "users/" + UserInf.getUserId(this), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: e.message = " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "onResponse: ------------responseBody = " + responseBody);
                        final Users users = Users.objectFromData(responseBody, "user");
                        if (null == users)
                            return;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvState.setText(users.getIdcheck());
                                String idfront = users.getIdfront();
                                if (idfront.indexOf("?") == -1)
                                    return;
                                idfront = idfront.substring(0, idfront.indexOf("?"));
                                Glide.with(IdentityActivity.this)
                                        .load(BaseUrl.IMGURL + idfront)
                                        .into(ivfront);
                                String idback = users.getIdback();
                                if (idback.indexOf("?") == -1)
                                    return;
                                idback = idback.substring(0, idback.indexOf("?"));
                                Glide.with(IdentityActivity.this)
                                        .load(BaseUrl.IMGURL + idback)
                                        .into(ivback);
                                btnUpload.setVisibility(View.GONE);
                                ivback.setFocusable(false);
                                ivback.setClickable(false);
                                ivfront.setClickable(false);
                                ivfront.setClickable(false);
                                tva.setVisibility(View.GONE);
                                tvb.setVisibility(View.GONE);
                            }
                        });
                    }
                });
    }

    @OnClick({
            R.id.iv_idback,
            R.id.iv_idfront,
            R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_idback:
                index = 1;
                break;
            case R.id.iv_idfront:
                index = 0;
                break;
        }
        if (view.getId() == R.id.iv_idback || view.getId() == R.id.iv_idfront)
            GetImgFromAlbum.selectPicFromLocal(this, REQUEST_CODE_LOCAL);
    }

    @OnClick({R.id.btn_upload,
            R.id.house_enter_back})
    public void uploadPic(View view) {
        switch (view.getId()) {
            case R.id.btn_upload:
                uploadId();
                break;
            case R.id.house_enter_back:
                this.finish();
                break;
        }
    }

    private void uploadId() {
        EventBus.getDefault().post(new ShowLoadingEvent());
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Log.d(TAG, "uploadId: ----------picstr.size =" + picStr.size());
        addListImages(builder, picStr);
        builder.addFormDataPart("userid", UserInf.getUserId(this) + "");
        MultipartBody multipartBody = builder.build();
        OkHttpUtils.okhttpPost(multipartBody, BaseUrl.URL + "users/idcard", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: --------------string = " + string);
                final Users user = Users.objectFromData(string, "user");
                EventBus.getDefault().post(new HideLodingEvent());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String idcheck = user.getIdcheck();
                        Log.d(TAG, "run: --------------------idcheck = " + idcheck);
                        tvState.setText(null == idcheck ? "":idcheck);
                        btnUpload.setVisibility(View.GONE);
                        ivback.setFocusable(false);
                        ivback.setClickable(false);
                        ivfront.setClickable(false);
                        ivfront.setClickable(false);
                    }
                });
            }
        });
    }

    private void addListImages(MultipartBody.Builder builder, ArrayList<String> mImgUrls) {
        Log.d(TAG, "onViewClicked: ==============mImageUrls.size = " + mImgUrls.size());
        for (int i = 0; i < mImgUrls.size(); i++) {
            File imgFile = new File(mImgUrls.get(i));
            if (imgFile != null && i == 0) {
                builder.addFormDataPart("idfront",
                        imgFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), imgFile));
            }
            if (imgFile != null && i == 1) {
                builder.addFormDataPart("idback",
                        imgFile.getName(),
                        RequestBody.create(MediaType.parse("image/*"), imgFile));
            }
        }
        mImgUrls.clear();
        GetImgFromAlbum.imgUrls.clear();//使用完成后清空，避免提交多次相同数据，TODO：查看是否需要去重
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgs = new ImageView[]{ivfront, ivback};
        picStr.add(GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, REQUEST_CODE_LOCAL, imgs[index], this));
    }

    //去主线程中更改UI
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ShowLoadingEvent event) {
        Log.d(TAG, "onEventMainThread: -----------------------");
        loadingView.startAnimation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HideLodingEvent event) {
        Log.d(TAG, "onEventMainThread: ----------------------");
        loadingView.stopAnimation();
    }
}
