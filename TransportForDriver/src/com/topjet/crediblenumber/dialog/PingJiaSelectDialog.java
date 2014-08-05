package com.topjet.crediblenumber.dialog;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.activity.CompleteActivity;
import com.topjet.crediblenumber.activity.DianPingDetailResultActivity;
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

public class PingJiaSelectDialog extends AlertDialog implements
		android.view.View.OnClickListener {

	private LinearLayout yixing;

	private LinearLayout erxing;

	private LinearLayout sanxing;

	private LinearLayout sixing;

	private LinearLayout wuxing;
	
	private Long USRID;
	
	private String DCT_DPUC;

	private Integer type = 0;

	private Handler dialogHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			Intent it = new Intent(PingJiaSelectDialog.this.getContext(),
					DianPingDetailResultActivity.class);
			it.putExtra("type", PingJiaSelectDialog.this.type);
			it.putExtra("USRID", PingJiaSelectDialog.this.USRID);
			it.putExtra("DCT_DPUC", PingJiaSelectDialog.this.DCT_DPUC);
			PingJiaSelectDialog.this.getContext().startActivity(it);

		};
	};

	public PingJiaSelectDialog(Context context, int theme,Long usrid,String DCT_DPUC) {		
		super(context, theme);
		this.USRID=usrid;
		this.DCT_DPUC=DCT_DPUC;
	}

	public PingJiaSelectDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog__pingjia_select_item);

		yixing = (LinearLayout) super.findViewById(R.id.yixing);
		yixing.setOnClickListener(this);

		erxing = (LinearLayout) super.findViewById(R.id.erxing);
		erxing.setOnClickListener(this);

		sanxing = (LinearLayout) super.findViewById(R.id.sanxing);
		sanxing.setOnClickListener(this);

		sixing = (LinearLayout) super.findViewById(R.id.sixing);
		sixing.setOnClickListener(this);

		wuxing = (LinearLayout) super.findViewById(R.id.wuxing);
		wuxing.setOnClickListener(this);

	}

	// @Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.yixing:
			yixing.setBackgroundColor(getContext().getResources().getColor(
					R.color.color_select_dialog));
			type = 1;
			break;
		case R.id.erxing:
			erxing.setBackgroundColor(getContext().getResources().getColor(
					R.color.color_select_dialog));
			type = 2;
			break;
		case R.id.sanxing:
			sanxing.setBackgroundColor(getContext().getResources()
					.getColor(R.color.color_select_dialog));
			type = 3;
			break;
		case R.id.sixing:
			sixing.setBackgroundColor(getContext().getResources().getColor(
					R.color.color_select_dialog));
			type = 4;
			break;
		case R.id.wuxing:
			wuxing.setBackgroundColor(getContext().getResources().getColor(
					R.color.color_select_dialog));
			type = 5;
			break;
		}

		dialogHandler.postDelayed(new Runnable() {
			public void run() {
				PingJiaSelectDialog.this.dismiss();
				dialogHandler.sendEmptyMessage(0);
			}
		}, 200);

	}

}