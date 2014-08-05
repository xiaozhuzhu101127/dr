package com.topjet.crediblenumber.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.MyApplication;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.AppConifg;
import com.topjet.crediblenumber.util.TransportLog;

public class TiShengKouBeiActivity extends BaseActivity implements OnClickListener{
	
	
	

	WebView mWebView;
	String mNumber;
	ProgressDialog mProgressDialog;
	Button btn_other_function;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_ti_sheng_kou_bei);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("提升口碑");
		btn_other_function = (Button)findViewById(R.id.btn_other_function);
		btn_other_function.setText("如何赢得好口碑");
		btn_other_function.setVisibility(View.VISIBLE);
		mWebView = (WebView) findViewById(R.id.credit_webview);
		WebSettings websettings = mWebView.getSettings();
		websettings.setSupportZoom(true);
		websettings.setBuiltInZoomControls(true);
		websettings.setJavaScriptEnabled(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//不加上，会显示白边
		
		mProgressDialog = ProgressDialog.show(this, "", "正在加载，请稍候...", true);
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("tel:")) {
					//网页链接拨电话
					if(url.endsWith("4000566560")){
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
						startActivity(intent);
					}
				} else if (url.startsWith("http:") || url.startsWith("https:")) {
					view.loadUrl(url);
				}
				return true;
			}

			public void onPageFinished(WebView view, String url) {
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			}
		});
		TransportLog.i("URL", AppConifg.WEB_ADDRESS );
		String url=AppConifg.WEB_KOUBEI;			
		mWebView.loadUrl(url);
	}

	

	@Override
	public void onClick(View v) {
		switch(v.getId()){	
		case R.id.btn_back:finish();break;
		case R.id.btn_other_function:
			startActivity( new Intent(TiShengKouBeiActivity.this,HaoKouBeiActivity.class));	
			break;
		
		}	 
	}

	@Override
	public void onBackPressed() {
		mWebView.stopLoading();
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		super.onBackPressed();
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
