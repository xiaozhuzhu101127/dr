package com.topjet.crediblenumber.adapter;

import org.json.JSONObject;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.util.Dict;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuyuTongJiAdapter extends CommonJsonAdapter {

	public QuyuTongJiAdapter(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
				 view = context.getLayoutInflater().inflate(
						R.layout.item_usertype_tongji_item, null);
				JSONObject g = (JSONObject) getItem(position);

				((TextView) view.findViewById(R.id.usrType)).setText(Dict.getCityName(g.optString("AREAR_CODE"),1)+"");
				((TextView) view.findViewById(R.id.memberCount)).setText(""+g
						.optInt("MEMBER_NUMBER"));
				((TextView) view.findViewById(R.id.queryCount)).setText(""+g
						.optInt("QUERY_NUMBER"));

				return view;
	}


}
