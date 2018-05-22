package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.EaseTokenBean;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.ToastUtil;
import com.cqxy.utils.UserInf;

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

public class UpdatePassWordActivity extends AppCompatActivity {

    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_newpwd)
    EditText etNewpwd;
    @BindView(R.id.et_checkpwd)
    EditText etCheckpwd;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private static String TAG = "UpdatePassWordActivity";
    private String phone = "";
    private String easetoken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass_word);
        ButterKnife.bind(this);
        postEaseToken();
        phone = getIntent().getStringExtra("phone");
        easetoken = getIntent().getStringExtra("easetoken");
        if (null == easetoken)
            easetoken = UserInf.getEaseToken(this);
        Log.d(TAG, "onCreate: -----------------easetoken = " + easetoken);
        if (null != phone)
            etTel.setText(phone);
        titleText.setText("修改密码");
        Log.d(TAG, "onCreate: -------EASEtoken = " + UserInf.getEaseToken(this));
        etTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 11) {
                    Log.d(TAG, "afterTextChanged: -----------------------");
                    String usertel = etTel.getText().toString();
                    String easeToken = UserInf.getEaseToken(UpdatePassWordActivity.this);
                    Log.d(TAG, "afterTextChanged: --------------------easetoken = " + easeToken);
//                    checkEaseUser(easeToken, usertel);
                }
            }
        });
    }

    /**
     * 获取easetoken
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
                Log.e(TAG, "Error------------->" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                EaseTokenBean easeTokenBean = EaseTokenBean.objectFromData(result);
                Log.d(TAG, "onResponse: --------------------putSp");
                String access_token = easeTokenBean.getAccess_token();
                easetoken = access_token;
                SpUtil.putString(UpdatePassWordActivity.this, SpUtil.SP_USER, SpUtil.EASE_TOKEN, access_token);
                SpUtil.putInt(UpdatePassWordActivity.this, SpUtil.SP_USER, SpUtil.EASE_TOKEN_TIME, easeTokenBean.getExpires_in());
            }
        });
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
                .addHeader("Accept", "application/vnd.github.v3+json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token )
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
                UpdatePassWordActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(UpdatePassWordActivity.this,"修改密码成功", Toast.LENGTH_SHORT);
                        UpdatePassWordActivity.this.finish();
                    }
                });
            }
        });
    }


    @OnClick({R.id.house_enter_back, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;

            case R.id.btn_commit:
                Log.d(TAG, "onViewClicked: -----------easetoken =" + easetoken);
                okhttpPutEasepwd(easetoken,
                        etTel.getText().toString(),
                        etNewpwd.getText().toString());//只加了修改ease
                uploadpwd();
                break;
        }
    }

    private void uploadpwd() {
        Log.d(TAG, "uploadpwd: ------------------start");
        FormBody formBody = new FormBody.Builder().add("user[password]", etNewpwd.getText().toString()).build();
        OkHttpUtils.okhttpPutNeedHead(UpdatePassWordActivity.this,
                formBody,
                BaseUrl.URL + "users/" + UserInf.getUserId(this) + ".json",
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
