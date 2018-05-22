package com.cqxy.fyb;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.utils.OkHttpUtils;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EventHandler eventHandler;
    @BindView(R.id.register_getcode)
    Button register_getcode;
    @BindView(R.id.register_phonenum)
    EditText register_phonenum;
    @BindView(R.id.Register_password)
    EditText Register_password;
    @BindView(R.id.Register_repassword)
    EditText Register_repassword;
    @BindView(R.id.Register_username)
    EditText etName;
    @BindView(R.id.Register_register)
    Button Register_register;
    @BindView(R.id.register_code)
    EditText register_code;
    @BindView(R.id.register_back)
    ImageView register_back;
    @BindView(R.id.tv_registe_title)
    TextView tvTitle;
    private int i = 60;
    private String phonenum;
    private MyHandler myHandler = new MyHandler(this);
    private String recommend;
    private static final String TAG ="RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        recommend = getIntent().getStringExtra("recommend");
        tvTitle.setText("注册账号");
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

    }

    private void initListeners() {
        register_getcode.setOnClickListener(this);
        Register_register.setOnClickListener(this);
        register_back.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_getcode:
                phonenum = register_phonenum.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    Toast.makeText(RegisterActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phonenum.length() != 11) {
                    Toast.makeText(RegisterActivity.this, "手机号码格式有误", Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.getVerificationCode("86", phonenum);
                register_getcode.setClickable(false);
                //开始倒计时
                new Thread(new Runnable() {
                    @Override
                    public void run() {
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
                }).start();


                break;
            case R.id.Register_register:
                String code = register_code.getText().toString().trim();
                phonenum = register_phonenum.getText().toString().trim();
                String password = Register_password.getText().toString().trim();
                String repassword = Register_repassword.getText().toString().trim();
                String username = etName.getText().toString().trim();
                if (TextUtils.isEmpty(phonenum)) {
                    Toast.makeText(getApplicationContext(), "手机号码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(code)) {
                    Toast.makeText(getApplicationContext(), "验证码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.isEmpty() || repassword.isEmpty() || password.equals("") || repassword.equals("")) {
                    Toast.makeText(getApplicationContext(), "密码不能为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (!password.equals(repassword)) {
                    Toast.makeText(getApplicationContext(), "两次输入密码不一致，重新输入",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (username.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.submitVerificationCode("86", phonenum, code);


                break;
            case R.id.register_back:
                this.finish();
                break;


        }
    }

    public void signUp() {
        new Thread() {
            @Override
            public void run() {
                try {
                    phonenum = register_phonenum.getText().toString().trim();
                    final String password = Register_password.getText().toString().trim();
                    EMClient.getInstance().createAccount(phonenum, password);

                    FormBody formBody = new FormBody.Builder()
                            .add("recommend", recommend)
                            .add("phone_num", phonenum)
                            .add("password", password)
                            .add("user_name",etName.getText().toString().trim())
                            .build();
                    OkHttpUtils.okhttpPost(formBody, BaseUrl.URL + "users", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("-------failure", e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });

                        }
                    });


                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int errorCode = e.getErrorCode();
                            String message = e.getMessage();
                            Log.d(TAG, String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
                            switch (errorCode) {
                                // 网络错误
                                case EMError.NETWORK_ERROR:
                                    Toast.makeText(RegisterActivity.this, "网络错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                // 用户已存在
                                case EMError.USER_ALREADY_EXIST:
                                    Toast.makeText(RegisterActivity.this, "用户已存在 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                                case EMError.USER_ILLEGAL_ARGUMENT:
                                    Toast.makeText(RegisterActivity.this, "参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                // 服务器未知错误
                                case EMError.SERVER_UNKNOWN_ERROR:
                                    Toast.makeText(RegisterActivity.this, "服务器未知错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                case EMError.USER_REG_FAILED:
                                    Toast.makeText(RegisterActivity.this, "账户注册失败 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(RegisterActivity.this, "ml_sign_up_failed code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });

                }
            }
        }.start();

    }


    private class MyHandler extends Handler {
        private final WeakReference<RegisterActivity> mActivty;
        private RegisterActivity act;

        public MyHandler(RegisterActivity activity) {
            mActivty = new WeakReference<RegisterActivity>(activity);
            this.act = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == -1) {
                //修改控件文本进行倒计时  i 以60秒倒计时为例
                act.register_getcode.setText(act.i + " s");
            } else if (msg.what == -2) {
                //修改控件文本，进行重新发送验证码
                act.register_getcode.setText("重新发送");
                act.register_getcode.setClickable(true);
                act.i = 60;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;

                // 短信注册成功后，返回MainActivity,然后提示
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        signUp();
                    }


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(act, "验证码已经发送",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ((Throwable) data).printStackTrace();
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    try {
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
}
