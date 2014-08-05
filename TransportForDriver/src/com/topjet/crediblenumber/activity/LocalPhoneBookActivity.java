package com.topjet.crediblenumber.activity;
 
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.topjet.crediblenumber.R; 
import com.topjet.crediblenumber.adapter.LocalPhoneBookListAdapter;
import com.topjet.crediblenumber.adapter.RichLocalPhoneBookListAdapter;
import com.topjet.crediblenumber.adapter.SimpleLocalPhoneBookListAdapter;  
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst; 
import com.topjet.crediblenumber.widget.RefreshableView;
import com.topjet.crediblenumber.widget.RefreshableView.PullToRefreshListener;
 

/**  
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-28 上午9:44:07  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */

public class LocalPhoneBookActivity
extends BaseListViewActivity 
implements  
OnClickListener,
OnScrollListener {
	private String mCategory; 
	private LocalPhoneBookListAdapter mListAdapter;
	private Button btn_other_function;
	private boolean append = false;	
	private RefreshableView refreshableView ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		setContentView(R.layout.activity_local_phone_book);
		super.onCreate(savedInstanceState);
		mCategory = getIntent().getExtras().getString("category");
		fetchData(append);
		//显示常驻地
		btn_other_function = (Button)findViewById(R.id.btn_other_function);
		btn_other_function.setText(app.getLocation());
	    if ( !Common.isEmpty(app.getLocation())) 
	    	btn_other_function.setVisibility(View.VISIBLE);
	  
		//显示标题
		((TextView)findViewById(R.id.tv_title)).setText(mCategory);
		//列表
		if("配货部、信息部".equals(mCategory)
			||"560服务站".equals(mCategory)
			)		mListAdapter = new RichLocalPhoneBookListAdapter(this);
		else mListAdapter =	new SimpleLocalPhoneBookListAdapter(this);
		
		mListView = (ListView)findViewById(R.id.listView);
		initFootView(this);
		mListView.addFooterView(footerView);
		
		mListView.setAdapter(mListAdapter);
		mListView.setOnScrollListener(this);
		
		
		//货运部、物流公司、服务站可点
		if("配货部、信息部".equals(mCategory)  
			|| "560服务站".equals(mCategory)){
			mListView.setOnItemClickListener(new OnItemClickListener(){				
				@Override
				public void onItemClick(AdapterView<?> parentView, View view, int position, long id) {
					JSONObject g = (JSONObject)mListAdapter.getItem(position);
					String mobile1 = g.optString("mobile1");
					String tel1 = g.optString("tel1");
					String tel = g.optString("tel");
					String phone = Common.isEmpty(mobile1)? (Common.isEmpty(tel1)?tel1:tel):mobile1;
					Intent intent = new Intent(LocalPhoneBookActivity.this, CreditResultActivity.class);
					intent.putExtra("number", phone);
					startActivity(intent);
				}
				
			});
		}		
		//下拉更新
		refreshableView = (RefreshableView) findViewById(R.id.refreshable_view); 
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
        }, 1);  
	} 
	
	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_back) finish();
		else if (v.getId() == R.id.ll_more) doMoreLoadData();
	}


	private void fetchData(boolean append){	
//		if(!append){
//			this.initProgress("正在获取数据，请稍后......");			
//		}
 
		if ("配货部、信息部".equals(mCategory))
		mLoadData.getMemberByMobile(baseHandler,
				DefaultConst.CMD_GET_MEMBER_Mobile, "UT_GW", append?mListAdapter.getCount():0);
		else if ("物流市场".equals(mCategory))
		mLoadData.getSites(baseHandler,
					DefaultConst.CMD_GET_SITES, "market", append?mListAdapter.getCount():0);
		else if ("560服务站".equals(mCategory))
			mLoadData.getSstByMobile(baseHandler, DefaultConst.CMD_GET_SST_Mobile, append?mListAdapter.getCount():0);
		else if ("汽修厂".equals(mCategory))
			mLoadData.getRepairMarket(baseHandler, DefaultConst.CMD_REPAIR_MARKET, append?mListAdapter.getCount():0);
		else if ("加油站".equals(mCategory))
			mLoadData.getStations(baseHandler, DefaultConst.CMD_GET_STATIONS, append?mListAdapter.getCount():0);	 
		else if ("车管所".equals(mCategory))
			mLoadData.getTkOffice(baseHandler, DefaultConst.CMD_TK_OFFICE, append?mListAdapter.getCount():0);
	}

	private void renderData(JSONArray data){
		if(append){
			mListAdapter.appendData(data);
		 
		}else{
			if(data.length() == 0){
				changeFootViewNoData();
				Common.showToast("暂无数据!",this);
				return;
			}
			mListAdapter.setData(data);
		}
		mListAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		if (!append) append = !append;
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == DefaultConst.CMD_GET_MEMBER_Mobile||
				msg.what == DefaultConst.CMD_GET_SITES	||
				msg.what == DefaultConst.CMD_GET_SST_Mobile ||
				msg.what == DefaultConst.CMD_REPAIR_MARKET ||
				msg.what == DefaultConst.CMD_GET_STATIONS || 
				msg.what == DefaultConst.CMD_TK_OFFICE){
			mListAdapter.setLoading(false);
			renderData(((JSONObject)msg.obj).optJSONArray("site"));
		}
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
	protected void showData() {
		// TODO Auto-generated method stub
		fetchData(append);
	}




	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
			 
		  }

		// 已经停止滚动
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 滚动到底部
			int viewCount = view.getCount() - 1;
			int viewLastPostion = view.getLastVisiblePosition();
			if (viewLastPostion == (viewCount)) {
				if (viewLastPostion != getLastVisiblePosition) {
					doMoreLoadData();
					getLastVisiblePosition = viewLastPostion;
				}
			} else if (view.getFirstVisiblePosition() == 0) {
				 
			}
			getLastVisiblePosition = 0;
		}
	}

 
}
