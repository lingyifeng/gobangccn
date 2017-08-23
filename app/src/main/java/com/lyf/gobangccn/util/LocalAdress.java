package com.lyf.gobangccn.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.jiang.common.utils.LogUtils;
import com.lyf.gobangccn.app.BaseApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

/**
 * Created by 01F on 2017/8/22.
 */

public class LocalAdress {

    private static LocationManager locationManager;
    private static LocationListener locationListener;
    private static LinkedHashMap<String, Object> map = new LinkedHashMap<>();
    private static Context mContext=BaseApplication.getAppContext();
    //基站定位
    public static LinkedHashMap<String, Object> getLocation() {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return map;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        map.put("lat", location.getLatitude());
        map.put("long", location.getLongitude());
        map.put("alt", location.getAltitude());
        map.put("time", location.getTime());
        LogUtils.loge("纬度：" + location.getLatitude());
        LogUtils.loge("经度：" + location.getLongitude());
        LogUtils.loge("海拔：" + location.getAltitude());
        LogUtils.loge("时间：" + location.getTime());
        return map;
    }

    /***
     * GPS 获取经纬度
     * @return
     */
    public static LinkedHashMap<String, Object> formListenerGetLocation() {
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //位置信息变化时触发
                map.put("lat", location.getLatitude());
                map.put("long", location.getLongitude());
                map.put("alt", location.getAltitude());
                map.put("time", location.getTime());
                LogUtils.loge("纬度：" + location.getLatitude());
                LogUtils.loge("经度：" + location.getLongitude());
                LogUtils.loge("海拔：" + location.getAltitude());
                LogUtils.loge("时间：" + location.getTime());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //GPS状态变化时触发
            }

            @Override
            public void onProviderEnabled(String provider) {
                //GPS禁用时触发
            }

            @Override
            public void onProviderDisabled(String provider) {
                //GPS开启时触发
            }
        };
        /**
         * 绑定监听
         * 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种，前者是GPS,后者是GPRS以及WIFI定位
         * 参数2，位置信息更新周期.单位是毫秒
         * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
         * 参数4，监听
         * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
         */
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return map;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        return map;
    }

    /***
     * wifi获取经纬度
     * @return
     */
    public static LinkedHashMap<String, Object> fromWIFILocation(){
        //WIFI的MAC地址
        WifiManager manager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        String wifiAddress = manager.getConnectionInfo().getBSSID();
        //根据WIFI信息定位
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://www.google.com/loc/json");
        JSONObject holder = new JSONObject();
        try {
            holder.put("version" , "1.1.0");
            holder.put( "host" , "maps.google.com");
            JSONObject data;
            JSONArray array = new JSONArray();
            if(wifiAddress!=null&&!wifiAddress.equals("")){
                data = new JSONObject();
                data.put("mac_address", wifiAddress);
                data.put("signal_strength", 8);
                data.put("age", 0);
                array.put(data);
            }
            holder.put("wifi_towers",array);
            StringEntity se = new StringEntity(holder.toString());
            post.setEntity(se);
            HttpResponse resp =client.execute(post);
            int state =resp.getStatusLine().getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity entity =resp.getEntity();
                if (entity != null) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
                    StringBuffer sb = new StringBuffer();
                    String resute = "";
                    while ((resute =br.readLine()) != null) {
                        sb.append(resute);
                    }
                    br.close();
                    data = new JSONObject(sb.toString());
                    data = (JSONObject)data.get("location");
                    Location loc = new Location(LocationManager.NETWORK_PROVIDER);
                    loc.setLatitude((Double)data.get("latitude"));
                    loc.setLongitude((Double)data.get("longitude"));
                    //其他信息同样获取方法
                    map.put("lat", data.get("latitude"));
                    map.put("long", data.get("longitude"));
                    LogUtils.loge("纬度：" + data.get("latitude"));
                    LogUtils.loge("经度：" + data.get("longitude"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }
}
