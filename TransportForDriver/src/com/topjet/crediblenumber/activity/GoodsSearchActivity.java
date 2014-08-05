package com.topjet.crediblenumber.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DialogUtil;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.MyDialog;
import com.topjet.crediblenumber.util.Receivable;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-19 下午5:26:15  
 * Description: 查找好货
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsSearchActivity extends BaseActivity implements Receivable{
	
	private TextView startProvinceText;
	private TextView targetProvinceText;
	private TextView startCityText;
	private TextView targetCityText;
	private TextView truckTypeText;
	private TextView truckLengthText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_search);
		super.onCreate(savedInstanceState);	
		initView();
	}
	private void initView() {
		startProvinceText = (TextView) findViewById(R.id.search_start_province);
		targetProvinceText = (TextView) findViewById(R.id.search_target_province);
//		startCityText = (TextView) findViewById(R.id.search_start_city);
//		targetCityText = (TextView) findViewById(R.id.search_target_city);
		truckTypeText = (TextView) findViewById(R.id.search_truck_type);
		truckLengthText = (TextView) findViewById(R.id.search_truck_legth);
		startProvinceText.setText(app.getLocationProvince());
//		startCityText.setText(app.getLocation());
//		startCityText.setTag(app.getLocationCode());
		startProvinceText.setText(app.getLocation());
		startProvinceText.setTag(app.getLocationCode());
		//查找好货页面不要推荐
		search_recommend.setVisibility(View.GONE);
		search_place.setVisibility(View.VISIBLE);
		search_place.setText(app.getLocation());
		//手工找货
		goods_footer_search.setEnabled(false);
//		goods_footer_search.setBackgroundResource(R.drawable.ser3);
		goods_footer_search.setBackgroundResource(R.color.darkblue);
	}
	@Override
	protected void ioHandler(Message msg) {
		
	}
	public void onClick(View v) {
		switch (v.getId()) {
		//两个弹出框
//		//出发地：省
//		case R.id.search_start_province:
//			picking = startProvinceText;
//			popProvincePicker();
//			break;
//		//出发地：市
//		case R.id.search_start_city:
//			if(null == startProvinceText.getTag()){
//				showToast("请先选择省份");
//				return;
//			}
//			picking = startCityText;
//			popCityPicker(startProvinceText);
//			break;
//		//目的地：省
//		case R.id.search_target_province:
//			picking = targetProvinceText;
//			popProvincePicker();
//			break;
//		//目的地：市
//		case R.id.search_target_city:
//			if(null == targetProvinceText.getTag()){
//				showToast("请先选择省份");
//				return;
//			}
//			picking = targetCityText;
//			popCityPicker(targetProvinceText);
//			break;
//		case R.id.search_target_province:
//		picking = targetProvinceText;
//		popProvincePicker();
//		break;
		//出发地
		case R.id.search_start_province:
			picking = startProvinceText;
			popProvincePicker();
			break;
		//目的地
		case R.id.search_target_province:
			picking = targetProvinceText;
			popProvincePicker();
			break;
		//车型
		case R.id.search_truck_type:
			picking = truckTypeText;
			popTruckTypePicker();
			break;
		//车长
		case R.id.search_truck_legth:
			picking = truckLengthText;
			popTruckLengthPicker();
			break;
		//查询货源
		case R.id.search_submit:
			submit();
			break;
		}
	}
	//查询货源
	private void submit() {
		Intent intent = new Intent(this, GoodsSearchListActivity.class);
		HashMap<String, Object> params = new HashMap<String, Object>();
//		//目的地
//		if(!Common.isEmpty((String)targetCityText.getTag())){
//			params.put("Target", targetCityText.getTag());
//		}else if(!Common.isEmpty((String)targetProvinceText.getTag())){
//			params.put("Target", targetProvinceText.getTag()+"_BX");
//		}
//		//出发地
//		if(!Common.isEmpty((String)startCityText.getTag())){
//			params.put("Depart", startCityText.getTag());
//			intent.putExtra("city", startCityText.getText());
//		}else if(!Common.isEmpty((String)startProvinceText.getTag())){
//			params.put("Depart", startProvinceText.getTag()+"_BX");
//			intent.putExtra("city", startProvinceText.getText());
//		}
		//目的地
		if(!Common.isEmpty((String)targetProvinceText.getTag()))
			params.put("Target", targetProvinceText.getTag());
		//出发地
		if(!Common.isEmpty((String)startProvinceText.getTag())){
			params.put("Depart", startProvinceText.getTag());
			intent.putExtra("city", startProvinceText.getText());
		}else {
			showToast("出发地不能为空!");
		}
		//车型
		if(!Common.isEmpty((String)truckTypeText.getTag())){
			params.put("TKType", truckTypeText.getTag());
		}
		//车长
		if(!Common.isEmpty((String)truckLengthText.getTag())){
			params.put("TKLen", truckLengthText.getTag());
		}
		//现在定义智能抢货的值为0，手动找货的参数为1；目前智能和手动是一致的
		params.put("queryType", "1");
		intent.putExtra("params", params);
		intent.putExtra("auto", false);
		startActivity(intent);
	}
	@Override
	public void receive(int type, Object... datas) {
		if(showing != null){
			showing.dismiss();
		}
		//市级是不限
		if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
			//切换地区，是自动找货。
			if(picking.equals(search_place)){
				autoSerach((String)picking.getTag(),(String)picking.getText());
			}
			//城市选择不限的时候显示省份
			return;
		}
		picking.setText((String)datas[1]);
		picking.setTag(datas[0]);
		//是省份的时候，继续展示城市
		if(DialogUtil.isProvinceDialog(showing) && !Dict.BLANK.equals(datas[0])){
			showing = DialogUtil.showCityDialog(this, (String)datas[0]);
			return;
		}
		//切换地区，是自动找货。
		if(picking.equals(search_place)){
			autoSerach((String)picking.getTag(),(String)picking.getText());
		}
	}
	//两个弹出框
//	@Override
//	public void receive(int type, Object... datas) {
//		if(showing != null){
//			showing.dismiss();
//		}
//		//切换地区，是自动找货。
//		if(picking.equals(search_place)){
//			if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
//				//城市选择不限的时候显示省份
//				return;
//			}
//			picking.setText((String)datas[1]);
//			picking.setTag(datas[0]);
//			//是省份的时候，继续展示城市
//			if(DialogUtil.isProvinceDialog(showing) && !Dict.BLANK.equals((String)datas[0])){
//				showing = DialogUtil.showCityDialog(this, (String)datas[0]);
//				return;
//			}
//			autoSerach((String)picking.getTag(),(String)picking.getText());
//			return;
//		}
//		//手动找货
//		picking.setText((String)datas[1]);
//		picking.setTag(datas[0]);
//	}
	private void popCityPicker(View v){
		showing = DialogUtil.showCityDialog(this, (String) v.getTag());
	}
	private void popProvincePicker(){
		showing = DialogUtil.showProvinceDialog(this);
	}
	private void popTruckLengthPicker(){
		showing = DialogUtil.showTruckLengthDialog(this);
	}

	private void popTruckTypePicker(){
		showing = DialogUtil.showTruckTypeDialog(this);
	}
}
