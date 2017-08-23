package com.lyf.gobangccn.net.api;

/**
 * Created by lenovo on 2016/12/10.
 */

public enum ApiType {

    //        DOMAIN(0, "http://192.168.8.130:8085/"),
//    DOMAIN(0, "https://mobile.chaojihr.com/"),
    DOMAIN(0, "http://dev.mobile.chaojihr.com/"),
    WECHAT(1, "https://api.weixin.qq.com/"),
    LOCAL(3,"http://apis.map.qq.com/"),
    UPDATE(2, "http://moboleupdate.pub.idaqi.com/");

    private final int id;
    private final String url;

    ApiType(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public static ApiType[] Array() {
        return new ApiType[]{DOMAIN, WECHAT, UPDATE};
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
