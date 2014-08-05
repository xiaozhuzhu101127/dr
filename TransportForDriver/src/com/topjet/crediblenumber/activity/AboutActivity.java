package com.topjet.crediblenumber.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
 

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-30 上午9:53:23  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class AboutActivity
extends BaseActivity 
implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about);
		super.onCreate(savedInstanceState);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("关于软件");
		((TextView)findViewById(R.id.version)).setText("版本号：V" + Common.getVersionName());
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
