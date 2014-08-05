package com.topjet.crediblenumber.activity;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.PingJiaDetailListAdapter;
import com.topjet.crediblenumber.adapter.QuyuTongJiAdapter;
import com.topjet.crediblenumber.adapter.UserTypeTongJiAdapter;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.DisplayUtil;

public class ShuizaiChaWoActivity extends BaseActivity implements OnClickListener{
	
	private TextView  zongping= null;
	
	private UserTypeTongJiAdapter mListAdapter;
	private QuyuTongJiAdapter  quyuTongJiAdapter;
	private ListView memberList;
	private ListView quyuList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_shui_zai_cha_wo);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("谁在查我");
		btn_other_function = (Button)findViewById(R.id.btn_other_function);
		btn_other_function.setText("提升口碑");
		btn_other_function.setVisibility(View.VISIBLE);
		zongping=(TextView)super.findViewById(R.id.zongping);
		mListAdapter = new UserTypeTongJiAdapter(this);
		quyuTongJiAdapter = new QuyuTongJiAdapter(this);
		memberList = (ListView) findViewById(R.id.memberList);
		quyuList = (ListView) findViewById(R.id.queryList);
		memberList.setAdapter(mListAdapter);
		quyuList.setAdapter(quyuTongJiAdapter);
		fetchData();
	
	
	}
	
	private void fetchData() {

		this.initProgress("加载中......");
		this.mLoadData.getChaKan(baseHandler, DefaultConst.CMD_DP_GET_CHA_KAN_RESULT);
	}


	@Override
	protected void ioHandler(Message msg) {
		if (msg.what == DefaultConst.CMD_DP_GET_CHA_KAN_RESULT) {
			JSONObject data =(JSONObject) msg.obj;
			renderData(data.optJSONArray("mTypeStat"));
			renderData1(data.optJSONArray("aTypeStat"));
			//android:text="至2014年1月2日，共计有来自24个省的1234个会员对您发起34322次查询"
		
			String text="至"+DisplayUtil.renderDate(new Date(), "yyyy年MM月dd日")+", 共计有来自<font color='#C9EF2A'>"
					+data.optInt("provinceTotal")+"个</font>省的<font color='#C9EF2A'>"
					+data.optInt("memberNumber")+"个</font>会员对您发起<font color='#C9EF2A'>"
					+data.optInt("queryNumber")+"次</font>查询";
			zongping.setText(Html.fromHtml(text));
			
		}
		

	}
	
	private void renderData(JSONArray data) {
		
			
			mListAdapter.setData(data);
			mListAdapter.notifyDataSetChanged();
	}
	
	private void renderData1(JSONArray data) {
		
		
		quyuTongJiAdapter.setData(data);
		quyuTongJiAdapter.notifyDataSetChanged();
}


	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_other_function:
			startActivity( new Intent(ShuizaiChaWoActivity.this,TiShengKouBeiActivity.class));	
			break;
		case R.id.btn_back: finish();break;	
		}
	}

}
