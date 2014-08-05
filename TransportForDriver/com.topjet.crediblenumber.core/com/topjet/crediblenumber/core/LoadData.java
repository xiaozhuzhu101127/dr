package com.topjet.crediblenumber.core;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.os.Message;

import com.topjet.crediblenumber.MyApplication;
import com.topjet.crediblenumber.model.UserInfo;
import com.topjet.crediblenumber.net.HttpHelp;
import com.topjet.crediblenumber.net.JSONRequest;
import com.topjet.crediblenumber.task.HttpTask;
import com.topjet.crediblenumber.task.HttpTask.RequestType;
import com.topjet.crediblenumber.task.ReadContactTask;
import com.topjet.crediblenumber.task.WeatherTask;
import com.topjet.crediblenumber.util.AppConifg;
import com.topjet.crediblenumber.util.Common;
import com.topjet.crediblenumber.util.DC;
import com.topjet.crediblenumber.util.DefaultConst;
import com.topjet.crediblenumber.util.TransportLog;

public class LoadData {
	private final static String TAG = LoadData.class.getName();
	private ExecutorService loadingExecutor;
	private int threadPollSize = 3;
	private ThreadFactory loadDataThreadFactory;
	private volatile static LoadData instance;
	public static final int DEFAULT_THREAD_PRIORITY = Thread.NORM_PRIORITY - 1;
	private HttpHelp httpHelp;

	public static LoadData getInstance() {
		if (instance == null) {
			synchronized (LoadData.class) {
				if (instance == null) {
					instance = new LoadData();
				}
			}
		}
		return instance;
	}

