package com.topjet.crediblenumber.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Dict;

public class GoodsUnavailableActivtiy extends BaseActivity{
	Button placeButton;
	TextView countView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_unavailable);
		super.onCreate(savedInstanceState);
		placeButton = (Button) findViewById(R.id.search_recommend_unavailable);
		placeButton.setText(Dict.getCityName(app.getLocationCode()));
		//成交总数
		countView = (TextView) findViewById(R.id.goods_unavailable_count);
		//TODO:接口未没有返回此参数
//		countView.setText(text);
	}

	@Override
	protected void ioHandler(Message msg) {
		
	}
	public void onClick(View v){
		gotoMain();
	}
	//只能返回主页
	@Override
	public void onBackPressed() {
		gotoMain();
	}

}
