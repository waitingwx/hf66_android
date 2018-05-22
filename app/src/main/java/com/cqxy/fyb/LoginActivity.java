package com.cqxy.fyb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.EaseTokenBean;
import com.cqxy.bean.UserSession;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import net.grandcentrix.tray.AppPreferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.Login_phone)
    EditText Login_phone;

    @BindView(R.id.Login_password)
    EditText Login_password;

    @BindView(R.id.Register)
    TextView Register;

    @BindView(R.id.ForgetPwd)
    TextView ForgetPwd;

    @BindView(R.id.Login)
    Button login;

    private String TAG = "LoginActivity";
    private String easeToken = "";
    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        postEaseToken();
        initListeners();
        isLogin();
//        appPreferences = new AppPreferences(this);
    }
    private void postEaseToken() {
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
                Log.e(TAG, "Error------------->" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                EaseTokenBean easeTokenBean = EaseTokenBean.objectFromData(result);
                Log.d(TAG, "onResponse: --------------------putSp");
                String access_token = easeTokenBean.getAccess_token();
                easeToken = access_token;
                SpUtil.putString(LoginActivity.this, SpUtil.SP_USER, SpUtil.EASE_TOKEN, access_token);
                SpUtil.putInt(LoginActivity.this, SpUtil.SP_USER, SpUtil.EASE_TOKEN_TIME, easeTokenBean.getExpires_in());
//                appPreferences.put(SpUtil.EASE_TOKEN,access_token);
//                appPreferences.put(SpUtil.EASE_TOKEN_TIME,easeTokenBean.getExpires_in());
            }
        });
    }

    public void isLogin() {
        if (EMClient.getInstance().isLoggedInBefore()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
    }


    private void initListeners() {
        Register.setOnClickListener(this);
        ForgetPwd.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Register:
                startActivity(new Intent(LoginActivity.this, RegisteActivity.class).addFlags(0x002));
                break;
            case R.id.ForgetPwd:
                Intent intent = new Intent(LoginActivity.this, ResetPwdActivity.class);
                String phone = Login_phone.getText().toString();
                intent.putExtra("phone",phone);
                intent.putExtra("easetoken",easeToken);
                startActivityForResult(intent,0x001);
                break;
            case R.id.Login:
                HuanXinLogin();
                break;
        }
    }

    private void HuanXinLogin() {

        final String phonenum = Login_phone.getText().toString().trim();
        final String password = Login_password.getText().toString().trim();

        if (phonenum.equals("") || phonenum.isEmpty() || password.equals("") || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadRegisteInfo(phonenum, password);
        EMClient.getInstance().login(phonenum, password, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: ------------------环信登录成功------------begin");
                //聊天会话初始化
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                Log.d(TAG, "onSuccess: ------------------环信登录成功------------end");
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, final String message) {
                Log.d(TAG, "onError: code = " + code + ",\nmessage = " + message);
            }
        });
    }

    private void uploadRegisteInfo(String phonenum, String password) {
        Log.d(TAG, "uploadRegisteInfo: ----------------------开始登陆服务器");
        //获得用户token
        FormBody formBody = new FormBody.Builder().add("user[phone_num]", phonenum).add("user[password]", password).build();
        OkHttpUtils.okhttpPost(formBody, BaseUrl.URL + "sessions.json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Log.d(TAG, "onResponse: =================responseBody = " + responseBody);
                Gson gson = new Gson();
                UserSession user = gson.fromJson(responseBody, UserSession.class);
                UserSession.SessionBean sessionBean = user.getSession();
                if (null == sessionBean) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Login_password.setText("");
                            Toast.makeText(LoginActivity.this, "您的手机或密码输入错误，请重新输入！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    //存入用户信息
                    SpUtil.putInt(LoginActivity.this,SpUtil.SP_USER,SpUtil.USER_ID, sessionBean.getId());
                    Log.d(TAG, "onResponse:  ===========sessionBean.getid = " + sessionBean.getId());
                    SpUtil.putString(LoginActivity.this,SpUtil.SP_USER,SpUtil.USER_TOKEN, sessionBean.getToken());
                    SpUtil.putString(LoginActivity.this,SpUtil.SP_USER,SpUtil.USER_PHONENUM, sessionBean.getPhone_num());
//                    appPreferences.put(SpUtil.USER_ID,sessionBean.getId());
//                    appPreferences.put(SpUtil.USER_TOKEN,sessionBean.getToken());
//                    appPreferences.put(SpUtil.USER_PHONENUM,sessionBean.getPhone_num());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    Log.d(TAG, "onResponse: ===========================结束登陆");
                }
            }
        });
    }


}
