package com.topjet.crediblenumber.location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import com.topjet.crediblenumber.MyApplication;
import com.topjet.crediblenumber.activity.GoodsSearchListActivity;
import com.topjet.crediblenumber.activity.LoginActivity;
import com.topjet.crediblenumber.activity.MainActivity;
import com.topjet.crediblenumber.util.TransportLog;
 

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-5 下午4:41:26  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class BaiduMapLocationProvider {
	private Context mcontext;
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	public BaiduMapLocationProvider(Context context) {
		this.mcontext = context;
		init(context);
	}


	public LocationClient mLocationClient = null;
	public BDLocationListener mBDLocationListener;

	public void init(Context context) {
	
		mBDLocationListener = new BDLocationListener(){
			@Override
			public void onReceiveLocation(BDLocation location) {
				
				TransportLog.i("BaiduMapLocationProvider", "location resultType"+location.getLocType()+"");
				String addr = location.getAddrStr();
				String city = location.getCity();
				String cityCode = location.getCityCode();
				String time = location.getTime();
				TransportLog.i("BaiduMapLocationProvider", location.getProvince() + ", " + location.getCity() + ", " + location.getDistrict() + ", " + location.getStreet() + ", " + location.getStreetNumber());
				TransportLog.i("BaiduMapLocationProvider", addr + "");
				TransportLog.i("BaiduMapLocationProvider", city + "");
				TransportLog.i("BaiduMapLocationProvider", cityCode + "");
				TransportLog.i("BaiduMapLocationProvider", time + "");
				TransportLog.i("BaiduMapLocationProvider", location.getLocType()  + "");
//				if(0.01<location.getLongitude()){
//				if(null == city || null == cityCode){
//					if(mcontext.getClass().equals(LoginActivity.class))
//						goMain();
//					return;
//				}
				
					MyApplication.getInstance().setAddressDetail(location.getProvince(), location.getCity(), location.getDistrict(), location.getStreet(), location.getStreetNumber());
					MyApplication.getInstance().setLocation(city);
					MyApplication.getInstance().setLocationProvince(location.getProvince());
					MyApplication.getInstance().setLocationCode(cityCode);
					MyApplication.getInstance().setLongitude(location.getLongitude());
					MyApplication.getInstance().setLatitude(location.getLatitude());
					MyApplication.getInstance().setLocationTime(getTime(time)); 
					if(MyApplication.getInstance().getShareHelper()!=null){
						MyApplication.getInstance().setLngAndLat(location.getLongitude(), location.getLatitude());
					}
					if(mcontext.getClass().equals(LoginActivity.class))
						doAutoSearch(cityCode);
//				}
			}

			public void onReceivePoi(BDLocation poiLocation) {
				//not use
			}
		};
		mLocationClient = new LocationClient(context); // 声明LocationClient类
		mLocationClient.setAK("FAabee918bf0012933a0921e8e99e1fe");
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false);//不用GPS定位
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
//		option.setScanSpan(5000);// 手动发起定位请求
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		option.setPriority(LocationClientOption.NetWorkFirst);//需要地址所以优先使用网络定位
		option.setProdName("运商行");
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(mBDLocationListener); // 注册监听函数
		mLocationClient.start();
	}	


	public void start(){
		mLocationClient.start();
	}

	public void requestLocation() {
		mLocationClient.requestLocation();
	}

	public void pause(){
		if(mLocationClient.isStarted()){
			mLocationClient.stop();
		}
	}

	public void stop(){
		
	}

	public void destroy(){
		stop();
		
	}

	private long getTime(String t){
		try {
			return formatter.parse(t).getTime();
		} catch (ParseException e) {
			return new Date().getTime();
		}
	}
	//登录之后，跳转到智能找货结果页
	private void doAutoSearch(String locationCode) {
		Intent intent = new Intent(mcontext, GoodsSearchListActivity.class);
		intent.putExtra("auto",true);
		intent.putExtra("Depart",locationCode);
		mcontext.startActivity(intent);
	}
	private void goMain() {
		MyApplication.getInstance().getScreenManager().popActivity((Activity) mcontext);
		Intent intent = new Intent(mcontext, MainActivity.class);
		mcontext.startActivity(intent);
		((Activity) mcontext).finish();
	}
}