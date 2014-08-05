package com.topjet.crediblenumber.activity;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.GoodsListAdapter;
import com.topjet.crediblenumber.model.GoodsParameter;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.DialogUtil;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.Receivable;
import com.topjet.crediblenumber.widget.RefreshableView;
import com.topjet.crediblenumber.widget.RefreshableView.PullToRefreshListener;

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
public class GoodsSearchListActivity extends BaseListViewActivity implements  OnClickListener,OnScrollListener,Receivable {
	GoodsListAdapter goodsAdapter;
	TextView hhcountView;
	TextView countView1;
	TextView countView2;
	HashMap<String, Object> params;
	RefreshableView refreshableView ;
	boolean  auto;
	boolean append = false;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_goods_search_list);
		super.onCreate(savedInstanceState);
		goodsAdapter = new GoodsListAdapter(this);
		initView();
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//详情
				JSONObject g = (JSONObject)goodsAdapter.getItem(position);
				showGoodsSourceDetail(g.optLong("GSID"),g.optString("GSSTS"));
			}
			
		});
		fetchData(append);
		mListView.addFooterView(footerView);
		mListView.setAdapter(goodsAdapter);
		mListView.setOnScrollListener(this);

	}
	@Override
	protected void ioHandler(Message msg) {
		JSONObject jObject = (JSONObject) msg.obj;
		JSONArray data = null;
		try {
			data = jObject.getJSONArray("goodsSourceInfo");
			renderData(data);
		} catch (JSONException e) {
			showToast("暂无数据");
		}
		goodsAdapter.setLoading(false);

	}
	private void initView(){
		initFootView(this);
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view); 
		hhcountView = (TextView) findViewById(R.id.goods_hh_count);
		mListView = (ListView) findViewById(R.id.goods_listView);
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
		
		//下拉更新
		// 不同界面在注册下拉刷新监听器时一定要传入不同的id。这里是0
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {  
            @Override  
            public void onRefresh() { 
            	//不添加数据
            	append = false;
            	fetchData(false);
            	//休眠1秒.不然刷新太快，会出现重复数据
            	try {
            		Thread.sleep(1000);
				} catch (Exception e) {
				}
                refreshableView.finishRefreshing();  
            }  
        }, 0);  
	}
	@Override
	public void receive(int type, Object... datas) {
		if(showing != null){
			showing.dismiss();
		}
		//切换地区，是自动找货。
		if(DialogUtil.isCityDialog(showing) && Dict.BLANK.equals(datas[0])){
			//城市选择不限的时候显示省份
			autoSerach((String)picking.getTag(),(String)picking.getText());
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
		Intent intent = new Intent(this, GoodsDetailActivity.class);
		intent.putExtra("GsId", id);
		intent.putExtra("GSSTS", GSSTS);
		intent.putExtra("auto", auto);
		startActivity(intent);
	}
	
	
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ll_more){
			//获取更多数据
			doMoreLoadData();
		}
	}
	
	@Override
	protected void showData() {
		fetchData(append);
	}

	private void fetchData(boolean append) {
		if(append){
			params.put("offset", goodsAdapter.getCount());
		}else {
			params.put("offset", 0);
		}
		mLoadData.fetchData(baseHandler, DefaultConst.CMD_QHH_FETCHDATA, params);
	}
	private void renderData(JSONArray data){
		countView1.setVisibility(View.VISIBLE);
		countView2.setVisibility(View.VISIBLE);
		if(data.length() == 0){
			//不是点击更多时候，假如没数据，应该显示0条数据。
			if(!isMore)
				hhcountView.setText("0");
			changeFootViewNoData();
			return;
		}
		if(append){
			goodsAdapter.appendData(data);
		}else{
			goodsAdapter.setData(data);
		}
		hhcountView.setText(goodsAdapter.getCount()+"");
		goodsAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		//添加数据
		if (!append) append = !append;
	}
	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == DefaultConst.CMD_ERROR_NET_RETURN_FAILED){
			clearImgMore();		
		}
		super.doError(msg);
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		// TODO：已经停止滚动（
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 滚动到底部
			int viewCount = view.getCount() - 1;
			int viewLastPostion = view.getLastVisiblePosition();
			if (viewLastPostion == (viewCount)) {
				if (viewLastPostion != getLastVisiblePosition) {
					doMoreLoadData();
				}
			} 

		}
	} 
}
