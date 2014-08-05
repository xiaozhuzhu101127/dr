package com.topjet.crediblenumber.activity;

import java.io.IOError;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.PingJiaDetailListAdapter;
import com.topjet.crediblenumber.adapter.ViolateReqAdapter;
import com.topjet.crediblenumber.dialog.PingJiaSelectDialog;
import com.topjet.crediblenumber.dialog.SelectDialog;
import com.topjet.crediblenumber.model.Violate;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DC;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.widget.RefreshableView;
import com.topjet.crediblenumber.widget.RefreshableView.PullToRefreshListener;

public class DianPingDetailResultActivity extends BaseListViewActivity
		implements OnClickListener, OnScrollListener {

	private Long USRID = null;
	
	private String DCT_DPUC = null;
	
	private Integer type= 0;

	private PingJiaDetailListAdapter mListAdapter;

	protected ListView mListView;

	private ImageView imgMore = null;

	private boolean append = false;

	private Dialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_dianping_detail_result);
		super.onCreate(savedInstanceState);
		((TextView) findViewById(R.id.tv_title)).setText("评价详情");
		btn_other_function = (Button) findViewById(R.id.btn_other_function);
		btn_other_function.setVisibility(View.VISIBLE);
		USRID = getIntent().getExtras().getLong("USRID");
		type = getIntent().getExtras().getInt("type");
		DCT_DPUC= getIntent().getExtras().getString("DCT_DPUC");
		btn_other_function.setText("全部星级");
		mListAdapter = new PingJiaDetailListAdapter(this);
		mListView = (ListView) findViewById(R.id.listView);
		fetchData(append);
		initFootView(this);
		mListView.addFooterView(footerView);

		mListView.setAdapter(mListAdapter);
		mListView.setOnScrollListener(this);
		
	}

	private void fetchData(boolean append) {

		mLoadData.getDpDetailList(baseHandler,
				DefaultConst.CMD_DP_DETAIL_LIST_RESULT, USRID,type,DCT_DPUC,
				append ? mListAdapter.getCount() : 0);
	}

	private void renderData(JSONArray data) {
		if (append) {
			mListAdapter.appendData(data);

		} else {
			if (data.length() == 0) {
				Common.showToast("暂无数据!", this);
				changeFootViewNoData();
				return;
			}
			mListAdapter.setData(data);
		}
		mListAdapter.notifyDataSetChanged();
		onLoadMoreOver();
		if (!append)
			append = !append;
	}

	@Override
	protected void ioHandler(Message msg) {
		if (msg.what == DefaultConst.CMD_DP_DETAIL_LIST_RESULT) {
			mListAdapter.setLoading(false);
			renderData(((JSONObject) msg.obj).optJSONArray("details"));
		}

	}

	@Override
	protected void doError(Message msg) {
		Common.showToast((String) msg.obj, this);

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
			// / submit();
			break;
		case R.id.score_shop_dialog_cancel:
			mDialog.dismiss();
			break;
		case R.id.score_shop_dialog_dial:
			mDialog.dismiss();
			// Intent it = new Intent(ViolateListAcitivity.this,
			// ViolateCategoryActivity.class);
			// startActivity(it);
			break;
		case R.id.btn_other_function:
			PingJiaSelectDialog selectDialog = new PingJiaSelectDialog(this,
					R.style.dialog,USRID,DCT_DPUC);// 创建Dialog并设置样式主题
			Window win = selectDialog.getWindow();
			LayoutParams params = new LayoutParams();
			// 位置的坐标如果是X=0 Y=0的话 那么弹出位置就是中间，负数的话就是向左，正数就是向右，相反就是向上向下。
			params.x = 250;// 设置x坐标
			params.y = -100;// 设置y坐标
			win.setAttributes(params);
			selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
			selectDialog.show();
			break;
		}

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {

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
