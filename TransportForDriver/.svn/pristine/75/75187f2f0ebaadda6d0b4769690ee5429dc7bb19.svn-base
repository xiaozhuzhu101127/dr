package com.topjet.crediblenumber.activity;

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
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.PhoneValidator;
 
/**
 * 
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技有限公司
 * Author:		BillWang
 * Create at:	2013-10-24 上午11:22:23  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */
public class RecommendActivity 
extends BaseActivity 
implements OnClickListener{

	static final int PICK_CONTACT_REQUEST = 1;

	EditText mInputView;
	EditText mContentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_recommend);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("推荐给朋友");
		mInputView = (EditText) findViewById(R.id.recommend_mobile);
		mContentView = (EditText) findViewById(R.id.recommend_content);
		findViewById(R.id.recommend_from_contact_btn).setOnClickListener(this);
		findViewById(R.id.recommend_send).setOnClickListener(this);
	}

	private void pickNumberFromContact() {
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		intent.setType(Phone.CONTENT_TYPE);
		startActivityForResult(intent, PICK_CONTACT_REQUEST);
	}

	private void sendSMS(){
		String mobile = mInputView.getText().toString().trim().replace(" ", "");
		if(!PhoneValidator.validateMobile(mobile)){
			Common.showToast("手机号码格式不正确！",this);
			mInputView.requestFocus();
			return;
		}
		String content = mContentView.getText().toString().trim();
		if(Common.isEmpty(content)){
			Common.showToast("请输入短信内容！",this);
			mContentView.requestFocus();
			return;
		}
		Uri uri = Uri.parse("smsto:" + mobile); 
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri); 
        smsIntent.putExtra("sms_body", content); 
        startActivity(smsIntent); 
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.recommend_from_contact_btn:
			pickNumberFromContact();
			break;
		case R.id.recommend_send:
			sendSMS();
			break;
		case R.id.btn_back: finish();break;
		
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
