package com.cqxy.fyb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.EaseTokenBean;
import com.cqxy.bean.EaseUserBean;
import com.cqxy.bean.UserSession;
import com.cqxy.bean.Users;
import com.cqxy.utils.ImageTask;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.cqxy.fyb.R.id.btn_code_reset;


public class ResetPwdActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_code_reset)
    Button btncode;
    @BindView(R.id.et_phonenum_reset)
    EditText etphoneNum;

    @BindView(R.id.btn_next)
    Button btnreset;
    @BindView(R.id.et_code_reset)
    EditText etcode;
    @BindView(R.id.iv_back)
    ImageView ivback;
    private int i = 60;
    private String phonenum;
    private MyHandler myHandler = new MyHandler(this);
    private EventHandler eventHandler;
    private static String TAG = "ResetPwdActivity";
    private String easetoken = "";
    private String usertoken = "";
    private int userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);
        phonenum = getIntent().getStringExtra("phone");
        if (null != phonenum)
            etphoneNum.setText(phonenum);

        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                myHandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        initListeners();
        postEaseToken();
    }

    private void initListeners() {
        btncode.setOnClickListener(this);
        btnreset.setOnClickListener(this);
        ivback.setOnClickListener(this);
        etcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 4) {
                    String code = etcode.getText().toString().trim();
                    phonenum = etphoneNum.getText().toString().trim();
                    if (TextUtils.isEmpty(phonenum)) {
                        Toast.makeText(getApplicationContext(), "手机号码不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else if (TextUtils.isEmpty(code)) {
                        Toast.makeText(getApplicationContext(), "验证码不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    myHandler.sendEmptyMessage(0x001);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case btn_code_reset:
                phonenum = etphoneNum.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    Toast.makeText(ResetPwdActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phonenum.length() != 11) {
                    Toast.makeText(ResetPwdActivity.this, "手机号码格式有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkHttpUtils.okhttpGet(BaseUrl.URL + "sessions/usersession/" + phonenum + ".json", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Gson gson = new Gson();
                        UserSession user = gson.fromJson(responseBody, UserSession.class);
                        UserSession.SessionBean sessionBean = user.getSession();
                        if (null == sessionBean) {

                        } else {
                            //存入用户信息
                            usertoken = sessionBean.getToken();
                            SpUtil.putString(ResetPwdActivity.this, SpUtil.SP_USER, SpUtil.USER_TOKEN, sessionBean.getToken());
                            SpUtil.putString(ResetPwdActivity.this, SpUtil.SP_USER, SpUtil.USER_PHONENUM, sessionBean.getPhone_num());
                            SpUtil.putInt(ResetPwdActivity.this, SpUtil.SP_USER, SpUtil.USER_ID, sessionBean.getId());
//                            AppPreferences appPreferences = new AppPreferences(ResetPwdActivity.this);
//                            appPreferences.put(SpUtil.USER_TOKEN,sessionBean.getToken());
//                            appPreferences.put(SpUtil.USER_PHONENUM,sessionBean.getPhone_num());
//                            appPreferences.put(SpUtil.USER_ID,sessionBean.getId());
                        }
                    }
                });
                SMSSDK.getVerificationCode("86", phonenum);
                if (null == easetoken)
                    postEaseToken();
                btncode.setClickable(false);
                //开始倒计时
                countdown.start();
                break;
            case R.id.btn_next:
                phonenum = etphoneNum.getText().toString().trim();

                resetPwd();
                break;
            case R.id.iv_back:
                this.finish();
                break;
        }
    }

    CountDownThread countdown = new CountDownThread();

    private void resetPwd() {
        //resetEASE
        //resetRails
        //2.判断是否是用户
        //3.结果提示给用户
        if (iteaseExist.getStatus() != AsyncTask.Status.PENDING)
            return;
        iteaseExist.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        itrailsExit.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //3.1不是用户，跳转到注册
        //3.2是用户，开放密码输入栏的权限
    }


    /**
     * 获取ease的token
     */
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                EaseTokenBean easeTokenBean = EaseTokenBean.objectFromData(result);
                String access_token = easeTokenBean.getAccess_token();
                easetoken = access_token;
                SpUtil.putString(ResetPwdActivity.this, SpUtil.SP_USER, SpUtil.EASE_TOKEN, access_token);
                SpUtil.putInt(ResetPwdActivity.this, SpUtil.SP_USER, SpUtil.EASE_TOKEN_TIME, easeTokenBean.getExpires_in());
//                AppPreferences appPreferences = new AppPreferences(ResetPwdActivity.this);
//                appPreferences.put(SpUtil.EASE_TOKEN,access_token);
//                appPreferences.put(SpUtil.EASE_TOKEN_TIME,easeTokenBean.getExpires_in());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    /**
     * 实现验证码倒计时
     */
    private class MyHandler extends Handler {
        private final WeakReference<ResetPwdActivity> mActivty;
        private ResetPwdActivity act;

        public MyHandler(ResetPwdActivity activity) {
            mActivty = new WeakReference<ResetPwdActivity>(activity);
            this.act = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                //修改控件文本进行倒计时  i 以60秒倒计时为例
                act.btncode.setText(act.i + " s");
            } else if (msg.what == -2) {
//                Log.d(TAG, "handleMessage: =-====================结束倒计时");有bug,结束倒计时无效
                //修改控件文本，进行重新发送验证码
                countdown.interrupt();
                act.btncode.setText("重新发送");
                act.btncode.setClickable(true);
                act.i = 60;
            } else if (msg.what == 0x001) {
                //TODO：1.send msg 2.verifiy the code
                Log.d(TAG, "handleMessage: ----------------phonenum = " + phonenum + "\netcode.getText().toString()");
                SMSSDK.submitVerificationCode("86", phonenum, etcode.getText().toString());
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.d(TAG, "handleMessage: -------------------event = " + event + "\nresult = " + result);
                // 短信注册成功后，返回MainActivity,然后提示
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //验证完成
                        Log.d(TAG, "handleMessage: -------------------验证完成-------");
                    }


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(act, "验证码已经发送",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ((Throwable) data).printStackTrace();
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Log.d(TAG, "handleMessage: -----------------------result  = 0 error");
                        myHandler.sendEmptyMessage(-2);
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            Toast.makeText(act, des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
            }
        }
    }

    ImageTask iteaseExist = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            String responseBody = "";
            OkHttpClient client = new OkHttpClient();
            String url = BaseUrl.EASE_GET_USER + phonenum;
            String token = "Bearer " + easetoken;
            Log.d(TAG, "doRequest: url = " + url + "\ntoken = " + token);
            Request request = new Request.Builder()
                    .get()
                    .header("User-Agent", "OkHttp Headers.java")
                    .addHeader("Accept", "application/json; q=0.5")
                    .addHeader("Authorization", token)
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                responseBody = response.body().string();
                return responseBody;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseBody;
        }

        @Override
        public Object doPraseResult(Object object) {
            String responseBody = (String) object;
            if ("" == responseBody)
                return null;
            EaseUserBean easeUserBean = EaseUserBean.objectFromData(responseBody);
            if (null != easeUserBean && null == easeUserBean.getAction()) {
                return null;
            } else {
                return easeUserBean;
            }
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            EaseUserBean easeUserBean = (EaseUserBean) object;
            if (null == easeUserBean && null == easeUserBean.getAction()) {
                Log.d(TAG, "onSuccess: -------easeuserbean ========null");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ResetPwdActivity.this, "ease用户未注册！", Toast.LENGTH_SHORT);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ResetPwdActivity.this, "ease用户手机号正确！", Toast.LENGTH_SHORT);
                        Intent intent = new Intent(ResetPwdActivity.this, ResetPwdIIActivity.class);
                        Bundle value = new Bundle();
                        value.putString("easetoken", easetoken);
                        value.putString("railstoken", usertoken);
                        value.putString("phone", phonenum);
                        value.putInt("userid", userid);
                        intent.putExtra("data", value);
                        startActivity(intent);
                        ResetPwdActivity.this.finish();
                    }
                });

            }
        }

        @Override
        public void onError() {
            ToastUtil.showToast(ResetPwdActivity.this,"用户不存在，请重新注册",Toast.LENGTH_SHORT);
            startActivity(new Intent(ResetPwdActivity.this,RegisterActivity.class));
            ResetPwdActivity.this.finish();
        }
    });


    ImageTask itrailsExit = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            String responseBody = OkHttpUtils.okhttpGet(BaseUrl.URL + "users/finduser/" + phonenum + ".json");
            return responseBody;
        }

        @Override
        public Object doPraseResult(Object object) {
            String responseBody = (String) object;
            Users users = Users.objectFromData(responseBody, "user");
            if (null == users)
                return null;
            else
                return users;
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            Users user = (Users) object;
            if (null != user.getCreated_at()) {
                userid = user.getId();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ResetPwdActivity.this, "rails用户手机号正确！", Toast.LENGTH_SHORT);
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ResetPwdActivity.this, "rails未注册！", Toast.LENGTH_SHORT);
                    }
                });
            }
        }

        @Override
        public void onError() {
        }
    });


    /**
     * 查看是否存在该用户
     *
     * @param token
     * @param usertel
     */
    private String checkEaseUser(String token, String usertel) {
        String strResponse = "";
        //get user base on tel
        Log.d(TAG, "checkEaseUser: -----------------token = " + token + "\nusertel = " + usertel);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Authorization", "Bearer " + token)
                .url(BaseUrl.EASE_GET_USER + usertel)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.d(TAG, "checkEaseUser: -------------rsponse.body = " + response.body().string());
            if (response.isSuccessful()) {
                strResponse = response.body().string();
                Log.d(TAG, "checkEaseUser: -------------------strResponse = " + strResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    class CountDownThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                for (; i > 0; i--) {
                    myHandler.sendEmptyMessage(-1);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //倒计时结束执行
                myHandler.sendEmptyMessage(-2);
            }
        }
    }

    @Override
    protected void onStop() {
        countdown.interrupt();
        super.onStop();
    }
}
