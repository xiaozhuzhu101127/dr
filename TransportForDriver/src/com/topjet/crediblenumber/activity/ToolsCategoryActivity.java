package com.topjet.crediblenumber.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		BillWang
 * Create at:	2013-9-3 下午3:15:57  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class ToolsCategoryActivity
extends BaseActivity 
implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_tools_category);
		super.onCreate(savedInstanceState); 
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("实用工具包");
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		//当地电话本
		case R.id.rl_local_phone_book:
			startActivity(new Intent(ToolsCategoryActivity.this, LocalPhoneBookCategoryActivity.class));
			break;
		case R.id.rl_category_localWeather:
			startActivity(new Intent(ToolsCategoryActivity.this, Weather1Activity.class));
			break;
		case R.id.rl_category_idcard:
			startActivity(new Intent(ToolsCategoryActivity.this, IdCardVerificationActivity.class));
			break;
		case R.id.rl_category_mall:
			startActivity(new Intent(ToolsCategoryActivity.this, ScoreShopCategoryActivity.class));
			break;
		case R.id.rl_category_violate:
			startActivity(new Intent(ToolsCategoryActivity.this, ViolateCategoryActivity.class));
			break;
		case R.id.item_contact_filter:
			startActivity(new Intent(ToolsCategoryActivity.this, FilterActivity.class));		
			break;
		case R.id.btn_back:finish();break;
		}
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
