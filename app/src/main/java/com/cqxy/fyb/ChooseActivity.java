package com.cqxy.fyb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cqxy.adapter.ListViewAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.ShangqBean;
import com.cqxy.constants.QuxianBean;
import com.cqxy.constants.ShangquanBean;
import com.cqxy.constants.XiaoquBean;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.view.ScaleUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseActivity extends AppCompatActivity {
    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.btn_confirm)
    AppCompatButton btnConfirm;
    private String sqResponseBody;
    List<ShangqBean.CityBean> city;
    private String[] chooseItems = new String[3];
    private String result;
    private String TAG = "ChooseActivity";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private ListView quxianListView;
    private ListView shangquanListView;
    private ListView xiaoquListView;
    private int xiaoquId;

    private ArrayList<QuxianBean> quxianDatas = new ArrayList<>();
    private ArrayList<ShangquanBean> shangquanDatas = new ArrayList<>();
    private ArrayList<XiaoquBean.XiaoqusBean> xiaoqusDatas = new ArrayList<>();

    ListViewAdapter<QuxianBean> quxianBeanListViewAdapter;
    ListViewAdapter<ShangquanBean> shangquanBeanListViewAdapter;
    ListViewAdapter<XiaoquBean.XiaoqusBean> xiaoquBeanListViewAdapter;
    private TextView textChoose;

    public TextView getTextChoose() {
        return textChoose;
    }

    public void setTextChoose(TextView textChoose) {
        this.textChoose = textChoose;
    }

    public void setXiaoquId(int xiaoquId) {
        this.xiaoquId = xiaoquId;
        Log.d(TAG, "setXiaoquId: -----xiaoquId = " + this.xiaoquId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ButterKnife.bind(this);
        initData();
        init();
        titleText.setText("选择小区");
    }
    private void initData() {
        Log.d(TAG, "initData: ===================");
        sqResponseBody = SpUtil.getString(this, "shangquan", "responsebody");
        city = ShangqBean.objectFromData(sqResponseBody).getCity();
        Log.d(TAG, "initData: -------sqResponseBody = " + sqResponseBody);
        for (int i = 0; i < city.size(); i++) {
            quxianDatas.add(city.get(i).getQuxian());
        }
    }
    private void init() {
        Log.d(TAG, "init: -----------------");
        quxianListView = (ListView) findViewById(R.id.ll_quxian);
        quxianListView.setDividerHeight(ScaleUtil.dp2px(this, 4));
        shangquanListView = (ListView) findViewById(R.id.ll_shangquan);
        shangquanListView.setDividerHeight(ScaleUtil.dp2px(this, 4));
        xiaoquListView = (ListView) findViewById(R.id.ll_xiaoqu);
        xiaoquListView.setDividerHeight(ScaleUtil.dp2px(this, 4));
        quxianBeanListViewAdapter = new ListViewAdapter<QuxianBean>(getQuxianDatas(), this) {
            @Override
            public void showTexts(int position, TextView covertView) {
                String name = getQuxianDatas().get(position).getName();
                covertView.setText(name);
            }
        };
        shangquanBeanListViewAdapter = new ListViewAdapter<ShangquanBean>(getShangquanDatas(), this) {
            @Override
            public void showTexts(int position, TextView covertView) {
                String name = getShangquanDatas().get(position).getName();
                covertView.setText(name);
            }
        };
        xiaoquBeanListViewAdapter = new ListViewAdapter<XiaoquBean.XiaoqusBean>(getXiaoqusDatas(), this) {
            @Override
            public void showTexts(int position, TextView covertView) {
                String name = getXiaoqusDatas().get(position).getName();
                covertView.setText(name);
            }
        };
        setListener();
    }

    private void setListener() {
        Log.d(TAG, "setListener: ----------------");
        quxianListView.setAdapter(quxianBeanListViewAdapter);
        quxianListView.setSelector(R.drawable.selector_listview_item);
        shangquanListView.setSelector(R.drawable.selector_listview_item);
        xiaoquListView.setSelector(R.drawable.selector_listview_item);
        quxianListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getShangquanDatas().size() != 0)
                    shangquanDatas.clear();
                shangquanDatas.addAll(city.get(position).getShangquan());
                chooseItems[0] = getQuxianDatas().get(position).getName();
                Log.d(TAG, "onItemClick: ===============quxianListener---------\nshangquandatas.size() ="
                        + getShangquanDatas().size() + "\nquxian = "
                        + getQuxianDatas().get(position).getName()
                        + "\nsahgnquandatas.get(0) =" + getShangquanDatas().get(0).getName());
                shangquanListView.setAdapter(shangquanBeanListViewAdapter);
                shangquanBeanListViewAdapter.notifyDataSetChanged();
            }
        });

        shangquanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: -----------shangquanlistview");
                chooseItems[1] = getShangquanDatas().get(position).getName();
                loadXiaoqu(shangquanDatas.get(position).getId());
            }
        });

        xiaoquListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: =========================xiaoqulistview");
                chooseItems[2] = getXiaoqusDatas().get(position).getName();
                setXiaoquId(xiaoqusDatas.get(position).getId());
                Log.d(TAG, "onItemSelected: -------------------sb = " + chooseItems[0]+chooseItems[1]+chooseItems[2]);
                result = chooseItems[0] + chooseItems[1] + chooseItems[2];
            }
        });

    }
    private void loadXiaoqu(int shangQuanId) {
        OkHttpUtils.okhttpGet(BaseUrl.URL + "xiaoqus/" + shangQuanId + ".json", new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onResponse: string = " + string);
                if (xiaoqusDatas.size() != 0)
                    xiaoqusDatas.clear();
                xiaoqusDatas.addAll(XiaoquBean.objectFromData(string).getXiaoqus());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "run: ---------------");
                        xiaoquListView.setAdapter(xiaoquBeanListViewAdapter);
                        xiaoquBeanListViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
    @OnClick({R.id.house_enter_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
            case R.id.btn_confirm:
                //TODO:返回数据
                Intent intent = new Intent();
                intent.putExtra("xiaoquid",xiaoquId);
                intent.putExtra("result",result);
                setResult(0x002,intent);
                this.finish();
                break;
        }
    }
    public int getXiaoquId() {
        return xiaoquId;
    }

    public ArrayList<QuxianBean> getQuxianDatas() {
        return quxianDatas;
    }

    public void setQuxianDatas(ArrayList<QuxianBean> quxianDatas) {
        this.quxianDatas = quxianDatas;
    }

    public ArrayList<ShangquanBean> getShangquanDatas() {
        return shangquanDatas;
    }

    public void setShangquanDatas(ArrayList<ShangquanBean> shangquanDatas) {
        this.shangquanDatas = shangquanDatas;
    }

    public ArrayList<XiaoquBean.XiaoqusBean> getXiaoqusDatas() {
        return xiaoqusDatas;
    }

    public void setXiaoqusDatas(ArrayList<XiaoquBean.XiaoqusBean> xiaoqusDatas) {
        this.xiaoqusDatas = xiaoqusDatas;
    }
}
