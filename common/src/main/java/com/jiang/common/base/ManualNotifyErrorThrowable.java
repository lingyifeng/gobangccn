package com.jiang.common.base;

/**
 * Created by jiang on 2017/4/28.
 */

public class ManualNotifyErrorThrowable extends Throwable {

    public ManualNotifyErrorThrowable(String message) {
        this(message, new Throwable());
    }

    public ManualNotifyErrorThrowable(String message, Throwable cause) {
        super(message, cause);
    }
}
