package com.topjet.crediblenumber.dialog;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.activity.CompleteActivity;
import com.topjet.crediblenumber.activity.MessageActivity;
import com.topjet.crediblenumber.activity.TwitterListActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;



public class SelectDialog extends AlertDialog implements android.view.View.OnClickListener{
	
	private TextView qsykTextView;	
	private LinearLayout qsykLinear;
	
	private TextView hyfbView;	
	private LinearLayout hyfbLinear;
	
	private TextView ysxfbTextView;	
	private LinearLayout ysxfbLinear;
	
	private TextView rdxwTextView;	
	private LinearLayout rdxwLinear;
	
	private String type="";
	
	   private  Handler dialogHandler = new Handler(){
		   
		   public void handleMessage(android.os.Message msg) {
				
			Intent it = new Intent(SelectDialog.this.getContext(), TwitterListActivity.class);
   			it.putExtra("type",SelectDialog.this.type);			
   			SelectDialog.this.getContext().startActivity(it);
			   
		   };
	   };
	
	public SelectDialog(Context context, int theme) {
	    super(context, theme);
	}

	public SelectDialog(Context context) {
	    super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.dialog_select_item);
	    qsykTextView = (TextView)super.findViewById(R.id.qsyk);
	    qsykLinear = (LinearLayout)super.findViewById(R.id.qsykLinear);
	    qsykLinear.setOnClickListener(this);
	    
	  
	    
	    hyfbView = (TextView)super.findViewById(R.id.hyfb);
	    hyfbLinear = (LinearLayout)super.findViewById(R.id.hyfbLinear);
	    hyfbLinear.setOnClickListener(this);
	    
	    ysxfbTextView = (TextView)super.findViewById(R.id.ysxfb);
	    ysxfbLinear = (LinearLayout)super.findViewById(R.id.ysxfbLinear);
	    ysxfbLinear.setOnClickListener(this);
	    
	    
	    rdxwTextView = (TextView)super.findViewById(R.id.rdxw);
	    rdxwLinear = (LinearLayout)super.findViewById(R.id.rdxwbLinear);
	    rdxwLinear.setOnClickListener(this);
	
	}
	

//	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case  R.id.qsykLinear:
			qsykLinear.setBackgroundColor(getContext().getResources().getColor(R.color.color_select_dialog));
			type="1";
			break;
		case  R.id.hyfbLinear:
			hyfbLinear.setBackgroundColor(getContext().getResources().getColor(R.color.color_select_dialog));	
			type="4";
			break;
		case  R.id.ysxfbLinear:
			ysxfbLinear.setBackgroundColor(getContext().getResources().getColor(R.color.color_select_dialog));	
			type="2";
			break;
		case  R.id.rdxwbLinear:
			rdxwLinear.setBackgroundColor(getContext().getResources().getColor(R.color.color_select_dialog));	
			type="3";
			break;
		}
		
		 dialogHandler.postDelayed(new Runnable() {
	            public void run() {
	            	SelectDialog.this.dismiss();
	            	dialogHandler.sendEmptyMessage(0);
	            }
	     }, 200);
		
		   
		
	}
	


}