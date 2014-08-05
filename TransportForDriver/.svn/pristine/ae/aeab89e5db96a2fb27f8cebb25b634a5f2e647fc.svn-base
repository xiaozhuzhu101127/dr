package com.topjet.crediblenumber.activity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-4 下午3:44:12  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class TwitterPublishActivity 
extends BaseActivity 
implements OnClickListener {
	EditText mContentView;
	Spinner mCommonView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_twitter_publish);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("大货在线");
		
		mContentView = (EditText) findViewById(R.id.twitter_content);
		mCommonView = (Spinner) findViewById(R.id.twitter_common_select);
		mCommonView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String text = ((TextView) view).getText().toString().trim();
				// 第一条为提示信息
				if (getResources().getStringArray(R.array.common_sentence)[0].equals(text)) {
					return;
				}
				text = text.substring(2);
				int start = Math.max(mContentView.getSelectionStart(), 0);
				int end = Math.max(mContentView.getSelectionEnd(), 0);
				mContentView.getText().replace(Math.min(start, end), Math.max(start, end), text, 0, text.length());
				mCommonView.setSelection(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});		
	}

	private void submit() {
		String content = mContentView.getText().toString().trim();
		if (Common.isEmpty(content)) {
			Common.showToast("请输入内容！",this);
			mContentView.requestFocus();
			return;
		}
		
		this.initProgress("正在提交数据，请稍后......");
		this.mLoadData.addCargoOnline(baseHandler, DefaultConst.CMD_ADD_CARGO_ONLINE, content);
	}

	@Override
	public void onClick(View v) { 
		switch (v.getId()) {
		case R.id.twitter_send:submit();break;
		case R.id.btn_back:	finish();break;
		}	
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_ADD_CARGO_ONLINE == msg.what){
			Common.showToast("发布成功！",this);
			finish();
		}
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
