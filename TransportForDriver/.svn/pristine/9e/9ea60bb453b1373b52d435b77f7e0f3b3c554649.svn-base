package com.topjet.crediblenumber.util;

import com.topjet.crediblenumber.R;

import android.content.Context;




/**  
 * <pre>
 * Copyright:	Copyright (c)2014 
 * Company:		杭州龙驹信息科技有限公司
 * Author:		wanghm
 * Create at:	2014-3-21 上午11:06:42  
 * Description:
 *
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------  
 * 
 * </pre>
 */  
public class DialogUtil {
	private static MyDialog TK_LEN_DIALOG;
	private static MyDialog TK_TYPE_DIALOG;
	private static MyDialog PROVINCE_DIALOG;
	private static MyDialog CITY_DIALOG;

	public static boolean isProvinceDialog(MyDialog dialog){
		if(null == PROVINCE_DIALOG || dialog != PROVINCE_DIALOG){
			return false;
		}
		return true;
	}

	public static boolean isCityDialog(MyDialog dialog){
		if(null == CITY_DIALOG || dialog != CITY_DIALOG){
			return false;
		}
		return true;
	}

	public static MyDialog showProvinceDialog(Context context){
		if(PROVINCE_DIALOG == null || !context.equals(PROVINCE_DIALOG.getContext())){
			PROVINCE_DIALOG = new MyDialog(context, R.layout.dialog_province, Dict.getProvinceDict());
		}
		PROVINCE_DIALOG.showDialog(0, 200);
		return PROVINCE_DIALOG;
	}

	public static MyDialog showCityDialog(Context context, String provinceCode){
		if(CITY_DIALOG == null || !context.equals(CITY_DIALOG.getContext())){
			CITY_DIALOG = new MyDialog(context, R.layout.dialog_city, Dict.getCityDict(provinceCode));
		}else{
			CITY_DIALOG.setData(Dict.getCityDict(provinceCode));
			CITY_DIALOG.setChanged(true);
		}
		CITY_DIALOG.showDialog(0, 200);
		return CITY_DIALOG;
	}

	public static MyDialog showTruckLengthDialog(Context context){
		if(TK_LEN_DIALOG == null || !context.equals(TK_LEN_DIALOG.getContext())){
			TK_LEN_DIALOG = new MyDialog(context, R.layout.dialog_tk_len, Dict.getTruckLengthDict());
		}
		TK_LEN_DIALOG.showDialog(0, 200);
		return TK_LEN_DIALOG;
	}

	public static MyDialog showTruckTypeDialog(Context context){
		if(TK_TYPE_DIALOG == null || !context.equals(TK_TYPE_DIALOG.getContext())){
			TK_TYPE_DIALOG = new MyDialog(context, R.layout.dialog_tk_type, Dict.getTruckTypeDict());
		}
		TK_TYPE_DIALOG.showDialog(0, 200);
		return TK_TYPE_DIALOG;
	}
}
