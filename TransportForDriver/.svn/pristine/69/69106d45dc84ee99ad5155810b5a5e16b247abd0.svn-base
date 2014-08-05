package com.topjet.crediblenumber.activity;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;

import android.widget.TextView;


import com.topjet.crediblenumber.R;

import com.topjet.crediblenumber.adapter.ViolateReqAdapter;
import com.topjet.crediblenumber.adapter.ViolateResultAdapter;

import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.model.Violate;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.widget.RefreshableView;
import com.topjet.crediblenumber.widget.RefreshableView.PullToRefreshListener;

public class ViolateListAcitivity extends BaseListViewActivity implements OnClickListener,OnScrollListener{


	private ViolateReqAdapter mListAdapter;
	
	protected ListView mListView; 
	
	private ImageView imgMore= null;
	
	private boolean append = false;	
	
	private Dialog mDialog;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_violate_list);
		super.onCreate(savedInstanceState);
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("查询结果");
		mListAdapter =	new ViolateReqAdapter(this);
		mListView = (ListView)findViewById(R.id.listView);
		fetchData(append);
		initFootView(this);
		mListView.addFooterView(footerView);
		
		mListView.setAdapter(mListAdapter);
		mListView.setOnScrollListener(this);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String violateId =((TextView)view.findViewById(R.id.violateId)).getText().toString();
				String state =((TextView)view.findViewById(R.id.violateYsxState)).getText().toString();
				Violate violate = Violate.getViolate(state);
				if(Violate.RESULT.getCode().equals(state)){
					Intent it  = new Intent(ViolateListAcitivity.this,ViolateResultAcitivity.class);
					it.putExtra("violateId", violateId);
					startActivity(it);
				}else{
					if (null == mDialog) {
						mDialog = new Dialog(ViolateListAcitivity.this, R.style.Theme_TransparentNoTitleBar);
						mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						mDialog.setContentView(R.layout.dialog_score_shop);
						
						TextView text1 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_cancel);
						text1.setText("确定");
						text1.setOnClickListener(ViolateListAcitivity.this);
						TextView  text2 = (TextView)mDialog.findViewById(R.id.score_shop_dialog_dial);
						text2.setText("返回");
						text2.setOnClickListener(ViolateListAcitivity.this);
					}
					TextView  diag_view =(TextView) mDialog.findViewById(R.id.score_shop_dialog_msg);					
					diag_view.setText(violate.getDescription2());
					mDialog.show();
				
					
				}
				
			}
		});
		
	}
	
	
	
	private void fetchData(boolean append){	

 

	
			mLoadData.getWeizhangList(baseHandler, DefaultConst.CMD_WEI_ZHANG_LIST, append?mListAdapter.getCount():0);
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
		}
		mListAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		if (!append) append = !append;
	}
	
	
	@Override
	protected void ioHandler(Message msg) {
		if (msg.what == DefaultConst.CMD_WEI_ZHANG_LIST
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
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();			
			Intent it = new Intent(ViolateListAcitivity.this, ViolateCategoryActivity.class);		
			startActivity(it);
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
