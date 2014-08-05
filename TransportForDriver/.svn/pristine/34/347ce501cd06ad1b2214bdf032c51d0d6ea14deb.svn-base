package com.topjet.crediblenumber.activity;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.GoodsListAdapter;
import com.topjet.crediblenumber.model.GoodsParameter;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.DialogUtil;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.Receivable;

/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-20 下午4:27:56  
 * Description:查询出的好货
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class GoodsSearchListActivity1 extends BaseActivity implements Receivable{
	ListView goods_listView;
	GoodsListAdapter goodsAdapter;
	TextView hhcountView;
	TextView countView1;
	TextView countView2;
	HashMap<String, Object> params;
	boolean  auto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_search_list);
		super.onCreate(savedInstanceState);
		goodsAdapter = new GoodsListAdapter(this);
		initView();
		goods_listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//没有验证，到验证页面
				if(!Common.checkDct_ut()){
					Intent intent = new Intent(context, CompleteActivity.class);
					intent.putExtra("identity", Identity.Driver);
					startActivity(intent);
					return;
				}
				//详情
				JSONObject g = (JSONObject)goodsAdapter.getItem(position);
				showGoodsSourceDetail(g.optLong("GSID"),g.optString("GSSTS"));
			}
			
		});
		mLoadData.fetchData(baseHandler, DefaultConst.CMD_QHH_FETCHDATA, params);
	}
	@Override
	protected void ioHandler(Message msg) {
		hindProgress();
		JSONObject jObject = (JSONObject) msg.obj;
		JSONArray data;
		try {
			data = jObject.getJSONArray("goodsSourceInfo");
			if(data.length() == 0){
				hhcountView.setText("0");
				showToast("暂无数据");
			}else {
				hhcountView.setText(data.length()+"");
				goodsAdapter.setData(data);
				goods_listView.setAdapter(goodsAdapter);
			}
		} catch (JSONException e) {
			showToast("暂无数据");
		}
		countView1.setVisibility(View.VISIBLE);
		countView2.setVisibility(View.VISIBLE);
	}
	private void initView(){
		initProgress("加载中……");
		hhcountView = (TextView) findViewById(R.id.goods_hh_count);
		goods_listView = (ListView) findViewById(R.id.goods_listView);
		countView1 = (TextView) findViewById(R.id.goods_hh_count_view_1);
		countView2 = (TextView) findViewById(R.id.goods_hh_count_view_2);
		Bundle extras  = getIntent().getExtras();
		params= (HashMap<String, Object>) extras.get("params");
		if(null == params){
			params = new HashMap<String, Object>();
		}
		//从登录的时候和切换右上角地区的时候用到
		if(null != extras.getString("Depart"))
			params.put("Depart", extras.getString("Depart"));
		params.put("USRID", shareHelper.getUserId());
		auto = extras.getBoolean("auto");
		//保存信息。报完价之后使用
		GoodsParameter parameter = new GoodsParameter();
		parameter.auto = auto;
		parameter.params = params;
		app.setGoodsParameter(parameter);
		//要切换地区
		search_recommend.setVisibility(View.GONE);
		search_place.setVisibility(View.VISIBLE);
		Bundle extra = getIntent().getExtras();
		if(null == extra || Common.isEmpty(extra.getString("city"))){
			search_place.setText(app.getLocation());
		}else {
			search_place.setText(extra.getString("city"));
		}
		//手动找货or智能
		if(auto){
			search_title.setText("智能找货");
			params.put("queryType", "0");
			goods_footer_auto.setEnabled(false);
//			goods_footer_auto.setBackgroundResource(R.drawable.iser3);
			goods_footer_auto.setBackgroundResource(R.color.darkblue);
		}else{
			params.put("queryType", "1");
			goods_footer_search.setEnabled(false);
//			goods_footer_search.setBackgroundResource(R.drawable.ser3);
			goods_footer_search.setBackgroundResource(R.color.darkblue);
		}
	}
	@Override
	public void receive(int type, Object... datas) {
		if(showing != null){
			showing.dismiss();
		}
		//切换地区，是自动找货。
		if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
			//城市选择不限的时候显示省份
			return;
		}
		picking.setText((String)datas[1]);
		picking.setTag(datas[0]);
		//是省份的时候，继续展示城市
		if(DialogUtil.isProvinceDialog(showing) && !Dict.BLANK.equals((String)datas[0])){
			showing = DialogUtil.showCityDialog(this, (String)datas[0]);
			return;
		}
		autoSerach((String)picking.getTag(),(String)picking.getText());
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
	/**
	 * 查看货源详情
	 * @param id
	 */
	private void showGoodsSourceDetail(long id,String GSSTS){
		Intent intent = new Intent(GoodsSearchListActivity1.this, GoodsDetailActivity.class);
		intent.putExtra("GsId", id);
		intent.putExtra("GSSTS", GSSTS);
		intent.putExtra("auto", auto);
		startActivity(intent);
	}
}
