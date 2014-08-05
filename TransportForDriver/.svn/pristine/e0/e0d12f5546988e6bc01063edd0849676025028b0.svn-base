package com.topjet.crediblenumber.activity;

import java.util.Random;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.MyApplication;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.ImageUtils;
import com.topjet.crediblenumber.util.ShapeUtils;
import com.topjet.crediblenumber.util.TransportLog;
/**
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-24 上午11:21:46  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class MainActivity 
extends BaseActivity 
implements OnClickListener {
	private ToggleButton mTruckStatusBtn; 
	private TextView  main_reward_text;
	
	private TextView realNameTextView;
	
	private TextView yslAccountTextView;
	
	private TextView userTypeTextView;
	
	private TextView localTrustLevelTextView;
	
	private ImageView pictureImageView;
	
	private Dialog mDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);	
		mTruckStatusBtn = (ToggleButton)findViewById(R.id.main_truck_status);		
		mTruckStatusBtn.setChecked(shareHelper.getTruckStatus());
		
		main_reward_text  = (TextView)findViewById(R.id.main_reward_text);
		main_reward_text.setOnClickListener(this);
		//设置数据
		realNameTextView =(TextView)super.findViewById(R.id.realName);
		yslAccountTextView =(TextView)super.findViewById(R.id.yslAccount);
		userTypeTextView =(TextView)super.findViewById(R.id.userType);
		localTrustLevelTextView=(TextView)super.findViewById(R.id.localTrustLevel);
		pictureImageView=(ImageView)super.findViewById(R.id.photoid);
		realNameTextView.setText(MyApplication.getInstance().getShareHelper().getRealName());
		yslAccountTextView.setText(""+MyApplication.getInstance().getShareHelper().getYslAccount());
		userTypeTextView.setText(MyApplication.getInstance().getShareHelper().getUserType());
		localTrustLevelTextView.setText(MyApplication.getInstance().getShareHelper().getLocaleName()+MyApplication.getInstance().getShareHelper().getLocalTrustLevel()+"名");
        String picture = MyApplication.getInstance().getShareHelper().getBase64Photo();
		if(picture!=null&&!"".equals(picture)){
			pictureImageView.setImageBitmap(ShapeUtils.toRoundBitmap(ImageUtils.base642BigMap(picture)));
		}
		
		//app.setAlarmLocation();
		//app.registerInCallPhoneListener();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	    app.unRegisterInCallPhoneListener();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
//		case R.id.settings:
		case R.id.main_item_settings:
			intent = new Intent(MainActivity.this, SettingsCategoryActivity.class);
			startActivity(intent);
			break;
			//抢好货
		case R.id.main_item_qhh:
			intent = new Intent(MainActivity.this, GoodsSearchListActivity.class);
			intent.putExtra("auto", true);
			intent.putExtra("Depart", app.getLocationCode());
			startActivity(intent);
			break;
		case R.id.main_item_credit_query:
			intent = new Intent(MainActivity.this, CreditQueryActivity.class);
			startActivity(intent);
			break;
		case R.id.huiyuanfuli:
			intent = new Intent(MainActivity.this, MemberWealActivity.class);
			startActivity(intent);
			break;
		//实用工具包
		case R.id.main_item_tools:
			intent = new Intent(MainActivity.this, ToolsCategoryActivity.class);
			startActivity(intent);
			break;
		//大货在线
		case R.id.main_item_twitter:
			intent = new Intent(MainActivity.this, TwitterListActivity.class);
			startActivity(intent);
			break;
		case R.id.main_item_message:
			intent = new Intent(MainActivity.this, MessageActivity.class);
			startActivity(intent);
			break;
		//定位管理
		case R.id.main_item_location_manage:
			intent = new Intent(MainActivity.this, LocationManageActivity.class);
			startActivity(intent);
			break;
		case R.id.main_reward_text:
			intent = new Intent(MainActivity.this, RewardDetailActivity.class);
			startActivity(intent);
			break;
//		case R.id.main_dial:
//			Intent callIntent = new Intent(Intent.ACTION_CALL);
//			callIntent.setData(AppConifg.TEL_560_URI);
//			startActivity(callIntent);
//			break;
		case R.id.main_truck_status:
			toggleTruckStatus();
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();
			Identity identity = Identity.Driver;
			Intent it = new Intent(MainActivity.this, CompleteActivity.class);
			it.putExtra("identity", identity);
			startActivity(it);
			break;
		case R.id.btn_back: finish();break;
		}
	}

	private void toggleTruckStatus(){
		this.mLoadData.updateTkState(baseHandler, DefaultConst.CMD_TRUCK_STATUS,  mTruckStatusBtn.isChecked()?1:0);
		
 	}
 
	@Override
	public void onBackPressed() {
		//返回laucher
		Intent i = new Intent(Intent.ACTION_MAIN);
		i.addCategory(Intent.CATEGORY_HOME);
		startActivity(i);
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_TRUCK_STATUS == msg.what)	{
			this.shareHelper.setTruckStatus(mTruckStatusBtn.isChecked());
			if(!mTruckStatusBtn.isChecked()){
				//提醒用户
				int count=100+new Random().nextInt(200)*2;
				if (null == mDialog) {
					mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
					mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					mDialog.setContentView(R.layout.dialog_score_shop);
					
					TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
					text1.setText("确定");
					text1.setOnClickListener(this);
					TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
					text2.setText("完善资料");
					text2.setOnClickListener(this);
				}
				TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);				
				view.setText("空车求货信息已经发送至"+count+"家认证货站，请耐心等待。资料越完整，越容易得到货主信任");
				mDialog.show();
			

			}
		}
	
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		mTruckStatusBtn.setChecked(!mTruckStatusBtn.isChecked());
		TransportLog.e(MainActivity.class.getName(), "更改大货状态失败==========err->"+msg.obj);
	}

}
