package com.cqxy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cqxy.bean.HouseBase;
import com.cqxy.fyb.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class HouseBaseAdapter extends RecyclerView.Adapter<HouseBaseAdapter.ViewHolder> {
    private List<HouseBase> datas;
    private Context act;
    public MyItemClickListener myItemClickListener;

    public HouseBaseAdapter(List<HouseBase> datas, Context act) {
        this.datas = datas;
        this.act = act;
    }

    @Override
    public HouseBaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(act).inflate(R.layout.housebaselayout, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HouseBaseAdapter.ViewHolder holder, final int position) {
       holder.headIcon.setBackgroundResource(datas.get(position).getHeadIcon());
        holder.name.setText(datas.get(position).getName());
        holder.houselocation.setText(datas.get(position).getHouselocation());
        holder.houseprice.setText(String.valueOf(datas.get(position).getHouseprice()));
        holder.housesize.setText(String.valueOf(datas.get(position).getHousesize()));
        holder.houseclass.setText(datas.get(position).getHouseclass());
        holder.housetype.setText(datas.get(position).getHousetype());
        holder.orientations.setText(datas.get(position).getOrientations());
        holder.decoration.setText(datas.get(position).getDecoration());
        holder.profit.setText(datas.get(position).getProfit());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemListener(MyItemClickListener myItemListener)
    {
        this.myItemClickListener=myItemListener;
    }


    public interface MyItemClickListener{
        void onItemClick(View view, int position);
    }
    public  static class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView headIcon;
        public TextView name,houselocation,decoration,housetype,houseclass,orientations,profit,housesize,houseprice;
        public LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            headIcon=(ImageView)itemView.findViewById(R.id.housebase_head);
            name=(TextView) itemView.findViewById(R.id.housebase_name);
            houselocation=(TextView) itemView.findViewById(R.id.housebase_houselocation);
//            decoration=(TextView) itemView.findViewById(R.id.housebase_decoration);
//            housetype=(TextView) itemView.findViewById(R.id.housebase_housetype);
//            houseclass=(TextView) itemView.findViewById(R.id.housebase_houseclass);
//            orientations=(TextView) itemView.findViewById(R.id.housebase_orientations);
//            profit=(TextView) itemView.findViewById(R.id.housebase_profit);
//            housesize=(TextView) itemView.findViewById(R.id.housebase_housesize);
//            houseprice=(TextView) itemView.findViewById(R.id.housebase_houseprice);
//            layout=(LinearLayout) itemView.findViewById(R.id.housebase_layout);
        }




    }
}
