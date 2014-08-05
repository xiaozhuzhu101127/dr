package com.topjet.crediblenumber.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.ImageTask;


/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-27 下午4:33:39  
 * Description: 我抢到的好货
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsMyDetailActivtiy extends BaseActivity{
	
	private long gsid;
	private JSONObject dealDetailInfo;
	
	private TextView orderView;
	private TextView startView;
	private TextView targetView;
	private TextView infoView;
	private TextView payView;
	private TextView addrView;
	private TextView accountView;
	private RatingBar ratingBar;

	private TextView agencyFeeView;
	private TextView priceView;
	private TextView shipperinfoView;
	private TextView priceView2;
	private TextView mobileView;
	private TextView telView;

	private TextView nameView;
	private TextView retainingView;
	private ImageView photoView;
	private RelativeLayout mobileLayout;
	private RelativeLayout telLayout;
	private boolean history;
	private LinearLayout detailLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_my_detail);
		super.onCreate(savedInstanceState);
		search_title.setText("恭喜抢货成功");
		Bundle extra = savedInstanceState;
		if(null == savedInstanceState)
			extra = getIntent().getExtras();
		gsid = extra.getLong("GSID");
		history = extra.getBoolean("history");
		initView();
		fetchData();
	}
	private void initView() {
		initProgress("加载中……");
		orderView = (TextView) findViewById(R.id.mygoods_ordernum);
		startView = (TextView) findViewById(R.id.mygoods_depart);
		targetView = (TextView) findViewById(R.id.mygoods_target);
		infoView = (TextView) findViewById(R.id.mygoods_gs1);
		payView = (TextView) findViewById(R.id.mygoods_gs2);
		addrView = (TextView) findViewById(R.id.mygoods_gs3);
		accountView = (TextView) findViewById(R.id.mygoods_account);
		ratingBar = (RatingBar) findViewById(R.id.mygoods_ratingbar);
		agencyFeeView = (TextView) findViewById(R.id.mygoods_info);
		priceView = (TextView) findViewById(R.id.mygoods_price);
		photoView = (ImageView) findViewById(R.id.mygoods_photo);
		nameView = (TextView) findViewById(R.id.mygoods_name);
		shipperinfoView = (TextView) findViewById(R.id.mygoods_shipperinfo);
		retainingView = (TextView) findViewById(R.id.mygoods_leftTime);
		priceView2 = (TextView) findViewById(R.id.mygoods_info_2);
		mobileView = (TextView) findViewById(R.id.mygoods_shipper_mobile_);
		telView = (TextView) findViewById(R.id.mygoods_shipper_tel_);
		mobileLayout = (RelativeLayout) findViewById(R.id.mygoods_shipper_mobile);
		telLayout = (RelativeLayout) findViewById(R.id.mygoods_shipper_tel);
		detailLayout = (LinearLayout) findViewById(R.id.detail_layout);
		//历史成交
		if(history){
			goods_footer_history.setEnabled(false);
//			goods_footer_history.setBackgroundResource(R.drawable.rec3);
			goods_footer_history.setBackgroundResource(R.color.darkblue);
			search_title.setText("历史成交");
			retainingView.setVisibility(View.GONE);
		}
	}
	private void fetchData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("GSID", gsid);
		params.put("USRID",shareHelper.getUserId());
		mLoadData.getDealDetailInfo(baseHandler, DefaultConst.CMD_GOODS_DEAL_DETAIL, params);
	}

	@Override
	protected void ioHandler(Message msg) {
		if(msg.what == DefaultConst.CMD_GOODS_COUNTDOWNTIME){
			new MyCountDownTimer((dealDetailInfo.optLong("LEFTTIME2"))*1000, 1000).start();
			return;
		}
		hindProgress();
		dealDetailInfo = ((JSONObject)msg.obj).optJSONObject("dealDetailInfo");
		showGoods(dealDetailInfo);
	}
	private void showGoods(JSONObject data) {
			orderView.setText("订单号:" + data.optLong("GSORDERNO"));
			// 出发地
			String ct_depart = Common.splitBX(data.optString("CT_DEPART"));
			startView.setText(Dict.getLocation(ct_depart));
			// 目的地
			String ct_target = Common.splitBX(data.optString("CT_TARGET"));
			targetView.setText(Dict.getLocation(ct_target));
			StringBuilder info = new StringBuilder();
			info.append("有");
			info.append(Common.weightOrVolume(data.optString("GSSCALE"), data.optString("GSUNIT")));
			info.append(Dict.getGsType(data.optString("GSTYPE"))).append("需").append(Dict.getTruckLength(data.optString("TKLEN")))
			.append(Dict.getTruckType(data.optString("TKTYPE"))).append("1辆");
			// 货源信息、需车信息
			infoView.setText(info.toString());
			StringBuilder pay = new StringBuilder();
			pay.append(Dict.getPayWay(data.optString("DCT_PAYWAY"))).append(" ").append(data.optString("GSLOADDATE")).append("装货，")
					.append(Dict.getGsLoad(data.optString("DCT_GSLOAD")));
			// 付款方式、装货方式
			payView.setText(pay.toString());
			//装货地点
			if(!Common.isEmpty(data.optString("GSLOADADDR"))){
				StringBuilder addr = new StringBuilder();
				addr.append("装货地点在").append(data.optString("GSLOADADDR"));
				addrView.setText(addr);
			}else {
				addrView.setText("");
			}
			//诚信值
			accountView.setText(String.valueOf(data.optLong("YSLACCOUNT")));
			//发货好评度
			ratingBar.setRating(data.optLong("SHIPPOINT"));
			ratingBar.setEnabled(false);
			// 信息费
			Long agencyFee = data.optLong("GSMSGPRICE", -1);
			StringBuilder agencyFeeStr = new StringBuilder();
			if(-1 == agencyFee){
				agencyFeeStr.append("面议");
				priceView2.setVisibility(View.GONE);
			}else{
				agencyFeeStr.append(agencyFee);
			}
			//TODO：不用三个textView，要试试
			//agencyFeeStr = Html.fromHtml("<font color=\"#cd472e\">" + agencyFee + "</font>元");
			agencyFeeView.setText(agencyFeeStr);
			// 成交价
			priceView.setText(data.optLong("SUBMITPRICE") + "");
			JSONObject shipper = data.optJSONObject("SHiPPER");
			// 照片
			new ImageTask(photoView).execute(shipper.optString("PHOTOURL"));
			// 货主姓名
			nameView.setText(shipper.optString("SHIPPERNAME"));
			// 公司名称/装货地址/电话
			StringBuilder shipperinfo = new StringBuilder();
			shipperinfo.append("公司:");
			if(!Common.isEmpty(shipper.optString("SHIPPERCOMPANY"))){
				shipperinfo.append(shipper.optString("SHIPPERCOMPANY"));
			}else {
				shipperinfo.append("--");
			}
			shipperinfo.append("\n").append("地址:");
			if(!Common.isEmpty(shipper.optString("SHIPPERADDR"))){
				shipperinfo.append(shipper.optString("SHIPPERADDR"));
			}else {
				shipperinfo.append("--");
			}
			shipperinfoView.setText(shipperinfo);
			if(!Common.isEmpty(shipper.optString("SHIPPERMOBILE"))){
				mobileView.setText(shipper.optString("SHIPPERMOBILE"));
				mobileLayout.setVisibility(View.VISIBLE);
			}
			if(!Common.isEmpty(shipper.optString("SHIPPERTEL"))){
				telView.setText(shipper.optString("SHIPPERTEL"));
				telLayout.setVisibility(View.VISIBLE);
			}
			//1 半小时报价时间结束就取消的做法
			// 剩余时间.两个参数的单位都是毫秒
//			new CountDownTimer((data.optLong("LEFTTIME2"))*1000, 1000) {
//				public void onTick(long millisUntilFinished) {
//					retainingView.setText("剩余时间：" + formatTime(millisUntilFinished/1000));
//				}
//
//				public void onFinish() {
//  				retainingView.setText("交易时间超时，交易取消！");
//					retainingView.setText("剩余时间：" + formatTime(0));
//				}
//			}.start();
			//2 报价时间结束，还要继续循环，用自己的倒计时。利用handler
			new MyCountDownTimer((data.optLong("LEFTTIME2"))*1000, 1000).start();
			detailLayout.setVisibility(View.VISIBLE);
	}
	private class MyCountDownTimer extends CountDownTimer{
		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			retainingView.setText("剩余时间:" + formatTime(millisUntilFinished/1000));
		}

		@Override
		public void onFinish() {
			baseHandler.sendEmptyMessage(DefaultConst.CMD_GOODS_COUNTDOWNTIME);
			
		}
		
	}
	private String formatTime(long secondsUntilFinished) {
		long minutes = secondsUntilFinished / 60;
		long seconds = secondsUntilFinished % 60;
		return new StringBuilder().append(minutes > 9 ? minutes : ("0" + minutes)).append("分")
				.append(seconds > 9 ? seconds : ("0" + seconds)).append("秒").toString();
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.mygoods_more:
		case R.id.mygoods_account_1:
		case R.id.mygoods_account_:
			doCrieditQuery();
			break;
		case R.id.mygoods_shipper_tel:
			startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + telView.getText())));
			break;
		case R.id.mygoods_shipper_mobile:
			startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + mobileView.getText())));
			break;
		}
	}
	//诚信查询
	private void doCrieditQuery() {
		Intent intent = new Intent(this, CreditResultActivity.class);
		JSONObject shipper = dealDetailInfo.optJSONObject("SHiPPER");
		intent.putExtra("number", shipper.optString("SHIPPERMOBILE"));
		startActivity(intent);
	}
}
