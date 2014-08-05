package com.topjet.crediblenumber.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.ShareHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-26 上午8:50:16  
 * Description:好货详情，抢货人apapter
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsDetailAdapter extends BaseAdapter{
	private Context context;
	private JSONArray data;
	
	public GoodsDetailAdapter(Context context, JSONArray data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return data.get(position);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		TextView nameView;
		TextView bjView;
		TextView timeView;
		DataWrapper dataWrapper;
		if(null == convertView){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_rush_list, null);
			imageView = (ImageView) convertView.findViewById(R.id.goods_detail_num);
			nameView = (TextView) convertView.findViewById(R.id.goods_detail_name);
			bjView = (TextView) convertView.findViewById(R.id.goods_detail_bj);
			timeView = (TextView) convertView.findViewById(R.id.goods_detail_time);
			dataWrapper = new DataWrapper(imageView, nameView, bjView, timeView);
			convertView.setTag(dataWrapper);
		}else{
			dataWrapper = (DataWrapper) convertView.getTag();
			imageView = dataWrapper.imageView;
			nameView = dataWrapper.nameView;
			bjView = dataWrapper.bjView;
			timeView = dataWrapper.timeView;
		}
		JSONObject	price = (JSONObject) getItem(position);
		//前五名有图片
		switch (position) {
		case 0:
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.num1));
			break;
		case 1:
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.num2));
			break;
		case 2:
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.num3));
			break;
		case 3:
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.num4));
			break;
		case 4:
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.num5));
			break;
		}
		//姓名
		nameView.setText(price.optString("USRREALNAME")+"(");
		//报价
		bjView.setText("报价:"+price.optString("PRICE")+"元");
		//时间
		timeView.setText(price.optString("CREATE_TIME"));
		if(price.optLong("USRID") == ShareHelper.getInstance(context).getUserId())
			convertView.setBackgroundColor(context.getResources().getColor(R.color.rush));
		return convertView;
	}
	private class DataWrapper{
		private ImageView imageView;
		private TextView nameView;
		private TextView bjView;
		private TextView timeView;
		public DataWrapper(ImageView imageView, TextView nameView, TextView bjView, TextView timeView) {
			this.imageView = imageView;
			this.nameView = nameView;
			this.bjView = bjView;
			this.timeView = timeView;
		}
	}
}
