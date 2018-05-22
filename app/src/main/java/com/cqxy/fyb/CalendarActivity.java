package com.cqxy.fyb;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.base.BaseUrl;
import com.cqxy.bean.DateBean;
import com.cqxy.utils.ImageTask;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.UserInf;
import com.hzw.zwcalendar.ZWCalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;

public class CalendarActivity extends AppCompatActivity {
    private TextView show;
    private ZWCalendarView calendarView;
    private HashMap<String, Boolean> sign;
    private static String TAG = "CalendarActivity";
    private ImageView ivBack;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ivBack = ((ImageView) findViewById(R.id.house_enter_back));
        ivBack.setImageResource(R.mipmap.calendar_back);
        tvTitle = ((TextView) findViewById(R.id.title_text));
        tvTitle.setText("签到");
        sign = getSpSign();
        if (it.getStatus() == AsyncTask.Status.PENDING)
            it.execute();
        calendarView = (ZWCalendarView) findViewById(R.id.calendarView);
        show = (TextView) findViewById(R.id.tv_calendar_show);
        calendarView.setSelectListener(new ZWCalendarView.SelectListener() {
            @Override
            public void change(int year, int month) {
                show.setText(String.format("%s 年 %s 月", year, month));
            }

            @Override
            public void select(int year, int month, int day, int week) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                // new Date()为获取当前系统时间
                String today = df.format(new Date());
                String mYear = String.format("%s", year);
                String mMonth = String.format("%02d", month);
                String mDay = String.format("%02d", day);

                String signDay = new StringBuilder().append(mYear).append("-").append(mMonth).append("-").append(mDay).toString();
                Log.d(TAG, "select: -----today = " + today + "\tsignday = " + signDay);
                if (today.equals(signDay)) {
                    sign.put(signDay, true);
                    if (createDate.getStatus() == AsyncTask.Status.PENDING)
                        createDate.execute();
                    setSpSign(sign);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "您只能签到今天呦～",
                            Toast.LENGTH_SHORT).show();
                }
                calendarView.setSignRecords(sign);
            }
        });

        //代码选中一个日期
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendarView.selectDate(2017, 9, 3);
//            }
//        });

        //前一月
        findViewById(R.id.calendar_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.showPreviousMonth();
            }
        });

        //后一月
        findViewById(R.id.calendar_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.showNextMonth();
            }
        });

        //返回今天
        findViewById(R.id.tv_calendar_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.backToday();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public HashMap<String, Boolean> getSpSign() {
        Map<String, Boolean> signedDay = SpUtil.getMap(this, SpUtil.SP_CALENDAR, SpUtil.CALENDAR_SIGNED);
        sign = new HashMap<>();
        if (null != signedDay)
            sign.putAll(signedDay);
        return sign;
    }

    public void setSpSign(HashMap<String, Boolean> spSign) {
        SpUtil.putMap(this, SpUtil.SP_CALENDAR, SpUtil.CALENDAR_SIGNED, spSign);
    }


    ImageTask it = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            return OkHttpUtils.okhttpGet(BaseUrl.URL + "checkins?userid=" + UserInf.getUserId(CalendarActivity.this));
        }

        @Override
        public Object doPraseResult(Object object) {
            String responseBody = (String) object;
            Log.d(TAG, "doPraseResult: ---------------responseBody = " + responseBody);
            return DateBean.arrayDateBeanFromData(responseBody,"checkins");
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            List<DateBean> dates = (List<DateBean>) object;
            if (dates.size() != 0) {
                for (int i = 0; i < dates.size(); i++) {
                    String time = dates.get(i).getTime();
                    Log.d(TAG, "onSuccess: 0-------------time = " + time);
                    sign.put(time, true);
                }
            }
        }

        @Override
        public void onError() {

        }
    });

    ImageTask createDate = new ImageTask(new ImageTask.IRequest() {
        @Override
        public Object doRequest() {
            FormBody formbody = new FormBody.Builder().add("userid", UserInf.getUserId(CalendarActivity.this) + "").build();
            return OkHttpUtils.okhttpPost(formbody, BaseUrl.URL + "checkins");
        }

        @Override
        public Object doPraseResult(Object object) {
            String responseBody = (String) object;
            return DateBean.objectFromData(responseBody, "checkins");
        }
    }, new ImageTask.IRequestCallback() {
        @Override
        public void onSuccess(Object object) {
            int id = ((DateBean) object).getId();
            Log.d(TAG, "onSuccess: ------------id = " + id);
        }

        @Override
        public void onError() {

        }
    });
}
