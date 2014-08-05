package com.topjet.crediblenumber.adapter;

import java.util.Date;

import org.json.JSONObject;

import com.audividi.imageloading.core.ImageLoader;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.RichLocalPhoneBookListAdapter.ViewHolder;
import com.topjet.crediblenumber.model.Violate;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DisplayUtil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViolateReqAdapter extends CommonJsonAdapter {
	
	public ViolateReqAdapter(Activity context) {
		super(context);
	}


	
	
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		JSONObject g = (JSONObject) getItem(position);
		
			view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_violate_simple, null);
			
			String time =DisplayUtil.renderDate(new Date(g.optLong("times")), "yyyy-MM-dd");
			((TextView)view.findViewById(R.id.violateTime)).setText(time);
			((TextView)view.findViewById(R.id.violateId)).setText(g.optString("WEIZHANG_REQ_ID"));
			((TextView)view.findViewById(R.id.violatePlate)).setText(g.optString("PLATE"));
			((TextView)view.findViewById(R.id.violateArea)).setText(g.optString("SEARCH_CITY"));
			Violate vio = Violate.getViolate(g.optString("YSX_STATE"));
			((TextView)view.findViewById(R.id.violateState)).setText(vio.getDescription());
			((TextView)view.findViewById(R.id.violateYsxState)).setText(vio.getCode());
		
 
		
		return view;
	}
	


}
