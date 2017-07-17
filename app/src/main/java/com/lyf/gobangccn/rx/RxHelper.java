package com.lyf.gobangccn.rx;


import com.lyf.gobangccn.net.bean.ResponseInfo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 01F on 2017/6/9.
 */

public class RxHelper {
    // 数据预处理和线程管理
    public static <T> Observable.Transformer<ResponseInfo<T>, T> handleResultBody() {
        return new Observable.Transformer<ResponseInfo<T>, T>() {
            @Override
            public Observable<T> call(Observable<ResponseInfo<T>> tObservable) {
                return tObservable.flatMap(new Func1<ResponseInfo<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(ResponseInfo<T> tResponseInfo) {
                        if (tResponseInfo.isSuccess()) {
                            return Observable.just(tResponseInfo.getBody());
                        }
                        return Observable.error(new Exception(tResponseInfo.getMsg()));
                    }
                }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };



//            new Observable.Transformer<ResponseInfo<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<ResponseInfo<T>> tObservable) {
//                Observable<ActivityEvent> eventObservable = lifecycleSubject.takeFirst(new Func1<ActivityEvent, Boolean>() {
//                    @Override
//                    public Boolean call(ActivityEvent activityevent) {
//                        return activityevent.equals(event);
//                    }
//                });
//                return tObservable.flatMap(new Func1<ResponseInfo<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> call(ResponseInfo<T> tResponseInfo) {
//                        if (tResponseInfo.isSuccess()) {
//                            return Observable.just(tResponseInfo.getBody());
//                        }
//                        return Observable.error(new Exception(tResponseInfo.getMsg()));
//                    }
//                }).takeUntil(eventObservable)
//                        .subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .subscribeOn(AndroidSchedulers.mainThread())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };


    }

    public static <T> Observable<T> getResult(Observable<ResponseInfo<T>> observable) {
        return observable.flatMap(new Func1<ResponseInfo<T>, Observable<T>>() {
            @Override
            public Observable<T> call(ResponseInfo<T> tResponseInfo) {
                if (tResponseInfo.isSuccess()) {
                    return Observable.just(tResponseInfo.getBody());
                }
                return Observable.error(new Exception(tResponseInfo.getMsg()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<ResponseInfo<T>, String> handleResultMsg() {
        return tObservable -> tObservable.flatMap(new Func1<ResponseInfo<T>, Observable<String>>() {
            @Override
            public Observable<String> call(ResponseInfo<T> tResponseInfo) {
                if (tResponseInfo.isSuccess()) {
                    return Observable.just(tResponseInfo.getMsg());
                }
                return Observable.error(new Exception(tResponseInfo.getMsg()));
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
