package com.cqxy.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cqxy.fyb.R;

/**
 * Created by wx on 2017/12/27.
 */

public class AffirmDialog extends Dialog {
    private TextView tvTitle;
    private TextView tvContent;
    private Button btnAffirm;
    private Button btnCancel;

    private boolean isChecked;
    private ReloadDataListener reloadDataListener;

    public void setReloadDataListener(ReloadDataListener reloadDataListener) {
        this.reloadDataListener = reloadDataListener;
    }

    public AffirmDialog(@NonNull Context context,boolean isChecked) {
        super(context);
        this.isChecked = isChecked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affirm_dialog);
        findViews();
        setTexts(isChecked);
        setListener();
    }

    private void setTexts(boolean isChecked) {
        if(isChecked) {
            tvTitle.setText("确认上架");
            tvTitle.setTextColor(Color.argb(255,30,144,255));
            tvContent.setText("您确定要重新上架该房源吗？");
        }else {
            tvTitle.setText("确认下架");
            tvTitle.setTextColor(Color.argb(255,30,144,255));
            tvContent.setText("您确定要下架该房源吗？");
        }
    }

    private void setListener() {
        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadDataListener.reloadData();
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void findViews() {
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_conent);
        btnAffirm = findViewById(R.id.btn_affirm);
        btnCancel = findViewById(R.id.btn_cancel);
    }


    public interface ReloadDataListener {
        void reloadData();
    }
}
