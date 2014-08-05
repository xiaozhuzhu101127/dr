package com.topjet.crediblenumber.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.dialog.CommonDialog;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.model.UserInfo;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.IdentityCardValidator;
import com.topjet.crediblenumber.util.PlateNoValidator;
import com.topjet.crediblenumber.util.Receivable;

public class MessageSearchActivity extends BaseActivity implements OnClickListener,Receivable{
	
	
	private static final int TYPE_PROVINCE = 0;
	private static final int TYPE_CITY = 1;
	private static final int TYPE_TRUCK_TYPE = 2;
	private static final int TYPE_TRUCK_LENGTH = 3;
	
	private TextView tv_titleView;
	
	CommonDialog mCityDialog;
	CommonDialog mProvinceDialog;
	CommonDialog mTruckTypeDialog;
	CommonDialog mTruckLengthDialog;
	
	
	TextView mTruckTypeView;
	TextView mTruckLengthView;

	TextView mTarget1View;
	
	TextView mDialogFor;
	
	private String tk_length="";
	private String tk_type="";
	private String tk_city="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_search);
		tv_titleView=(TextView)super.findViewById(R.id.tv_title);
		
		mTruckTypeView = (TextView) findViewById(R.id.complete_truck_type);
		mTruckLengthView = (TextView) findViewById(R.id.complete_truck_length);
		mTarget1View = (TextView) findViewById(R.id.complete_target1);
		
		tv_titleView.setText("搜索");
		
		mProvinceDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_PROVINCE,
				Dict.getProvinceDict());
		mProvinceDialog.setTvName("请选择省份");
		mCityDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_CITY);
		mCityDialog.setTvName("请选择城市");
		mTruckTypeDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_TRUCK_TYPE,
				Dict.getTruckTypeDict());
		mTruckTypeDialog.setTvName("请选择类型");
		mTruckLengthDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_TRUCK_LENGTH,
				Dict.getTruckLengthDict());
		mTruckLengthDialog.setTvName("请选择车长");
	}
	
	
	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.for_target1:
			mDialogFor = mTarget1View;
			mProvinceDialog.showDialog();
			break;	
		case R.id.rl_complete_truck_type:
			mDialogFor = mTruckTypeView;
			mTruckTypeDialog.showDialog();
			break;
		case R.id.rl_complete_truck_length:
			mDialogFor = mTruckLengthView;
			mTruckLengthDialog.showDialog();
			break;
		case R.id.complete_submit:
			submit();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	
	@Override
	public void receive(int type, Object... datas) {
		switch (type) {
		case TYPE_CITY:
			mCityDialog.dismiss();
			if (Dict.BLANK.equals(datas[0])) {
				return;
			}
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			if(!"BX".equals(datas[0])){
				tk_city = (String) datas[1];
			}
			break;
		case TYPE_PROVINCE:
			mProvinceDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			// 省份不限
			if (Dict.BLANK.equals(datas[0])) {				
				return;
			}
			if(!"BX".equals(datas[0])){
				tk_city = (String) datas[1];
			}
			mCityDialog.setData(Dict.getCityDict((String) datas[0]));
			mCityDialog.notifyDataSetChanged();
			mCityDialog.showDialog();
			break;
		case TYPE_TRUCK_TYPE:
			mTruckTypeDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			tk_type=(String) datas[1];
			break;
		case TYPE_TRUCK_LENGTH:
			mTruckLengthDialog.dismiss();
			mDialogFor.setTag(datas[0]);
			mDialogFor.setText((String) datas[1]);
			tk_length=(String) datas[1];
			break;

		}
	}
	
	
	
	
	private void submit() {
		Intent it = new Intent(this, MessageActivity.class);
		it.putExtra("tk_length",tk_length);
		it.putExtra("tk_type", tk_type);
		it.putExtra("tk_city", tk_city);
		startActivity(it);
		
	}
	

}
