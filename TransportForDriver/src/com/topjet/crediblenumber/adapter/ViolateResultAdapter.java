package com.topjet.crediblenumber.adapter;

import java.util.Date;

import org.json.JSONObject;

import com.audividi.imageloading.core.ImageLoader;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.RichLocalPhoneBookListAdapter.ViewHolder;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DisplayUtil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViolateResultAdapter extends CommonJsonAdapter {
	
	public ViolateResultAdapter(Activity context) {
		super(context);
	}


	
	
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		JSONObject g = (JSONObject) getItem(position);
		
			view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_violate_result_simple, null);
			JSONObject t = g.optJSONObject("VIOLATE_TIME");
			if(!(t==null||t.length()==0)){
				String time =DisplayUtil.renderDate(new Date(t.optLong("time")), "yyyy-MM-dd hh:mm:ss");
				((TextView)view.findViewById(R.id.violateTime1)).setText("第"+(++position)+"条     违法时间     "+time);
				((TextView)view.findViewById(R.id.violateTime2)).setText(DisplayUtil.renderDate(new Date(t.optLong("time")), "yyyy-MM-dd"));

			}
		
			((TextView)view.findViewById(R.id.plate)).setText(g.optString("PLATE"));
			
			((TextView)view.findViewById(R.id.plateType)).setText(g.optString("PLATE_CATEGORY"));
			((TextView)view.findViewById(R.id.violatePlace)).setText(g.optString("VIOLATE_ADDRESS"));
			((TextView)view.findViewById(R.id.violateBehavior)).setText(g.optString("VIOLATE_BEHAVIOR"));
			((TextView)view.findViewById(R.id.violateCompany)).setText(g.optString("PROCESS_COMPANY"));
		
// 
		
		return view;
	}
	


}
