package com.cqxy.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqxy.bean.PolicyBean;
import com.cqxy.fyb.R;

/**
 * Created by wx on 2017/10/20.
 */

public class PolicyContentDialog extends DialogFragment {
    PolicyBean.CommentsBean content;
    Context context;

    public PolicyContentDialog(Context mContext, PolicyBean.CommentsBean commentsBean) {
        this.content = commentsBean;
        this.context = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if(dialog != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            dialog.getWindow().setLayout(
                    ScaleUtil.getScreenWidth(context) - ScaleUtil.dp2px(context,16),
                    (ScaleUtil.getScreenHeight(context) *4)/ 5);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.policy_dialog, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_policy_title);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_policy_content);
        tvTitle.setText(content.getPname());
        tvContent.setText(content.getPcontent());

    }

}
