package com.cqxy.fyb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.UserInf;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class UpdatePersonalActivity extends AppCompatActivity {
    public static int UPDATE_HEAD = 1;
    public static int UPDATE_NAME = 2;
    public static int UPDATE_SEX = 3;
    public static int UPDATE_SHOPAREA = 4;
    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.updateactivity_et)
    EditText updateactivityEt;
    @BindView(R.id.updateactivity_save)
    AppCompatButton updateactivitySave;
    @BindView(R.id.rg_update_personal)
    RadioGroup rgGender;

    private int type;
    private String TAG = "UpdatePersonalActivity";
    private String strGender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_personal);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 1);
        if(null != getIntent().getStringExtra("name"))
            updateactivityEt.setText(getIntent().getStringExtra("name"));
        if(null != getIntent().getStringExtra("sex"))
            updateactivityEt.setText(getIntent().getStringExtra("sex"));
        if(null != getIntent().getStringExtra("shoparea"))
            updateactivityEt.setText(getIntent().getStringExtra("shoparea"));
        setTitles(type);
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_male)
                    strGender = "男";
                else
                    strGender = "女";

            }
        });
    }

    public void setTitles(int type) {
        switch (type) {
            case 2:
                rgGender.setVisibility(View.GONE);
                titleText.setText("修改姓名");
                updateactivityEt.setVisibility(View.VISIBLE);
                break;
            case 3:
                titleText.setText("修改性别");
                rgGender.setVisibility(View.VISIBLE);
                updateactivityEt.setVisibility(View.GONE);
                break;
            case 4:
                rgGender.setVisibility(View.GONE);
                updateactivityEt.setVisibility(View.VISIBLE);
                titleText.setText("修改个性签名");
                break;
        }
    }

    @OnClick({R.id.house_enter_back, R.id.updateactivity_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
            case R.id.updateactivity_save:
                updateUser(type);
                break;
        }
    }


    private void updateUser(int type) {
        String puturl = "";
        if (type == 2) {
            puturl = "user[user_name]";
        } else if (type == 3) {
            puturl = "user[gender]";
        } else if (type == 4) {
//            puturl = "user[shangquan]";
            puturl = "user[qianming]";
        }
        Log.e("----type:", puturl);
        String toname = null;
        if (rgGender.getVisibility() == View.GONE)
            toname = updateactivityEt.getText().toString().trim();
        else
            toname = strGender;
        if (toname.equals("") || toname == null) {
            Toast.makeText(UpdatePersonalActivity.this, "没有任何修改，保存无效", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            Log.d(TAG, "updateUser: --------------------------data = " + toname);
            intent.putExtra("data", toname);
            setResult(type, intent);
            putPerferences(type, toname);
            FormBody formBody = new FormBody.Builder().add(puturl, toname).build();

            OkHttpUtils.okhttpPutNeedHead(this, formBody, BaseUrl.URL + "users/" + UserInf.getUserId(this) + ".json", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });

            Toast.makeText(UpdatePersonalActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
        }
        this.finish();


    }
//AppPreferences appPreferences = new AppPreferences(this);
    private void putPerferences(int type, String toname) {
        switch (type) {
            case 2:
                Log.d(TAG, "putPerferences: ------------name=" + toname);
                SpUtil.putString(this,SpUtil.SP_USER,SpUtil.USER_NAME, toname);
//                appPreferences.put(SpUtil.USER_NAME,toname);
                break;
            case 3:
                Log.d(TAG, "putPerferences: ------------gender=" + toname);
                SpUtil.putString(this,SpUtil.SP_USER,SpUtil.USER_GENDER, toname);
//                appPreferences.put(SpUtil.USER_GENDER,toname);
                break;
            case 4:
                Log.d(TAG, "putPerferences: ------------qianming=" + toname);
                SpUtil.putString(this,SpUtil.SP_USER,SpUtil.USER_QIANMING, toname);
//                appPreferences.put(SpUtil.USER_QIANMING,toname);
                break;
        }
    }
}
