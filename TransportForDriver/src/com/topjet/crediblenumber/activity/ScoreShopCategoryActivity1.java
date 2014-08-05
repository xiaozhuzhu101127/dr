package com.topjet.crediblenumber.activity;


import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
 
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.AppConifg;
import com.topjet.crediblenumber.util.DefaultConst;
 
/**
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-24 上午11:22:58  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class ScoreShopCategoryActivity1
extends BaseActivity
implements OnClickListener {

	private Dialog mDialog;

	TextView mBalanceScoreView;

//	GenericController mBalanceFetchController;
//	OnResponseHandler mOnBalanceFetchResponseHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_score_shop_category_1);
		super.onCreate(savedInstanceState);
 
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("积分商城");
//		mBalanceScoreView = (TextView) findViewById(R.id.score_shop_balance);
//		mOnBalanceFetchResponseHandler = new OnResponseHandler() {
//
//			@Override
//			public void onComplete(int statusCode) {
//				dismissLoadingDialog();
//			}
//
//			@Override
//			public void onSuccess(Object response, Object extra) {
//				renderData(((JSONObject) response).optJSONObject("accountState"));
//			}
//
//		};
		fetchData();
	}

	private void renderData(JSONObject data) {
	//	int score = data.optInt("activityNumber", 0);
	//	mBalanceScoreView.setText( "");
	}

	private void fetchData() { 
		
		this.initProgress("正在获取数据，请稍后......");
		mLoadData.getMemberAccountState(baseHandler, DefaultConst.CMD_GET_MEMBER_ACCOUNT_STATE);
	}

	private void showInfo() {
		if (null == mDialog) {
			mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.setContentView(R.layout.dialog_score_shop);
			TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);
			view.setText("如需兑换请致电4000566560，我们的客服人员将会为您兑换");
			TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
			text1.setText("暂不兑换");
			text1.setOnClickListener(this);
			TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
			text2.setText("拨打电话");
			mDialog.findViewById(R.id.score_shop_dialog_cancel).setOnClickListener(this);
			mDialog.findViewById(R.id.score_shop_dialog_dial).setOnClickListener(this);
		}
		mDialog.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_redeem_20:
		case R.id.rl_redeem_30:
		case R.id.rl_redeem_50:
			showInfo();
			break;
		case R.id.score_rule:
			startActivity(new Intent(ScoreShopCategoryActivity1.this,
					ScoreRuleActivity.class));
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(AppConifg.TEL_560_URI);
			startActivity(callIntent);
			break;
		case R.id.btn_back: finish();break;
		}
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if ( DefaultConst.CMD_GET_MEMBER_ACCOUNT_STATE
			== msg.what) renderData((JSONObject)msg.obj);
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
