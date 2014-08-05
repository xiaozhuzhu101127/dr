package com.topjet.crediblenumber.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-24 下午1:56:00  
 * Description: 报价和调整报价
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsBidActivity extends BaseActivity{
	
	EditText cjText;
	Button bjButton;
	int price;
	Bundle extras;
	Boolean change;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_bid);
		super.onCreate(savedInstanceState);
		extras = savedInstanceState;
		if(null == savedInstanceState){
			extras = getIntent().getExtras();
		}
		change = extras.getBoolean("change");
		initView();
		
	}
	private void initView(){
		cjText = (EditText) findViewById(R.id.goods_cj);
		bjButton = (Button) findViewById(R.id.goods_bj_1);
		//可以调整价格
		if(change){
			cjText.setText(extras.getString("price"));
			bjButton.setText("确认调整价格");
			search_title.setText("我要调价");
			return;
		}
		search_title.setText("我要报价");
	}
	@Override
	protected void ioHandler(Message msg) {
		showToast(change?"调整报价成功！":"报价成功！");
		shareHelper.setCallPrice(true);
		//回到好货列表列表
//		Intent intent = new Intent(this, GoodsSearchListActivity.class);
//		intent.putExtra("auto", app.getGoodsParameter().auto);
//		intent.putExtra("params", app.getGoodsParameter().params);
		//不再回到列表，到详情
		Intent intent = new Intent(this, GoodsDetailActivity.class);
		intent.putExtra("GsId", Long.parseLong(String.valueOf(extras.get("GsId"))));
		intent.putExtra("call", true);
		intent.putExtra("GSSTS", "GSSTS_CALLPRICE");
		startActivity(intent);
	}
	public void bid(View v){
		switch (v.getId()) {
		case R.id.goods_50:
			cutbj(50);
			break;
		case R.id.goods_100:
			cutbj(100);
			break;
		case R.id.goods_150:
			cutbj(150);
			break;
		case R.id.goods_200:
			cutbj(200);
			break;
		case R.id.goods_bj_1:
			bj();
			break;
		}
	}
	//对话框
	private void bj(){
		Dialog alertDialog = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("报价")
				.setMessage(change?"确定调整报价吗？":"一旦报价，如货主同意成交则必须前去接货。请确认？")
		.setPositiveButton(change?"确定调整":"确定报价", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dobj();
			}
		}).setNegativeButton(change?"暂不调整":"暂不报价", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create();
		alertDialog.show();
	}
	//报价
	private void dobj() {
		price = Integer.parseInt(cjText.getText().toString());
		if(0 >= price){
			showToast("出价太低");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USRID", shareHelper.getUserId());
		params.put("GsId", extras.get("GsId"));
		params.put("PRICE", price);
		price = Integer.parseInt(cjText.getText().toString());
		mLoadData.callPrice(baseHandler, DefaultConst.CMD_GOODS_CALLPRICE, params);
	}
	//调整价格
	private void cutbj(int num) {
		if(Common.isEmpty(cjText.getText().toString()))
			return;
		price = Integer.parseInt(cjText.getText().toString());
		if(0>=(price - num)){
			showToast("出价太低");
		}else {
			cjText.setText((price - num)+"");
		}
	}
}
