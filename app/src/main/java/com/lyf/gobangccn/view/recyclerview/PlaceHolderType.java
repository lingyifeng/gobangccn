package com.lyf.gobangccn.view.recyclerview;


import com.lyf.gobangccn.R;

/**
 * Created by lenovo on 2016/12/16.
 */

public enum PlaceHolderType {
    NODATA(R.mipmap.placeholder_find, R.string.placeholder_nodata_title, R.string.placeholder_empty);

    private final int icon;
    private final int title;
    private final int content;

    PlaceHolderType(int drawableid, int titleResid, int contentResid) {
        icon = drawableid;
        title = titleResid;
        content = contentResid;
    }

    public int getIcon() {
        return icon;
    }

    public int getTitle() {
        return title;
    }

    public int getContent() {
        return content;
    }

}
