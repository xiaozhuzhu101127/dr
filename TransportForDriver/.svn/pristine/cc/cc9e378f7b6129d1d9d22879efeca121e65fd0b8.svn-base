package com.topjet.crediblenumber.adapter;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
import com.audividi.imageloading.core.ImageLoader;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.MyApplication;
import com.topjet.crediblenumber.adapter.RichLocalPhoneBookListAdapter.ViewHolder;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;
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
public class TwitterListAdapter
extends LocalPhoneBookListAdapter 
implements OnClickListener {
	 

	public TwitterListAdapter(Activity context) {
		super(context);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		 
		JSONObject g = (JSONObject) getItem(position);
		ViewHolder viewHolder;
		if (null ==view ){
			view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_twitter_item, null);
			viewHolder = new ViewHolder();
			view.setTag(viewHolder);			
		}
		else viewHolder = (ViewHolder)view.getTag();
 
		viewHolder.item_twitter_photo = (ImageView)view.findViewById(R.id.item_twitter_photo);
		viewHolder.item_twitter_name
		= (TextView)view.findViewById(R.id.item_twitter_name);
		viewHolder.item_twitter_credit
		= (TextView)view.findViewById(R.id.item_twitter_credit);
		viewHolder.item_twitter_recommend_count
		= (TextView)view.findViewById(R.id.item_twitter_recommend_count);
		viewHolder.item_twitter_info
		= (TextView)view.findViewById(R.id.item_twitter_info);
		viewHolder.item_twitter_content
		= (TextView)view.findViewById(R.id.item_twitter_content);
		viewHolder.item_twitter_content_place
		= (TextView)view.findViewById(R.id.item_twitter_content_place);
		viewHolder.item_twitter_mobile
		= (TextView)view.findViewById(R.id.item_twitter_mobile);
		// 照片
		String url = DisplayUtil.toAbsoluteUrl(g.optString("PADDR"));
		if (!Common.isEmpty(url)) {
			ImageLoader.getInstance().displayImage(
					url, 
					viewHolder.item_twitter_photo,
					options);
		}
		// 名字
		String name = g.optString("REALNAME");
		viewHolder.item_twitter_name.setText(Common.isEmpty(name)?"":name);
		// 诚信值
		viewHolder.item_twitter_credit.setText("诚信值："
				+ DisplayUtil.renderNumber(g.optInt("YSLACCOUNT")));
		long id = g.optLong("CARGOID");
		// 被顶次数	 
		viewHolder.item_twitter_recommend_count.setTag(id);
		viewHolder.item_twitter_recommend_count.setOnClickListener(this);
		viewHolder.item_twitter_recommend_count.setText(DisplayUtil.renderNumber(g
				.optInt("COUNTNUM")));
		// 地区 时间等信息
		JSONObject t = g.optJSONObject("CREATE_TIME");
		Log.d("TIME", new Date(t.optLong("time")).toString());
		String cityName = Dict.getCityName(g.optString("CT_REGOIN"), 2);
		String info = new StringBuilder()
		        .append(cityName)
				.append(" ")
				.append(DisplayUtil.renderDate(new Date(t.optLong("time")), "M-dd HH:mm")).toString();
		viewHolder.item_twitter_info.setText(info);
		// 内容
		viewHolder.item_twitter_content.setText(g.optString("MSG"));
		//发布地址
		viewHolder.item_twitter_content_place.setText("本信息发布于"+g.optString("PLACE"));
		viewHolder.item_twitter_mobile.setText(g.optString("MOBILE"));
		return view;
	}
	
	static class  ViewHolder{
		ImageView  item_twitter_photo;
		TextView  item_twitter_name,
		item_twitter_credit,
		item_twitter_recommend_count,
		item_twitter_info,
		item_twitter_content,
		item_twitter_mobile,
		item_twitter_content_place;
	}

	private void recommend(final long id, final TextView textView) {
		 
		MyApplication.getInstance().getLoadData().updateCargoCount(new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);	
				switch (msg.what) {		
				case DefaultConst.CMD_UPDATE_CARGO_COUNT:doUpdateCargoCount(msg);break;
				}
			}

			private void doUpdateCargoCount(Message msg) {
				// TODO Auto-generated method stub
				int count = ((JSONObject)msg.obj).optInt("count");
				//更新adapter中相应记录，以免加载下一页的时候被顶次数又回到原先数值
				for(int i = 0;i< getCount();i++){
					JSONObject g = (JSONObject)getItem(i);
					if(g.optLong("CARGOID") == id){
						try {
							g.putOpt("COUNTNUM", count);
							
						} catch (JSONException e) {
							Log.e("ERROR", e.getMessage(), e);
						}
					}
				}
				textView.setText(count+"");
			}
	  }, DefaultConst.CMD_UPDATE_CARGO_COUNT, id);
	}

	@Override
	public void onClick(View v) {
		long id = (Long) v.getTag();
		recommend(id, (TextView)v.findViewById(R.id.item_twitter_recommend_count));
	}
}
