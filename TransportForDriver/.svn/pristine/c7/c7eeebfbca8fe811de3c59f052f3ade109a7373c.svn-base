package com.topjet.crediblenumber.activity;

import java.util.ArrayList;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.CommonMenuAdapter;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-24 上午11:23:07  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class SettingsCategoryActivity 
extends BaseActivity 
implements OnClickListener
{
	CommonMenuAdapter adapter;
	ArrayList<String> munuList = new ArrayList<String>(6);
	ListView listview_setting_menu;
	Button btn_setting_logout;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_common_category);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("软件设置");
		
		listview_setting_menu = (ListView)this.findViewById(R.id.listview_setting_menu);
		
		munuList.add("关于软件");
		munuList.add("常见问题");
		munuList.add("推荐给朋友");
		munuList.add("完善资料");
		munuList.add("营销代表专用");
		munuList.add("账户余额"); 
		munuList.add("密码修改"); 
		adapter = new CommonMenuAdapter(this);	
		listview_setting_menu.setAdapter(adapter);
		adapter.setList(munuList);
		listview_setting_menu.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postion, long arg3) {
				// TODO Auto-generated method stub
				if ("关于软件".equals(munuList.get(postion))){					
					startActivity(new Intent(SettingsCategoryActivity.this, AboutActivity.class));
				}
				else if ("常见问题".equals(munuList.get(postion))){					
					startActivity(new Intent(SettingsCategoryActivity.this, FaqActivity.class));
				}
				else if ("推荐给朋友".equals(munuList.get(postion))){					
					startActivity(new Intent(SettingsCategoryActivity.this, RecommendActivity.class));
				}
				else if ("完善资料".equals(munuList.get(postion))){					
					startActivity(new Intent(SettingsCategoryActivity.this, CompleteCategoryActivity.class));
				}
				else if ("营销代表专用".equals(munuList.get(postion))){					
					startActivity(new Intent(SettingsCategoryActivity.this, SalesmanActivity.class));
				}
				else if ("账户余额".equals(munuList.get(postion))){					
					startActivity(new Intent(SettingsCategoryActivity.this, BalanceActivity.class));
				}else if  ("密码修改".equals(munuList.get(postion))){					
					startActivity(new Intent(SettingsCategoryActivity.this, PasswordActivity.class));
				}
			}});
		
		 btn_setting_logout = (Button)this.findViewById(R.id.btn_setting_logout);
		 btn_setting_logout.setVisibility(View.VISIBLE);
		 btn_setting_logout.setText("退出当前账号");
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back) finish();
		else if (v.getId() == R.id.btn_setting_logout) logout();
	}
 
	 
	private void logout() {
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("退出").setMessage("确定退出?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//不经过服务端了，服务端有问题。
//						doQuit();
						isDestroy = true;
						finish();
					}

				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}
	
	private void doQuit() {
		// TODO Auto-generated method stub
	 
		this.mLoadData.loginOut(baseHandler, 
				DefaultConst.CMD_LOGIN_OUT, 
				app.getMemberID()+"");

	}
	@Override
	protected void ioHandler(Message msg) {
		if (msg.what ==DefaultConst.CMD_LOGIN_OUT ){
			isDestroy = true;
			finish();
		}
	}

	@Override
	protected void doError(Message msg) {
		if (msg.what ==DefaultConst.CMD_LOGIN_OUT ){
			isDestroy = true;
			finish();
		}
	}

}