	protected LoadData() {

		loadDataThreadFactory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setPriority(DEFAULT_THREAD_PRIORITY);
				return t;
			}
		};

		httpHelp = HttpHelp.getInstance();
	}

	private void initExecutorsIfNeed() {
		if (loadingExecutor == null || loadingExecutor.isShutdown()) {
			loadingExecutor = createExecutor();
		}
	}

	private ExecutorService createExecutor() {
		BlockingQueue<Runnable> taskQueue = new LIFOLinkedBlockingDeque<Runnable>();
		return new ThreadPoolExecutor(threadPollSize, threadPollSize, 0L,
				TimeUnit.MILLISECONDS, taskQueue, loadDataThreadFactory);
	}

	/**
	 * 用户登录
	 * 
	 * @param handler
	 * @param cmd
	 * @param loginName
	 * @param pwd
	 */
	public void login(Handler handler, int cmd, String loginName, String pwd) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", loginName);
		params.put("pwd", pwd);
		String req = new JSONRequest("user", "login", params).toString();
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		TransportLog.i(TAG, req);
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 密码修改
	 * 
	 * @param handler
	 * @param cmd
	 * @param pwd
	 */
	public void updatePassword(Handler handler, int cmd, String pwd) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pwd", pwd);
		String req = new JSONRequest("user", "updatePassword", params)
				.toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);

	}

	/**
	 * 用户退出
	 * 
	 * @param handler
	 * @param cmd
	 * @param userid
	 */
	public void loginOut(Handler handler, int cmd, String userid) {
		String req = new JSONRequest("user", "exit", Collections.singletonMap(
				"userid", userid)).toString();
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		TransportLog.i(TAG, req);
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * apk更新
	 * 
	 * @param handler
	 * @param cmd
	 * @param version
	 */
	public void apkUpdate(Handler handler, int cmd, String version) {
		String req = new JSONRequest("user", "version",
				Collections.singletonMap("version", version)).toString();
		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 通讯录算选
	 * 
	 * @param handler
	 * @param cmd
	 * @param number
	 * @param phone
	 */
	public void upPhoneList(final Handler handler, final int cmd) {

		ReadContactTask readContactTask = new ReadContactTask(
				new com.topjet.crediblenumber.task.ReadContactTask.IReadListener() {

					@Override
					public void readStart() {
						// TODO Auto-generated method stub

					}

					@Override
					public void readFinish(String names, String number) {
						// TODO Auto-generated method stub
						if ("".equals(names) || "".equals(number)) {
							Message msg = new Message();
							msg.what = DefaultConst.CMD_ERROR_NET_ADD_REQUEST;
							msg.obj = "没有任务联系人";
							handler.sendMessage(msg);
							return;
						}

						Map<String, Object> params = new HashMap<String, Object>();
						params.put("names", names);
						params.put("phone", number);
						params.put("iemi", Common.getSerialNumber());
						params.put("version", Common.getVersionName());
						params.put("imsi", Common.getImsi());
						String req = new JSONRequest("user", "phoneList",
								params).toString();
						TransportLog.i(TAG, req);
						byte[] requestdata = httpHelp
								.sendPostRequest(Collections.singletonMap("p",
										req));
						HttpTask httpTask = new HttpTask(handler, cmd);
						httpTask.initData(RequestType.POST,
								AppConifg.SERVER_ADDRESS, requestdata, -1);
						loadingExecutor.submit(httpTask);
					}

					@Override
					public void readFailed() {
						// TODO Auto-generated method stub
						Message msg = new Message();
						msg.what = DefaultConst.CMD_ERROR_NET_ADD_REQUEST;
						msg.obj = "读取联系人列表出错！";
						handler.sendMessage(msg);
					}
				});
		initExecutorsIfNeed();
		loadingExecutor.submit(readContactTask);
	}

	/**
	 * 纠错提交错误报告
	 * 
	 * @param handler
	 * @param cmd
	 * @param number
	 * @param types
	 * @param content
	 */
	public void reportContactInfoError(Handler handler, int cmd, String number,
			ArrayList<String> types, String content) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", number);
		params.put("ErrorType", types);
		params.put("CorrectionComments", content);
		String req = new JSONRequest("contact", "reportContactInfoError",
				Collections.singletonMap("ContactInfo", params)).toString();
		TransportLog.i(TAG, req);

		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);

		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 来电提示信息
	 * 
	 * @param handler
	 * @param cmd
	 * @param incomingNumber
	 */
	public void callPhoneInfoByPhoneNumber(Handler handler, int cmd,
			String incomingNumber) {
		String req = new JSONRequest("contact", "callPhoneInfoByPhoneNumber",
				Collections.singletonMap("PhoneNumber", incomingNumber))
				.toString();
		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 密码重置
	 * 
	 * @param handler
	 * @param cmd
	 * @param account
	 */
	public void mobileResetPwd(Handler handler, int cmd, String account) {
		String req = new JSONRequest("user", "mobileResetPwd",
				Collections.singletonMap("phone", account)).toString();
		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 配货部，信息部信息获取接口
	 * 
	 * @param handler
	 * @param cmd
	 * @param Code
	 *            dctUT 为UT_GW 表示货运部，配货部 ;dctUT 为UT_CO 表示物流公司
	 * @param offset
	 */
	public void getMemberByMobile(Handler handler, int cmd, String Code,
			int offset) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dctUT", Code);
		// params.put("dctLps", code);
		params.put("place", MyApplication.getInstance().getLocationCode());
		params.put("offset", offset);

		String req = new JSONRequest("user", "getMemberByMobile", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 物流市场（场站）接口
	 * 
	 * @param handler
	 * @param cmd
	 * @param code
	 * @param offset
	 */
	public void getSites(Handler handler, int cmd, String code, int offset) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dctUT", code);
		params.put("dctLps", code);
		params.put("place", MyApplication.getInstance().getLocationCode());
		params.put("offset", offset);

		String req = new JSONRequest("app", "getSites", params).toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 560服务站
	 * 
	 * @param handler
	 * @param cmd
	 * @param offset
	 */
	public void getSstByMobile(Handler handler, int cmd, int offset) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dctUT", "UT_GW");
		params.put("place", MyApplication.getInstance().getLocationCode());
		params.put("offset", offset);

		String req = new JSONRequest("user", "getSstByMobile", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 汽修厂获取接口
	 * 
	 * @param handler
	 * @param cmd
	 * @param offset
	 */
	public void getRepairMarket(Handler handler, int cmd, int offset) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dctLps", "LPS_REPAIRMARKET");
		params.put("dctUT", "LPS_REPAIRMARKET");
		params.put("place", MyApplication.getInstance().getLocationCode());
		params.put("offset", offset);

		String req = new JSONRequest("app", "getRepairMarket", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 加油厂获取接口
	 * 
	 * @param handler
	 * @param cmd
	 * @param offset
	 */
	public void getStations(Handler handler, int cmd, int offset) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dctLps", "LPS_STATIONS");
		params.put("dctUT", "LPS_STATIONS");
		params.put("place", MyApplication.getInstance().getLocationCode());
		params.put("offset", offset);

		String req = new JSONRequest("app", "getRepairMarket", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 车管所
	 * 
	 * @param handler
	 * @param cmd
	 * @param offset
	 */
	public void getTkOffice(Handler handler, int cmd, int offset) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dctLps", "LPS_TKOFFICE");
		params.put("place", MyApplication.getInstance().getLocationCode());
		params.put("offset", offset);

		String req = new JSONRequest("app", "getTkOffice", params).toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 大货在线列表展示
	 * 
	 * @param handler
	 * @param cmd
	 * @param offset
	 */
	public void getCargoOnline(Handler handler, int cmd, String type, int offset) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("place", MyApplication.getInstance().getLocationCode());
		params.put("offset", offset);
		params.put("type", type);
		String req = new JSONRequest("app", "getCargoOnline", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 大货在线顶字请求
	 * 
	 * @param handler
	 * @param cmd
	 * @param cargoID
	 */
	public void addCargoOnline(Handler handler, int cmd, String content) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("CT_REGOIN", MyApplication.getInstance().getLocationCode());
		params.put("place", MyApplication.getInstance().getAddressDetail());
		params.put("msg", content);
		String req = new JSONRequest("app", "addCargoOnline", params)
				.toString();

		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 完善或修改个人资料
	 * 
	 * @param handler
	 * @param cmd
	 * @param userInfo
	 */
	public void updateMobileMemberInfo(Handler handler, int cmd,
			UserInfo userInfo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idno", userInfo.cardNo);
		params.put("realName", userInfo.realName);
		params.put("dct_ut", userInfo.dctUt);
		params.put("companyName", userInfo.companyName);
		params.put("address", userInfo.address);
		params.put("plate1", userInfo.plate1);
		params.put("plate2", userInfo.plate2);
		params.put("plate3", userInfo.plate3);
		params.put("dct_tklen", userInfo.dctTklen);
		params.put("dct_tt", userInfo.dctTT);
		params.put("tkTarget1", userInfo.tkTarget1);
		params.put("tkTarget2", userInfo.tkTarget2);
		params.put("tkTarget3", userInfo.tkTarget3);
		params.put("tkTarget4", userInfo.tkTarget4);
		params.put("drivePicture", userInfo.drivePicture);
		String req = new JSONRequest("user", "updateMobileMemberInfo", params)
				.toString();

		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);

		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	public void getMobileMemberInfoById(Handler handler, int cmd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberID", "memberID");

		String req = new JSONRequest("user", "getMobileMemberInfoById", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 营销代表手机号码保存
	 * 
	 * @param handler
	 * @param cmd
	 */
	public void updateRecommend(Handler handler, int cmd, String mobile) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		params.put("iemi", Common.getSerialNumber());
		params.put("version", Common.getVersionName());
		params.put("imsi", Common.getImsi());
		String req = new JSONRequest("user", "updateRecommend", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 账号余额
	 * 
	 * @param handler
	 * @param cmd
	 */
	public void getMemberAccountState(Handler handler, int cmd) {
		Map<String, Object> params = new HashMap<String, Object>();

		params.put("UserID", MyApplication.getInstance().getMemberID());

		String req = new JSONRequest("user", "getMemberAccountState", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	public void upGpsPoint(Handler handler, int cmd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("LNG", MyApplication.getInstance().getLongitude());
		params.put("LAT", MyApplication.getInstance().getLatitude());

		String req = new JSONRequest("user", "upGpsPoint", params).toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	public void getLocationReq(Handler handler, int cmd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USERID", MyApplication.getInstance().getMemberID());
		String req = new JSONRequest("user", "getLocationReq", params)
				.toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 定位操作请求
	 * 
	 * @param handler
	 * @param cmd
	 * @param id
	 */
	public void updateLocationReq(Handler handler, int cmd, Long id) {

		String req = new JSONRequest("user", "updateLocationReq",
				id == null ? Collections.singletonMap("USERID", MyApplication
						.getInstance().getMemberID())
						: Collections.singletonMap("id", id)).toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	public void pushMessage(Handler handler, int cmd) {
		if ("0.0".equals(MyApplication.getInstance().getLongitude()))
			return;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("LNG", MyApplication.getInstance().getLongitude());
		params.put("LAT", MyApplication.getInstance().getLatitude());
		params.put("time", MyApplication.getInstance().getLocationTime());
		params.put("address", MyApplication.getInstance().getAddressDetail());
		params.put("CT_REGOIN", MyApplication.getInstance().getLocationCode());
		String req = new JSONRequest("user", "pushMessage", params).toString();

		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));

		TransportLog.i(TAG, req);
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 
	 * @param handler
	 * @param cmd
	 * @param state
	 */
	public void updateTkState(Handler handler, int cmd, int state) {
		String req = new JSONRequest("user", "updateTkState",
				Collections.singletonMap("state", state)).toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 身份证核查
	 * 
	 * @param handler
	 * @param cmd
	 * @param state
	 */
	public void getPersonIDCardInfo(Handler handler, int cmd, String name,
			String idno) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Name", name);
		params.put("IDCardNumber", idno);
		String req = new JSONRequest("user", "getPersonIDCardInfo",
				Collections.singletonMap("PersonInfo", params)).toString();

		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 身份证核查
	 * 
	 * @param handler
	 * @param cmd
	 * @param state
	 */
	public void addWeiZhang(Handler handler, int cmd, String photo, String city) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("photo", photo);
		params.put("city", city);
		String req = new JSONRequest("app", "addWeiZhang", params).toString();
		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);

		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);

	}

	public void getWeather(Handler handler, int cmd, String city)
			throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		params = Collections.singletonMap("city",
				URLEncoder.encode(city, "UTF-8"));

		String get = httpHelp.sendGetRequest(AppConifg.WEATHER_SERVER_ADDRESS,
				params, DefaultConst.DEFAULT_CONN_TIMEOUT, true);
		HttpTask httpTask = new HttpTask(handler, cmd);

		httpTask.initData(RequestType.GET, get, null, -1, true);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	public void getWeather1(Handler handler, int cmd, String city)
			throws Exception {

		Map<String, String> params = new HashMap<String, String>();
		params.put("password", "DJOYnieT8234jlsK");
		params.put("day", "0");
		city = city.replace("市", "");
		city = URLEncoder.encode(city, "gb2312");
		params.put("city", city);

		// params = Collections.singletonMap("city", city);

		String get = httpHelp.sendGetRequest(AppConifg.WEATHER_SERVER_ADDRESS1,
				params, DefaultConst.DEFAULT_CONN_TIMEOUT, true);
		WeatherTask httpTask = new WeatherTask(handler, cmd);

		httpTask.initData(WeatherTask.RequestType.GET, get, null, -1, true);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	public void updateCargoCount(Handler handler, int cmd, long id) {
		String req = new JSONRequest("app", "updateCargoCount",
				Collections.singletonMap("CARGOID", id)).toString();

		TransportLog.i(TAG, req);
		String get = httpHelp
				.sendGetRequest(Collections.singletonMap("p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.GET, get, null, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 违章查询列表
	 * 
	 * @param handler
	 * @param cmd
	 * @param offset
	 */
	public void getWeizhangList(Handler handler, int cmd, int offset) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		String req = new JSONRequest("app", "getWeizhangList", params)
				.toString();
		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);

		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	public void getWeizhangResultList(Handler handler, int cmd,
			Long weizhangID, int offset) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", offset);
		params.put("weizhangID", weizhangID);
		String req = new JSONRequest("app", "getWeizhangResultList", params)
				.toString();
		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);

		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 评价等用户信息查询
	 * **/
	public void getDpInfo(Handler handler, int cmd, String mobile) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mobile", mobile);
		String req = new JSONRequest("user", "getDpInfo", params).toString();
		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 评价提交
	 * **/
	public void addDpDetail(Handler handler, int cmd, Long USRID,
			String DCT_DPUC, int first1, int first2, int first3, int first4,
			int total, String DPCONTENT) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USRID", USRID);
		// 被评价人是发货放
		if (DC.DPUC_SHIP.equals(DCT_DPUC)) {
			params.put("TRUTHPOINT", first1);
			params.put("PAYMENTPOINT", first2);
		} else {

			params.put("DELIVERYPOINT", first1);
			params.put("GOODSPOINT", first2);
		}

		params.put("PRICEPOINT", first3);
		params.put("ATTITUDEPOINT", first4);
		params.put("POINT", total);
		params.put("DCT_DPUC", DCT_DPUC);
		params.put("DPCONTENT", DPCONTENT);
		String req = new JSONRequest("user", "addDpDetail", params).toString();
		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 评价历史查询
	 * **/
	public void getDpDetailList(Handler handler, int cmd, Long USRID, int type,
			String DCT_DPUC, int offset) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USRID", USRID);
		params.put("type", type);
		params.put("DCT_DPUC", DCT_DPUC);
		params.put("offset", offset);
		String req = new JSONRequest("user", "getDpDetailList", params)
				.toString();
		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}

	/**
	 * 谁在看我
	 * **/
	public void getChaKan(Handler handler, int cmd) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("USRID", MyApplication.getInstance().getShareHelper()
				.getUserId());
		String req = new JSONRequest("user", "getChaKan", params).toString();
		TransportLog.i(TAG, req);
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 查找好货
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void fetchData(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "getGoodsInfoList", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 好货详情
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void goodsDetail(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "getGoodsInfoDetail", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 撤销报价
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void canclePrice(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "canceCallPrice", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 报价
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void callPrice(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "callPrice", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 成交历史
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void tradeHistory(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "callPriceSubmit", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 报价历史
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void callHistory(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "callPriceHistory", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 轮询有无成交
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void getDealInfo(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "getDealInfo", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 成交详情
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void getDealDetailInfo(Handler handler,int cmd,Map<String, Object> params){
		String req = new JSONRequest("goodsSource", "getDealDetailInfo", params).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	/**
	 * 查询交易时间
	 * @param handler
	 * @param cmd
	 * @param params
	 */
	public void getExchangeHour(Handler handler,int cmd){
		String req = new JSONRequest("goodsSource", "getExchangeHour", null).toString();
		byte[] requestdata = httpHelp.sendPostRequest(Collections.singletonMap(
				"p", req));
		HttpTask httpTask = new HttpTask(handler, cmd);
		httpTask.initData(RequestType.POST, AppConifg.SERVER_ADDRESS,
				requestdata, -1);
		initExecutorsIfNeed();
		loadingExecutor.submit(httpTask);
	}
	public void stop() {
		if (loadingExecutor != null) {
			loadingExecutor.shutdownNow();
		}
	}

}
