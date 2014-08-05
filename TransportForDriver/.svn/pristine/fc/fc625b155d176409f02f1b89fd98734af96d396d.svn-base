package com.topjet.crediblenumber.activity;

 

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Message;
 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
 
 
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.LocationManageListAdapter;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
 
/**
 * 定位管理
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-25 下午5:07:43  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class LocationManageActivity 
extends BaseActivity
implements OnClickListener{

	private TextView mPlateNoView;
	private ListView mListView;

	private LocationManageListAdapter mListAdapter;
 
	private Button btn_other_function;
	private View selView=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_location_manage);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("定位管理");
		btn_other_function = (Button)findViewById(R.id.btn_other_function);
		btn_other_function.setVisibility(View.VISIBLE);
		btn_other_function.setText("全部同意");
		
		mPlateNoView = (TextView)findViewById(R.id.location_manage_plateno);
		mListView = (ListView) findViewById(R.id.listView);
		mListAdapter = new LocationManageListAdapter(this);
		mListView.setAdapter(mListAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String mobile = ((TextView)view.findViewById(R.id.item_location_manage_phone)).getText().toString().trim();
				if(!Common.isEmpty(mobile)){
					gotoCredit(mobile);
				}
			}

		});		
  
		fetchData();
	}

	@Override
	public void onClick(View v) {
		if(R.id.btn_other_function == v.getId()) permitAll();
		else if (R.id.btn_back  == v.getId()) finish();
	}

	private void renderData(String plateno, JSONArray requests){
		mPlateNoView.setText(Common.isEmpty(plateno)?"本手机尚未绑定车辆":"本手机已经绑定" + plateno);
		mListAdapter.setData(requests);
		mListAdapter.notifyDataSetChanged();
	}

	private void fetchData(){		
		this.initProgress("加载中......");
		this.mLoadData.getLocationReq(baseHandler, DefaultConst.CMD_GET_LOCATION_REQ);
	}

	private void permitAll(){
		changeLocationPermission(null, null);
	}

	
	public void changeLocationPermission(Long id, View itemView){ 
		if(null == id)	this.initProgress("加载中......");
		selView=itemView;
		mLoadData.updateLocationReq(baseHandler, DefaultConst.CMD_UPDATE_LOCATION_REQ, id);
	}
	
 
	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		switch(msg.what)
		{
		case DefaultConst.CMD_GET_LOCATION_REQ :
			JSONObject data = (JSONObject)msg.obj;
			String plateno = data.optString("plate");
			JSONArray requests = data.optJSONArray("locs");
			renderData(plateno, requests);
			break;
		case DefaultConst.CMD_UPDATE_LOCATION_REQ:doUpdateLocationReq();break;
		}
	}

	private void doUpdateLocationReq() {
		// TODO Auto-generated method stub
		if (null == selView){
		 fetchData();
		 return;
		}
		ToggleButton b = (ToggleButton)selView.findViewById(R.id.location_permittion_btn);
		if(b.isChecked()){
			((TextView)selView.findViewById(R.id.location_permission_permit)).setVisibility(View.VISIBLE);
			((TextView)selView.findViewById(R.id.location_permission_refuse)).setVisibility(View.GONE);
		}else{
			((TextView)selView.findViewById(R.id.location_permission_permit)).setVisibility(View.GONE);
			((TextView)selView.findViewById(R.id.location_permission_refuse)).setVisibility(View.VISIBLE);
		} 
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub	 
		if(selView!=null){
			ToggleButton b = (ToggleButton)selView.findViewById(R.id.location_permittion_btn);
			b.setChecked(!b.isChecked());	
		}
	};
}
