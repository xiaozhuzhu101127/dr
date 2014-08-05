package com.topjet.crediblenumber.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.dialog.CommonDialog;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.util.Base64Encode;
import com.topjet.crediblenumber.util.CLSBase64;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.FileUtils;
import com.topjet.crediblenumber.util.Receivable;

public class ViolateAcitivity extends BaseActivity implements OnClickListener,Receivable{
	
	

	private ImageView  violatePicture;
	
    private Bitmap map;
	
	private byte buff[];
	
	CommonDialog mCityDialog;
	CommonDialog mProvinceDialog;
	
	TextView mDialogFor;
	
	TextView mTarget1View;
	private String tk_city="";
	
	private boolean isCity=false;
	private static final int TYPE_PROVINCE = 0;
	private static final int TYPE_CITY = 1;
	
	private Dialog mDialog;
	
	private ImageView imgMore= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_violate);
		super.onCreate(savedInstanceState);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("违章查询");
		violatePicture =(ImageView)findViewById(R.id.violatePicture);	
		mTarget1View = (TextView) findViewById(R.id.complete_target1);
		imgMore = (ImageView)findViewById(R.id.mediawall_img_more);
	//	Intent myIntent = getIntent();
	//	buff = (byte[])myIntent.getSerializableExtra("bitmap");
	//	violatePicture.setImageBitmap(BitmapFactory.decodeByteArray(buff, 0, buff.length));
		
//		violatePicture.setImageBitmap(BitmapFactory.decodeFile(FileUtils.violateImage.toString()));
		map = FileUtils.getViolatePicture(this.context, FileUtils.violateImage);
		violatePicture.setImageBitmap(map);
		
		
		mProvinceDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_PROVINCE,
				Dict.getProvinceDict());
		mProvinceDialog.setTvName("请选择省份");
		mCityDialog = new CommonDialog(this, R.layout.common_gridview_dialog, TYPE_CITY);
		mCityDialog.setTvName("请选择城市");
	
	}
	
	@Override
	protected void ioHandler(Message msg) {
		mTarget1View.setClickable(true);
		imgMore.clearAnimation();
		imgMore.setVisibility(View.GONE);	
		if (null == mDialog) {
			mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.setContentView(R.layout.dialog_score_shop);
			TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);		
			view.setText("查询已受理。由于查询量过大，请于两小时后查看结果。！");
			TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
			text1.setText("确定");
			text1.setOnClickListener(this);
			TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
			text2.setText("返回");
			text2.setOnClickListener(this);
		}
		mDialog.show();
		

	}

	@Override
	protected void doError(Message msg) {
		mTarget1View.setClickable(true);
		imgMore.clearAnimation();
		imgMore.setVisibility(View.GONE);	
		Common.showToast((String)msg.obj,this);
		

	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.for_target1:
			mDialogFor = mTarget1View;
			mProvinceDialog.showDialog();
			break;		
		case R.id.btn_back:
			finish();
			break;
		case R.id.complete_submit:
			submit();
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();	
			Intent it = new Intent(ViolateAcitivity.this, MainActivity.class);		
			startActivity(it);
			break;
		}
	}
	
	
	
	
	private void submit() {		
		
			FileInputStream in = null;
			try {
				
				if(Common.isEmpty(tk_city)){
					Common.showToast("请选择查询城市",this);
					return ;
				}
				if(!isCity){
					Common.showToast("请选择要查询的具体城市",this);
					return ;
				}
			
			//	in = new FileInputStream(FileUtils.violateImage.toString());			
				String msg =Base64.encodeToString(FileUtils.Bitmap2Bytes(map),Base64.DEFAULT);
	    	mLoadData.addWeiZhang(baseHandler, DefaultConst.CMD_WEI_ZHANG,msg  ,tk_city );
	    	
	    	//不能dian 
	    	mTarget1View.setClickable(false);
	    	//动画提示
	    	imgMore.setVisibility(View.VISIBLE);
			Animation hyperspaceJumpAnimation = AnimationUtils
					.loadAnimation(this, R.anim.anim_sroll_loading);
			imgMore.startAnimation(hyperspaceJumpAnimation);
			
			
	    	
			} catch (Exception e) {
				Log.e("ViolateActivity", e.getMessage());
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
				tk_city +=" "+ (String) datas[1];
			}
			isCity=true;
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
			isCity=false;
			mCityDialog.setData(Dict.getCityDict((String) datas[0]));
			mCityDialog.notifyDataSetChanged();
			mCityDialog.showDialog();
			break;
	

		}
	}
}
