package com.topjet.crediblenumber.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
 * Create at:	2013-6-18 上午10:28:01  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class CreditTouShuActivity extends BaseActivity implements
		OnClickListener {

	private CheckBox nameView;
	private CheckBox addressView;
	private CheckBox typeView;
	private CheckBox ohthView;

	private EditText contentView;
	private String number;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_toushu_correct);
		super.onCreate(savedInstanceState);

		number = getIntent().getExtras().getString("number");
		((TextView) findViewById(R.id.tv_title)).setText("投诉");
		btn_other_function = (Button) findViewById(R.id.btn_other_function);
		btn_other_function.setText("首页");
		btn_other_function.setVisibility(View.VISIBLE);

		contentView = (EditText) findViewById(R.id.correct_content);

	}

	private void correct() {
		ArrayList<String> types = new ArrayList<String>(4);

		types.add("MISSTS_OTHER");

		String content = contentView.getText().toString();
		if ("".equals(content)) {
			Common.showToast("请填写备注信息！", this);
			return;
		}

		this.initProgress("正在提交数据，请稍后......");
		this.mLoadData.reportContactInfoError(baseHandler,
				DefaultConst.CMD_REPORT_CONTACT_INFO_ERR, number, types,
				content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
		case R.id.correct_cancel:
			finish();
			break;
		case R.id.correct_submit:
			correct();
			break;
		case R.id.btn_other_function:
			gotoMain();
			break;
		case R.id.dianhuatousu:
			Intent phoneIntent = new Intent("android.intent.action.CALL",Uri.parse("tel:4000566560"));
			startActivity(phoneIntent);

		}
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_REPORT_CONTACT_INFO_ERR == msg.what)
			Common.showToast("提交成功", this);
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub

	}

}
