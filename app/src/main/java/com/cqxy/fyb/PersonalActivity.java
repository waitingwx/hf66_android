package com.cqxy.fyb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.utils.GetImgFromAlbum;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.UserInf;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.bumptech.glide.request.RequestOptions.overrideOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;

public class PersonalActivity extends AppCompatActivity {

    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.personal_activity_head)
    ImageView personalActivityHead;
    @BindView(R.id.personal_activity_headlayout)
    LinearLayout personalActivityHeadlayout;
    @BindView(R.id.personal_activity_name)
    TextView personalActivityName;
    @BindView(R.id.personal_activity_namelayout)
    LinearLayout personalActivityNamelayout;
    @BindView(R.id.personal_activity_sex)
    TextView personalActivitySex;
    @BindView(R.id.personal_activity_sexlayout)
    LinearLayout personalActivitySexlayout;
    @BindView(R.id.personal_activity_shoparea)
    TextView personalActivityShoparea;
    @BindView(R.id.personal_activity_shoparealayout)
    LinearLayout personalActivityShoparealayout;
    private int REQUEST_CODE = 0;
    private static String TAG = "PersonalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        titleText.setText("个人信息");
        showAvatar();
        setAllTestFromPerference();
    }

    private void showAvatar() {
        String userLocalAvatar = UserInf.getUserLocalAvatar(this);
        String userRemoteAvatar = UserInf.getUserRemoteAvatar(this);
        if (null == userLocalAvatar && null == userRemoteAvatar)
            return;
        if (userLocalAvatar != "") {
            Log.d(TAG, "showAvatar: ---------------userLocalAvatar = " + userLocalAvatar);
            GetImgFromAlbum.showImage(userLocalAvatar, personalActivityHead);
            return;
        }
        if (userRemoteAvatar != "") {
            Log.d(TAG, "showAvatar: ---------------userRemoteAvatar = " + userRemoteAvatar);
            Glide.with(this)
                    .load(userRemoteAvatar)
                    .apply(overrideOf(40, 40))
                    .apply(placeholderOf(R.mipmap.mine_man))
                    .into(personalActivityHead);
        } else {
            Log.d(TAG, "showAvatar: -------------else");
            remotePic();
        }
    }

    private void remotePic() {
        Log.d(TAG, "RemotePic: -------------------start");
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(this), UserInf.getUserPhonenum(this),
                BaseUrl.URL + "users/" + UserInf.getUserId(this) + ".json", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("users", "onResponse: ===============string" + string);
                        final Users users = Users.objectFromData(string, "user");
                        if (null == users)
                            return;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int endIndex = users.getAsset().indexOf("?");
                                if (endIndex == -1)
                                    return;
                                String substring = users.getAsset().substring(0, endIndex);
                                SpUtil.putString(PersonalActivity.this, SpUtil.SP_USER, SpUtil.USER_ASSET, BaseUrl.IMGURL + substring);
                                Glide.with(PersonalActivity.this)
                                        .load(BaseUrl.IMGURL + substring)
                                        .apply(placeholderOf(R.mipmap.mine_man))
                                        .into(personalActivityHead);
                            }
                        });
                    }
                });
    }

    private void setAllTestFromPerference() {
        personalActivityName.setText(UserInf.getUserName(this));
        personalActivitySex.setText(UserInf.getUserGender(this));
        personalActivityShoparea.setText(UserInf.getUserQianming(this));
    }

    @OnClick({R.id.house_enter_back, R.id.personal_activity_headlayout, R.id.personal_activity_namelayout, R.id.personal_activity_sexlayout, R.id.personal_activity_shoparealayout})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, UpdatePersonalActivity.class);
        intent.putExtra("type", UpdatePersonalActivity.UPDATE_HEAD);
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
            case R.id.personal_activity_headlayout:
                GetImgFromAlbum.selectPicFromLocal(PersonalActivity.this, 0x000);
                break;
            case R.id.personal_activity_namelayout:
                intent.putExtra("type", UpdatePersonalActivity.UPDATE_NAME);
                intent.putExtra("name",personalActivityName.getText().toString());
                REQUEST_CODE = 0x001;
                break;
            case R.id.personal_activity_sexlayout:
                intent.putExtra("type", UpdatePersonalActivity.UPDATE_SEX);
                intent.putExtra("sex",personalActivitySex.getText().toString());
                REQUEST_CODE = 0x002;
                break;
            case R.id.personal_activity_shoparealayout:
                intent.putExtra("type", UpdatePersonalActivity.UPDATE_SHOPAREA);
                intent.putExtra("shoparea",personalActivityShoparea.getText().toString());
                REQUEST_CODE = 0x003;
                break;
        }
        if (view.getId() != R.id.house_enter_back && view.getId() != R.id.personal_activity_headlayout) {
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    Intent returnIntent = new Intent();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data)
            return;
        String dataStringExtra = data.getStringExtra("data");
        switch (requestCode) {
            case 0x000://pic
                String picUrl = GetImgFromAlbum.CodeActivityResult(requestCode, resultCode, data, 0x000, personalActivityHead, this);
                Log.d(TAG, "onActivityResult: ----------------------picUrl = " + picUrl);
                returnIntent.putExtra("picurl", picUrl);
                SpUtil.putString(PersonalActivity.this, SpUtil.SP_USER, SpUtil.USER_LOCALAVATAR, picUrl);
                commitToRemote(picUrl);//提交到服务器
                break;
            case 0x001://name
                returnIntent.putExtra("name", dataStringExtra);
                personalActivityName.setText(dataStringExtra);
                break;
            case 0x002://gender
                personalActivitySex.setText(dataStringExtra);
                break;
            case 0x003://area
                personalActivityShoparea.setText(dataStringExtra);
                break;
        }
        setResult(10010, returnIntent);
    }

    private void commitToRemote(String picUrl) {
        Log.d(TAG, "commitToRemote: --------------------picurl = " + picUrl);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File imgFile = new File(picUrl);
        if (imgFile != null) {
            Log.d(TAG, "commitToRemote: -----------------------imfile != null");
            builder.addFormDataPart("user[asset]",
                    imgFile.getName(),
                    RequestBody.create(MediaType.parse("image/*"), imgFile));
        }
        MultipartBody mpb = builder.build();
        OkHttpUtils.okhttpPutNeedHead(this, mpb, BaseUrl.URL + "users/" + UserInf.getUserId(this) + ".json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("PersonalActivity", "onResponse: ===============response.body = " + response.body().string());
            }
        });
    }
}
