package com.topjet.crediblenumber.adapter;

import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.audividi.imageloading.core.DisplayImageOptions;
import com.audividi.imageloading.core.ImageLoader;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DisplayUtil;

/**  
 * 配货部、信息部
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
public class RichLocalPhoneBookListAdapter
extends LocalPhoneBookListAdapter{
 
	public RichLocalPhoneBookListAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		JSONObject g = (JSONObject) getItem(position);
		ViewHolder viewHolder;
		//可能会导致问题，暂时不做处理
//		if (null ==view ){
			view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_local_phone_book_rich, null);
			viewHolder = new ViewHolder();
			view.setTag(viewHolder);			
//		}
//		else viewHolder = (ViewHolder)view.getTag();
 
		viewHolder.item_local_phone_book_photo = (ImageView)view.findViewById(R.id.item_local_phone_book_photo);
		viewHolder.item_local_phone_book_name
		= (TextView)view.findViewById(R.id.item_local_phone_book_name);
		viewHolder.item_local_phone_book_credit
		= (TextView)view.findViewById(R.id.item_local_phone_book_credit);
		viewHolder.item_local_phone_book_company
		= (TextView)view.findViewById(R.id.item_local_phone_book_company);
		viewHolder.item_local_phone_book_phone
		= (TextView)view.findViewById(R.id.item_local_phone_book_phone);
		viewHolder.item_local_phone_book_address
		= (TextView)view.findViewById(R.id.item_local_phone_book_address);
		viewHolder.xingji=(RatingBar)view.findViewById(R.id.xingji);
		
		//照片
		String url = DisplayUtil.toAbsoluteUrl(g.optString("photoaddr"));
		if(!Common.isEmpty(url)){
			ImageLoader.getInstance().displayImage(
					url, 
					viewHolder.item_local_phone_book_photo,
					options);
		}
		//名字
		viewHolder.item_local_phone_book_name.setText(g.optString("realName"));
		//诚信值
		viewHolder.item_local_phone_book_credit.setText("诚信值：" + DisplayUtil.renderNumber(g.optInt("yslAccount")));
		//公司
		DisplayUtil.hideOnBlank(viewHolder.item_local_phone_book_company, g.optString("companyName"));
		//电话
		DisplayUtil.hideOnBlank(viewHolder.item_local_phone_book_phone, DisplayUtil.renderPhoneAndMobile(g.optString("tel1"), g.optString("mobile1")));
		//地址
		DisplayUtil.hideOnBlank(viewHolder.item_local_phone_book_address, g.optString("busiAddr"), "地址：");
		
		//星级,选高的那个
		Long CARRYPOINT =g.optLong("CARRYPOINT");
		Long SHIPPOINT =g.optLong("SHIPPOINT");
		
		if(CARRYPOINT==null&&SHIPPOINT==null){
			CARRYPOINT=0l;
		}else if(SHIPPOINT!=null&&CARRYPOINT==null){
			CARRYPOINT=CARRYPOINT;
		}else if(CARRYPOINT!=null&&CARRYPOINT!=null){
			CARRYPOINT= SHIPPOINT>CARRYPOINT?SHIPPOINT:CARRYPOINT;
		}
		viewHolder.xingji.setRating(CARRYPOINT);
		return view;
	}
	
	static class  ViewHolder{
		ImageView  item_local_phone_book_photo;
		TextView  item_local_phone_book_name,
					item_local_phone_book_credit,
					item_local_phone_book_company,
					item_local_phone_book_phone,
					item_local_phone_book_address;
					RatingBar			xingji;
	}

}
