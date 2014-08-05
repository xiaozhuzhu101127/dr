package com.topjet.crediblenumber.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
 
import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.MyApplication;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.PhoneValidator;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-8-27 下午5:05:01  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class CreditQueryActivity 
extends BaseActivity 
implements OnClickListener {

	static final int PICK_CONTACT_REQUEST = 1;
	static final int PICK_CALL_LOG_REQUEST = 2;

	EditText mInputView;
	AlertDialog mCallLogDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_credit_query);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("物流诚信查询");
		mInputView = (EditText) findViewById(R.id.credit_query_input);		
		//抢好货之后新增
		Bundle extras = savedInstanceState;
		if(null == savedInstanceState)
			extras = getIntent().getExtras();
		if(null != extras && !Common.isEmpty(extras.getString("creditMobile")))
			mInputView.setText(extras.getString("creditMobile"));
			
	}

	private void pickNumberFromContact() {
		Intent intent = new Intent(Intent.ACTION_PICK, 
				ContactsContract.Contacts.CONTENT_URI);
		intent.setType(Phone.CONTENT_TYPE);
		startActivityForResult(intent, PICK_CONTACT_REQUEST);
	}

	private void pickNumberFromHistory() {
		String[] callLogFields = { android.provider.CallLog.Calls._ID, android.provider.CallLog.Calls.NUMBER,
				android.provider.CallLog.Calls.CACHED_NAME /*
															 * im not using the
															 * name but you can
															 */};
		String viaOrder = android.provider.CallLog.Calls.DATE + " DESC";
		String WHERE = android.provider.CallLog.Calls.NUMBER + " >0"; /*
																	 * filter
																	 * out
																	 * private
																	 * /unknown
																	 * numbers
																	 */

		final Cursor callLog_cursor = getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI,
				callLogFields, WHERE, null, viaOrder);

		AlertDialog.Builder callLogDialogBuilder = new AlertDialog.Builder(this);

		android.content.DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int item) {
				callLog_cursor.moveToPosition(item);

				String number = callLog_cursor.getString(callLog_cursor
						.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
				mInputView.setText(number);
				mCallLogDialog.dismiss();
				callLog_cursor.close();
			}
		};
		callLogDialogBuilder.setCursor(callLog_cursor, listener, android.provider.CallLog.Calls.NUMBER);
		callLogDialogBuilder.setTitle("选择一个号码");
		mCallLogDialog = callLogDialogBuilder.create();
		mCallLogDialog.show();
	}

	private void submit() {
		String number = mInputView.getText().toString().trim();
		if(Common.isEmpty(number)){
			Common.showToast("号码不能为空！",this);
			mInputView.requestFocus();
			return;
		}
		if (!PhoneValidator.validatePhoneOrMobile(number) 
			&& !PhoneValidator.validPhone400(number)) {
			Common.showToast("电话或手机号码格式不正确！",this);
			mInputView.requestFocus();
			return;
		}
		Intent intent = new Intent(CreditQueryActivity.this, CreditResultActivity.class);
		intent.putExtra("number", number);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.credit_query_from_contact_btn:pickNumberFromContact();break;
		case R.id.credit_query_from_history_btn:pickNumberFromHistory();break;
		case R.id.credit_query_submit_btn:submit();break;
		case R.id.btn_back:finish();break;
		case R.id.shuizaichawo:
			startActivity( new Intent(CreditQueryActivity.this,ShuizaiChaWoActivity.class));		
			break;
		case R.id.wodekoubei:
			Intent it = new Intent(CreditQueryActivity.this,CreditResultActivity.class);
			it.putExtra("number", MyApplication.getInstance().getShareHelper().getUsername());
			startActivity(it);
			break;
		
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == PICK_CONTACT_REQUEST) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {
				// Get the URI that points to the selected contact
				Uri contactUri = data.getData();
				// We only need the NUMBER column, because there will be only
				// one row in the result
				String[] projection = { Phone.NUMBER };

				// Perform the query on the contact to get the NUMBER column
				// We don't need a selection or sort order (there's only one
				// result for the given URI)
				// CAUTION: The query() method should be called from a separate
				// thread to avoid blocking
				// your app's UI thread. (For simplicity of the sample, this
				// code doesn't do that.)
				// Consider using CursorLoader to perform the query.
				Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
				cursor.moveToFirst();

				// Retrieve the phone number from the NUMBER column
				int column = cursor.getColumnIndex(Phone.NUMBER);
				String number = cursor.getString(column);

				mInputView.setText(number);
			}
		}
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub
		
	}
}
