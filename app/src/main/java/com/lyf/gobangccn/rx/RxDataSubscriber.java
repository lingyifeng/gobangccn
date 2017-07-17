package com.lyf.gobangccn.rx;

import android.app.Dialog;
import android.content.Context;

import com.jiang.common.widget.LoadingDialog;

import java.lang.ref.WeakReference;

import rx.Subscriber;


/**
 * Created by 01F on 2017/6/9.
 */

public abstract class RxDataSubscriber<T> extends Subscriber<T> {
    private static final String TAG = RxDataSubscriber.class.getName();

    public WeakReference<Context> mReference;
    private Dialog mLoadingDialog;

    public RxDataSubscriber(Context context) {
        mReference = new WeakReference<>(context);
    }

    public void dismissLoadingDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }

    public void showDialogLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.showDialogForLoading(mReference.get());
        }
    }

    @Override
    public void onNext(T response) {
        _onNext(response);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof Exception) {
            //访问获得对应的Exception
            _onError(HttpExceptionHandle.handleException(e).getMessage());
        } else {
            //将Throwable 和 未知错误的status code返回
            _onError(new HttpExceptionHandle.ResponeThrowable(e, HttpExceptionHandle.ERROR.UNKNOWN).getMessage());
        }
    }

    @Override
    public void onCompleted() {
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
