package com.topjet.crediblenumber.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;

/**
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-24 上午11:22:31  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class RewardDetailActivity 
extends BaseActivity
implements OnClickListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_reward_detail);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("安装奖励细则");
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btn_back) finish();
	}

}
