package com.lyf.gobangccn.net.service;

import com.lyf.gobangccn.net.bean.LocalBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 01F on 2017/8/22.
 */

public interface LocalService {
    @GET("/jsapi")
    Observable<LocalBean> getLocalAdress(@Query("qt")String qt,@Query("lnglat")String latLong,
                                         @Query("key")String key,@Query("output")String output,
                                         @Query("pf")String pf,@Query("ref")String ref);
}
