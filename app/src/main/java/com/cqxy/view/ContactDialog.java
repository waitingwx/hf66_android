package com.cqxy.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

/**
 * Created by wx on 2017/10/19.
 */

public class ContactDialog extends Dialog {
    private Context mContext;
    EditText etSubmit;
    Button btnSumit;
    private ImageView back;
    public ContactDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_contact_us);
        initViews();
        getWindows();
    }

    private void initViews() {
        back=findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        etSubmit = findViewById(R.id.et_enter);
        btnSumit = findViewById(R.id.btn_submit);
        btnSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = etSubmit.getText().toString();
                if(TextUtils.isEmpty(s))
                    Toast.makeText(mContext,"您未填写建议",Toast.LENGTH_SHORT).show();
                else {
                    FormBody formBody = new FormBody.Builder()
                            .add("user_id", UserInf.getUserId(mContext)+"")
                            .add("content", s).build();
                    OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(mContext),
                            UserInf.getUserPhonenum(mContext),
                            formBody, BaseUrl.URL + "cooperations", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            dismiss();
                        }
                    });
                }
            }
        });
    }

    public void getWindows()
    {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        int height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        etSubmit.getLayoutParams().height=height/2;
    }


}
