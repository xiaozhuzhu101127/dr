package com.topjet.crediblenumber.activity;



import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.AppConifg;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
 
/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-3 下午1:59:07  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class BalanceActivity 
extends BaseActivity 
implements OnClickListener{
	TextView mBalanceMoneyView;
	TextView mBalanceScoreView; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_balance);
		super.onCreate(savedInstanceState);
		mBalanceMoneyView = (TextView) findViewById(R.id.balance_money);
		mBalanceScoreView = (TextView) findViewById(R.id.balance_score);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("账户余额");

		fetchData();
	}

	private void renderData(JSONObject data){
		String money = data.optString("accountRemainStr", "0");
		int score = data.optInt("activityNumber", 0);
		mBalanceMoneyView.setText(money);
		mBalanceScoreView.setText(score + "");
	}

	private void fetchData(){ 
		
		this.initProgress("正在获取数据，请稍后......");
		this.mLoadData.getMemberAccountState(baseHandler, DefaultConst.CMD_GET_MEMBER_ACCOUNT_STATE);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back) finish();
		else if (v.getId() == R.id.balance_call){
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(AppConifg.TEL_560_URI);
			startActivity(callIntent);
		}
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_GET_MEMBER_ACCOUNT_STATE == msg.what ){			
			renderData(((JSONObject)msg.obj).optJSONObject("accountState"));
		}
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		Common.showToast("获取数据失败!", this);
	}
}
