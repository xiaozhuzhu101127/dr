package com.topjet.crediblenumber;
 
import java.util.HashSet;
import java.util.Set;

import com.topjet.crediblenumber.activity.CompleteActivity;
import com.topjet.crediblenumber.activity.CreditResultActivity;
import com.topjet.crediblenumber.activity.GoodsMyDetailActivtiy;
import com.topjet.crediblenumber.activity.GoodsSearchListActivity;
import com.topjet.crediblenumber.activity.GoodsUnavailableActivtiy;
import com.topjet.crediblenumber.activity.MainActivity;
import com.topjet.crediblenumber.activity.GoodsMyCallActivity;
import com.topjet.crediblenumber.activity.GoodsTradeHistoryActivity;
import com.topjet.crediblenumber.activity.GoodsSearchActivity;
import com.topjet.crediblenumber.activity.RecommendActivity;
import com.topjet.crediblenumber.core.LoadData;
import com.topjet.crediblenumber.db.Database;
import com.topjet.crediblenumber.model.Identity;
import com.topjet.crediblenumber.util.CloseAble;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.DialogUtil;
import com.topjet.crediblenumber.util.MyDialog;
import com.topjet.crediblenumber.util.ScreenManager;
import com.topjet.crediblenumber.util.ShareHelper;
import com.topjet.crediblenumber.util.StringUtils;
import com.topjet.crediblenumber.util.TransportLog;
 
 

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity  extends Activity implements CloseAble{
	protected   ShareHelper shareHelper;
	protected   Database db;
	protected   InputMethodManager imm;  
	protected   MyApplication app;	
	protected   ProgressDialog mDialog;
	protected 	boolean  isDestroy = false;
	protected 	TextView tv_title;
	protected 	Button btn_other_function;
	protected   ScreenManager mScreenManager;
	public static Context context;
	protected Toast mToast;
	protected Button search_back;
	protected Button search_recommend;
	protected TextView search_title;
	protected TextView search_place;
	protected ImageButton goods_footer_home;
	protected ImageButton goods_footer_ybj;
	protected ImageButton goods_footer_history;
	protected ImageButton goods_footer_auto;
	protected ImageButton goods_footer_search;
	/**
	 * 弹出框的触发表单
	 */
	protected TextView picking;
	/**
	 * 正在显示的弹框
	 */
	protected MyDialog showing;
	private static Set<Activity> COLSEABLE = new HashSet<Activity>();
	@SuppressLint("HandlerLeak")
	protected   Handler baseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			hindProgress();
			switch (msg.what) {		
			case DefaultConst.CMD_LOGIN:	
			case DefaultConst.CMD_APK_UPDATE:	
			case DefaultConst.CMD_LOGIN_OUT:	
			case DefaultConst.CMD_UP_PHONELIST:	
			case DefaultConst.CMD_MOBILE_RESET_PWD:				
			case DefaultConst.CMD_TRUCK_STATUS:
			case DefaultConst.CMD_UP_RECOMMEND:
			case DefaultConst.CMD_GET_MEMBER_ACCOUNT_STATE:
			case DefaultConst.CMD_REPORT_CONTACT_INFO_ERR:
			case DefaultConst.CMD_ADD_CARGO_ONLINE:
			case DefaultConst.CMD_GET_PERSON_IDCARD:
			case DefaultConst.CMD_GET_LOCATION_REQ:
			case DefaultConst.CMD_UPDATE_LOCATION_REQ:
			case DefaultConst.CMD_PUSH_MESSAGE:
			case DefaultConst.CMD_GET_MEMBER_Mobile:
			case DefaultConst.CMD_GET_SST_Mobile:
			case DefaultConst.CMD_REPAIR_MARKET:
			case DefaultConst.CMD_GET_STATIONS:
			case DefaultConst.CMD_TK_OFFICE:
			case DefaultConst.CMD_GET_SITES:
			case DefaultConst.CMD_GET_CARGO_ONLINE:
			case DefaultConst.CMD_GET_WEATHER:		
			case DefaultConst.CMD_GET_MOBILE_MEMBER_INFO_BY_ID:
			case DefaultConst.CMD_UP_MOBILE_MEMBER_INFO:	
			case DefaultConst.CMD_UPDATE_PASSWORD:
			case DefaultConst.CMD_WEI_ZHANG:
			case DefaultConst.CMD_WEI_ZHANG_LIST:
			case DefaultConst.CMD_WEI_ZHANG_LIST_RESULT:
			case DefaultConst.CMD_DP_INFO_QUERY_RESULT:
			case DefaultConst.CMD_DP_INFO_ADD_RESULT:
			case DefaultConst.CMD_DP_DETAIL_LIST_RESULT:
			case DefaultConst.CMD_DP_GET_CHA_KAN_RESULT:
			case DefaultConst.CMD_QHH_FETCHDATA:
			case DefaultConst.CMD_GOODS_DETAIL:
			case DefaultConst.CMD_GOODS_CALLPRICE:
			case DefaultConst.CMD_GOODS_CANCLEPRICE:
			case DefaultConst.CMD_GOODS_DEAL_DETAIL:
			case DefaultConst.CMD_GOODS_MYCALL:
			case DefaultConst.CMD_GOODS_COUNTDOWNTIME:
			case DefaultConst.CMD_GOODS_HISTORY:
				ioHandler(msg);			
				break;
			case DefaultConst.CMD_ERROR_NET_ADD_REQUEST:			 
			case DefaultConst.CMD_ERROR_NET_REQUEST_ERROR:				 
			case DefaultConst.CMD_ERROR_NET_RETURN_FAILED:	
			//不在交易期
			case DefaultConst.CMD_GOODS_UNAVAILABLE:
				doError(msg);
				break;
			case DefaultConst.CMD_CMSS_NET_ADD_REQUEST:
				doCmssRequest(msg);
				break;
			}
		}
	};
	protected LoadData mLoadData;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		super.onCreate(savedInstanceState); 
		COLSEABLE.add(this);
		app  = MyApplication.getInstance();
		mLoadData = app.getLoadData(); 
		db = app.getDb();
		shareHelper = app.getShareHelper() ;		
		mScreenManager = app.getScreenManager();
		mScreenManager.pushActivity(this);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		context = this;
		//抢好货的头和尾
		if(app.getGoodsList().contains(context.getClass()))
			setMenu();
	}
	protected void setMenu(){
		BtnOnClickListener btnOnClickListener = new BtnOnClickListener(); 
		search_back = (Button) findViewById(R.id.search_back);
		search_recommend = (Button) findViewById(R.id.search_recommend);
		search_place = (TextView) findViewById(R.id.search_place);
		search_place.setText(app.getLocation());
		search_place.setOnClickListener(btnOnClickListener);
		search_back.setOnClickListener(btnOnClickListener);
		search_recommend.setOnClickListener(btnOnClickListener);
		goods_footer_search = (ImageButton) findViewById(R.id.goods_footer_search);
		search_title = (TextView) findViewById(R.id.search_title);
		goods_footer_home = (ImageButton) findViewById(R.id.goods_footer_home);
		goods_footer_ybj = (ImageButton) findViewById(R.id.goods_footer_ybj);
		goods_footer_history = (ImageButton) findViewById(R.id.goods_footer_history);
		goods_footer_auto = (ImageButton) findViewById(R.id.goods_footer_auto);
		goods_footer_search.setOnClickListener(btnOnClickListener);
		goods_footer_home.setOnClickListener(btnOnClickListener);
		goods_footer_ybj.setOnClickListener(btnOnClickListener);
		goods_footer_history.setOnClickListener(btnOnClickListener);
		goods_footer_auto.setOnClickListener(btnOnClickListener);
	}
	public class  BtnOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			//返回
			case R.id.search_back:
				finish();
				break;
			//推荐给朋友
			case R.id.search_recommend:
				startActivity(new Intent(context, RecommendActivity.class));
				break;
			//切换地址找货
			case R.id.search_place:
				picking = search_place;
				popProvincePicker();
				break;
			//主页
			case R.id.goods_footer_home:
				startActivity(new Intent(context, MainActivity.class));
				break;
			//已报价
			case R.id.goods_footer_ybj:
				startActivity(new Intent(context, GoodsMyCallActivity.class));
				break;
			//历史成交
			case R.id.goods_footer_history:
				startActivity(new Intent(context, GoodsTradeHistoryActivity.class));
				break;
			//智能找货
			case R.id.goods_footer_auto:
				autoSerach(app.getLocationCode(),app.getLocation());
				break;
			//找货
			case R.id.goods_footer_search:
				goodsSerach(app.getLocation());
				break;
			}
		}

	}
	private void popProvincePicker(){
		showing = DialogUtil.showProvinceDialog(this);
	}
	
	protected  void autoSerach(String depart,String city) {
		Intent intent = new Intent(this, GoodsSearchListActivity.class);
		intent.putExtra("Depart", depart);
		intent.putExtra("auto", true);
		intent.putExtra("city", city);
		startActivity(intent);
	}
	protected  void goodsSerach(String city) {
		Intent intent = new Intent(this, GoodsSearchActivity.class);
		startActivity(intent);
	}
	public void hideKeyboard(View view) {
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public   void showKeyboard(View view) {
		imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
	}
	
	
 
	@Override   
	public void onBackPressed() {   
	       super.onBackPressed();   
	     
	  }  
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mScreenManager)   mScreenManager.popActivity(this);
	    TransportLog.i("popActivity", "执行退出==");
		if (!isDestroy){
	    	return;	    
	    }
		app.setSessionId("");
		app.cancelAlarm();
		if (null != mLoadData) mLoadData.stop();
		if (null != db) db.closeDB();
		shareHelper.setLogin(false);
		//貌似只能关闭当前页面
