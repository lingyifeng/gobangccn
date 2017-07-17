package com.alipay.lyf.rxjavasample.helper.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by DB on 2016/11/1.
 */

public abstract class BaseHolder<M extends Object> extends RecyclerView.ViewHolder {
    protected M data ;
    protected Context mContext;
    public BaseHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }
    public void setData(M data) {
        this.data = data;
    }
}
