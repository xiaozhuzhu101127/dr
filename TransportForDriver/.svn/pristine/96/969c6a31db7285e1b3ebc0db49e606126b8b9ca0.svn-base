package com.topjet.crediblenumber.adapter;
 
import com.topjet.crediblenumber.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommonMenuAdapter 
extends ArrayBaseAdapter<String> {

	public CommonMenuAdapter(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub 
		convertView = mContext.getLayoutInflater().inflate(R.layout.setting_menu_items, null);
		TextView tvSettingMenu = (TextView)convertView.findViewById(R.id.tv_menu_name);
		tvSettingMenu.setText((String)getItem(position));
		return convertView;
	}

}
