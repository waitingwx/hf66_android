package com.cqxy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqxy.bean.PolicyBean;
import com.cqxy.fyb.R;

import java.util.ArrayList;

/**
 * Created by wx on 2017/10/17.
 */

public class MyArrayAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PolicyBean.CommentsBean> innerDatas;
    private TextView tvTitle;
    private TextView tvContent;

    public MyArrayAdapter(Context mContext, ArrayList<PolicyBean.CommentsBean> innerDatas) {
        this.mContext = mContext;
        this.innerDatas = innerDatas;
    }

    @Override
    public int getCount() {
        if(null == innerDatas)
            return 0;
        else
            return innerDatas.size() == 0 ? 0 :innerDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.policy_item,null);
        }
        tvTitle = ((TextView) convertView.findViewById(R.id.tv_title));
        tvContent = ((TextView) convertView.findViewById(R.id.tv_content));
        tvTitle.setText(innerDatas.get(position).getPname());
        tvContent.setText(innerDatas.get(position).getPcontent());
        return convertView;
    }
}
