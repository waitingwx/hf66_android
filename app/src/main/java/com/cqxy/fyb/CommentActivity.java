package com.cqxy.fyb;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqxy.adapter.CommentListViewAdapter;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.CommentBean;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.TextContentUtils;
import com.cqxy.utils.UserInf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvAcTitle;
    private ListView lvComment;
    private String TAG = "CommentActivity";
    List<CommentBean.CommentsBean> comments;
    private TextView tvNocomment;
    private EditText etCommentbody;
    private int exampleid;
    private String urlStr;
    private CommentListViewAdapter lvCommentAdapter;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initViews();
        requestDatas();
    }

    private void requestDatas() {
        Bundle extras = getIntent().getBundleExtra("ids");
        exampleid = extras.getInt("exampleid");
        String url = BaseUrl.URL + "comments?id=" + exampleid;
//        String url = BaseUrl.url+"comments?id="+3;
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(this), UserInf.getUserPhonenum(this), url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responsebody = response.body().string();
                Log.d(TAG, "onResponse: responsebody = " + responsebody);
                comments = CommentBean.objectFromData(responsebody).getComments();
                Log.d(TAG, "onResponse: comments.size1 = " + comments.size());
                showNonComments(comments);
            }
        });
    }

    private void showNonComments(final List<CommentBean.CommentsBean> comments) {
        Log.d(TAG, "showNonComments: comments.size2 = " + comments.size());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null == comments || 0 == comments.size()) {
                    tvNocomment.setVisibility(View.VISIBLE);
                } else {
                    tvNocomment.setVisibility(View.GONE);
                    lvCommentAdapter = new CommentListViewAdapter(CommentActivity.this, (ArrayList<CommentBean.CommentsBean>) comments);
                    lvComment.setAdapter(lvCommentAdapter);
                    lvComment.setDivider(new ColorDrawable(Color.rgb(246, 246, 246)));
                    lvComment.setDividerHeight(2);
                    lvComment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.d(TAG, "onItemLongClick: -----------------VIEW");
                            Toast.makeText(CommentActivity.this, "长按添加子评论", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });
                    lvCommentAdapter.notifyDataSetChanged();
                }
                //
            }
        });
    }

    private void initViews() {
        ivBack = ((ImageView) findViewById(R.id.house_enter_back));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvAcTitle = (TextView) findViewById(R.id.title_text);
        tvAcTitle.setText("全部评论");
        btnSubmit = ((Button) findViewById(R.id.btn_submit));
        lvComment = ((ListView) findViewById(R.id.lv_comment));
        tvNocomment = ((TextView) findViewById(R.id.tv_nocomment));
        etCommentbody = ((EditText) findViewById(R.id.et_comment_body));
        etCommentbody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSubmit.setTextColor(getResources().getColor(R.color.colorBlue));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void btnClick(View view) {
        String commentBody = TextContentUtils.getViewContent(etCommentbody);
        urlStr = BaseUrl.URL + "comments";
        postAsyn(commentBody, urlStr);
    }

    private void postAsyn(String commentBody, String urlStr) {
        FormBody formBody = new FormBody.Builder()
                .add("user_id", UserInf.getUserId(this) + "")
                .add("id", exampleid + "")
                .add("content", commentBody)
                .build();
        OkHttpUtils.okhttpPostWithHead(UserInf.getUserToken(CommentActivity.this),
                UserInf.getUserPhonenum(CommentActivity.this),
                formBody, urlStr, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: --------------------------------------------post response");
                        String responseBody = new String(response.body().bytes(), "UTF-8");
                        //TODO:获取到的data加到list最顶部
                        Log.d(TAG, "onResponse:post ----------responsemessage = " + responseBody);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                etCommentbody.setText("");
                            }
                        });
                        //暂时获取不到response,怀疑是后台问题，error:  ActionController::UnknownFormat
//                in Api::V1::CommentsController#create///////Api::V1::CommentsController#create is missing a template for this request format and variant.
                        //使用再次获取评论列表来刷新
                        requestDatas();

                    }
                });
    }

    private void requestPostBySyn(String actionUrl, String commentBody) {
        try {
            Log.d(TAG, "requestPostBySyn: ====================start,actionUrl = " + actionUrl);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();

            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            //生成表单实体对象
            FormBody formBody = new FormBody.Builder()
                    .add("user_id", UserInf.getUserId(this) + "")
                    .add("id", exampleid + "")
                    .add("content", commentBody)
                    .build();
            //创建一个请求
            final Request request = new Request.Builder()
                    .url(actionUrl)
                    .post(formBody)
                    .build();
            //创建一个Call
            final Call call = okHttpClient.newCall(request);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: =======================");
                    //执行请求
                    Response response = null;
                    try {
                        response = call.execute();
                        if (response.isSuccessful()) {
                            Log.e(TAG, "response ----->" + response.body().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "run: ===========================end");
                }
            }).start();

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
