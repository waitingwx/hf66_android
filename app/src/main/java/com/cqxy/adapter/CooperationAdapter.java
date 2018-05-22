package com.cqxy.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.CooperationHouses;
import com.cqxy.bean.Users;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wx on 2017/12/25.
 */

public class CooperationAdapter extends RecyclerView.Adapter<CooperationAdapter.ViewHolder>{
        private List<CooperationHouses.CommentsBean> datas;
        private Activity act;
        public MyItemClickListener myItemClickListener;

        public CooperationAdapter(List<CooperationHouses.CommentsBean> datas, Activity act) {
            this.datas = datas;
            this.act = act;
        }


        @Override
        public CooperationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(act).inflate(R.layout.house_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CooperationAdapter.ViewHolder holder, final int position) {
            getUserPhone(datas.get(position).getUser_id(), holder);
            holder.headIcon.setBackgroundResource(R.mipmap.mine_man);
            holder.houselocation.setText(datas.get(position).getPosition());
            holder.houseprice.setText("金额："+String.valueOf(datas.get(position).getRent())+"元");
            holder.housesize.setText("使用面积：" + String.valueOf(datas.get(position).getHouse_area())+ "m²");
            String created_at = datas.get(position).getCreated_at();
            String time = created_at.substring(0, created_at.indexOf("T"));
            holder.housecreatetime.setText(time);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myItemClickListener.onItemClick(view, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (datas != null) {
                return datas.size();
            } else {
                return 0;
            }
        }

        public void setOnItemListener(MyItemClickListener myItemListener) {
            this.myItemClickListener = myItemListener;
        }


        public interface MyItemClickListener {
            void onItemClick(View view, int position);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView headIcon;
            public TextView name, housecreatetime,housesize, houseprice, houselocation, houseProfit;
            public RelativeLayout layout;


            public ViewHolder(View itemView) {
                super(itemView);
                headIcon = (ImageView) itemView.findViewById(R.id.housebase_head);
                name = (TextView) itemView.findViewById(R.id.housebase_name);
                houselocation = (TextView) itemView.findViewById(R.id.housebase_houselocation);
                housesize = (TextView) itemView.findViewById(R.id.housebase_housesize);
                houseprice = (TextView) itemView.findViewById(R.id.housebase_price);
                houseProfit = (TextView) itemView.findViewById(R.id.housebase_profit);
                housecreatetime = (TextView) itemView.findViewById(R.id.housebase_time);
                layout = (RelativeLayout) itemView.findViewById(R.id.housebase_layout);
            }


        }

        public void getUserPhone(int userid, final CooperationAdapter.ViewHolder holder) {
            OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + "users/" + userid + ".json", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Gson gson = new Gson();
                    final Users user = gson.fromJson(response.body().string(), Users.class);
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.name.setText(user.getPhone_num());
                        }
                    });

                }
            });
        }

}
