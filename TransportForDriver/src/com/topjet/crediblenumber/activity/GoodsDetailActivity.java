package com.topjet.crediblenumber.activity;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.GoodsDetailAdapter;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-25 上午8:50:28  
 * Description:好货详情
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsDetailActivity extends BaseActivity{
	
	TextView createtimeView;
	TextView departView;
	TextView targetView;
	TextView gs1View;
	TextView gs2View;
	TextView gs3View;
	TextView accountView;
	RatingBar ratingBar;
	TextView rushcountView;
	TextView countView1;
	TextView countView2;
	TextView mobileView;
	TextView infoPriceView;
	LinearLayout layout;
	Button cancleButton;
	Button rushButton;
	Button tzbButton;
	TextView ycxButton;
	TextView ycjButton;
	ListView qhrlistView;
	JSONObject goods;
	JSONArray prices;
	long usrid;
	String gsState;
	Map<Long, String> myPrice = new HashMap<Long, String>();
	String priceid;
	boolean auto;
	boolean call;
	boolean history;
	View alertView;
	ImageView imageView;
	LinearLayout detailLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_detail);
		super.onCreate(savedInstanceState);
		usrid = shareHelper.getUserId();
		Bundle extras = savedInstanceState;
		if(null == savedInstanceState)
			extras = getIntent().getExtras();
		long gsId = extras.getLong("GsId");
		gsState = extras.getString("GSSTS");
		auto = extras.getBoolean("auto");
		call = extras.getBoolean("call");
		history = extras.getBoolean("history");
		initView();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("GsId", gsId);
		mLoadData.goodsDetail(baseHandler, DefaultConst.CMD_GOODS_DETAIL, params);
	}

	private void initView() {
		initProgress("加载中……");
		createtimeView = (TextView) findViewById(R.id.goods_detail_createtime);
		departView = (TextView) findViewById(R.id.goods_detail_depart);
		targetView = (TextView) findViewById(R.id.goods_detail_target);
		gs1View = (TextView) findViewById(R.id.goods_detail_gs1);
		gs2View = (TextView) findViewById(R.id.goods_detail_gs2);
		gs3View = (TextView) findViewById(R.id.goods_detail_gs3);
		accountView = (TextView) findViewById(R.id.goods_detail_account);
		ratingBar = (RatingBar) findViewById(R.id.goods_detail_ratingbar);
		rushcountView = (TextView) findViewById(R.id.goods_detail_qcount);
		cancleButton = (Button) findViewById(R.id.goods_detail_cxbtn);
		tzbButton = (Button) findViewById(R.id.goods_detail_tzbtn);
		qhrlistView = (ListView) findViewById(R.id.goods_detail_qhrlist);
		rushButton = (Button) findViewById(R.id.goods_detail_bjbtn);
		ycxButton = (TextView) findViewById(R.id.goods_detail_ycxbtn);
		ycjButton = (TextView) findViewById(R.id.goods_detail_ycjbtn);
		countView1 = (TextView) findViewById(R.id.goods_detail_count_view_1);
		countView2 = (TextView) findViewById(R.id.goods_detail_count_view_2);
		mobileView = (TextView) findViewById(R.id.goods_detail_mobile_);
		layout = (LinearLayout) findViewById(R.id.goods_detail_mobile);
		infoPriceView = (TextView) findViewById(R.id.goods_detail_gs4);
		alertView = findViewById(R.id.goods_detail_alert_view);
		imageView = (ImageView) findViewById(R.id.goods_detail_alert);
		detailLayout = (LinearLayout) findViewById(R.id.detail_layout);
		
		search_title.setText("货源详情");
		//已报价详情
		if(call){
			goods_footer_ybj.setEnabled(false);
//			goods_footer_ybj.setBackgroundResource(R.drawable.ybj3);
			goods_footer_ybj.setBackgroundResource(R.color.darkblue);
			return;
		}
		//历史成交
		if(history){
			goods_footer_history.setEnabled(false);
//			goods_footer_history.setBackgroundResource(R.drawable.rec3);
			goods_footer_history.setBackgroundResource(R.color.darkblue);
			return;
		}
		if(auto){//智能
			goods_footer_auto.setEnabled(false);
//			goods_footer_auto.setBackgroundResource(R.drawable.iser3);
			goods_footer_auto.setBackgroundResource(R.color.darkblue);
		}else{//手动
			goods_footer_search.setEnabled(false);
//			goods_footer_search.setBackgroundResource(R.drawable.ser3);
			goods_footer_search.setBackgroundResource(R.color.darkblue);
		}
	}

	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		if(msg.what == DefaultConst.CMD_GOODS_CANCLEPRICE){
			showToast("撤销报价成功！");
			return;
		}
		JSONObject data = (JSONObject) msg.obj;
		goods = data.optJSONObject("goodsDetail");
		prices = goods.optJSONArray("PRICES");
		myPrice = getMyPrice(prices);
		//发布时间参数
		createtimeView.setText(goods.optString("GSISSUEDATE"));
		// 出发地
		String ct_depart = Common.splitBX(goods.optString("CT_DEPART"));
		departView.setText(Dict.getLocation(ct_depart));
		// 目的地
		String ct_target = Common.splitBX(goods.optString("CT_TARGET"));
		targetView.setText(Dict.getLocation(ct_target));
		// 货源信息、需车信息
		StringBuilder info = new StringBuilder();
		info.append("有");
		info.append(Common.weightOrVolume(goods.optString("GSSCALE"), goods.optString("GSUNIT")));
		info.append(Dict.getGsType(goods.optString("GSTYPE"))).append("需").append(Dict.getTruckLength(goods.optString("TKLEN")))
				.append(Dict.getTruckType(goods.optString("TKTYPE"))).append("1辆");
		gs1View.setText(info);
		// 付款方式、装货方式
		StringBuilder pay = new StringBuilder();
		pay.append(Dict.getPayWay(goods.optString("DCT_PAYWAY"))).append(" ").append(goods.optString("GSLOADDATE")).append("装货，")
				.append(Dict.getGsLoad(goods.optString("DCT_GSLOAD")));
		// 付款方式、装货方式
		gs2View.setText(pay.toString());
		//信息费
		if(!Common.isEmpty(goods.optString("GSINFOPRICE")))
			infoPriceView.setText("信息费:"+goods.optString("GSINFOPRICE")+"元");
		//装货地点
		if(!Common.isEmpty(goods.optString("GSDESC"))){
			gs3View.setText("备注:"+goods.optString("GSDESC"));
		}else {
			gs3View.setText("");
		}
		//诚信值
		accountView.setText("诚信值:"+goods.optLong("YSLACCOUNT"));
		//电话
		mobileView.setText(goods.optString("GSMOBILE"));
		//发货好评度
		ratingBar.setRating(goods.optLong("SHIPPOINT"));
		ratingBar.setEnabled(false);
		//有几人在抢
		if(null == prices){
			rushcountView.setText("0");
		}else {
			rushcountView.setText(prices.length()+"");
			//抢货人列表
			GoodsDetailAdapter adapter = new GoodsDetailAdapter(context, prices);
			qhrlistView.setAdapter(adapter);
		}
		countView1.setVisibility(View.VISIBLE);
		countView2.setVisibility(View.VISIBLE);
		//撤销、报价或已成交
		switch (Dict.getGsState(gsState)) {
		//已报价，可以调
		case 0:
			tzbButton.setVisibility(View.VISIBLE);
			cancleButton.setVisibility(View.VISIBLE);
			break;
		//已成交
		case 1:
			ycjButton.setVisibility(View.VISIBLE);
			alertView.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.VISIBLE);
			break;
		//抢货中
		case 2:
			rushButton.setVisibility(View.VISIBLE);
			break;
		//已撤销
		case 3:
			ycxButton.setVisibility(View.VISIBLE);
			alertView.setVisibility(View.VISIBLE);
			imageView.setVisibility(View.VISIBLE);
			break;
		}
		detailLayout.setVisibility(View.VISIBLE);
	}
	public void click(View v){
		switch (v.getId()) {
		case R.id.goods_detail_bjbtn:
		case R.id.goods_detail_tzbtn:
			bid();
			break;
		case R.id.goods_detail_cxbtn:
			showCancleDialog();
			break;
			//诚信查询
		case R.id.goods_detail_more:
			doCrieditQuery();
			break;
		//拨打货主电话
		case R.id.goods_detail_mobile:
			startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + mobileView.getText())));
			break;
		}
	}
	private void showCancleDialog() {
		Dialog dialog = new AlertDialog.Builder(this).setTitle("取消报价").setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage("撤消后无法再对此货源进行报价!")
				.setPositiveButton("确定", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						canclePrice();
						
					}
				}).setNegativeButton("取消", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
		dialog.show();
	}

	//诚信查询
	private void doCrieditQuery() {
		Intent intent = new Intent(this, CreditResultActivity.class);
		intent.putExtra("number", goods.optString("GSMOBILE"));
		startActivity(intent);
	}
	//报价
	private void bid(){
		Intent intent = new Intent(this, GoodsBidActivity.class);
		if(!myPrice.isEmpty()){
			intent.putExtra("change", true);
			intent.putExtra("price",myPrice.get(usrid));
		}
		intent.putExtra("GsId", goods.optLong("GSID"));
		startActivity(intent);
	}
	//撤销报价
	private void canclePrice(){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("USRID", usrid);
		params.put("PRICEIDS", priceid);
		mLoadData.canclePrice(baseHandler, DefaultConst.CMD_GOODS_CANCLEPRICE, params);
		//不能再报价或者调整价格
		rushButton.setVisibility(View.GONE);
		cancleButton.setVisibility(View.GONE);
		tzbButton.setVisibility(View.GONE);
		//提示已撤销
		ycxButton.setVisibility(View.VISIBLE);
		alertView.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.VISIBLE);
	}
	//我的报价
	private Map<Long, String> getMyPrice(JSONArray data){
		if(null != data){
			for(int i = 0;i<data.length();i++){
				try {
					if(data.getJSONObject(i).optLong("USRID") == usrid){
						myPrice.put(usrid,data.getJSONObject(i).optString("PRICE"));
						priceid = data.getJSONObject(i).optString("PRICEID");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return myPrice;
	}
}
