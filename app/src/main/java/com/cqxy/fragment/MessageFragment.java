package com.cqxy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cqxy.fyb.ChatActivity;
import com.cqxy.fyb.HomeActivity;
import com.cqxy.fyb.R;
import com.cqxy.listener.MyConnectionListener;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MessageFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private HomeActivity act;
    private TabLayout message_tablayout;
    private FragmentManager fm;
    private EaseContactListFragment contactListFragment;
    private EaseConversationListFragment conversationListFragment;
    private static EMMessageListener emMessageListener;
    private static String TAG = "MessageFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.act = (HomeActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        message_tablayout = view.findViewById(R.id.message_tablayout);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFragment();
        initConversationFragment();
        initTabs();
        initListeners();

    }

    private void initFragment() {

        fm = getFragmentManager();
        fm.beginTransaction().add(R.id.message_fragment, new EaseConversationListFragment()).commit();

    }

    private void initListeners() {
        message_tablayout.addOnTabSelectedListener(this);
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener(act));
        emMessageListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
            }

            //            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };

        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);


    }

    private void initTabs() {
        message_tablayout.addTab(message_tablayout.newTab().setText("聊天"));
        message_tablayout.addTab(message_tablayout.newTab().setText("好友"));
        message_tablayout.getTabAt(0).select();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            //聊天
            case 0:
                initConversationFragment();
                break;
            case 1:
                initContactListFragment();
                break;
        }
    }

    private void initContactListFragment() {
        contactListFragment = new EaseContactListFragment();

        new Thread() {//需要在子线程中调用
            @Override
            public void run() {
                //需要设置联系人列表才能启动fragment
                contactListFragment.setContactsMap(getContact());
            }
        }.start();

        //设置item点击事件
        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {

            @Override
            public void onListItemClicked(EaseUser user) {
                startActivity(new Intent(act, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername()));
            }
        });
        fm.beginTransaction().replace(R.id.message_fragment, contactListFragment).commit();

    }

    private void initConversationFragment() {
        conversationListFragment = new EaseConversationListFragment();

        conversationListFragment.setConversationListItemClickListener(
                new EaseConversationListFragment.EaseConversationListItemClickListener() {
                    @Override
                    public void onListItemClicked(EMConversation conversation) {
                        Intent intent = new Intent(act, ChatActivity.class);
                        intent.putExtra("phone", conversation.conversationId());
                        startActivity(intent);
                    }
                });
        fm.beginTransaction().replace(R.id.message_fragment, conversationListFragment).commit();
    }


    private Map<String, EaseUser> getContact() {
        Map<String, EaseUser> map = new HashMap<>();
        try {
            List<String> userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();//获取列表
            Log.d(TAG, "getContact: ===================联系人列表.size = " + userNames.size());
            for (String userId : userNames) {
                map.put(userId, new EaseUser(userId));
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void addpeoples(final EaseContactListFragment contactListFragment) {

        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {


            @Override
            public void onContactInvited(String username, String reason) {
                //收到好友邀请
                Log.e("-----------", "onContactInvited: " + username);

            }

            @Override
            public void onFriendRequestAccepted(String s) {
                Log.e("-----------", "onFriendRequestAccepted: " + s);
            }

            @Override
            public void onFriendRequestDeclined(String s) {
                Log.e("-----------", "onFriendRequestDeclined: " + s);
            }

            @Override
            public void onContactDeleted(String username) {
                //被删除时回调此方法
                Log.e("-----------", "onContactDeleted: " + username);
            }


            @Override
            public void onContactAdded(String username) {
                //增加了联系人时回调此方法
                Log.e("-----------", "onContactAdded: " + username);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        contactListFragment.setContactsMap(getContacts());
                        contactListFragment.refresh();
                    }
                }).start();

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }
}
