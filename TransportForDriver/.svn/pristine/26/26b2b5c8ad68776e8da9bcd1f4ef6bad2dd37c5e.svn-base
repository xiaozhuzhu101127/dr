package com.topjet.crediblenumber.activity;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.adapter.MessageListAdapter;
import com.topjet.crediblenumber.model.MessageInfo;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.Dict;
import com.topjet.crediblenumber.util.DisplayUtil;

/**
 * 消息推送页
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-24 上午11:22:04  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
public class MessageActivity extends BaseActivity implements OnClickListener {

	ListView mListView;
	private MessageListAdapter mListAdapter;

	Button btn_setting_logout;

	private String tk_length = null;

	private String tk_type = null;

	private String tk_city = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_common_category);
		super.onCreate(savedInstanceState);

		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_title.setText("信息中心");
		btn_other_function = (Button) findViewById(R.id.btn_other_function);
		btn_other_function.setVisibility(View.VISIBLE);
		btn_other_function.setText("全部删除");
		mListAdapter = new MessageListAdapter(this);
		mListView = (ListView) findViewById(R.id.listview_setting_menu);
		mListView.setAdapter(mListAdapter);
		// 取参数
		tk_length = this.getIntent().getStringExtra("tk_length");
		tk_type = this.getIntent().getStringExtra("tk_type");
		tk_city = this.getIntent().getStringExtra("tk_city");
		if("不限".equals(tk_length)){
			tk_length="";
		}
		if("不限".equals(tk_type)){
			tk_type="";
		}
		ArrayList<MessageInfo> list = db.getMessageInfos(tk_length, tk_type,
				tk_city);
		if (list != null && list.size() > 0) {
			mListAdapter.setList(list);
		} else {
			mListAdapter.setList(new ArrayList<MessageInfo>());
		}

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mobile = ((TextView) view
						.findViewById(R.id.item_msg_mobile)).getText()
						.toString();
				if (!Common.isEmpty(mobile)) {
					gotoCredit(mobile);
				}

			}
		});

		btn_setting_logout = (Button) this
				.findViewById(R.id.btn_setting_logout);
		btn_setting_logout.setVisibility(View.VISIBLE);
		btn_setting_logout.setText("货源搜索");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.mLoadData.pushMessage(baseHandler, DefaultConst.CMD_PUSH_MESSAGE);
	}

	@Override
	public void onClick(View v) {
		if (R.id.btn_other_function == v.getId())
			doDelAll();
		else if (R.id.btn_back == v.getId())
			finish();
		else if (R.id.btn_setting_logout == v.getId()) {
			startActivity(new Intent(this, MessageSearchActivity.class));
		
		}
	}

	private void doDelAll() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("删除全部消息记录").setMessage("确定删除?(永久删除)")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						db.clearMessageList();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		if (DefaultConst.CMD_PUSH_MESSAGE == msg.what) {
			doInputMsgToDB(msg);
			mListAdapter.setList(db.getMessageInfos(tk_length, tk_type, tk_city));
		}
	}

	private void doInputMsgToDB(Message msg) {
		// TODO Auto-generated method stub
		JSONArray array = ((JSONObject) msg.obj).optJSONArray("messages");
		if (array.length() == 0) {
			return;
		}
		ArrayList<MessageInfo> msgInfos = new ArrayList<MessageInfo>();
		for (int i = 0; i < array.length(); i++) {
			JSONObject g = array.optJSONObject(i);
			MessageInfo messageInfo = new MessageInfo();
			messageInfo.photo = g.optString("photoaddr");
			messageInfo.name = g.optString("REALNAME");
			messageInfo.credit = g.optString("YSLACCOUNT");
			JSONObject t = g.optJSONObject("CREATE_TIME");
			messageInfo.info = new StringBuilder()
					.append(Dict.getCityName(g.optString("CT_REGOIN"), 2))
					.append(" ")
					.append(DisplayUtil.renderDate(new Date(t.optLong("time")),
							"M-dd HH:mm")).toString();
			messageInfo.content = g.optString("MSG");
			messageInfo.mobile = g.optString("MOBILE");
			msgInfos.add(messageInfo);
		}
		db.addMessage(msgInfos);

	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub

	}

}
