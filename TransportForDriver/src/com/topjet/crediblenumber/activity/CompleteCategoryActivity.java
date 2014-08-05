package com.topjet.crediblenumber.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.model.Identity;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-30 下午2:37:32  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class CompleteCategoryActivity 
extends BaseActivity 
implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_complete_category);
		super.onCreate(savedInstanceState);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText("完善资料");
		((TextView) findViewById(R.id.mobile)).setText(app.getShareHelper().getUsername());

	}

	@Override
	public void onClick(View v) {
		Identity identity = null;
		switch (v.getId()) {
		case R.id.rl_driver:
			identity = Identity.Driver;
			Intent intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		case R.id.rl_owner:
			identity = Identity.GoodsSourceOwner;
			intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		case R.id.rl_info:
			identity = Identity.InfomationMinistry;
			intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		case R.id.rl_company:
			identity = Identity.LogisticCompany;
			intent = new Intent(CompleteCategoryActivity.this, CompleteActivity.class);
			intent.putExtra("identity", identity);
			startActivity(intent);
			break;
		case R.id.btn_back:
			finish();
			break;
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
