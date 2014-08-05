package com.topjet.crediblenumber.activity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;

import android.widget.ListView;

import android.widget.TextView;


import com.topjet.crediblenumber.R;

import com.topjet.crediblenumber.adapter.ViolateReqAdapter;
import com.topjet.crediblenumber.adapter.ViolateResultAdapter;

import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.widget.RefreshableView;
import com.topjet.crediblenumber.widget.RefreshableView.PullToRefreshListener;

public class ViolateResultAcitivity extends BaseListViewActivity implements OnClickListener,OnScrollListener{


	private ViolateResultAdapter mListAdapter;
	
	protected ListView mListView; 
	
	private boolean append = false;	
	
	private Long violateId=0L;
	
	private TextView violateCountView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_violate_result);
		super.onCreate(savedInstanceState);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		violateCountView =(TextView)this.findViewById(R.id.violateCount);
		tv_title.setText("查询结果");
		Intent it = super.getIntent();
		violateId=Long.valueOf(it.getStringExtra("violateId"));
		mListAdapter =	new ViolateResultAdapter(this);
		mListView = (ListView)findViewById(R.id.listView);
		fetchData(append);
		initFootView(this);
		mListView.addFooterView(footerView);
		
		mListView.setAdapter(mListAdapter);
		mListView.setOnScrollListener(this);
	
	
	}
	
	
	
	private void fetchData(boolean append){	

 

	
			mLoadData.getWeizhangResultList(baseHandler, DefaultConst.CMD_WEI_ZHANG_LIST_RESULT,violateId, append?mListAdapter.getCount():0);
	}

	
	private void renderData(JSONArray data){
		if(append){
			mListAdapter.appendData(data);
		 
		}else{
			if(data.length() == 0){
				Common.showToast("暂无数据!",this);
				changeFootViewNoData();
				return;
			}
			mListAdapter.setData(data);
			violateCountView.setText("该车牌号有"+data.length()+"条违法信息");
		}
		mListAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		if (!append) append = !append;
	}
	
	
	@Override
	protected void ioHandler(Message msg) {
		if (msg.what == DefaultConst.CMD_WEI_ZHANG_LIST_RESULT
				){
			mListAdapter.setLoading(false);
			renderData(((JSONObject)msg.obj).optJSONArray("weizhang"));
		}

	}

	@Override
	protected void doError(Message msg) {
		Common.showToast((String)msg.obj,this);

	}
	
	@Override
	protected void showData() {
		// TODO Auto-generated method stub
		fetchData(append);
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.btn_back:
			finish();
			break;
		case R.id.complete_submit:
//		/	submit();
			break;
		}
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
