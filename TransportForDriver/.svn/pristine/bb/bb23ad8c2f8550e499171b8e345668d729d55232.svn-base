package com.topjet.crediblenumber.activity; 

import org.json.JSONObject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.IdentityCardValidator;
import com.topjet.crediblenumber.util.ImageUtils;
 

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-3 下午4:22:48  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class IdCardVerificationActivity
extends BaseActivity 
implements OnClickListener {

	ImageView mPhotoView;
	EditText mNameView;
	EditText mIdcView;
	View mResultGroupView;
	TextView mResultView;
	View mSubmitView;
 
	Handler mShowResultGroupHandler;
	LinearLayout idv_result_group;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_id_card_verification);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("身份证核查");
		
		mPhotoView = (ImageView) findViewById(R.id.idv_photo);
		mNameView = (EditText) findViewById(R.id.idv_name);
		mIdcView = (EditText) findViewById(R.id.idv_idno);
		mResultGroupView = findViewById(R.id.idv_result_group);
		mResultView = (TextView) findViewById(R.id.idv_result);
		mSubmitView = findViewById(R.id.idv_submit); 
		
		idv_result_group =(LinearLayout)findViewById(R.id.idv_result_group); 
      
	}

	private void renderData(JSONObject data) {
		// 照片
		mPhotoView.setImageBitmap(ImageUtils.base642BigMap(data.optString("iDCardPhoto")));
		mResultView.setText("一致");
		idv_result_group.setVisibility(View.VISIBLE);
	}

	public void showResultGroup() {
		mResultGroupView.setVisibility(View.VISIBLE);
		mSubmitView.setVisibility(View.GONE);
	}

	private void verifyIdCard() {
		String name = mNameView.getText().toString().trim();
		if(Common.isEmpty(name)){
			Common.showToast("请输入名字",this);
			mNameView.requestFocus();
			return;
		}
		String idno = mIdcView.getText().toString().trim();
		if(Common.isEmpty(idno)){
			Common.showToast("请输入身份证号！",this);
			mIdcView.requestFocus();
			return;
		}
		if(!IdentityCardValidator.validateCard(idno)){
			Common.showToast("身份证号格式不正确！",this);
			mIdcView.requestFocus();
			return;
		}	 
		
		this.initProgress("正在获取数据，请稍后......");
		mLoadData.getPersonIDCardInfo(baseHandler, 
				DefaultConst.CMD_GET_PERSON_IDCARD, 
				name,
				idno);
	}

	@Override
	public void onClick(View v) { 
		if (v.getId() == R.id.btn_back) finish();
		else if (v.getId() == R.id.idv_submit) verifyIdCard();	 
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_GET_PERSON_IDCARD == msg.what)	renderData(((JSONObject) msg.obj).optJSONObject("IDCardInfo"));
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		mResultView.setText((String)msg.obj);
		idv_result_group.setVisibility(View.VISIBLE);
	}

}
