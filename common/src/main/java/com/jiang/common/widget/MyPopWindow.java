package com.jiang.common.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by CJ on 2017/2/22.
 */

public class MyPopWindow extends PopupWindow {
    public MyPopWindow(Context context) {
        super(context);
    }

    public MyPopWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPopWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyPopWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchorView, int xoff, int yoff) {

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {

            int[] a = new int[2];
            anchorView.getLocationInWindow(a);

            showAtLocation(anchorView, Gravity.NO_GRAVITY, xoff, a[1] + anchorView.getHeight() + yoff);

        } else {
            super.showAsDropDown(anchorView, xoff, yoff);
        }
    }
}
