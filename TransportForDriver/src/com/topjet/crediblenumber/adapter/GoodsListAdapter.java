package com.topjet.crediblenumber.adapter;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.activity.CompleteActivity;
import com.topjet.crediblenumber.activity.GoodsSearchListActivity;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.Dict;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class GoodsListAdapter  extends BaseAdapter implements OnClickListener{
	private LinkedList<JSONObject> data;
	private Context context;
	private boolean loading = false;
	
	public GoodsListAdapter(Context context){
		this.data = new LinkedList<JSONObject>();
		this.context = context;
	}
	public void setData(JSONArray data){
		if(null == data){
			return;
		}
		if(null != this.data){
			this.data.clear();
		}
		for(int i = 0;i<data.length();i++){
			this.data.add(data.optJSONObject(i));
		}
	}

	public List<JSONObject> getData(){
		return data;
	}

	public void prependData(JSONArray data){
		if(null == data){
			return;
		}
		for(int i = data.length() - 1;i>=0;i--){
			this.data.addFirst(data.optJSONObject(i));
		}
	}

	public void appendData(JSONArray data){
		if(null == data){
			return;
		}
		for(int i = 0;i < data.length();i++){
			this.data.add(data.optJSONObject(i));
		}
	}

	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < 0 || position >= data.size()) {
			return null;
		}
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@SuppressLint("UseValueOf")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//目的地
		TextView target;
		//抢光了/有几人在抢
		TextView rush;
		//重量
		TextView weight;
		//货物类型
		TextView goods_type;
		//车长
		TextView length;
		//车型
		TextView truck_type;
		//抢
		ImageButton rush_btn;
		//调
		ImageButton change_btn;
		//不可抢
		ImageButton norush;
		//不可调
		ImageButton nochange;
		//星级
		RatingBar rating_bar;
		if(null == convertView){//放入缓存
			convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_search_list, null);
			target = (TextView) convertView.findViewById(R.id.goods_serach_target);
			rush = (TextView) convertView.findViewById(R.id.goods_serach_qiangcount);
			weight = (TextView) convertView.findViewById(R.id.goods_serach_weight);
			goods_type = (TextView) convertView.findViewById(R.id.goods_serach_goodsType);
			length = (TextView) convertView.findViewById(R.id.goods_serach_truckLegth);
			truck_type = (TextView) convertView.findViewById(R.id.goods_serach_truckType);
			rush_btn = (ImageButton) convertView.findViewById(R.id.goods_search_qiang);
			change_btn = (ImageButton) convertView.findViewById(R.id.goods_search_change);
			norush = (ImageButton) convertView.findViewById(R.id.goods_search_noqiang);
			nochange = (ImageButton) convertView.findViewById(R.id.goods_search_nochange);
			rating_bar = (RatingBar)convertView.findViewById(R.id.rating_bar);
			convertView.setTag(new DataWrapper(target, rush, weight, goods_type, length, truck_type, rush_btn, change_btn, norush, nochange,rating_bar));
		}else{//取出
			DataWrapper dataWrapper = (DataWrapper) convertView.getTag();
			target = dataWrapper.target;
			rush = dataWrapper.rush;
			weight = dataWrapper.weight;
			goods_type = dataWrapper.goods_type;
			length = dataWrapper.length;
			truck_type = dataWrapper.truck_type;
			rush_btn = dataWrapper.rush_btn;
			change_btn = dataWrapper.change_btn;
			norush = dataWrapper.norush;
			nochange = dataWrapper.nochange;
			rating_bar = dataWrapper.rating_bar;
		}
		//绑定数据
		JSONObject g = (JSONObject) getItem(position);
		switch (Dict.getGsState(g.optString("GSSTS"))) {
		// 默认显示：抢货按钮
		case 2:
			// 可以抢货，显示：抢货人数，抢货按钮
			//抢货人数
			rush.setText("有" + g.optInt("COUNT") + "人在抢");
			break;
		case 1:
			// 抢完，显示：抢光啦，不可抢按钮
			rush.setText("抢光啦");
			norush.setVisibility(View.VISIBLE);
			rush_btn.setVisibility(View.GONE);
			convertView.setBackgroundColor(context.getResources().getColor(R.color.color_goods_noqiang));
			break;
		case 0:
			// 已报价，显示：抢货人数，调价按钮
			//抢货人数
			rush.setText("有" + g.optInt("COUNT") + "人在抢");
			change_btn.setVisibility(View.VISIBLE);
			rush_btn.setVisibility(View.GONE);
			break;
		case 3:
			// 撤消报价，显示：已撤消，不可调按钮
			rush.setText("已撤销");
			norush.setVisibility(View.VISIBLE);
			rush_btn.setVisibility(View.GONE);
			convertView.setBackgroundColor(context.getResources().getColor(R.color.color_goods_noqiang));
			break;
		}
		rush_btn.setOnClickListener(this);
		rush_btn.setTag(new Object[]{g.optLong("GSID"), "0",false});
		change_btn.setOnClickListener(this);
		change_btn.setTag(new Object[]{g.optLong("GSID"), g.optString("CALLPRICE"),true});
		//目的地
		String ct_target = Common.splitBX(g.optString("CT_TARGET"));
		target.setText(Dict.getLocation(ct_target));
		//重量或体积
		weight.setText(Common.weightOrVolume(g.optString("GSSCALE"), g.optString("GSUNIT")));
		//货源类型
		goods_type.setText(Dict.getGsType(g.optString("GSTYPE")));
		//车长
		length.setText(Dict.getTruckLength(g.optString("TKLEN")));
		//车型
		truck_type.setText(Dict.getTruckType(g.optString("TKTYPE")));
		//分数
		rating_bar.setRating(new Float(g.optString("SHIPPOINT")));
		rating_bar.setEnabled(false);
		return convertView;
	}
	
	private class DataWrapper{
		public TextView target;
		public TextView rush;
		public TextView weight;
		public TextView goods_type;
		public TextView length;
		public TextView truck_type;
		public ImageButton rush_btn;
		public ImageButton change_btn;
		public ImageButton norush;
		public ImageButton nochange;
		public RatingBar rating_bar;
		public DataWrapper(TextView target, TextView rush, TextView weight, TextView goods_type,
				TextView length, TextView truck_type, ImageButton rush_btn, ImageButton change_btn, ImageButton norush,
				ImageButton nochange,RatingBar rating_bar) {
			this.target = target;
			this.rush = rush;
			this.weight = weight;
			this.goods_type = goods_type;
			this.length = length;
			this.truck_type = truck_type;
			this.rush_btn = rush_btn;
			this.change_btn = change_btn;
			this.norush = norush;
			this.nochange = nochange;
			this.rating_bar = rating_bar;
		}
		
	}

	@Override
	public void onClick(View v) {
		//验证身份
		//不验证了
//		if(!Common.checkDct_ut()){
//			Intent intent = new Intent(context, CompleteActivity.class);
//			intent.putExtra("identity", Identity.Driver);
//			context.startActivity(intent);
//			return;
//		}
		Object[] data = (Object[])v.getTag();
		//报价/调整报价
		//也可以定义一个接口，SearchListActivity实现这个接口；
		((GoodsSearchListActivity)context).bid((Long)data[0], (String)data[1],(Boolean)data[2]);
	}
}
