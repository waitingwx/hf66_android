package com.cqxy.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HouseExampleBeans;
import com.cqxy.bean.HouseRentBean;
import com.cqxy.bean.Users;
import com.cqxy.fyb.R;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by Administrator on 2017/8/30.
 */

public class HouseRentAdapter extends RecyclerView.Adapter<HouseRentAdapter.ViewHolder> {
    private List<HouseRentBean.CommentsBean> datas;
    private Activity act;
    public MyItemClickListener myItemClickListener;
    private static final String TAG = "HouseRentAdapter";

    public HouseRentAdapter(List<HouseRentBean.CommentsBean> datas, Activity act) {
        this.datas = datas;
        this.act = act;

    }

    @Override
    public HouseRentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(act).inflate(R.layout.house_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HouseRentAdapter.ViewHolder holder, final int position) {
        HouseRentBean.CommentsBean commentsBean = datas.get(position);
        getUserPhone(commentsBean.getHouse_example_id(), holder);
        String userimg = commentsBean.getUserasset();
        int endIndex = userimg.indexOf("?");
        if (endIndex != -1) {
            userimg = userimg.substring(0, endIndex);
            Glide.with(act)
                    .load(BaseUrl.IMGURL + userimg)
                    .apply(RequestOptions.placeholderOf(R.mipmap.mine_man))
                    .apply(RequestOptions.errorOf(R.mipmap.mine_man))
                    .apply(bitmapTransform(new CropCircleTransformation()))
                    .into(holder.headIcon);
        }
        holder.houselocation.setText(commentsBean.getPosition());
        holder.houseprice.setText("租金：" + commentsBean.getRent() + "元/月");
        holder.housesize.setText("面积：" + commentsBean.getHouse_area() + "m²");
        holder.housefloor.setText("楼层：" + commentsBean.getFloor() + "层");
        String leixing = commentsBean.getLeixing();
        String bvalue = commentsBean.getBvalue();
        if (null == leixing || null == bvalue)
            holder.houseprofit.setText("盈利：");
        else
            holder.houseprofit.setText("盈利：" + leixing + bvalue + "");
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
        if (null == datas)
            return 0;
        else
            return datas.size() == 0 ? 0 : datas.size();
    }

    public void setOnItemListener(MyItemClickListener myItemListener) {
        this.myItemClickListener = myItemListener;
    }


    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView headIcon;
        public TextView housecreatetime, name, housefloor, housesize, houseprice, houselocation, houseprofit;
        public RelativeLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);
            headIcon = (ImageView) itemView.findViewById(R.id.housebase_head);
            name = (TextView) itemView.findViewById(R.id.housebase_name);
            houselocation = (TextView) itemView.findViewById(R.id.housebase_houselocation);
            houseprice = (TextView) itemView.findViewById(R.id.housebase_price);
            houseprofit = (TextView) itemView.findViewById(R.id.housebase_profit);
            housefloor = (TextView) itemView.findViewById(R.id.housebase_housefloor);
            housesize = (TextView) itemView.findViewById(R.id.housebase_housesize);
            housecreatetime = (TextView) itemView.findViewById(R.id.housebase_time);
            layout = (RelativeLayout) itemView.findViewById(R.id.housebase_layout);
        }

    }

    public void getUserPhoneByid(int userid, final HouseRentAdapter.ViewHolder holder) {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + "users/" + userid + ".json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String string = response.body().string();
                Log.d("HouseRentAdapter", "onResponse: _____________________________________response.body = " + string);

                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        Log.d("HouseRentAdapter", "run: ======================string = " + string);
                        Users user = Users.objectFromData(string, "user");
                        Log.d("HouseRentAdapter", "run: ------------------------------setName");
                        String phone_num = user.getPhone_num();
                        holder.name.setText(phone_num.substring(0, 3) + "*****");
                    }
                });

            }
        });
    }

    public void getUserPhone(int houseid, final HouseRentAdapter.ViewHolder holder) {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act), BaseUrl.URL + "house_examples/" + houseid + ".json", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                final HouseExampleBeans.HouseexampleBean house = gson.fromJson(response.body().string(), HouseExampleBeans.class).getHouseexample();
                getUserPhoneByid(house.getUser_id(), holder);

            }
        });
    }
}
