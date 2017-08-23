package com.lyf.gobangccn.net.model;

import com.lyf.gobangccn.net.bean.LocalBean;
import com.lyf.gobangccn.net.service.LocalService;

import rx.Observable;

/**
 * Created by 01F on 2017/8/22.
 */

public class LocalModel {
    private LocalService mLocalService;
    public LocalModel(LocalService service){
        this.mLocalService=service;
    }

    public Observable<LocalBean> getLocalAdress(String qt,String lnglat,String key,
                                                String output,String pf,String ref){
        return mLocalService.getLocalAdress(qt, lnglat, key, output, pf, ref);
    }
}
