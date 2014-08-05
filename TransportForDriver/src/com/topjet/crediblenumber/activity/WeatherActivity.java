package com.topjet.crediblenumber.activity;

import org.json.JSONArray;
import org.json.JSONObject;
 
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.dialog.CommonDialog;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.DisplayUtil;
import com.topjet.crediblenumber.util.Receivable;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 

/**
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-25 下午4:21:17  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class WeatherActivity 
extends BaseActivity 
implements OnClickListener,Receivable {

	TextView mCityView;
	ImageView mIcon0View;
	TextView mInfo0View;
	ImageView mIcon1View;
	TextView mInfo1View;
	ImageView mIcon2View;
	TextView mInfo2View;

	TextView mIndexDressingView;
	TextView mIndexDressingDetailView;
	TextView mIndexComfortableView;
	TextView mIndexComfortableDetailView;
	TextView mIndexColdView;
	TextView mIndexColdDetailView;
	TextView mIndexCarWashView;
	TextView mIndexCarWashDetailView;
	TextView mIndexPollutionView;
	TextView mIndexPollutionDetailView;
	TextView mIndexMorningView;
	TextView mIndexMorningDetailView;
	TextView mIndexStreetView;
	TextView mIndexStreetDetailView;
	TextView mIndexUltravioletView;
	TextView mIndexUltravioletDetailView;

	private static final int TYPE_PROVINCE = 0;
	private static final int TYPE_CITY = 1;
 
	CommonDialog mProvinceDialog;
	CommonDialog mCityDialog;

	private String mCity;
 

	private void initUI() {
		mCityView = (TextView) findViewById(R.id.weather_city);
		mIcon0View = (ImageView) findViewById(R.id.weather_icon_0);
		mIcon1View = (ImageView) findViewById(R.id.weather_icon_1);
		mIcon2View = (ImageView) findViewById(R.id.weather_icon_2);
		mInfo0View = (TextView) findViewById(R.id.weather_info_0);
		mInfo1View = (TextView) findViewById(R.id.weather_info_1);
		mInfo2View = (TextView) findViewById(R.id.weather_info_2);

		mIndexDressingView = (TextView) findViewById(R.id.weather_index_dressing);
		mIndexDressingDetailView = (TextView) findViewById(R.id.weather_index_dressing_detail);
		mIndexComfortableView = (TextView) findViewById(R.id.weather_index_comfortable);
		mIndexComfortableDetailView = (TextView) findViewById(R.id.weather_index_comfortable_detail);
		mIndexColdView = (TextView) findViewById(R.id.weather_index_cold);
		mIndexColdDetailView = (TextView) findViewById(R.id.weather_index_cold_detail);
		mIndexCarWashView = (TextView) findViewById(R.id.weather_index_car_wash);
		mIndexCarWashDetailView = (TextView) findViewById(R.id.weather_index_car_wash_detail);
		mIndexPollutionView = (TextView) findViewById(R.id.weather_index_pollution);
		mIndexPollutionDetailView = (TextView) findViewById(R.id.weather_index_pollution_detail);
		mIndexMorningView = (TextView) findViewById(R.id.weather_index_morning);
		mIndexMorningDetailView = (TextView) findViewById(R.id.weather_index_morning_detail);
		mIndexStreetView = (TextView) findViewById(R.id.weather_index_street);
		mIndexStreetDetailView = (TextView) findViewById(R.id.weather_index_street_detail);
		mIndexUltravioletView = (TextView) findViewById(R.id.weather_index_ultraviolet);
		mIndexUltravioletDetailView = (TextView) findViewById(R.id.weather_index_ultraviolet_detail);
	
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("本地天气");
		
		btn_other_function  = (Button)this.findViewById(R.id.btn_other_function);
		btn_other_function.setText("其他");
		btn_other_function.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_weather);
		super.onCreate(savedInstanceState);
		initUI(); 
		fetchData();
		mProvinceDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_PROVINCE, Dict.getProvinceDict());
		mProvinceDialog.setTvName("请选择省份");
		mCityDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_CITY);
		mCityDialog.setTvName("请选择城市");
	}

	private void renderData(JSONObject data) {
		JSONObject future = data.optJSONObject("future");
		String actualCity = future.optString("name");
		if (mCity != null && !"北京".equals(mCity) && "北京".equals(actualCity)) {
			Common.showToast("没有[" + mCity + "]的天气信息",this);
		}
		mCityView.setText(actualCity);
		JSONArray forecast = future.optJSONArray("forecast");
		JSONObject forecast0 = forecast.optJSONObject(0);
		JSONObject forecast1 = forecast.optJSONObject(1);
		JSONObject forecast2 = forecast.optJSONObject(2);
		String wd0 = forecast0.optString("BWD");
		String wd1 = forecast1.optString("BWD");
		String wd2 = forecast2.optString("BWD");
		String wp0 = forecast0.optString("BWS");
		String wp1 = forecast1.optString("BWS");
		String wp2 = forecast2.optString("BWS");
		String weather0 = future.optString("wea_0");
		String weather1 = future.optString("wea_1");
		String weather2 = future.optString("wea_2");
		String tMin0 = future.optString("tmin_0");
		String tMin1 = future.optString("tmin_1");
		String tMin2 = future.optString("tmin_2");
		String tMax0 = future.optString("tmax_0");
		String tMax1 = future.optString("tmax_1");
		String tMax2 = future.optString("tmax_2");
		String icon0 = future.optString("bwea_0_icon");
		String icon1 = future.optString("bwea_1_icon");
		String icon2 = future.optString("bwea_2_icon");
		String info0 = new StringBuilder().append(tMin0).append("℃").append("~").append(tMax0).append("℃\n").append("")
				.append(weather0).append("\n").append(wd0).append(" ").append(wp0).toString();
		mInfo0View.setText(info0);
		// new DownloadImageTask(mIcon0View, true).execute(ICON_BASE + icon0);
		mIcon0View.setBackgroundResource(getResources().getIdentifier(DisplayUtil.removeFileExtension(icon0),
				"drawable", getPackageName()));
		String info1 = new StringBuilder().append("明天\n").append(tMin1).append("℃").append("~").append(tMax1)
				.append("℃\n").append("").append(weather1).append("\n").append(wd1).append(" ").append(wp1).toString();
		mInfo1View.setText(info1);
		// new DownloadImageTask(mIcon1View, true).execute(ICON_BASE + icon1);
		mIcon1View.setBackgroundResource(getResources().getIdentifier(DisplayUtil.removeFileExtension(icon1),
				"drawable", getPackageName()));
		String info2 = new StringBuilder().append("后天\n").append(tMin2).append("℃").append("~").append(tMax2)
				.append("℃\n").append("").append(weather2).append("\n").append(wd2).append(" ").append(wp2).toString();
		mInfo2View.setText(info2);
		// new DownloadImageTask(mIcon2View, true).execute(ICON_BASE + icon2);
		mIcon2View.setBackgroundResource(getResources().getIdentifier(DisplayUtil.removeFileExtension(icon2),
				"drawable", getPackageName()));
		// 指数
		JSONObject zhishu = future.optJSONObject("zhishu");
		mIndexDressingView.setText("穿衣指数：" + zhishu.optString("CT_N"));
		mIndexDressingDetailView.setText(zhishu.optString("CT"));
		mIndexComfortableView.setText("舒适度指数：" + zhishu.optString("CO_N"));
		mIndexComfortableDetailView.setText(zhishu.optString("CO"));
		mIndexColdView.setText("感冒指数：" + zhishu.optString("GM_N"));
		mIndexColdDetailView.setText(zhishu.optString("GM"));
		mIndexCarWashView.setText("洗车指数：" + zhishu.optString("XC_N"));
		mIndexCarWashDetailView.setText(zhishu.optString("XC"));
		mIndexPollutionView.setText("空气污染指数：" + zhishu.optString("PL_N"));
		mIndexPollutionDetailView.setText(zhishu.optString("PL"));
		mIndexMorningView.setText("晨练指数：" + zhishu.optString("CL_N"));
		mIndexMorningDetailView.setText(zhishu.optString("CL"));
		mIndexStreetView.setText("逛街指数：" + zhishu.optString("GJ_N"));
		mIndexStreetDetailView.setText(zhishu.optString("GJ"));
		mIndexUltravioletView.setText("紫外线指数：" + zhishu.optString("UV_N"));
		mIndexUltravioletDetailView.setText(zhishu.optString("UV"));
	}

	private void fetchData() {		 
	
		String city = mCity;
		if (null == city) 	city = app.getLocation();

		if(!Common.isEmpty(city)) try {
				this.initProgress("加载中......");
				this.mLoadData.getWeather(baseHandler, DefaultConst.CMD_GET_WEATHER, city);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_other_function:mProvinceDialog.showDialog();break;
		case R.id.btn_back:finish();break;
		}
 	
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_GET_WEATHER == msg.what) renderData((JSONObject) msg.obj);
			
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(int type, Object... datas) {
		// TODO Auto-generated method stub
		switch (type) {
		case TYPE_CITY:
			mCityDialog.dismiss();
			if (Dict.BLANK.equals(datas[0])) {
				mCity = null;
			} else {
				mCity = (String) datas[1];
			}
			fetchData();
			break;
		case TYPE_PROVINCE:
			mProvinceDialog.dismiss();
			// 省份不限
			if (Dict.BLANK.equals(datas[0])) {
				mCity = null;
				fetchData();
				return;
			}
			// 直辖市、特别行政区
			if (Dict.isMunicipalCity((String) datas[0]) || Dict.isSpecialCity((String) datas[0])) {
				mCity = (String) datas[1];
				fetchData();
				return;
			}
			mCityDialog.setData(Dict.getCityDict((String) datas[0]));
			mCityDialog.notifyDataSetChanged();
			mCityDialog.showDialog();
			break;
		}
	}
}
