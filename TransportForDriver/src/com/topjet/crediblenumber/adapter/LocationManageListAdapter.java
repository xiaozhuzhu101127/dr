package com.topjet.crediblenumber.adapter;

import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.audividi.imageloading.core.DisplayImageOptions;
import com.audividi.imageloading.core.ImageLoader;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.activity.LocationManageActivity;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DisplayUtil;


/**
 * 配货部、信息部
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-28 上午10:54:20  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class LocationManageListAdapter 
extends LocalPhoneBookListAdapter
implements OnClickListener {
	 
	private LocationManageActivity context;

	public LocationManageListAdapter(LocationManageActivity context) {
		super(context);
		this.context = context;
		
	 
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
			JSONObject g = (JSONObject) getItem(position);
		ViewHolder viewHolder;
//		if (null == view){
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_location_manage_item, null);
			view.setTag(viewHolder);
//		}
//		else{
//			viewHolder = (ViewHolder)view.getTag();
//		}		
		viewHolder.item_location_manage_photo = (ImageView)view.findViewById(R.id.item_location_manage_photo);
		viewHolder.item_location_manage_credit
		= (TextView)view.findViewById(R.id.item_location_manage_credit);
		viewHolder.item_location_manage_name
		= (TextView)view.findViewById(R.id.item_location_manage_name);
		viewHolder.item_location_manage_company
		= (TextView)view.findViewById(R.id.item_location_manage_company);
		viewHolder.item_location_manage_phone
		= (TextView)view.findViewById(R.id.item_location_manage_phone);
		viewHolder.location_permission_refuse
		= (TextView)view.findViewById(R.id.location_permission_refuse);
		viewHolder.location_permission_permit
		= (TextView)view.findViewById(R.id.location_permission_permit);		
		viewHolder.location_permittion_btn
		= (ToggleButton)view.findViewById(R.id.location_permittion_btn);
		 
		// 照片
		String url = DisplayUtil.toAbsoluteUrl(g.optString("photoaddr"));
		if (!Common.isEmpty(url)) {		 
				ImageLoader.getInstance().displayImage(
						url, 
						viewHolder.item_location_manage_photo,
						options);
		}
		// 名字
		String name = g.optString("REALNAME");
		viewHolder.item_location_manage_name.setText(Common.isEmpty(name)?"":name);
		//公司	 
		DisplayUtil.hideOnBlank(viewHolder.item_location_manage_company, g.optString("COMPANY_NAME"));
		//电话
		DisplayUtil.hideOnBlank(viewHolder.item_location_manage_phone, g.optString("MOBILE"));
		// 诚信值
		viewHolder.item_location_manage_credit.setText("诚信值："
				+ DisplayUtil.renderNumber(g.optInt("YSLACCOUNT")));
		long id = g.optLong("LOCATEID");
		// 按钮
		ToggleButton permitBtn = ((ToggleButton) view.findViewById(R.id.location_permittion_btn));
		permitBtn.setTag(new Object[]{id, view});
		permitBtn.setOnClickListener(this);
		boolean permit = g.optInt("REQ_STATUS") == 1;
		permitBtn.setChecked(permit);
		if(permit){
			viewHolder.location_permission_permit.setVisibility(View.VISIBLE);
			viewHolder.location_permission_refuse.setVisibility(View.GONE);
		}else{
			viewHolder.location_permission_permit.setVisibility(View.GONE);
			viewHolder.location_permission_refuse.setVisibility(View.VISIBLE);
		}
		return view;                   
	}
	
	static class  ViewHolder{
		ImageView  item_location_manage_photo;
		TextView  item_location_manage_credit,
				 item_location_manage_name,
				 item_location_manage_company,
				 item_location_manage_phone,
				 location_permission_refuse,
				 location_permission_permit;
		ToggleButton		 location_permittion_btn;
	}

	@Override
	public void onClick(View v) {
		Object[] data = (Object[]) v.getTag();
		Long id = (Long)data[0];
		View itemView = (View)data[1];
		context.changeLocationPermission(id, itemView);
	}
}
