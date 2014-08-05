package com.topjet.crediblenumber.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.Dict;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

public class GoodsHistoryAdapter  extends BaseAdapter{
	private JSONArray data;
	private Context context;
	
	public GoodsHistoryAdapter(Context context){
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
		TextView orderid;
		TextView depart;
		TextView target;
		TextView weight;
		TextView goodsType;
		TextView truckLegth;
		TextView truckType;
		TextView desc;
		TextView price;
		TextView cjtime;
		TextView owner;
		RatingBar rating_bar;
		if(null == convertView){//放入缓存
			convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_history_list, null);
			orderid = (TextView) convertView.findViewById(R.id.goods_history_orderid);
			depart = (TextView) convertView.findViewById(R.id.goods_history_depart);
			target = (TextView) convertView.findViewById(R.id.goods_history_target);
			weight = (TextView) convertView.findViewById(R.id.goods_history_weight);
			goodsType = (TextView) convertView.findViewById(R.id.goods_history_goodsType);
			truckLegth = (TextView) convertView.findViewById(R.id.goods_history_truckLegth);
			truckType = (TextView) convertView.findViewById(R.id.goods_history_truckType);
			price = (TextView) convertView.findViewById(R.id.goods_history_price);
			desc = (TextView) convertView.findViewById(R.id.goods_history_desc);
			cjtime = (TextView) convertView.findViewById(R.id.goods_history_cjtime);
			rating_bar = (RatingBar)convertView.findViewById(R.id.history_rating_bar);
			owner = (TextView) convertView.findViewById(R.id.goods_history_owner);
			convertView.setTag(new DataWrapper(orderid, depart, target, weight, goodsType, truckLegth, truckType, desc, price, cjtime, owner, rating_bar));
		}else{//取出
			DataWrapper dataWrapper = (DataWrapper) convertView.getTag();
			orderid = dataWrapper.orderid;
			depart = dataWrapper.depart;
			target = dataWrapper.target;
			weight = dataWrapper.weight;
			goodsType = dataWrapper.goodsType;
			truckLegth = dataWrapper.truckLegth;
			truckType = dataWrapper.truckType;
			price = dataWrapper.price;
			cjtime = dataWrapper.cjtime;
			owner = dataWrapper.owner;
			desc = dataWrapper.desc;
			rating_bar = dataWrapper.rating_bar;
		}
		//绑定数据
		JSONObject g = (JSONObject) getItem(position);
		//订单号
		orderid.setText(g.optString("GSORDERNO"));
		//始发地
		String ct_depart = Common.splitBX(g.optString("CT_DEPART"));
		depart.setText(Dict.getLocation(ct_depart));
		//目的地
		String ct_target = Common.splitBX(g.optString("CT_TARGET"));
		target.setText(Dict.getLocation(ct_target));
		//重量或体积
		weight.setText(Common.weightOrVolume(g.optString("GSSCALE"), g.optString("GSUNIT")));
		//货源类型
		goodsType.setText(Dict.getGsType(g.optString("GSTYPE")));
		//车长
		truckLegth.setText(Dict.getTruckLength(g.optString("TKLEN")));
		//车型
		truckType.setText(Dict.getTruckType(g.optString("TKTYPE")));
		//TODO:备注没有
		desc.setText(g.optString("desc"));
		//价格
		price.setText("成交价:"+g.optString("SUBMITPRICE")+"元");
		//成交时间
		cjtime.setText(g.optString("PRICEDATE"));
		//货主
		owner.setText("货主:"+g.optString("SHIPPERNAME"));
		//星级
		if(!Common.isEmpty(g.optString("SHIPPOINT")))
			rating_bar.setRating(new Float(g.optString("SHIPPOINT")));
		rating_bar.setEnabled(false);
		return convertView;
	}
	
	private class DataWrapper{
		public TextView orderid;
		public TextView depart;
		public TextView target;
		public TextView weight;
		public TextView goodsType;
		public TextView truckLegth;
		public TextView truckType;
		public TextView desc;
		public TextView price;
		public TextView cjtime;
		public TextView owner;
		public RatingBar rating_bar;
		public DataWrapper(TextView orderid, TextView depart, TextView target, TextView weight, TextView goodsType,
				TextView truckLegth, TextView truckType, TextView desc, TextView price, TextView cjtime,
				TextView owner, RatingBar rating_bar) {
			super();
			this.orderid = orderid;
			this.depart = depart;
			this.target = target;
			this.weight = weight;
			this.goodsType = goodsType;
			this.truckLegth = truckLegth;
			this.truckType = truckType;
			this.desc = desc;
			this.price = price;
			this.cjtime = cjtime;
			this.owner = owner;
			this.rating_bar = rating_bar;
		}
	}
}
