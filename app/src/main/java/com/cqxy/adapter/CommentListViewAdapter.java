package com.cqxy.adapter;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.bean.CommentBean;
import com.cqxy.fyb.R;

import java.util.ArrayList;

/**
 * Created by lpl on 17-10-23.
 */

public class CommentListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<CommentBean.CommentsBean> innerDatas;
    private ImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvContent;
    private TextView tvTime;
    private TextView tvReply;
private static String TAG = "CommentListViewAdapter";
    public CommentListViewAdapter(Context mContext, ArrayList<CommentBean.CommentsBean> innerDatas) {
        Log.d(TAG, "CommentListViewAdapter: -------------------------------");
        this.mContext = mContext;
        this.innerDatas = innerDatas;
    }

    @Override
    public int getCount() {
        return innerDatas.size() == 0 ? 0 : innerDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return innerDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d(TAG, "getView: --------------start");
        if(null == view ) {
            view = View.inflate(mContext, R.layout.comment_item,null);
        }
        ivAvatar = ((ImageView) view.findViewById(R.id.iv_avatar));
        tvUserName = ((TextView) view.findViewById(R.id.tv_comment_user_name));
        tvContent = ((TextView) view.findViewById(R.id.tv_comment_content));
        tvTime = ((TextView) view.findViewById(R.id.tv_comment_time));
        tvReply = ((TextView) view.findViewById(R.id.tv_comment_reply));
        //TODO:获取username显示
        CommentBean.CommentsBean commentsBean = innerDatas.get(i);
        tvUserName.setText(commentsBean.getUser_id()+"");
        tvContent.setText(commentsBean.getBody());
        String times = commentsBean.getCreated_at().toString();
        tvTime.setText(times.substring(0,times.indexOf("T")));
        Log.d(TAG, "getView: -----------------------------------end");
        return view;
    }
}
