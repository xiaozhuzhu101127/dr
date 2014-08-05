package com.topjet.crediblenumber.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;

public class MemberWealActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_member_weal);
		super.onCreate(savedInstanceState);

		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText("会员福利");

	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_category_mall:
			startActivity(new Intent(MemberWealActivity.this,
					ScoreShopCategoryActivity.class));
			break;
		case R.id.choujiang:

			Intent intent = new Intent(MemberWealActivity.this,
					MemberWealSearchActivity.class);
			intent.putExtra("usrid", 1212);
			intent.putExtra("mobile", "13666615069");
			startActivity(intent);
			break;
		case R.id.btn_back:
			finish();
			break;
		}

	}

}
