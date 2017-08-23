package com.lyf.gobangccn.ui.gobang;

import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.jiang.common.utils.LogUtils;
import com.jiang.common.utils.ToastUtil;
import com.jiang.common.widget.ToolBarBuilder;
import com.lyf.gobangccn.R;
import com.lyf.gobangccn.base.MVPBaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 01F on 2017/7/2.
 */

public class GobangFragment extends MVPBaseFragment {
    @BindView(R.id.btn_aid)
    Button mBtnAid;
    @BindView(R.id.mapview)
    MapView mMapview;


    private int chatType = EaseConstant.CHATTYPE_SINGLE;

    private BaiduMap mBaiduMap;
    private String mAddress;
    private Double latitude;
    private Double longtitude;
    private LatLng center;
    private LocationClient mMLocationClient;

    @Override
    public int getLayoutId() {
        return R.layout.frag_gobang;
    }

    @Override
    protected void init(View view) {
        new ToolBarBuilder(view)
                .setTitle("定位");

        initMap();
    }

    private void initMap() {
        mBaiduMap = mMapview.getMap();
        LocationClientOption mLocationClientOption = new LocationClientOption();
        mLocationClientOption.setCoorType("bd09ll");// 设置坐标类型
        mLocationClientOption.setIsNeedAddress(true);// 设置是否需要地址信息，默认为无地址
        mLocationClientOption.setOpenGps(true);// 设置是否打开gps进行定位
        mLocationClientOption.setScanSpan(2000);// 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
        /*
        LocationClient定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
         */
        mMLocationClient = new LocationClient(mContext, mLocationClientOption);
        /*
        开始定位
         */
        MyBDLocationListener mBDLocationListener = new MyBDLocationListener();// 获取监听对象（MyBDLocationListener是实现了BDLocationListener接口的类）
        mMLocationClient.registerLocationListener(mBDLocationListener);// 注册定位监听函数
        mMLocationClient.start();// 启动定位sdk
        mMLocationClient.requestLocation();
    }

    @Override
    protected void initInjector() {

    }

    @OnClick(R.id.btn_aid)
    public void onViewClicked() {
        if (mAddress != null && latitude != null && longtitude != null) {
            sendTextMessage("地址：" + mAddress + " 纬度：" + latitude + " 经度： " + longtitude);
            ToastUtil.showLong("地址：" + mAddress + " 纬度：" + latitude + " 经度： " + longtitude);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapview.onPause();
    }

    @Override
    public void onDestroyView() {
        mMapview.onDestroy();
        super.onDestroyView();
    }


    //send message
    protected void sendTextMessage(String content) {
        EMMessage message = EMMessage.createTxtSendMessage(content, "Lingyifeng");
        sendMessage(message);

    }

    protected void sendMessage(EMMessage message) {
        if (message == null) {
            return;
        }

        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            message.setChatType(EMMessage.ChatType.GroupChat);
        } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
            message.setChatType(EMMessage.ChatType.ChatRoom);
        }
        //send message
        EMClient.getInstance().chatManager().sendMessage(message);

    }

    /**
     * 实现定位请求回调接口
     */
    private class MyBDLocationListener implements BDLocationListener {

        /**
         * 发生定位时的回调方法
         *
         * @param bdLocation 百度坐标类，内部封装了如经纬度、半径等属性信息
         */
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            /*
            百度地图：下面是根据定位信息，落实到地图相关的代码，以及百度地图界面相关配置
             */
            mBaiduMap = mMapview.getMap();
            mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.zoomTo(18.0f);// 设置初始缩放级别
            mBaiduMap.setMapStatus(mMapStatusUpdate);// 改变地图状态
            /*
            MyLocationData定位数据对象
             */
            MyLocationData mLocationData = new MyLocationData.Builder()
                    .accuracy(1)// 定位精度
                    .direction(bdLocation.getDirection())// GPS定位时方向角度，顺时针0-360
                    .latitude(bdLocation.getLatitude())//百度纬度坐标
                    .longitude(bdLocation.getLongitude())//百度经度坐标
                    .build();// 设置相关显示数据
            mBaiduMap.setMyLocationData(mLocationData);// 把定位数据设置到BaiduMap对象
            MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.COMPASS,
                    true,
                    null);
            mBaiduMap.setMyLocationConfigeration(mLocationConfiguration);// 把定位图层显示方式设置到BaiduMap对象
            mAddress = bdLocation.getAddrStr();
            LogUtils.loge("address: " + mAddress);
            if (bdLocation.getStreetNumber() != null) {
                LogUtils.loge("addressnum:" + bdLocation.getStreetNumber());
                if (bdLocation.getStreet() != null) {
                    LogUtils.loge("addressstreet:" + bdLocation.getStreet());
                }
            }
//            //获取定位经度
            latitude = bdLocation.getLatitude();
//            //获取定位纬度
            longtitude = bdLocation.getLongitude();
//            center = new LatLng(latitude, longtitude);

        }


    }

}
