package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cqxy.adapter.DownAdapter;
import com.cqxy.bean.downbean;
import com.cqxy.fyb.DownActivity;
import com.cqxy.fyb.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by PeLon on 2017/9/19.
 */

public class DownFragment extends Fragment {
    @BindView(R.id.down_recyclerview)
    RecyclerView downRecyclerview;
    private DownActivity act;
    private Unbinder unbinder;
    private DownAdapter adapter;
    private String[] names;
    private String type;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (DownActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_down_layout, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    private void initRecyclerview() {
        downRecyclerview.setLayoutManager(new GridLayoutManager(act, 2));
        downRecyclerview.setItemAnimator(new DefaultItemAnimator());
        downRecyclerview.setHasFixedSize(true);
    }
    private void initDatas() {
        List<downbean> datas=new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            datas.add(new downbean(names[i],R.mipmap.word));
        }
        adapter=new DownAdapter(datas,act);
        downRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerview();
        initDatas();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemListener(new DownAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(act,"点击了该文件:",Toast.LENGTH_SHORT).show();
                Log.e("----itempath:",Environment.getExternalStorageDirectory()+"/"+position+"."+type);
                openFiles(Environment.getExternalStorageDirectory()+"/"+position+"."+type);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    //打开文件时调用
    public void openFiles(String filesPath) {
        Uri uri = Uri.parse("file://" + filesPath);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);

        String type = getMIMEType(filesPath);
        intent.setDataAndType(uri, type);
        if (!type.equals("*/*")) {
            try {
                startActivity(intent);
            } catch (Exception e) {
                startActivity(showOpenTypeDialog(filesPath));
            }
        } else {
            startActivity(showOpenTypeDialog(filesPath));
        }
    }

    //显示打开方式
    public void show(String filesPath){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        startActivity(showOpenTypeDialog(filesPath));
    }

    public static Intent showOpenTypeDialog(String param) {
        Log.e("ViChildError", "showOpenTypeDialog");
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * --获取文件类型 --
     */
    public static String getMIMEType(String filePath) {
        String type = "*/*";
        String fName = filePath;

        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") {
            return type;
        }

        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
            }
        }
        return type;
    }

    /**
     * -- MIME 列表 --
     */
    public static final String[][] MIME_MapTable =
            {
                    // --{后缀名， MIME类型}   --
                    {".doc", "application/msword"},
                    {".docx", "application/msword"},
                    {".pdf", "application/pdf"},
                    {".png", "image/png"},
                    {".jpeg", "image/jpeg"},
                    {".jpg", "image/jpeg"},
                    {".htm", "text/html"},
                    {".html", "text/html"},

                    {"", "*/*"}
            };
}
