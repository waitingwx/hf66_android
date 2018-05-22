package com.cqxy.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cqxy.constants.XiaoquBean;

import java.util.ArrayList;

/**
 * Created by wx on 2017/11/14.
 */

public class XiaoquAdapter extends BaseAdapter implements Filterable {
    Context mContext;
    private TextView text;
    private ArrayList<XiaoquBean.XiaoqusBean> mOriginalValues;
//    private ArrayFilter mFilter;
    private final Object mLock = new Object();
    private Handler handler;
    private String TAG = "XiaoquAdapter";


    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    private int selectedId;

    public XiaoquAdapter(Context mContext, ArrayList<XiaoquBean.XiaoqusBean> innerDatas, Handler handler) {
        this.mOriginalValues = innerDatas;
        this.mContext = mContext;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return mOriginalValues.size() == 0 ? 0 : mOriginalValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mOriginalValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView)
            convertView = View.inflate(mContext, android.R.layout.simple_spinner_item, null);
        text = ((TextView) convertView.findViewById(android.R.id.text1));
        final int index = mOriginalValues.get(position).getId();
        final String name = mOriginalValues.get(position).getName();
        text.setText(name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = index;
                Bundle data = new Bundle();
                data.putString("text",name);
                msg.setData(data);
                handler.sendMessage(msg);
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
//        if (mFilter == null) {
//            mFilter = new ArrayFilter();
//        }
        return null;
    }
//
//    private class ArrayFilter extends Filter {
//        @Override
//        protected FilterResults performFiltering(CharSequence prefix) {
//            final FilterResults results = new FilterResults();
//
//            if (mOriginalValues == null) {
//                synchronized (mLock) {
//                    mOriginalValues = new ArrayList<>(mOriginalValues);
//                }
//            }
//
//            if (prefix == null || prefix.length() == 0) {
//                final ArrayList<XiaoquBean.XiaoqusBean> list;
//                synchronized (mLock) {
//                    list = new ArrayList<>(mOriginalValues);
//                }
//                results.values = list;
//                results.count = list.size();
//            } else {
////                final String prefixString = prefix.toString().toLowerCase();
//                final String prefixString = PinyinUtils.getFirstSpell(prefix.toString());
//                Log.d(TAG, "performFiltering: ---------prefixString = " + prefixString);
//                final ArrayList<XiaoquBean.XiaoqusBean> values;
//                synchronized (mLock) {
//                    values = new ArrayList<>(mOriginalValues);
//                }
//
//                final int count = values.size();
//                final ArrayList<XiaoquBean.XiaoqusBean> newValues = new ArrayList<>();
//
//                for (int i = 0; i < count; i++) {
//                    final XiaoquBean.XiaoqusBean value = values.get(i);
////                    final String valueText = value.toString().toLowerCase();
//                    Log.i(TAG, "PinyinUtils.getFirstSpell(value.getName())"
//                            + PinyinUtils.getFirstSpell(value.getName())+",\nPinyinUtils.getPingYin(value.getName())"+PinyinUtils.getPingYin(value.getName()));
//                    // First match against the whole, non-splitted value
//                    if (PinyinUtils.getFirstSpell(value.getName()).contains(prefixString)) {
//                        newValues.add(value);
//                    }
//                }
//
//                results.values = newValues;
//                results.count = newValues.size();
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            //noinspection unchecked
//            mOriginalValues = (ArrayList<XiaoquBean.XiaoqusBean>) results.values;
//            if (results.count > 0) {
//                notifyDataSetChanged();
//            } else {
//                notifyDataSetInvalidated();
//            }
//        }
//    }
}
