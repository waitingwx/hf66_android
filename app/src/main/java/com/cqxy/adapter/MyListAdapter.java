package com.cqxy.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cqxy.fyb.R;
import com.cqxy.view.ScaleUtil;

import java.util.ArrayList;

/**
 * Created by wx on 2017/10/17.
 */

public class MyListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> strDatas;
    private ArrayList<TextView> tvList;
    private int itemPadding;


    public MyListAdapter(Context mContext, ArrayList<String> strDatas) {
        this.mContext = mContext;
        this.strDatas = strDatas;
        itemPadding = ScaleUtil.dp2px(mContext,4);
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
    @Override
    public int getCount() {
        return strDatas.size() == 0 ? 0:strDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 重新填充note_item部局，并把它作为项的view返回
        convertView = LayoutInflater.from(mContext).inflate(R.layout.note_item, null);
        TextView txtNote = (TextView)convertView.findViewById(R.id.tv_note);
        txtNote.setText(strDatas.get(position));
        // 根据列数计算项目宽度，以使总宽度尽量填充屏幕 2 :column_count
        int itemWidth = (int)(mContext.getResources().getDisplayMetrics().widthPixels -  2 * 10)  / 2;
        int itemHeight = convertView.getHeight() * (strDatas.size() /2)+(itemPadding *2);
        AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                itemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        Log.d("ListAdapter", "getView: itemWidth = "+itemWidth+",itemHeight = " + itemHeight +",itemPadding" +itemPadding );
        convertView.setLayoutParams(param);
        notifyDataSetChanged();
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
