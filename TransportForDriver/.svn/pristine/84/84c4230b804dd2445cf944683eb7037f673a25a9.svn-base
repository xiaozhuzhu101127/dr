package com.topjet.crediblenumber.activity;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.audividi.imageloading.core.DisplayImageOptions;
import com.audividi.imageloading.core.ImageLoader;
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.util.AppConifg;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DC;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.DisplayUtil;
import com.topjet.crediblenumber.util.ImageUtils;
import com.topjet.crediblenumber.util.ShapeUtils;
import com.topjet.crediblenumber.util.TransportLog;
 
 /**
  * 
  * <pre>
  * Copyright:	Copyright (c)2009  
  * Company:		杭州龙驹信息科技有限公司
  * Author:		BillWang
  * Create at:	2013-10-24 下午2:25:01  
  *  
  * 修改历史:
  * 日期    作者    版本  修改描述
  * ------------------------------------------------------------------  
  * 
  * </pre>
  */

public class CreditResultActivity 
extends BaseActivity 
implements OnClickListener{
	/**
	 * 需要heard右侧拥有资料有误按钮
	 */
	
	String phone;
	ProgressDialog mProgressDialog;
	Button btn_other_function;
	private ImageView  pictureImageView = null;
	
	private TextView  realNameText = null;
	private TextView companyNameText = null;
	private TextView yslAccountText = null;
	
	private RatingBar  fahuoxingjiBar = null;
	
	private RatingBar chengyunxingjiBar = null;
	
	private TextView fahuoxingjiCountText = null;
	
	private TextView chengyunxingjiCountText = null;
	
	private TextView mobileText = null;
	private TextView telText = null;
	private TextView paimingText = null;
	private TextView usrTypeText = null;
	private TextView memberLevelText = null;
	private TextView registerTimeText = null;
	private TextView companylinesText = null;
	private TextView companyAddressText = null;
	private TextView usridText = null;
	private Long USRID;
	private String phoneStr;
	
	private String mobile;
	
	private String tel;
	
	protected DisplayImageOptions options=new DisplayImageOptions.Builder()
	// 默认图片
	.showStubImage(R.drawable.idc_photo)
	.showImageForEmptyUri(R.drawable.idc_photo)
	.cacheInMemory()
	.cacheOnDisc()
	.build();
	

	

	//1 表示 DianPingDetailResultActivity，2表示DianPingAddActivity
	private String typeActivity;
	
	private Dialog mDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_credit_result);
		super.onCreate(savedInstanceState);	 
		
		((TextView)findViewById(R.id.tv_title)).setText("用户信息");
		btn_other_function = (Button)findViewById(R.id.btn_other_function);
		btn_other_function.setText("推荐本软件");
		btn_other_function.setVisibility(View.VISIBLE);
		phone = getIntent().getExtras().getString("number"); 
		pictureImageView=(ImageView)super.findViewById(R.id.picture);
		realNameText = (TextView)super.findViewById(R.id.realName);
		companyNameText = (TextView)super.findViewById(R.id.companyName);
		yslAccountText = (TextView)super.findViewById(R.id.yslAccount);  
		fahuoxingjiBar = (RatingBar)super.findViewById(R.id.fahuoxingji);  
		chengyunxingjiBar = (RatingBar)super.findViewById(R.id.chengyunxingji);  
		
		fahuoxingjiCountText = (TextView)super.findViewById(R.id.fahuoxingjicount);  
		chengyunxingjiCountText= (TextView)super.findViewById(R.id.chengyunxingjicount);  
		
		mobileText= (TextView)super.findViewById(R.id.mobile);  
		telText= (TextView)super.findViewById(R.id.tel);  
		paimingText= (TextView)super.findViewById(R.id.paiming);  
		usrTypeText= (TextView)super.findViewById(R.id.userType);  
		memberLevelText= (TextView)super.findViewById(R.id.memberLevel); 
		registerTimeText= (TextView)super.findViewById(R.id.registerTime); 
		companylinesText= (TextView)super.findViewById(R.id.companylines); 
		companyAddressText= (TextView)super.findViewById(R.id.companyAddress); 
		
	
		
		
		fetchData();
		
	}
	
	
	
	private void fetchData() {

		this.initProgress("加载中......");
		this.mLoadData.getDpInfo(baseHandler, DefaultConst.CMD_DP_INFO_QUERY_RESULT,phone);
	}

//	private void correct(){
//		Intent intent = new Intent(CreditResultActivity.this, CreditCorrectActivity.class);
//		intent.putExtra("number", mNumber);
//		startActivity(intent);
//	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case  R.id.btn_other_function:	
			startActivity(new Intent(CreditResultActivity.this,RecommendActivity.class));
			break;
		case R.id.btn_back:finish();break;
		
		case R.id.queryDianPing:
//			Intent ittt = new Intent(CreditResultActivity.this,DianPingDetailResultActivity.class);		
//			ittt.putExtra("USRID", USRID);
//			ittt.putExtra("type", 0);
//			startActivity(ittt);
//			break;
			
			if (null == mDialog) {
				mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
				mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDialog.setContentView(R.layout.dialog_score_shop);
				
				TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
				text1.setText("我是承运方");
				text1.setOnClickListener(this);
				TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
				text2.setText("我是发货方");
				text2.setOnClickListener(this);
				TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);				
				view.setText("请选择您的身份");
			}
			typeActivity="1";
			mDialog.show();
			break;
		
		case R.id.addDianPing:
			//菜单样式修改
