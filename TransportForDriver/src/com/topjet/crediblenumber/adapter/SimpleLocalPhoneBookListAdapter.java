package com.topjet.crediblenumber.adapter;

import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.topjet.crediblenumber.R;
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
public class SimpleLocalPhoneBookListAdapter
extends LocalPhoneBookListAdapter{

	public SimpleLocalPhoneBookListAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_local_phone_book_simple, null);
		JSONObject g = (JSONObject) getItem(position);
		//公司
		DisplayUtil.hideOnBlank((TextView) view.findViewById(R.id.item_local_phone_book_company), g.optString("stname"));
		//地址
		DisplayUtil.hideOnBlank((TextView) view.findViewById(R.id.item_local_phone_book_address), g.optString("staddr"), "地址：");
		//电话
		DisplayUtil.hideOnBlank((TextView) view.findViewById(R.id.item_local_phone_book_phone), g.optString("tel"), "电话：");
		//简介
		DisplayUtil.hideOnBlank((TextView) view.findViewById(R.id.item_local_phone_book_intro), g.optString("stdesc"), "简介：");
		return view;
	}
}
