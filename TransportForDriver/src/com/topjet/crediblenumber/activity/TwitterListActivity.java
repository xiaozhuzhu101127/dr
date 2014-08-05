package com.topjet.crediblenumber.activity;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle; 
import android.os.Message; 
import android.view.View;
import android.view.View.OnClickListener; 
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView; 
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.TwitterListAdapter;
import com.topjet.crediblenumber.dialog.SelectDialog;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst; 
import com.topjet.crediblenumber.widget.RefreshableView;
import com.topjet.crediblenumber.widget.RefreshableView.PullToRefreshListener;
 

 /**
  * 
  * <pre>
  * Copyright:	Copyright (c)2009  
  * Company:		杭州龙驹信息科技有限公司
  * Author:		BillWang
  * Create at:	2013-10-24 上午11:23:23  
  *  
  * 修改历史:
  * 日期    作者    版本  修改描述
  * ------------------------------------------------------------------  
  * 
  * </pre>
  */
public class TwitterListActivity 
extends BaseListViewActivity 
implements  
OnClickListener,
OnScrollListener
 { 
	private TwitterListAdapter mListAdapter; 
	private boolean append = false;
	
	private String type;
	
	protected 	Button btn_other_function;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		setContentView(R.layout.activity_twitter_list);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("大货在线");
 
		mListView = (ListView)findViewById(R.id.listView);
		type= this.getIntent().getStringExtra("type");
		
		btn_other_function = (Button) this
				.findViewById(R.id.btn_other_function);
		btn_other_function.setText("其他");
		btn_other_function.setVisibility(View.VISIBLE);
 
		mListAdapter = new TwitterListAdapter(this);
		initFootView(this);
		mListView.addFooterView(footerView);
		mListView.setAdapter(mListAdapter);
		mListView.setOnScrollListener(this);	
	
		mListView.setOnItemClickListener(new OnItemClickListener() {
			
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView text =(TextView)view.findViewById(R.id.item_twitter_mobile);
				String mobile = text.getText().toString();
				if(!Common.isEmpty(mobile.trim())){
					Intent intent = new Intent(TwitterListActivity.this, CreditResultActivity.class);
					intent.putExtra("number", mobile.trim());
					startActivity(intent);
				}
				
				
			}
		});
		
		
		fetchData();
	
	}
 

	private void fetchData(){		 
//		if(!append){
//			this.initProgress("正在获取数据......");
//		}
		mLoadData.getCargoOnline(baseHandler, 
				DefaultConst.CMD_GET_CARGO_ONLINE,type,
				append?mListAdapter.getCount():0);
		
	}

	private void renderData(JSONArray data){
		if(append){
			mListAdapter.appendData(data);			
		}else{
			if(data.length() == 0){
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.twitter_publish:startActivity(new Intent(TwitterListActivity.this, TwitterPublishActivity.class));break;
		case R.id.btn_back:	finish();break;
		case R.id.ll_more:	doMoreLoadData();break;	
		case R.id.btn_other_function:
			SelectDialog selectDialog = new SelectDialog(this,R.style.dialog);//创建Dialog并设置样式主题
			Window win = selectDialog.getWindow();
			LayoutParams params = new LayoutParams();
			//位置的坐标如果是X=0 Y=0的话 那么弹出位置就是中间，负数的话就是向左，正数就是向右，相反就是向上向下。 
			params.x = 250;//设置x坐标
			params.y = -240;//设置y坐标
			win.setAttributes(params);
			selectDialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
			selectDialog.show();
		}		
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == DefaultConst.CMD_GET_CARGO_ONLINE)	renderData(((JSONObject)msg.obj).optJSONArray("cargo"));

	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		clearImgMore();
	}


	@Override
	protected void showData() {
		// TODO Auto-generated method stub
		fetchData();
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
