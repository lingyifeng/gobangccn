package com.lyf.gobangccn.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jiang.common.utils.PrefUtils;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.app.BaseApplication;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.easemob.DemoHelper;
import com.lyf.gobangccn.easemob.db.DemoDBManager;
import com.lyf.gobangccn.ui.login.LoginActivity;
import com.lyf.gobangccn.ui.login.LoginManager;
import com.lyf.gobangccn.ui.main.MainActivity;

import butterknife.BindView;

import static com.lyf.gobangccn.ui.login.LoginActivity.KEY_ACCOUNT;
import static com.lyf.gobangccn.ui.login.LoginActivity.KEY_PASSWORD;

public class SplashActivity extends MVPBaseActivity {


    @BindView(R.id.ll_splash)
    LinearLayout mLlSplash;
    private Handler mHandler = new Handler();
    private boolean isGoMain;
    private boolean isSuccess;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        //动画执行的时间
        alphaAnimation.setDuration(2000);
        mLlSplash.setAnimation(alphaAnimation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String account = PrefUtils.getString(BaseApplication.getAppContext(), KEY_ACCOUNT, null);
        String password = PrefUtils.getString(BaseApplication.getAppContext(), KEY_PASSWORD, null);

        if (account != null && password != null) {
            isGoMain = true;
            DemoDBManager.getInstance().closeDB();

            // reset current user name before login
            DemoHelper.getInstance().setCurrentUserName(account);

            final long start = System.currentTimeMillis();
            // call login method
            Log.d(TAG, "EMClient.getInstance().login");
            EMClient.getInstance().login(account, password, new EMCallBack() {

                @Override
                public void onSuccess() {
                    Log.d(TAG, "login: onSuccess");

                    PrefUtils.putString(BaseApplication.getAppContext(), KEY_ACCOUNT, account);
                    PrefUtils.putString(BaseApplication.getAppContext(), KEY_PASSWORD, password);
                    // ** manually load all local groups and conversation
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();

                    isSuccess = true;
                    // get user's info (this should be get from App's server or 3rd party service)
                    DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                    LoginManager.getInstance().setIslogin(true);

                }

                @Override
                public void onProgress(int progress, String status) {
                    Log.d(TAG, "login: onProgress");
                }

                @Override
                public void onError(final int code, final String message) {
                    Log.d(TAG, "login: onError: " + code);
                    isSuccess = false;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            stopProgressDialog();
                            showShortToast(R.string.Login_failed);
                        }
                    });
                }
            });
        }
        mHandler.postDelayed(() -> {
            if (isGoMain) {

                if (isSuccess) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void initInjector() {

    }

}
