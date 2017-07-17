package com.lyf.gobangccn.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jiang.common.base.CommonActivity;
import com.jiang.common.utils.AppManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MVPBaseActivity<T extends BasePresenter> extends CommonActivity {


    @Inject
    public T mPresenter;
    public Context mContext;
    private int count = -1;

    private Unbinder unbinder;

    protected String TAG = null;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (unbinder == null)
            unbinder = ButterKnife.bind(this);
        mContext = this;
        //init()中只进行初始化动作
        init();
        initInjector();
        TAG = getClass().getSimpleName();
    }



    /*********************
     * 子类实现
     *****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //初始化view
    protected abstract void init();

    //    //Dagger 注入
    protected abstract void initInjector();


    public T getPresenter() {
        return mPresenter;
    }


    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter = null;
        if (unbinder != null) {
            unbinder.unbind();
        }
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }
}
