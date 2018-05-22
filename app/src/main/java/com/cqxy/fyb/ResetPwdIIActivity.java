package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.ToastUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResetPwdIIActivity extends AppCompatActivity {
    @BindView(R.id.et_pwd_reset)
    EditText etPwd;
    @BindView(R.id.et_repwd_reset)
    EditText etRepwd;
    private static String TAG = "ResetPwdIIActivity";
    private String easetoken="";
    private String railstoken="";
    private String phone="";
    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwdii);
        ButterKnife.bind(this);
        Bundle data = getIntent().getBundleExtra("data");
        easetoken = data.getString("easetoken");
        railstoken = data.getString("railstoken");
        phone = data.getString("phone");
        userid = data.getInt("userid");
        Log.d(TAG, "onCreate: easetoken = " + easetoken+"\nrailstoken = " + railstoken+"\nphone = " + phone+"\nuserid =" +userid);
    }

    @OnClick({R.id.btn_reset})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                String password = etPwd.getText().toString().trim();
                String repassword = etRepwd.getText().toString().trim();
                if (password.isEmpty() || repassword.isEmpty() || password.equals("") || repassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "密码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(repassword)) {
                    Toast.makeText(getApplicationContext(), "两次输入密码不一致，重新输入",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                okhttpPutEasepwd(easetoken,phone,password);
                uploadpwd(userid,password,railstoken,phone);
                break;
        }
    }
    private void okhttpPutEasepwd(String token, String userTel, String newpwd) {
        Log.d(TAG, "okhttpPutEasepwd: -=-----------------------token = " + token + "\nusertel = " + userTel + "\nnewpwd = " + newpwd);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = String.format("{\"newpassword\": \"%s\"}", newpwd);
        Log.d(TAG, "okhttpPutEasepwd: -------------json - " + json);
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request1 = new Request.Builder()
                .put(requestBody)
                .url(String.format("http://a1.easemob.com/1160171030178595/hf66/users/%s/password", userTel))
                .cacheControl(new CacheControl.Builder().maxStale(1, TimeUnit.MINUTES).build())
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call1 = client.newCall(request1);

        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "-----put =======Error------------->" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                Log.d(TAG, "put -----result - ----->" + result);
                ResetPwdIIActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ResetPwdIIActivity.this, "修改密码成功", Toast.LENGTH_SHORT);
                        ResetPwdIIActivity.this.finish();
                    }
                });
            }
        });
    }
    private void uploadpwd(int userid, String pwd,String usertoken,String phonenum) {
        Log.d(TAG, "uploadpwd: ------------start -=====userid = " + userid + "\npwd = " + pwd + "\nusertoken = " + usertoken);
        FormBody formBody = new FormBody.Builder().add("user[password]", pwd).build();
        OkHttpUtils.okhttpPutNeedHead(usertoken, phonenum,
                formBody,
                BaseUrl.URL + "users/" + userid + ".json",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ------------e.tostring = " + e.getMessage().toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "onResponse: ---------------responseBody = " + responseBody);
                    }
                });
    }
}
