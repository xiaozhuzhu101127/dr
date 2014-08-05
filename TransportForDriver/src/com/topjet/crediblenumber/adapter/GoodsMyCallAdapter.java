package com.topjet.crediblenumber.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.activity.GoodsMyCallActivity;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.Dict;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class GoodsMyCallAdapter  extends BaseAdapter implements OnClickListener{
	private JSONArray data;
	private Context context;
	
	public GoodsMyCallAdapter(Context context){
		this.context = context;
	}
	public void setData(JSONArray data){
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
	@SuppressLint("UseValueOf")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView target;
		TextView weight;
		TextView goodsType;
		TextView price;
		TextView bjinfo;
		ImageButton tiaoButton;
		ImageButton notiaoButton;
		if(null == convertView){//放入缓存
			convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_my_list, null);
			target = (TextView) convertView.findViewById(R.id.goods_my_target);
			weight = (TextView) convertView.findViewById(R.id.goods_my_heary);
			goodsType = (TextView) convertView.findViewById(R.id.goods_my_goodsType);
			price = (TextView) convertView.findViewById(R.id.goods_my_price);
			bjinfo = (TextView) convertView.findViewById(R.id.goods_my_bjinfo);
			tiaoButton = (ImageButton) convertView.findViewById(R.id.goods_my_tiao);
			notiaoButton = (ImageButton) convertView.findViewById(R.id.goods_my_notiao);
			convertView.setTag(new DataWrapper(target, weight, goodsType, price, bjinfo,tiaoButton,notiaoButton));
		}else{//取出
			DataWrapper dataWrapper = (DataWrapper) convertView.getTag();
			target = dataWrapper.target;
			weight = dataWrapper.weight;
			goodsType = dataWrapper.goodsType;
			price = dataWrapper.price;
			bjinfo = dataWrapper.bjinfo;
			tiaoButton = dataWrapper.tiaoButton;
			notiaoButton = dataWrapper.notiaoButton;
		}
		//绑定数据
		JSONObject g = (JSONObject) getItem(position);
		
		//目的地
		String ct_depart = Common.splitBX(g.optString("CT_TARGET"));
		target.setText(Dict.getLocation(ct_depart));
		//重量或体积
		weight.setText(Common.weightOrVolume(g.optString("GSSCALE"), g.optString("GSUNIT")));
		//货源类型
		goodsType.setText(Dict.getGsType(g.optString("GSTYPE")));
		//价格
		JSONObject pricejson = g.optJSONObject("PRICE");
		price.setText("我的报价:"+pricejson.optString("PRICE")+"元");
		//排名
		bjinfo.setText("第"+pricejson.optString("SORT")+"名 - "+pricejson.optString("LEFTTIME"));
		switch(Dict.getGsState(g.optString("GSSTS"))){
			case 0:
			case 2:
				//可以调
				tiaoButton.setOnClickListener(this);
				tiaoButton.setTag(new Object[]{g.optLong("GSID"),pricejson.optString("PRICE"),true});
				break;
			case 1:
			case 3:
				//不可调
				tiaoButton.setVisibility(View.GONE);
				notiaoButton.setVisibility(View.VISIBLE);
				convertView.setBackgroundColor(context.getResources().getColor(R.color.color_goods_noqiang));
				break;
		}
		return convertView;
	}
	
	private class DataWrapper{
		public TextView target;
		public TextView weight;
		public TextView goodsType;
		public TextView price;
		public TextView bjinfo;
		public ImageButton tiaoButton;
		public ImageButton notiaoButton;
		public DataWrapper(TextView target, TextView weight, TextView goodsType, TextView price, TextView bjinfo,
				ImageButton tiaoButton, ImageButton notiaoButton) {
			this.target = target;
			this.weight = weight;
			this.goodsType = goodsType;
			this.price = price;
			this.bjinfo = bjinfo;
			this.tiaoButton = tiaoButton;
			this.notiaoButton = notiaoButton;
		}
	}

	@Override
	public void onClick(View v) {
		Object[] data = (Object[])v.getTag();
		//报价/调整报价
		//也可以定义一个接口，GoodsMyCallActivity实现这个接口；
		((GoodsMyCallActivity)context).bid((Long)data[0], (String)data[1],(Boolean)data[2]);
	}
}
