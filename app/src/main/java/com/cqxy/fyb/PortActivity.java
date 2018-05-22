package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.adapter.PortGridviewAdapter;
import com.cqxy.bean.PointGridBean;
import com.cqxy.utils.RecycleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortActivity extends AppCompatActivity {

    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.port_version)
    TextView portVersion;
    @BindView(R.id.port_time)
    TextView portTime;
    @BindView(R.id.port_publish_progress)
    ProgressBar portPublishProgress;
    @BindView(R.id.port_stock_progress)
    ProgressBar portStockProgress;
    @BindView(R.id.port_recyclerview)
    RecyclerView portRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_port);
        ButterKnife.bind(this);
        RecycleUtils.initGridLayoutRecyclerViews(portRecyclerview,this,3);
        initDatas();
    }

    private void initDatas() {
        titleText.setText("端口信息");
        final List<PointGridBean> datas=new ArrayList<>();
        datas.add(new PointGridBean("1","剩余发布量"));
        datas.add(new PointGridBean("1","今日新增房源"));
        datas.add(new PointGridBean("1","房源用量"));
        datas.add(new PointGridBean("18","昨日点击量"));
        datas.add(new PointGridBean("0","即将过期房源"));
        datas.add(new PointGridBean("0","违规房源"));

        PortGridviewAdapter adapter=new PortGridviewAdapter(datas,this);
        portRecyclerview.setAdapter(adapter);
        adapter.setOnItemListener(new PortGridviewAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(PortActivity.this,"点击了"+datas.get(position).getContent(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @OnClick(R.id.house_enter_back)
    public void onViewClicked() {
        this.finish();
    }
}
