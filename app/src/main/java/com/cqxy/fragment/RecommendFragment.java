package com.cqxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.fyb.R;
import com.cqxy.utils.ImageTask;
import com.cqxy.utils.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wx on 2017/12/26.
 */

public class RecommendFragment extends Fragment {
    private Context mContent;
    private TextInputLayout tilTEL;
    private EditText etTel;
    private Button btnAffirm;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilTEL = ((TextInputLayout) view.findViewById(R.id.til_recommend_tel));
        etTel = ((EditText) view.findViewById(R.id.et_recommend_tel));
        btnAffirm = ((Button) view.findViewById(R.id.btn_affirm));
        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if(11 == s.length()) {
                    chekUser(s.toString());
//                    if(itrailsExit.getStatus() == AsyncTask.Status.PENDING)
//                        itrailsExit.execute();
                }
            }
        });

    }

    private void chekUser(String phonenum) {
        OkHttpUtils.okhttpGet(BaseUrl.URL + "users/finduser/" + etTel.getText(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                Users user = Users.objectFromData(responseBody,"user");
                if(null == user)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tilTEL.setHint("您的推荐人没有用这个号码注册！");
                        }
                    });
                else
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tilTEL.setHint("推荐人验证成功！");
                        }
                    });
            }
        });
    }
    ImageTask itrailsExit = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            String responseBody = OkHttpUtils.okhttpGet(BaseUrl.URL + "users/finduser/" + etTel.getText() + ".json");
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
            tilTEL.setHint("推荐人验证成功！");
        }

        @Override
        public void onError() {
            tilTEL.setHint("您的推荐人没有用这个号码注册！");
        }
    });
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContent = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContent = null;
    }
}
