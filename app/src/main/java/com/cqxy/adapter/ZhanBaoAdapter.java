package com.cqxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqxy.fyb.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class ZhanBaoAdapter extends RecyclerView.Adapter<ZhanBaoAdapter.ViewHolder>{
    private Context context;
    private List<String> datas;
    private static String TAG = "ZhanBaoAdapter";

    public ZhanBaoAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        Log.d(TAG, "ZhanBaoAdapter: ---------------战报data.size = "+ datas.size());
    }

    @Override
    public ZhanBaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.zhanbaoadapterlayout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ZhanBaoAdapter.ViewHolder holder, int position) {
        if(null == datas && datas.size() == 0)
            holder.zhanbao_content.setText("==========================");
        else {
//            Log.d(TAG, "onBindViewHolder: =====================data.size = " +datas.size()+ ",position = " + position+",\nposition % (datas.size() -1)"+position % (datas.size() -1));
            String text = datas.get(position).toString();
            if (null == text)
                holder.zhanbao_content.setText("---------------");
            else
                holder.zhanbao_content.setText(text);
        }
    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount: ==========datas.size()" + datas.size());
        return datas.size() == 0 ? 0 : datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView zhanbao_content;
        public ViewHolder(View view){
            super(view);
            zhanbao_content = (TextView) view.findViewById(R.id.zhanbao_content);
        }
}

}