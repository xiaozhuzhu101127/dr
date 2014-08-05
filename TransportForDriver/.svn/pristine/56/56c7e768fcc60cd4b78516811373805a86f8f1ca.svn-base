package com.topjet.crediblenumber.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.GoodsMyCallAdapter;
import com.topjet.crediblenumber.util.DefaultConst;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-24 下午4:49:35  
 * Description:已报价好货
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsMyCallActivity extends BaseActivity{
	TextView countView;
	TextView countView1;
	TextView countView2;
	GoodsMyCallAdapter adapter;
	ListView callListView;
	Button cancleButton;
	JSONArray result = null;
	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_my_call);
		super.onCreate(savedInstanceState);
		initView();
		adapter = new GoodsMyCallAdapter(this);
		callListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JSONObject g = (JSONObject)adapter.getItem(position);
				viewDetail(g.optLong("GSID"),g.optString("GSSTS"));
			}
		});
		fetchdata();
	}
	private void initView(){
		goods_footer_ybj.setEnabled(false);
//		goods_footer_ybj.setBackgroundResource(R.drawable.ybj3);
		goods_footer_ybj.setBackgroundResource(R.color.darkblue);
		search_title.setText("已报价");
		countView = (TextView) findViewById(R.id.goods_my_count);
		countView1 = (TextView) findViewById(R.id.goods_my_count_view_1);
		countView2 = (TextView) findViewById(R.id.goods_my_count_view_2);
		cancleButton = (Button) findViewById(R.id.goods_my_cxbj);
		callListView = (ListView) findViewById(R.id.goods_my_listView);
		imageView = (ImageView) findViewById(R.id.goods_my_search);
	}
	private void fetchdata() {
		initProgress("加载中……");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("USRID", shareHelper.getUserId());
		mLoadData.callHistory(baseHandler, DefaultConst.CMD_GOODS_MYCALL, params);
	}
	private void viewDetail(long GsId,String GSSTS) {
		//GsId
		Intent intent = new Intent(this, GoodsDetailActivity.class);
		intent.putExtra("GsId", GsId);
		intent.putExtra("GSSTS", GSSTS);
		intent.putExtra("call", true);
		startActivity(intent);
	}
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		countView1.setVisibility(View.VISIBLE);
		countView2.setVisibility(View.VISIBLE);
		//撤销报价
		if(msg.what == DefaultConst.CMD_GOODS_CANCLEPRICE){
			countView.setText("0");
			cancleButton.setVisibility(View.GONE);
			imageView.setVisibility(View.VISIBLE);
			result = new JSONArray();
			adapter.setData(result);
			adapter.notifyDataSetChanged();
			showToast("撤消全部报价成功");
			return;
		}
		//查看报价历史
		try {
			result = ((JSONObject) msg.obj).getJSONArray("Detail");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(result == null || result.length() == 0){
			countView.setText("0");
			cancleButton.setVisibility(View.GONE);
			imageView.setVisibility(View.VISIBLE);
			return;
		}
		countView.setText(result.length()+"");
		adapter.setData(result);
		callListView.setAdapter(adapter);
	}
	public void click(View v){
		switch (v.getId()) {
		//撤销全部报价
		case R.id.goods_my_cxbj:
			Dialog dialog = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("撤消全部报价").setMessage("您将撤消全部报价，一旦撤消将无法再次对货源进行报价。强烈建议只在线下找到货源时进行撤消。")
			.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					cancleAll();
				}
			}).setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			}).create();
			dialog.show();
			break;
		//智能找货
		case R.id.goods_my_search:
			autoSerach(app.getLocationCode(),app.getLocation());
			break;
		}
	}

	/**
	 *报价/调整报价
	 * @param gsid
	 * @param price
	 */
	public void bid(long gsid,String price,boolean change){
		Intent intent = new Intent(this,GoodsBidActivity.class);
		intent.putExtra("GsId", gsid);
		intent.putExtra("price", price);
		intent.putExtra("change", change);
		startActivity(intent);
	}
	public void cancleAll(){
		//得到所有priceid
		StringBuilder prices = new StringBuilder();
		JSONObject price = null;
		for(int i = 0;i<result.length();i++){
			price = result.optJSONObject(i).optJSONObject("PRICE");
			prices.append(price.optLong("PRICEID"));
			prices.append(",");
		}
		prices = prices.deleteCharAt(prices.length()-1);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("USRID", shareHelper.getUserId());
		params.put("PRICEIDS",prices);
		mLoadData.canclePrice(baseHandler, DefaultConst.CMD_GOODS_CANCLEPRICE, params);
	}
}
