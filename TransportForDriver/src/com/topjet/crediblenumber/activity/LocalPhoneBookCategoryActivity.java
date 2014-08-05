package com.topjet.crediblenumber.activity;

 
import java.util.ArrayList;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.CommonMenuAdapter;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DisplayUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
 

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-27 下午5:00:08  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class LocalPhoneBookCategoryActivity 
extends BaseActivity 
implements OnClickListener{
	private Button btn_other_function;
	CommonMenuAdapter adapter;
	ArrayList<String> munuList = new ArrayList<String>(6);
	ListView listview_setting_menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_common_category);
		super.onCreate(savedInstanceState);
		//显示常驻地	 
		btn_other_function = (Button)findViewById(R.id.btn_other_function);
		btn_other_function.setText(app.getLocation());
	    if ( !Common.isEmpty(app.getLocation())) btn_other_function.setVisibility(View.VISIBLE);
	    tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("当地电话本");
 
	    listview_setting_menu = (ListView)this.findViewById(R.id.listview_setting_menu);
	
		munuList.add("配货部、信息部");
		munuList.add("物流市场");
		munuList.add("560服务站");
		munuList.add("汽修厂");
		munuList.add("加油站");
		munuList.add("车管所"); 
		adapter = new CommonMenuAdapter(this);	
		listview_setting_menu.setAdapter(adapter);
		adapter.setList(munuList);
		listview_setting_menu.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion, long arg3) {
				// TODO Auto-generated method stub				 
				Intent intent = new Intent(LocalPhoneBookCategoryActivity.this,
						LocalPhoneBookActivity.class);
				intent.putExtra("category", munuList.get(postion));
				startActivity(intent);				 
			}});
		
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back) finish();
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}

}
