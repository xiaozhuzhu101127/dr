package com.topjet.crediblenumber.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.audividi.imageloading.cache.naming.HashCodeFilesNameGenerator;
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.GoodsHistoryAdapter;
import com.topjet.crediblenumber.util.DefaultConst;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-24 下午4:51:05  
 * Description: 历史成交好货
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsTradeHistoryActivity extends BaseActivity{
	ListView historyView;
	TextView countView;
	TextView countView1;
	TextView countView2;
	GoodsHistoryAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_trade_history);
		super.onCreate(savedInstanceState);
		initView();
		adapter = new GoodsHistoryAdapter(this);
		fetchData();
	}
	private void fetchData() {
		initProgress("加载中……");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("USRID", shareHelper.getUserId());
		mLoadData.tradeHistory(baseHandler, DefaultConst.CMD_GOODS_HISTORY, params);
	}
	private void initView(){
		search_title.setText("历史成交");
		goods_footer_history.setEnabled(false);
//		goods_footer_history.setBackgroundResource(R.drawable.rec3);
		goods_footer_history.setBackgroundResource(R.color.darkblue);
		historyView = (ListView) findViewById(R.id.goods_history_list);
		countView = (TextView) findViewById(R.id.goods_history_count);
		countView1 = (TextView) findViewById(R.id.goods_history_count_view_1);
		countView2 = (TextView) findViewById(R.id.goods_history_count_view_2);
		historyView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JSONObject g = (JSONObject)adapter.getItem(position);
				Intent intent = new Intent(GoodsTradeHistoryActivity.this, GoodsMyDetailActivtiy.class);
				intent.putExtra("GSID",g.optLong("GSID"));
				intent.putExtra("history", true);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		JSONArray data = null;
		try {
			data = ((JSONObject) msg.obj).getJSONArray("Detail");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		countView.setText(data.length()+"");
		countView1.setVisibility(View.VISIBLE);
		countView2.setVisibility(View.VISIBLE);
		adapter.setData(data);
		historyView.setAdapter(adapter);
	}

}
