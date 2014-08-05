package com.topjet.crediblenumber.util;

import java.util.ArrayList;
import java.util.List;

import com.topjet.crediblenumber.activity.GoodsSearchActivity;

/**
 * 统一指令及常量定义
 * @author billwang
 *
 */
public class DefaultConst {
	 public static final int  CMD_LOGIN = 0x1001;
	 public static final int  CMD_LOGIN_OUT = CMD_LOGIN+1;
	 public static final int  CMD_APK_UPDATE = CMD_LOGIN_OUT+1;
	 public static final int  CMD_UP_PHONELIST = CMD_APK_UPDATE+1; 
	 public static final int  CMD_MOBILE_RESET_PWD = CMD_UP_PHONELIST+1; 
	 public static final int  CMD_TRUCK_STATUS = CMD_MOBILE_RESET_PWD+1;  
	 public static final int  CMD_UP_RECOMMEND = CMD_TRUCK_STATUS+1; 
	 public static final int  CMD_GET_MEMBER_ACCOUNT_STATE = CMD_UP_RECOMMEND+1; 
	 public static final int  CMD_GET_MEMBER_Mobile = CMD_GET_MEMBER_ACCOUNT_STATE+1;
	 public static final int  CMD_GET_SST_Mobile = CMD_GET_MEMBER_Mobile+1;
	 public static final int  CMD_REPAIR_MARKET = CMD_GET_SST_Mobile+1;
	 public static final int  CMD_GET_STATIONS = CMD_REPAIR_MARKET+1;
	 public static final int  CMD_TK_OFFICE = CMD_GET_STATIONS+1;
	 public static final int  CMD_REPORT_CONTACT_INFO_ERR = CMD_TK_OFFICE+1;
	 public static final int  CMD_ADD_CARGO_ONLINE = CMD_REPORT_CONTACT_INFO_ERR+1;
	 public static final int  CMD_GET_CARGO_ONLINE = CMD_ADD_CARGO_ONLINE+1;
	 public static final int  CMD_GET_PERSON_IDCARD = CMD_GET_CARGO_ONLINE+1;
	 public static final int  CMD_GET_WEATHER = CMD_GET_PERSON_IDCARD+1;
	 public static final int  CMD_GET_LOCATION_REQ = CMD_GET_WEATHER+1;
	 public static final int  CMD_UPDATE_LOCATION_REQ = CMD_GET_LOCATION_REQ+1;
	 public static final int  CMD_PUSH_MESSAGE = CMD_UPDATE_LOCATION_REQ+1;
	 public static final int  CMD_UPDATE_CARGO_COUNT = CMD_PUSH_MESSAGE+1;
	 public static final int  CMD_GET_SITES = CMD_UPDATE_CARGO_COUNT+1;
	 public static final int  CMD_GET_MOBILE_MEMBER_INFO_BY_ID= CMD_GET_SITES+1;
	 public static final int  CMD_UP_MOBILE_MEMBER_INFO= CMD_GET_MOBILE_MEMBER_INFO_BY_ID+1;
	 public static final int  CMD_CALL_PHONE_INFO= CMD_UP_MOBILE_MEMBER_INFO+1;
	 public static final int  CMD_UPDATE_PASSWORD= CMD_CALL_PHONE_INFO+1;
	 public static final int  CMD_WEI_ZHANG= CMD_UPDATE_PASSWORD+1;
	 public static final int  CMD_WEI_ZHANG_LIST= CMD_WEI_ZHANG+1;
	 public static final int  CMD_WEI_ZHANG_LIST_RESULT= CMD_WEI_ZHANG_LIST+1;
	 public static final int  CMD_DP_INFO_QUERY_RESULT= CMD_WEI_ZHANG_LIST_RESULT+1;
	 public static final int  CMD_DP_INFO_ADD_RESULT= CMD_DP_INFO_QUERY_RESULT+1;
	 public static final int  CMD_DP_DETAIL_LIST_RESULT= CMD_DP_INFO_ADD_RESULT+1;
	 public static final int  CMD_DP_GET_CHA_KAN_RESULT= CMD_DP_DETAIL_LIST_RESULT+1;
	 public static final int  CMD_QHH_FETCHDATA= CMD_DP_GET_CHA_KAN_RESULT+1;
	 public static final int  CMD_GOODS_DETAIL= CMD_QHH_FETCHDATA+1;
	 public static final int  CMD_GOODS_CANCLEPRICE= CMD_GOODS_DETAIL+1;
	 public static final int  CMD_GOODS_CALLPRICE= CMD_GOODS_CANCLEPRICE+1;
	 public static final int  CMD_GOODS_HISTORY= CMD_GOODS_CALLPRICE+1;
	 public static final int  CMD_GOODS_MYCALL= CMD_GOODS_HISTORY+1;
	 public static final int  CMD_GOODS_DEAL= CMD_GOODS_MYCALL+1;
	 public static final int  CMD_GOODS_DEAL_DETAIL= CMD_GOODS_DEAL+1;
	 public static final int  CMD_GOODS_UNAVAILABLE= CMD_GOODS_DEAL_DETAIL+1;
	 public static final int  CMD_GOODS_EXCHANGEHOUR= CMD_GOODS_UNAVAILABLE+1;
	 
	 
	 public static final int  CMD_ERROR_NET_ADD_REQUEST= 0x2001;
	 public static final int  CMD_ERROR_NET_RETURN_FAILED= CMD_ERROR_NET_ADD_REQUEST+1;
	 public static final int  CMD_ERROR_NET_REQUEST_ERROR= CMD_ERROR_NET_RETURN_FAILED+1;
	 //cmcc之类的跳转
	 public static final int  CMD_CMSS_NET_ADD_REQUEST= 0x3001;
	 //轮询是否有好货成交
	 public static final int  CMD_GOODS_CHECK= CMD_CMSS_NET_ADD_REQUEST+1;
	//开始一个新的倒计时
	 public static final int  CMD_GOODS_COUNTDOWNTIME= CMD_GOODS_CHECK+1;
	 
	 public static final int DEFAULT_CONN_TIMEOUT = 5*1000; // 10seconds
	 public static final int DEFAULT_READ_TIMEOUT = 10*1000; // 10seconds
	 public static final int GOODS_CHECK_TIME = 60*1000; // 1分钟
	 
	 public static final String ALARM_LOCATION_ACTION="android.action.alarm.location";
	 public static final String ALARM_APKUPDATE_ACTION="android.action.apk.update";
}
