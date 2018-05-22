package com.cqxy.fyb;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.HomeImgs;
import com.cqxy.fragment.DownFragment;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.UserInf;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DownActivity extends AppCompatActivity {


    @BindView(R.id.house_enter_back)
    ImageView houseEnterBack;
    @BindView(R.id.title_text)
    TextView titleText;
    String TAG = "DownActivity";
    private DownFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        ButterKnife.bind(this);

        initFragment();
        getDownFile();

    }

    private void initFragment() {
        titleText.setText("下载管理");

        fragment = new DownFragment();

    }


    public void getDownFile()
    {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(this),UserInf.getUserPhonenum(this),BaseUrl.URL + "xiazais", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson=new Gson();
                String string = response.body().string();


                List<HomeImgs.HomeimgsBean> imgdatas=new ArrayList<HomeImgs.HomeimgsBean>();
                imgdatas=gson.fromJson(string, HomeImgs.class).getHomeimgs();

                if (null == imgdatas)
                    return;
                String names[]=new String[imgdatas.size()];
                String type="";
                for (int i = 0; i < imgdatas.size(); i++) {
                    String url = imgdatas.get(i).getUrl();
                    Log.d(TAG, "onResponse: ===========url = " + url);
                    String fileurl=BaseUrl.IMGURL+url.substring(0,url.indexOf("?"));
                    type=fileurl.substring(fileurl.lastIndexOf(".")+1,fileurl.length());
                    String name = imgdatas.get(i).getName();
                    names[i] = name;
                    String savePath = isExistDir(i+"."+type);
                    Log.d(TAG, "onResponse: fileUrl = " + fileurl+",\ntype = " + type+",\nnames["+i+"] = " + names[i]+",\nsavePath = " +savePath);
                    getFileByUrl(fileurl,savePath);
                }

                fragment.setNames(names);
                fragment.setType(type);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().add(R.id.down_framelayout,fragment).commit();
            }
        });
    }


    public void getFileByUrl(String fileurl, final String savePath)
    {
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(DownActivity.this),UserInf.getUserPhonenum(DownActivity.this),fileurl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                    Log.e("----faile",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = response.body().byteStream();
                long total = response.body().contentLength();
                Log.e("----filesize:",total+"");
                FileOutputStream fos=new FileOutputStream(savePath);
                byte[] b=new byte[2048];
                int len=0;
                long sum = 0;

                while ((len = is.read(b)) != -1) {
                    fos.write(b, 0, len);
                    sum += len;
                    int progress = (int) (sum * 1.0f / total * 100);
                    // 下载中
                }
                fos.flush();
            }
        });

    }


    @OnClick({R.id.house_enter_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.house_enter_back:
                this.finish();
                break;
        }
    }


    /**
     * 判断文件是否存在
     * @param saveDir
     * @return
     * @throws IOException
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        downloadFile.createNewFile();
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

}