//	    android.os.Process.killProcess(android.os.Process.myPid());
		//使用任务管理器关闭自己的应用
//		ActivityManager activityMgr= (ActivityManager) context.getSystemService(ACTIVITY_SERVICE );
//		activityMgr.restartPackage(getPackageName()); 
		//或者像抢好货一样，关闭所有activity（一个set）
		closeAll();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void finish() {
		//货源列表返回首页
		if(this.getClass().equals(GoodsSearchListActivity.class) && shareHelper.isLogin()){
			startActivity(new Intent(this, MainActivity.class));
			return;
		}
		super.finish(); 
	}
	
	protected void initProgress(int res) {
		// TODO Auto-generated method stub
		if (null ==mDialog ){
			mDialog = new ProgressDialog(this);
			mDialog.setMessage(	getString(res));
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(true);
		}
		mDialog.show();
	}
	/**
	 * 公用组件：进度条
	 */
	protected void initProgress(String  msg) {
		// TODO Auto-generated method stub	 
		if (null ==mDialog ){
			mDialog = new ProgressDialog(this);
			mDialog.setMessage(	msg);
			mDialog.setCanceledOnTouchOutside(false);
			mDialog.setCancelable(true);
		}
		mDialog.show();
	}
	/**
	 * 公用组件：关闭进度条
	 */
	protected void hindProgress(){
		if (null !=mDialog )	mDialog.dismiss();		
	}
	
	/**
	 * 抽象方法，给各个activity实现，去做自己的操作
	 * @param msg
	 */
	protected abstract void ioHandler(Message msg) ; 
	/**
	 * //处理异常
	 * @param msg 
	 */
	protected  void doError(Message msg){
		String info = (String)msg.obj;
		if(Common.isEmpty(info))
			return;
		//不在交易期
		if(info.equals(StringUtils.OUTOFTRADE)){
			//在抢好货模块，才会跳到不在交易期页面
			if(app.getGoodsList().contains(context.getClass())){
				startActivity(new Intent(this, GoodsUnavailableActivtiy.class));
			}
			return;
		}
		//check fail
		if(info.equals(StringUtils.CHECK_NOT_TW) || info.equals(StringUtils.CHEAK_NOT_PS)){
			//司机，提交过行驶证照片，直接到首页
			if("UT_TW".equals(shareHelper.getDct_ut()) && shareHelper.isDriverPic()){
				showToast(StringUtils.WAITCHECK);
				startActivity(new Intent(context,MainActivity.class));
				return;
			}
			showToast(info);
			//没提交到行驶证照片完善资料页面
			Intent intent = new Intent(context, CompleteActivity.class);
			intent.putExtra("identity", Identity.getIdentity(shareHelper.getDct_ut()));
			startActivity(intent);
			return;
		}
		//普通异常
		showToast(info);
	}; 

	protected  void doCmssRequest(Message msg){
		Uri uri = Uri.parse((String)msg.obj);
		Intent it = new Intent();
		it.setAction(Intent.ACTION_VIEW);
		it.setData(uri);
		context.startActivity(it);

	}; 
	
	/**
	 * 公用组件： Toast
	 * @param message
	 */
	public void showToast(final String message){
		//runOnUiThread是activity的方法，内部是handler。如果当前线程不是ui线程则加入队列，是的话，则会立即执行
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(null == mToast){
					mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
//					mToast.setGravity(Gravity.CENTER, 0, 0);
//					mToast.setView(mToast.getView());
				}
				mToast.setText(message);
				mToast.show();
			}
		});
	}
	
	/**
	 * 跳转到诚信详情页
	 * @param number
	 */
	protected void gotoCredit(String number){
		Intent intent = new Intent(this, CreditResultActivity.class);
		intent.putExtra("number", number);
		startActivity(intent);
	}
	
	protected void gotoMain(){
		mScreenManager.popAllActivityExceptOne(MainActivity.class); 
	}
	/**
	 * 执行退出操作
	 */
	protected void quit(){
		//退出登录
//		logout();
		//关闭所有activity
//		closeAll();
		MyApplication.getInstance().onTerminate();
	}
	private void closeAll(){
		for(Activity close : COLSEABLE){
			((CloseAble) close).close();
		}
		COLSEABLE.clear();
	}
	@Override
	public void close() {
		finish();
	}
	public static void showTradeDialog(final Long gsid, String info){
		if(context.getClass().getName().equals(GoodsMyDetailActivtiy.class.getName())){
			//已经在成交订单页面，不再弹窗
			return;
		}
		Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_trade);
		TextView infoView = (TextView) dialog.findViewById(R.id.goods_trade_info);
		infoView.setText(info);
		Button button = (Button) dialog.findViewById(R.id.goods_trade);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShareHelper.getInstance(context).setCheckDeal(true);
				Intent intent = new Intent(context, GoodsMyDetailActivtiy.class);
				intent.putExtra("GSID", gsid);
				context.startActivity(intent);
				((BaseActivity) context).finish();
			}
		});
		dialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.cancel();
			}
		});
		dialog.show();
	}
}