//		        TextView  text =(TextView) v.findViewById(R.id.addDianPingText);
//		        v.setBackgroundResource(R.drawable.dianping);
//		        Resources resource = (Resources) getBaseContext().getResources();  
//		        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.addDianPingTextColor);  
//		        if (csl != null) {  
//		            text.setTextColor(csl);  
//		            text.setShadowLayer(2, 1, 1, R.color.addDianPingTextColorShadow);
//		        }  
//		       
			
			if (null == mDialog) {
				mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
				mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mDialog.setContentView(R.layout.dialog_score_shop);
				
				TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
				text1.setText("我是承运方");
				text1.setOnClickListener(this);
				TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
				text2.setText("我是发货方");
				text2.setOnClickListener(this);
				TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);				
				view.setText("请选择您的身份");
			}
			typeActivity="2";
			mDialog.show();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();			
			Intent it = null;
			if("1".equals(typeActivity)){
				it=new Intent(CreditResultActivity.this,DianPingDetailResultActivity.class);
			}else{
				it=new Intent(CreditResultActivity.this,DianPingAddActivity.class);
			}
			//这里要反着来，点发货方，DCT_DPUC 为承运方
			it.putExtra("DCT_DPUC", DC.DPUC_CARRY);
			it.putExtra("USRID", USRID);		
			startActivity(it);
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();			
			Intent itt =null;
			if("1".equals(typeActivity)){
				itt=new Intent(CreditResultActivity.this,DianPingDetailResultActivity.class);
			}else{
				itt=new Intent(CreditResultActivity.this,DianPingAddActivity.class);
			}
					
			itt.putExtra("DCT_DPUC", DC.DPUC_SHIP);
			itt.putExtra("USRID", USRID);		
			startActivity(itt);
			break;
		case R.id.toushu:
			Intent intent = null;
			intent=new Intent(CreditResultActivity.this, CreditTouShuActivity.class);
			intent.putExtra("number", phoneStr);
			startActivity(intent);
			break;
		case R.id.baocuo:
			Intent itx = new Intent(CreditResultActivity.this, CreditCorrectActivity.class);
			itx.putExtra("number", phoneStr);
			startActivity(itx);
			break;
		case R.id.mobileLayout:
			if(!Common.isEmpty(mobile)){
				 startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + mobile)));
			}				
		  
		case R.id.telLayout:
			if(!Common.isEmpty(tel)){
				 startActivity(new Intent("android.intent.action.CALL",Uri.parse("tel:" + tel)));
			}			
			
		}	 
	}
	

	

	

	@Override
	protected void ioHandler(Message msg) {
		   
	
		if (msg.what == DefaultConst.CMD_DP_INFO_QUERY_RESULT) {
			renderData((JSONObject) msg.obj);
		}
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
	private void renderData(JSONObject jsonData) {
		JSONObject data = null;
		try {
			data = jsonData.getJSONObject("member");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		//base转图片
//		String picture = data.optString("idCardPhoto");
//		if(!Common.isEmpty(picture)){
//			pictureImageView.setImageBitmap(ImageUtils.base642BigMap(picture));
//		}
		String url = DisplayUtil.toAbsoluteUrl(data.optString("photoAddr"));
		if(!Common.isEmpty(url)){
			ImageLoader.getInstance().displayImage(
					url, 
					pictureImageView,
					options);
		}
		//姓名
		realNameText.setText(data.optString("REALNAME"));
		//公司名称
		companyNameText.setText(data.optString("COMPANYNAME"));
		//指数
		yslAccountText.setText(data.optString("YSLACCOUNT"));
		//发货星级展示	
		fahuoxingjiBar.setRating( data.optInt("SHIPPOINT"));
		//发货次数
		fahuoxingjiCountText.setText(data.optInt("BSHIPCOUNT")+"个评价");
		//承运星级
		chengyunxingjiBar.setRating(data.optInt("CARRYPOINT"));
		
		//承运次数
		chengyunxingjiCountText.setText(data.optInt("BCARRYCOUNT")+"个评价");
		
		 mobile=data.optString("MOBILE1");
		 tel=data.optString("TEL1");
		mobileText.setText(mobile);		
		telText.setText(tel);
		if(!Common.isEmpty(mobile)){
			phoneStr=mobile;
		}else{
			phoneStr=tel;
		}
		//当地排名
		String cityName=data.optString("cityName");
		if(!Common.isEmpty(cityName)){
			paimingText.setText(cityName+"第"+data.optInt("localTrustLevel")+"名");	
		}
		//会员类型
		usrTypeText.setText(Identity.getIdentity(data.optString("DCT_UT")).getDescription());
		//会员级别
		memberLevelText.setText(data.optString("serviceName")); 
		//注册时间
		JSONObject t = data.optJSONObject("CREATE_TIME");
		if(!(t==null||t.length()==0)){			
			registerTimeText.setText(DisplayUtil.renderDate(new Date(t.optLong("time")), "yyyy-MM-dd hh:mm:ss"));

		}
		
		companylinesText.setText(data.optString("BUSILINES"));
		companyAddressText.setText(data.optString("BUSIADDRESS"));
		USRID = data.optLong("USRID");
		
		
	
		
	}
}
