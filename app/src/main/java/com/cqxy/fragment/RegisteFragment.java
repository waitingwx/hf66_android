package com.cqxy.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqxy.fyb.R;

/**
 * Created by wx on 2017/12/26.
 */

public class RegisteFragment extends Fragment {
    private Context mContent;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registe,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //1.获取到11位电话号码

        //2.判断是否存在这个用户，
        //2.1如果存在，提示填写成功
        //2.2如果不存在，清空电话号码并提示未注册
    }

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
