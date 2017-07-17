package com.lyf.gobangccn.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 01F on 2017/6/9.
 */

public class RxSchedulers {
    private final static Observable.Transformer schedulersTransformer = new  Observable.Transformer() {
        @Override public Object call(Object observable) {
            return ((Observable)  observable).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };
    //因为这里schedulers并不会与发送出的事件产生任何的互动操作。
    //确保Transformer真正与类型无关。否则，即使代码通过了编译
    //在运行时仍然存在抛出ClassCastException异常。
    public static <T> Observable.Transformer<T, T> io_main() {
        return (Observable.Transformer<T, T>)schedulersTransformer;
    }
}

