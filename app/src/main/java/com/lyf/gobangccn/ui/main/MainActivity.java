package com.lyf.gobangccn.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.EMLog;
import com.jiang.common.widget.BottomTabView;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.base.MVPBaseFragment;
import com.lyf.gobangccn.easemob.Constant;
import com.lyf.gobangccn.easemob.DemoHelper;
import com.lyf.gobangccn.ui.contacts.ContactsFragment;
import com.lyf.gobangccn.ui.gobang.GobangFragment;
import com.lyf.gobangccn.ui.login.LoginActivity;
import com.lyf.gobangccn.ui.message.MessageFrament;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;

public class MainActivity extends MVPBaseActivity {

    public boolean isConflict = false;
    @BindString(R.string.gobang)
    String goBang;
    @BindString(R.string.message)
    String message;
    @BindString(R.string.contacts)
    String contacts;
    @BindView(R.id.tab_main)
    BottomTabView mTabMain;
    TextView unreadLabel;
    private boolean isExceptionDialogShow = false;
    private ArrayList<MVPBaseFragment> mFragments;
    private int currentPosition;
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };
    private AlertDialog.Builder exceptionBuilder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //make sure activity will not in background if user is logged into another device or removed
        if (savedInstanceState != null && savedInstanceState.getBoolean(Constant.ACCOUNT_REMOVED, false)) {
            DemoHelper.getInstance().logout(false, null);
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        } else if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false)) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        ArrayList<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        BottomTabView.TabItemView goBangTabView = new BottomTabView.TabItemView(this, goBang, R.color.unchoose, R.color.dot_color, R.mipmap.doc,
                R.mipmap.doc_blue);
        BottomTabView.TabItemView messageTabView = new BottomTabView.TabItemView(this, message, R.color.unchoose, R.color.dot_color, R.mipmap.massage,
                R.mipmap.massage_blue);
        unreadLabel = messageTabView.tvUnRead;
        unreadLabel.setVisibility(View.VISIBLE);
        BottomTabView.TabItemView contactTabView = new BottomTabView.TabItemView(this, contacts, R.color.unchoose, R.color.dot_color, R.mipmap.contacts,
                R.mipmap.contactsblue);
        tabItemViews.add(goBangTabView);
        tabItemViews.add(messageTabView);
        tabItemViews.add(contactTabView);
        mTabMain.setTabItemViews(tabItemViews);

        mFragments = new ArrayList<>();
        mFragments.add((GobangFragment) getSupportFragmentManager().findFragmentById(R.id.frag_gobang));
        mFragments.add((MessageFrament) getSupportFragmentManager().findFragmentById(R.id.frag_message));
        mFragments.add((ContactsFragment) getSupportFragmentManager().findFragmentById(R.id.frag_contact));
       changePosition(0);
        mTabMain.setOnTabItemSelectListener(new BottomTabView.OnTabItemSelectListener() {
            @Override
            public void onTabItemSelect(int position) {
                currentPosition=position;
                changePosition(position);

            }
        });
    }

    private void changePosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            if (i == position) {
                ft.show(mFragments.get(i));
            } else {
                ft.hide(mFragments.get(i));
            }

        }
        ft.commit();
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isConflict) {
            updateUnreadLabel();
        }

        // unregister this event listener when this activity enters the
        // background
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(this);

        EMClient.getInstance().chatManager().addMessageListener(messageListener);


    }

    /**
     * get unread message count
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    /**
     * update unread message count
     */
    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(String.valueOf(count));
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showExceptionDialogFromIntent(intent);
    }

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();

                if (currentPosition == 1) {
                    // refresh conversation list
                    if (mFragments.get(1) != null) {
                        mFragments.get(1).refresh();
                    }
                }
            }
        });
    }

    /**
     * show the dialog when user met some exception: such as login on another device, user removed or user forbidden
     */
    private void showExceptionDialog(String exceptionType) {
        isExceptionDialogShow = true;
        DemoHelper.getInstance().logout(false, null);
        String st = getResources().getString(R.string.Logoff_notification);
        if (!MainActivity.this.isFinishing()) {
            // clear up global variables
            try {
                if (exceptionBuilder == null)
                    exceptionBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                exceptionBuilder.setTitle(st);
                exceptionBuilder.setMessage(getExceptionMessageId(exceptionType));
                exceptionBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        isExceptionDialogShow = false;
                        finish();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                exceptionBuilder.setCancelable(false);
                exceptionBuilder.create().show();
                isConflict = true;
            } catch (Exception e) {
                EMLog.e(TAG, "---------color conflictBuilder error" + e.getMessage());
            }
        }
    }

    private int getExceptionMessageId(String exceptionType) {
        if (exceptionType.equals(Constant.ACCOUNT_CONFLICT)) {
            return R.string.connect_conflict;
        } else if (exceptionType.equals(Constant.ACCOUNT_REMOVED)) {
            return R.string.em_user_remove;
        } else if (exceptionType.equals(Constant.ACCOUNT_FORBIDDEN)) {
            return R.string.user_forbidden;
        }
        return R.string.Network_error;
    }

    private void showExceptionDialogFromIntent(Intent intent) {
        EMLog.e(TAG, "showExceptionDialogFromIntent");
        if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_CONFLICT, false)) {
            showExceptionDialog(Constant.ACCOUNT_CONFLICT);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_REMOVED, false)) {
            showExceptionDialog(Constant.ACCOUNT_REMOVED);
        } else if (!isExceptionDialogShow && intent.getBooleanExtra(Constant.ACCOUNT_FORBIDDEN, false)) {
            showExceptionDialog(Constant.ACCOUNT_FORBIDDEN);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isConflict", isConflict);
        super.onSaveInstanceState(outState);
    }
}
