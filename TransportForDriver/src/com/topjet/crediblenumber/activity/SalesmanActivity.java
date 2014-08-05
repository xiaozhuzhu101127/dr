package com.topjet.crediblenumber.activity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.PhoneValidator;

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-2 下午3:48:26  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class SalesmanActivity 
extends BaseActivity 
implements OnClickListener{

	TextView mMobileInputView;
	TextView mMobileTextView;
	TextView mAttentionView;
	View mSubmitView;
	View mGotoFilterView;
	View mRewardDetailView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_salesman);
		super.onCreate(savedInstanceState);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("营销代表专用");
		
		mMobileInputView = (TextView) findViewById(R.id.salesman_mobile_input);
		mMobileTextView = (TextView) findViewById(R.id.salesman_mobile_text);
		mAttentionView = (TextView) findViewById(R.id.salesman_attention);
		mSubmitView = findViewById(R.id.salesman_submit);
		mGotoFilterView = findViewById(R.id.salesman_filter);
		mRewardDetailView = findViewById(R.id.salesman_reward_detail);

		showParticularFrame();
	}

	
	@Override
	public void onResume() {
		super.onResume();
		showParticularFrame();
	}


	/**
	 * 1.未上传通讯录提示上传后才有奖励。<br/>
	 * 2.已上传通讯录未录入推荐人手机号，显示录入界面。<br/>
	 * 3.已录入推荐人手机号，显示推荐人手机号。
	 * @param isUploaded
	 * @param recommendMobile
	 */
	private void showParticularFrame(){
		boolean isUploaded = shareHelper.isUploaded();
		//未上传通讯录
		if(!isUploaded){
			mMobileTextView.setVisibility(View.GONE);
			mMobileInputView.setVisibility(View.GONE);
			mAttentionView.setVisibility(View.VISIBLE);
			mSubmitView.setVisibility(View.GONE);
			mRewardDetailView.setVisibility(View.VISIBLE);
			mGotoFilterView.setVisibility(View.VISIBLE);
			return;
		}
		String recommendMobile = shareHelper.getRecommendMobile();
		//未录入推荐人手机号
		if(Common.isEmpty(recommendMobile)){
			mMobileTextView.setVisibility(View.GONE);
			mMobileInputView.setVisibility(View.VISIBLE);
			mAttentionView.setVisibility(View.GONE);
			mSubmitView.setVisibility(View.VISIBLE);
			mGotoFilterView.setVisibility(View.GONE);
			mRewardDetailView.setVisibility(View.VISIBLE);
			return;
		}
		//已录入推荐人手机号
		mRewardDetailView.setVisibility(View.GONE);
		mMobileTextView.setVisibility(View.VISIBLE);
		mMobileTextView.setText(shareHelper.getRecommendMobile());
		mMobileInputView.setVisibility(View.GONE);
		mAttentionView.setVisibility(View.GONE);
		mRewardDetailView.setVisibility(View.VISIBLE);
		mSubmitView.setVisibility(View.GONE);
		mGotoFilterView.setVisibility(View.GONE);
	}

	private void submit(){
		String mobile = mMobileInputView.getText().toString().trim();
		if(!PhoneValidator.validateMobile(mobile)){
			Common.showToast("推荐人手机号格式不正确！",this);
			mMobileInputView.requestFocus();
			return;
		}	 
		
		this.initProgress("正在上传推荐人手机，请稍后......");
		this.mLoadData.updateRecommend(
				baseHandler, 
				DefaultConst.CMD_UP_RECOMMEND
				,mobile);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.salesman_submit:
			submit();
			break;
		case R.id.salesman_filter:
			Intent filterIntent = new Intent(SalesmanActivity.this, FilterActivity.class);
			filterIntent.putExtra("auto", true);
			startActivity(filterIntent);
			break;
		case R.id.salesman_reward_detail:
			Intent rewardDetailIntent = new Intent(SalesmanActivity.this, RewardDetailActivity.class);
			startActivity(rewardDetailIntent);
			break;
		case R.id.btn_back:finish();
		}
	}


	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_UP_RECOMMEND == msg.what){
			Common.showToast("设置成功！",this);
			shareHelper.setRecommendMobile(mMobileInputView.getText().toString().trim());
			showParticularFrame();
		}
	}


	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub.
		if (!Common.isEmpty((String)msg.obj))	Common.showToast((String)msg.obj,this);
	}

}
