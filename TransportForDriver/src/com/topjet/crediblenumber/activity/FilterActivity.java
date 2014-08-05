package com.topjet.crediblenumber.activity;
 
 
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
 
import com.google.gson.JsonObject;
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.apkupdate.UpgradeManager;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
 

 /**
  * 
  * <pre>
  * Copyright:	Copyright (c)2009  
  * Company:		杭州龙驹信息科技有限公司
  * Author:		BillWang
  * Create at:	2013-10-24 下午2:28:52  
  *  
  * 修改历史:
  * 日期    作者    版本  修改描述
  * ------------------------------------------------------------------  
  * 
  * </pre>
  */
public class FilterActivity 
extends BaseActivity 
implements OnClickListener { 
	boolean mAuto = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_filter);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("通讯录筛查");
		Bundle bundle = getIntent().getExtras(); 
		if (bundle != null) mAuto = bundle.getBoolean("auto");
 
 
		//从营销代表专用页面过来，自动筛查
		if(mAuto){
			filter();
		}
	}

	private void filter() {
		initProgress("正在进行通讯录筛查，请稍后......"); 
		
		this.mLoadData.upPhoneList(baseHandler, 
				DefaultConst.CMD_UP_PHONELIST);
	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.filter_btn:filter();break;
		case R.id.btn_back: finish();break;
		}
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		JSONObject json=(JSONObject)msg.obj;
		String content="";
		if(json!=null)
			content=json.optString("msg");
		//如果有http地址，那么说明是要更新的
		if(content!=null&&content.indexOf("http")>=0){
			UpgradeManager.getInstance(this.context).contactVersion(json);
			return ;
		}
		switch(msg.what){
		
		case DefaultConst.CMD_UP_PHONELIST:doUpPhoneList(content);break;
		}
	}

	private void doUpPhoneList(String msg) {
		
		
		// TODO Auto-generated method stub
		shareHelper.setUploaded(true);
		new AlertDialog
		.Builder(FilterActivity.this).
		setIcon(android.R.drawable.ic_dialog_info)
		.setTitle("筛查成功")
		.setMessage(msg)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//营销代表点确定后直接返回
				if(mAuto){
					FilterActivity.this.finish();
				}
			}
		}).show();
 
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
 
		if (!Common.isEmpty((String) msg.obj)) {
			Common.showToast("通讯录筛查失败：" + (String) msg.obj, this);
		} else {
			Common.showToast("通讯录筛查失败，请检查网络是否通畅", this);
		}		 
	}

}
