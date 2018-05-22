package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cqxy.base.BaseUrl;
import com.cqxy.bean.Users;
import com.cqxy.fyb.ContantUsActivity;
import com.cqxy.fyb.HomeActivity;
import com.cqxy.fyb.IdentityActivity;
import com.cqxy.fyb.LoginActivity;
import com.cqxy.fyb.MyWealthActivity;
import com.cqxy.fyb.PersonalActivity;
import com.cqxy.fyb.PortActivity;
import com.cqxy.fyb.R;
import com.cqxy.fyb.RegisteActivity;
import com.cqxy.fyb.UpdatePassWordActivity;
import com.cqxy.fyb.individualActivity;
import com.cqxy.utils.GetImgFromAlbum;
import com.cqxy.utils.OkHttpUtils;
import com.cqxy.utils.SpUtil;
import com.cqxy.utils.ToastUtil;
import com.cqxy.utils.UserInf;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

import static com.bumptech.glide.request.RequestOptions.errorOf;
import static com.bumptech.glide.request.RequestOptions.overrideOf;
import static com.bumptech.glide.request.RequestOptions.placeholderOf;

/**
 * Created by Administrator on 2017/9/15.
 */

public class PersonalFragment extends Fragment {
    @BindView(R.id.personal_head)
    ImageView personalHead;
    @BindView(R.id.personal_name)
    TextView personalName;

    @BindView(R.id.personal_layout_name)
    LinearLayout personalLayoutName;
    @BindView(R.id.personal_layout_wealth)
    LinearLayout personalLayoutWealth;
    @BindView(R.id.personal_layout_idmessage)
    LinearLayout personalLayoutIdmessage;
    @BindView(R.id.personal_layout_updatepassword)
    LinearLayout personalLayoutUpdatepassword;
    @BindView(R.id.personal_layout_port)
    LinearLayout personalLayoutPort;
    @BindView(R.id.personal_layout_contactus)
    LinearLayout personalLayoutContactus;
    @BindView(R.id.personal_loginout)
    Button personalLoginout;
    @BindView(R.id.personal_layout_mystore)
    LinearLayout personalLayoutMystore;
    @BindView(R.id.ll_recommend)
    LinearLayout llRecommend;
    @BindView(R.id.rl_help)
    LinearLayout rlHelp;

    private HomeActivity act;
    private Unbinder unbinder;
    private String TAG = "PersonalFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (HomeActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_personal, null);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String name = "" == UserInf.getUserName(act) ? UserInf.getUserPhonenum(act) : UserInf.getUserName(act);
        personalName.setText(name);
        String userLocalAvatar = UserInf.getUserLocalAvatar(act);
        Log.d("PersonalFragment", "onActivityCreated: ============userid = " + UserInf.getUserId(act));
        if ("" != userLocalAvatar) {
            Log.d(TAG, "onActivityCreated: ------------------------------userLocalAvatar" + userLocalAvatar);
            GetImgFromAlbum.showImage(userLocalAvatar, personalHead);
        }else
            RemotePic();
    }

    private void RemotePic() {
        Log.d(TAG, "RemotePic: -------------------start");
        OkHttpUtils.okhttpGetNeedHead(UserInf.getUserToken(act), UserInf.getUserPhonenum(act),
                BaseUrl.URL + "users/" + UserInf.getUserId(act) + ".json", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        Log.d("users", "onResponse: ===============string" + string);
                        final Users users = Users.objectFromData(string, "user");
                        if (null == users)
                            return;
                        act.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int endIndex = users.getAsset().indexOf("?");
                                if (endIndex == -1)
                                    return;
                                String substring = users.getAsset().substring(0, endIndex);
                                SpUtil.putString(act, SpUtil.SP_USER, SpUtil.USER_ASSET, BaseUrl.IMGURL + substring);
                                Glide.with(act)
                                        .load(BaseUrl.IMGURL + substring)
                                        .apply(overrideOf(40, 40))
                                        .apply(placeholderOf(R.mipmap.mine_man))
                                        .apply(errorOf(R.mipmap.mine_man))
                                        .into(personalHead);
                            }
                        });
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.personal_layout_name,
            R.id.personal_layout_mystore,
            R.id.personal_layout_wealth,
            R.id.personal_layout_idmessage,
            R.id.personal_layout_updatepassword,
            R.id.personal_layout_port,
            R.id.personal_layout_contactus,
            R.id.personal_loginout,
            R.id.ll_recommend,
            R.id.rl_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_layout_name:
                Intent updateIntent = new Intent(act, PersonalActivity.class);
                startActivityForResult(updateIntent,0x001);
                break;
            case R.id.personal_layout_mystore:
                startActivity(new Intent(act, individualActivity.class));
                break;
            case R.id.personal_layout_wealth:
                startActivity(new Intent(act, MyWealthActivity.class));
                break;
            case R.id.personal_layout_idmessage:
                startActivity(new Intent(act, IdentityActivity.class));

                break;
            case R.id.personal_layout_updatepassword:
                Intent intent = new Intent(act, UpdatePassWordActivity.class);
                intent.putExtra("phone", UserInf.getUserPhonenum(act));
                startActivity(intent);
                break;
            case R.id.personal_layout_port:
                startActivity(new Intent(act, PortActivity.class));
                break;
            case R.id.personal_layout_contactus:
                startActivity(new Intent(act, ContantUsActivity.class));
                break;
            case R.id.personal_loginout:
                loginout();
                break;
            case R.id.rl_help:
                resetHelp();
                break;
            case R.id.ll_recommend:
                startActivity(new Intent(act, RegisteActivity.class).addFlags(0x001));
                break;
        }
    }

    private void resetHelp() {
        MaterialShowcaseView.resetSingleUse(act, "homeactivity");
        ToastUtil.showToast(act,"已重置帮助页面",Toast.LENGTH_SHORT);
    }

    public void loginout() {
        // 调用sdk的退出登录方法，第一个参数表示是否解绑推送的token，没有使用推送或者被踢都要传false
        EMClient.getInstance().logout(false, new EMCallBack() {
            @Override
            public void onSuccess() {
                // 调用退出成功，结束app
                startActivity(new Intent(act, LoginActivity.class));
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(act, "退出登录成功！", Toast.LENGTH_SHORT).show();
                    }
                });

                act.finish();
                //TODO:清空所有userinfo
                SpUtil.clearSp(act, SpUtil.SP_USER);
//                AppPreferences appPreferences = new AppPreferences(act);
//                appPreferences.clear();

            }

            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ------------------------------------------------------------------------");
        if(null == data)
            return;
        String name = data.getStringExtra("name");
        String picUrl = data.getStringExtra("picurl");
        switch (requestCode) {
            case 0x001:
                Log.d(TAG, "onActivityResult: -------name = " + name+"\npicUrl = " + picUrl);
                showName(name);
                showAvatar(picUrl);
                break;
        }
    }

    private void showAvatar(String picUrl) {
        Log.d(TAG, "showAvatar: ------------start----picurl = " +picUrl);
        if("" == picUrl || null == picUrl)
            return;
        GetImgFromAlbum.showImage(picUrl, personalHead);
    }

    private void showName(String name) {
        Log.d(TAG, "showName: ------------------start-----name = "+ name);
        if("" == name || null == name)
            return;
        personalName.setText(name);
    }
}
