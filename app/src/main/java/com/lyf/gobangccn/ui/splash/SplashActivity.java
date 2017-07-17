package com.lyf.gobangccn.ui.splash;

import android.content.Intent;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import com.jiang.common.utils.PrefUtils;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.app.BaseApplication;
import com.lyf.gobangccn.base.MVPBaseActivity;
import com.lyf.gobangccn.ui.login.LoginActivity;
import com.lyf.gobangccn.ui.main.MainActivity;

import butterknife.BindView;

public class SplashActivity extends MVPBaseActivity {


    @BindView(R.id.ll_splash)
    LinearLayout mLlSplash;
    private Handler mHandler = new Handler();
    private boolean isGoMain;

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
        String account = PrefUtils.getString(BaseApplication.getAppContext(), LoginActivity.KEY_ACCOUNT, null);
        String password = PrefUtils.getString(BaseApplication.getAppContext(), LoginActivity.KEY_PASSWORD, null);

        if (account != null && password != null) {
            isGoMain=true;
        }
        mHandler.postDelayed(() -> {
            if(isGoMain){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }else {
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
