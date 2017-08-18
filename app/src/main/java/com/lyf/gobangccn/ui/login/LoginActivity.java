package com.lyf.gobangccn.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.jiang.common.utils.PrefUtils;
import com.jiang.common.widget.EditTextWithDelete;
import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.app.BaseApplication;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.easemob.DemoHelper;
import com.lyf.gobangccn.easemob.db.DemoDBManager;
import com.lyf.gobangccn.ui.main.MainActivity;
import com.lyf.gobangccn.ui.register.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends MVPBaseActivity {


    @BindView(R.id.edit_account)
    EditTextWithDelete mEditAccount;
    @BindView(R.id.edit_password)
    EditTextWithDelete mEditPassword;
    @BindView(R.id.tv_regist)
    TextView mTvRegist;
    public static final String KEY_ACCOUNT = "ACCOUNT";
    public static final String KEY_PASSWORD = "PASSWORD";

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        new ToolBarBuilder(this)
                .setTitle(getString(R.string.login))
                .setNavigationIcon(R.drawable.ic_back)
                .setNavigationOnClickListener(v -> onBackPressed());
        if (DemoHelper.getInstance().getCurrentUsernName() != null) {
            mEditAccount.setText(DemoHelper.getInstance().getCurrentUsernName());
        }
    }

    @Override
    protected void initInjector() {

    }



    @OnClick({R.id.btn_login, R.id.tv_regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.tv_regist:
                RegisterActivity.startAction(this);
                finish();
                break;
        }
    }

    public void doLogin(){
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            showLongToast(R.string.network_isnot_available);
            return;
        }
        String currentUsername = mEditAccount.getText().toString().trim();
        String currentPassword = mEditPassword.getText().toString().trim();

        if (TextUtils.isEmpty(currentUsername)) {
            showShortToast(R.string.User_name_cannot_be_empty);
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            showShortToast(R.string.Password_cannot_be_empty);
            return;
        }

        startProgressDialog(getString(R.string.Is_landing));

        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // call login method
        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");

                PrefUtils.putString(BaseApplication.getAppContext(),KEY_ACCOUNT,currentUsername);
                PrefUtils.putString(BaseApplication.getAppContext(),KEY_PASSWORD,currentPassword);
                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                if (!LoginActivity.this.isFinishing()) {
                    stopProgressDialog();
                }
                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                LoginManager.getInstance().setIslogin(true);
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);

                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                       stopProgressDialog();
                        showShortToast(R.string.Login_failed);
                    }
                });
            }
        });
    }
}
