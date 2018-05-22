package com.cqxy.fyb;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cqxy.listener.MyConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

public class ChatActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initFragment();
    }

    private void initFragment() {
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(this));
        fragmentManager=getSupportFragmentManager();
        //new出EaseChatFragment或其子类的实例
        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = new Bundle();
        String phone=getIntent().getStringExtra("phone");
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE,EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, phone);
        chatFragment.setArguments(args);
        fragmentManager.beginTransaction().add(R.id.chat_fragment,chatFragment).commit();
    }

}
