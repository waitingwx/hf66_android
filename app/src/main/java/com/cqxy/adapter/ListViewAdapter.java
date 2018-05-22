package com.cqxy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqxy.view.ScaleUtil;

import java.util.ArrayList;

/**
 * Created by wx on 2017/11/15.
 */

public abstract class ListViewAdapter<T> extends BaseAdapter {
    ArrayList<T> innerDatas;
    Context mContext;
    TextView text;

    public ListViewAdapter(ArrayList<T> innerDatas, Context mContext) {
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
        showTexts(position,text);
        return convertView;
    }
    public abstract void showTexts(int position, TextView covertView);
}
