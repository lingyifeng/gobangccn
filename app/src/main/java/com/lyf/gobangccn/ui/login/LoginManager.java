package com.lyf.gobangccn.ui.login;

import com.hyphenate.chat.EMClient;
import com.jiang.common.utils.PrefUtils;
import com.lyf.gobangccn.app.BaseApplication;

/**
 * Created by 01F on 2017/7/22.
 */

public class LoginManager {
    private static volatile LoginManager mLoginManager = null;
    private boolean islogin;

    private LoginManager() {
    }

    public static LoginManager getInstance() {
        if (mLoginManager == null) {
            synchronized (LoginManager.class) {
                if (mLoginManager == null) {
                    mLoginManager = new LoginManager();
                }
            }
        }
        return mLoginManager;
    }

    public void setIslogin(boolean islogin) {
        this.islogin = islogin;
    }

    public boolean isLogin() {
        return islogin;
    }

    public void loginOut(){
        EMClient.getInstance().logout(true, null);
        PrefUtils.putString(BaseApplication.getAppContext(),LoginActivity.KEY_ACCOUNT,null);
        PrefUtils.putString(BaseApplication.getAppContext(),LoginActivity.KEY_PASSWORD,null);
        islogin = false;
    }
}
