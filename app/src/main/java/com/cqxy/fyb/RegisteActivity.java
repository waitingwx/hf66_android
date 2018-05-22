package com.cqxy.fyb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class RegisteActivity extends AppCompatActivity {

    private TextView titleText;
    private ImageView ivBack;

    private TextInputLayout tilTEL, tilName;
    private EditText etTel, etName;
    private Button btnAffirm;
    private Button btnSkip;
    public static String TAG = "RegisteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        findviews();
        if (getIntent().getFlags() == 0x001)
            btnSkip.setVisibility(View.GONE);

        if (UserInf.getUserRecommend(this) == "")
            getUserInfo();
        else {
            disableInput(UserInf.getUserRecommend(this), UserInf.getUserRecommName(this));
        }
    }

    private void disableInput(String recommend, String username) {
        Log.d(TAG, "disableInput: ----------------recommend = " + recommend);
        tilName.setVisibility(View.VISIBLE);
        etName.setText(username);
        etTel.setText(recommend);
        tilTEL.setHint("");
        btnAffirm.setVisibility(View.GONE);
        etTel.setFocusable(false);
        etTel.setFocusableInTouchMode(false);
    }


    private void getUserInfo() {
        String url = BaseUrl.URL + "users/" + UserInf.getUserId(this);
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(this), UserInf.getUserPhonenum(this), url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                final Users user = Users.objectFromData(responseBody,"user");
                if (null != user && user.getRecommend() != "")
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            disableInput(user.getRecommend(), user.getRecomm_name());
                        }
                    });
                else
                    Log.d(TAG, "onResponse: ---------------responseBody = " + responseBody);
            }
        });
    }


    private void findviews() {
        titleText = (TextView) findViewById(R.id.tv_registe_title);
        titleText.setText("推荐人");
        btnSkip = ((Button) findViewById(R.id.btn_skip));
        ivBack = (ImageView) findViewById(R.id.iv_registe_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisteActivity.this.finish();
            }
        });
        tilTEL = ((TextInputLayout) findViewById(R.id.til_recommend_tel));
        tilName = ((TextInputLayout) findViewById(R.id.til_recommend_name));
        etTel = ((EditText) findViewById(R.id.et_recommend_tel));
        etName = ((EditText) findViewById(R.id.et_recommend_name));
        btnAffirm = ((Button) findViewById(R.id.btn_affirm));
        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (RegisteActivity.this.getIntent().getFlags()) {
                    case 0x001://个人中心
                        modifyRecommend();
                        RegisteActivity.this.finish();
                        break;
                    case 0x002://登录
                        startActivity(new Intent(RegisteActivity.this, RegisterActivity.class).putExtra("recommend", etTel.getText().toString()));
                        RegisteActivity.this.finish();
                        break;
                }

            }
        });
        etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (0 == s.length())
                    tilTEL.setHint("请输入您的推荐人！");
                if (11 == s.length()) {
                    chekUser(s.toString());
                }
            }
        });
    }

    private void modifyRecommend() {
        FormBody formBody = new FormBody.Builder().add("user[recommend]", etTel.getText().toString()).build();
        OkHttpUtils.okhttpPutNeedHead(UserInf.getUserToken(RegisteActivity.this),
                UserInf.getUserPhonenum(RegisteActivity.this),
                formBody,
                BaseUrl.URL + "users/" + UserInf.getUserId(this),
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d("RegisteActivity", "onResponse: ----------responseBody = " + response.body().string());
                    }
                });
    }

    private void chekUser(String phonenum) {
        OkHttpUtils.okhttpGet(BaseUrl.URL + "users/finduser/" + phonenum, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                final Users user = Users.objectFromData(responseBody, "user");
                if (null == user)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tilTEL.setHint("您的推荐人没有用这个号码注册！");

                        }
                    });
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            tilTEL.setHint("推荐人验证成功！");
                            tilName.setVisibility(View.VISIBLE);
                            etName.setText(user.getUser_name());
                        }
                    });
            }
        });
    }

    public void skiprecommend(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        this.finish();
    }
}
