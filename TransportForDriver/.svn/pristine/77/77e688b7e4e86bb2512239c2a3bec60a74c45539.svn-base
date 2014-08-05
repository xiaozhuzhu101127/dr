package com.topjet.crediblenumber.adapter;

import java.util.Date;

import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.audividi.imageloading.core.ImageLoader;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.activity.DianPingDetailResultActivity;
import com.topjet.crediblenumber.activity.LocationManageActivity;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DC;
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
public class PingJiaDetailListAdapter extends LocalPhoneBookListAdapter
		implements OnClickListener {

	private DianPingDetailResultActivity context;

	public PingJiaDetailListAdapter(DianPingDetailResultActivity context) {
		super(context);
		this.context = context;

	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		JSONObject g = (JSONObject) getItem(position);
		ViewHolder viewHolder;
		if (null == view) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context.getApplicationContext())
					.inflate(R.layout.item_pingjia_detail_item, null);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		String DCT_DPUC=g.optString("DCT_DPUC");
		viewHolder.miaoshu1 = (TextView) view.findViewById(R.id.miaoshu1);
		viewHolder.miaoshu2 = (TextView) view.findViewById(R.id.miaoshu2);
		viewHolder.pingfen1 = (TextView) view.findViewById(R.id.pingfen1);
		viewHolder.pingfen2 = (TextView) view.findViewById(R.id.pingfen2);
		viewHolder.touxiang = (ImageView) view.findViewById(R.id.touxiang);
		viewHolder.realName = (TextView) view.findViewById(R.id.realName);
		viewHolder.companyName = (TextView) view.findViewById(R.id.companyName);
		viewHolder.yslAccount = (TextView) view.findViewById(R.id.yslAccount);
		viewHolder.pingjiaContent = (TextView) view.findViewById(R.id.pingjiaContent);
		viewHolder.pingjiaShiJian = (TextView) view.findViewById(R.id.pingjiaShiJian);
		viewHolder.zhonghedefen = (TextView) view.findViewById(R.id.zhonghedefen);
		viewHolder.yunjiaheli = (TextView) view.findViewById(R.id.yunjiaheli);
		viewHolder.taidulianghao=(TextView)view.findViewById(R.id.taidulianghao);
		viewHolder.xingji=(RatingBar)view.findViewById(R.id.xingji);
		// 照片
		String url = DisplayUtil.toAbsoluteUrl(g.optString("photoAddr"));
		if (!Common.isEmpty(url)) {
			ImageLoader.getInstance().displayImage(url, viewHolder.touxiang,
					options);
		}
		
		// 姓名
		viewHolder.realName.setText(g.optString("DPUSRREALNAME"));
		//公司名
		viewHolder.companyName.setText(g.optString("DPCOMPANY"));
		//诚信值
		viewHolder.yslAccount.setText(g.optString("DPYSLACCOUNT"));
		
		//评价内容
		viewHolder.pingjiaContent.setText(g.optString("DPCONTENT"));
		
		//评价时间
		JSONObject t = g.optJSONObject("CREATE_TIME");
		if(!(t==null||t.length()==0)){			
			viewHolder.pingjiaShiJian.setText(DisplayUtil.renderDate(new Date(t.optLong("time")), "yyyy-MM-dd hh:mm:ss"));

		}
		int total =g.optInt("POINT");
		viewHolder.zhonghedefen.setText(total+"星");
		viewHolder.xingji.setRating(total);
		//货运方
		if(DC.DPUC_SHIP.equals(DCT_DPUC)){
			viewHolder.miaoshu1.setText("如实描述:");
			viewHolder.miaoshu2.setText("结款及时:");
			viewHolder.pingfen1.setText(g.optInt("TRUTHPOINT")+"星");
			viewHolder.pingfen2.setText(g.optInt("PAYMENTPOINT")+"星");
		}else{
			//承运方
			viewHolder.miaoshu1.setText("送货及时:");
			viewHolder.miaoshu2.setText("货物完好:");
			viewHolder.pingfen1.setText(g.optInt("DELIVERYPOINT")+"星");
			viewHolder.pingfen2.setText(g.optInt("GOODSPOINT")+"星");
		}
		//运价合理
		viewHolder.yunjiaheli.setText(g.optInt("PRICEPOINT")+"星");
		//带度良好
		viewHolder.taidulianghao.setText(g.optInt("ATTITUDEPOINT")+"星");
		return view;
	}

	static class ViewHolder {
		ImageView touxiang;
		TextView realName, companyName, yslAccount,
		pingjiaContent, pingjiaShiJian, yunjiaheli,taidulianghao,
		zhonghedefen, miaoshu1,miaoshu2,pingfen1,pingfen2;
		RatingBar  xingji;

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
