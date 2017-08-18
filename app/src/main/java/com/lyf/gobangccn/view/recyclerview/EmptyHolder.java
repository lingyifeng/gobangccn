package com.lyf.gobangccn.view.recyclerview;

import android.view.View;

import com.lyf.gobangccn.R;

/**
 * Created by DB on 2016/11/1.
 */

public class EmptyHolder extends IViewHolder {

    public EmptyHolder(View itemView) {
        super(itemView);
    }

    public void setErrorType(PlaceHolderType status) {
        this.setText(R.id.tv_placeholder, status.getTitle())
                .setImageResource(R.id.img_placeholder, status.getIcon());
    }
}
