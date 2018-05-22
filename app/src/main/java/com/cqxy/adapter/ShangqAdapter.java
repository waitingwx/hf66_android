package com.cqxy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqxy.constants.ShangquanBean;
import com.cqxy.view.ScaleUtil;

import java.util.List;

/**
 * Created by wx on 2017/11/13.
 */

public class ShangqAdapter extends BaseAdapter {
    List<ShangquanBean> innerDatas;
    Context mContext;
    private TextView text;

    public ShangqAdapter(Context mContext, List<ShangquanBean> innerDatas) {
        this.innerDatas = innerDatas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return innerDatas.size() == 0 ? 0 :innerDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return innerDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null == convertView)
            convertView = View.inflate(mContext,android.R.layout.simple_spinner_item,null);
        text = ((TextView) convertView.findViewById(android.R.id.text1));
        text.setHeight(ScaleUtil.dp2px(mContext,32f));
        text.setText(innerDatas.get(position).getName());
        return convertView;
    }
}
