package com.topjet.crediblenumber.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DC;
import com.topjet.crediblenumber.util.DefaultConst;

public class DianPingAddActivity extends BaseActivity 
implements OnClickListener,OnRatingBarChangeListener,OnFocusChangeListener{
	
	private String DCT_DPUC = null;
	
	private Long USRID = null;
	
	private TextView  firstTextView = null;
	private TextView zonghefen = null;
	private TextView  secondTextView = null;
	
	private RatingBar  taidulianghao = null;
	private RatingBar  yujiaheli = null;
	private RatingBar  huowuwanhao = null;
	private RatingBar  fahuojishi = null;
	private EditText  pingjiaContent   = null;
	private Dialog mDialog;
	
	private int first1 ;
	private int first2 ;
	private int first3 ;
	private int first4 ;
	private int 	total;
	
	
	
	
	
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_dianpin_add);
		super.onCreate(savedInstanceState);	 
		
		((TextView)findViewById(R.id.tv_title)).setText("添加评价");
		DCT_DPUC = getIntent().getExtras().getString(DC.DCT_DPUC); 
		USRID=getIntent().getExtras().getLong("USRID");
		firstTextView=(TextView)super.findViewById(R.id.firstPingjia);
		secondTextView=(TextView)super.findViewById(R.id.secondPingjia);
		if(DC.DPUC_CARRY.equals(DCT_DPUC)){
			firstTextView.setText("送货及时");
			secondTextView.setText("货物完好");
		}else{
			firstTextView.setText("如实描述");
			secondTextView.setText("结款及时");
		}
		taidulianghao  =(RatingBar)super.findViewById(R.id.taidulianghao);
		yujiaheli  =(RatingBar)super.findViewById(R.id.yujiaheli);
		huowuwanhao  =(RatingBar)super.findViewById(R.id.huowuwanhao);
		fahuojishi  =(RatingBar)super.findViewById(R.id.fahuojishi);
		zonghefen=(TextView)super.findViewById(R.id.zonghefen);
		
		taidulianghao.setOnRatingBarChangeListener(this);
		yujiaheli.setOnRatingBarChangeListener(this);
		huowuwanhao.setOnRatingBarChangeListener(this);
		fahuojishi.setOnRatingBarChangeListener(this);
		pingjiaContent=(EditText)super.findViewById(R.id.pingjiaContent);
		pingjiaContent.setOnFocusChangeListener(this);
		pingjiaContent.clearFocus();
		 first4 =Math.round(taidulianghao.getRating());
		 first3 =Math.round(yujiaheli.getRating());
		 first2 =Math.round(huowuwanhao.getRating());
		 first1 =Math.round(fahuojishi.getRating());
	     total=Math.round((first1+first2+first3+first4)/4);
		
	}
	
	
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		first4 =Math.round(taidulianghao.getRating());
		first3 =Math.round(yujiaheli.getRating());
		first2 =Math.round(huowuwanhao.getRating());
		first1 =Math.round(fahuojishi.getRating());
	     total=Math.round((first1+first2+first3+first4)/4);
		 zonghefen.setText(""+total);
		
		
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		EditText  editText =(EditText)v;
		String msg=editText.getText().toString();
		if(hasFocus){
			
			if("请输入评价内容".equals(msg)){
				editText.setText("");
			}
		}else{
			if(Common.isEmpty(msg)){
				editText.setText("请输入评价内容");
			}
			InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);  
	        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);    
		}
		
	}
	
	
	
	@Override
	protected void ioHandler(Message msg) {
		if (null == mDialog) {
			mDialog = new Dialog(this, R.style.Theme_TransparentNoTitleBar);
			mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mDialog.setContentView(R.layout.dialog_score_shop);
			
			TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
			text1.setText("返回");
			text1.setOnClickListener(this);
			TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
			text2.setText("关闭");
			text2.setOnClickListener(this);
			TextView  view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);				
			view.setText("评价成功");
		}
		
		mDialog.show();
		
	}
	
	

	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
//		case  R.id.btn_other_function:	correct();break;
		case R.id.btn_back:finish();break;
		case R.id.complete_submit:
			submit();
			break;
		case R.id.score_shop_dialog_cancel:
			finish();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();
			
			break;
		
		
		}	 
		
	}



	private void submit() {
		String DPCONTENT=pingjiaContent.getText().toString();
		if("请输入评价内容".equals(DPCONTENT)){
			DPCONTENT="";
		}
		this.initProgress("加载中......");
		this.mLoadData.addDpDetail(baseHandler, DefaultConst.CMD_DP_INFO_ADD_RESULT,USRID,DCT_DPUC,
				first1,first2,first3,first4,total,DPCONTENT);
		
	}


	public String getDCT_DPUC() {
		return DCT_DPUC;
	}



	public void setDCT_DPUC(String dCT_DPUC) {
		DCT_DPUC = dCT_DPUC;
	}



	public TextView getFirstTextView() {
		return firstTextView;
	}



	public void setFirstTextView(TextView firstTextView) {
		this.firstTextView = firstTextView;
	}



	public TextView getSecondTextView() {
		return secondTextView;
	}



	public void setSecondTextView(TextView secondTextView) {
		this.secondTextView = secondTextView;
	}



	public RatingBar getTaidulianghao() {
		return taidulianghao;
	}



	public void setTaidulianghao(RatingBar taidulianghao) {
		this.taidulianghao = taidulianghao;
	}



	public RatingBar getYujiaheli() {
		return yujiaheli;
	}



	public void setYujiaheli(RatingBar yujiaheli) {
		this.yujiaheli = yujiaheli;
	}



	public RatingBar getHuowuwanhao() {
		return huowuwanhao;
	}



	public void setHuowuwanhao(RatingBar huowuwanhao) {
		this.huowuwanhao = huowuwanhao;
	}



	public RatingBar getFahuojishi() {
		return fahuojishi;
	}



	public void setFahuojishi(RatingBar fahuojishi) {
		this.fahuojishi = fahuojishi;
	}
	
	

}
