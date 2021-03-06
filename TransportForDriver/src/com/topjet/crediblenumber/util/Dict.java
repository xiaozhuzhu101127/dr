package com.topjet.crediblenumber.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-7-11 上午9:20:25  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class Dict {
	public static final String BLANK = "";
	public static final String CITY_DEFAULT_NAME = "全国";

	private static Map<String, String> TRUCK_LENGTH;
	private static Map<String, String> TRUCK_TYPE;
	private static Map<String, String> PROVINCE;
	private static Map<String, Map<String, String>> CITY;
	//货物类型
	private static Map<String, String> GSTYPE;
	//支付方式
	private static Map<String, String> PAYWAY;
	//装货方式
	private static Map<String, String> GSLOAD;
	//货源状态
	private static Map<String, Integer> GSSTATE;
	/**
	 * 直辖市
	 * @param cityCode
	 * @return
	 */
	public static boolean isMunicipalCity(String cityCode){
		if(null == cityCode){
			return false;
		}
		if(cityCode.equals("BJ")||cityCode.equals("SH")||cityCode.equals("TJ")||cityCode.equals("CQ")){
			return true;
		}
		return false;
	}

	/**
	 * 特别行政区
	 * @param cityCode
	 * @return
	 */
	public static boolean isSpecialCity(String cityCode){
		if(null == cityCode){
			return false;
		}
		if(cityCode.equals("AM") || cityCode.equals("XG") || cityCode.equals("TW")){
			return true;
		}
		return false;
	}
	//省份加市
	public static String getLocation(String cityCode){
		if(Common.isEmpty(cityCode)){
			return CITY_DEFAULT_NAME;
		}
		StringBuilder location = new StringBuilder();
		if(null != getProvince(cityCode))
			location.append(getProvince(cityCode));
		if(null != getCityName(cityCode))
			location.append(getCityName(cityCode));
		return location.toString();
	}
	//省份
	public static String getProvince(String cityCode){
		if(Common.isEmpty(cityCode)){
			return CITY_DEFAULT_NAME;
		}
		String[] codes = cityCode.split("_");
		return getProvinceDict().get(codes[0]);
	}
	public static String getCityName(String cityCode, int level){
		if(Common.isEmpty(cityCode)){
			return CITY_DEFAULT_NAME;
		}
		String[] codes = cityCode.split("_");
		if(codes.length == 1){
			return getProvinceDict().get(codes[0]) == null?"":getProvinceDict().get(codes[0]);
		}
		if(level == 1){
			return getCityDict(codes[0])==null?"":getCityDict(codes[0]).get(cityCode);
		}
		String ret ="";
		if (null != getProvinceDict().get(codes[0]))
			ret = getProvinceDict().get(codes[0]);
		if (null != getCityDict(codes[0]))
			ret += getCityDict(codes[0]).get(cityCode);
		return   ret ;
	}
	//城市
	public static String getCityName(String cityCode){
		return getCityName(cityCode, 1);
	}
	//车型
	public static String getTruckType(String code){
		return getTruckTypeDict().get(code);
	}
	//车长
	public static String getTruckLength(String code){
		return getTruckLengthDict().get(code);
	}
	//货物类型
	public static String getGsType(String code){
		return getGsTypeDict().get(code);
	}
	//付款方式
	public static String getPayWay(String code){
		return getPayWayDict().get(code);
	}
	//装货方式
	public static String getGsLoad(String code){
		return getGsLoadDict().get(code);
	}
	//货源状态
	public static Integer getGsState(String code){
		return getGsStateDict().get(code);
	}
	public static Map<String, String> getProvinceDict(){
		if(null == PROVINCE){
			initCityDict();
		}
		return PROVINCE;
	}

	public static Map<String, String> getCityDict(String provinceCode){
		if(null == PROVINCE){
			initCityDict();
		}
		return CITY.get(provinceCode);
	}
	public static Map<String, Integer> getGsStateDict(){
		if(GSSTATE == null){
			GSSTATE = new LinkedHashMap<String, Integer>();
			GSSTATE.put("GSSTS_CALLPRICE", 0);
			GSSTATE.put("GSSTS_SUBMIT", 1);
			GSSTATE.put("GSSTS_WT", 2);
			GSSTATE.put("GSSTS_CANCE", 3);
		}
		return GSSTATE;
	}
	public static Map<String, String> getGsLoadDict(){
		if(GSLOAD == null){
			GSLOAD = new LinkedHashMap<String, String>();
			GSLOAD.put("GSLOAD_NOW", "马上装车");
			GSLOAD.put("GSLOAD_NextDay ", "今定明装");
			GSLOAD.put("GSLOAD_WithTK", "随车装");
		}
		return GSLOAD;
	}
	public static Map<String, String> getPayWayDict(){
		if(PAYWAY == null){
			PAYWAY = new LinkedHashMap<String, String>();
			PAYWAY.put("PAYWAY_COD", "货到付款");
			PAYWAY.put("PAYWAY_FUll ", "预付全额");
			PAYWAY.put("PAYWAY_PREHAlF", "先付一半");
			PAYWAY.put("PAYWAY_RCP", "回单结运费");
		}
		return PAYWAY;
	}
	public static Map<String, String> getGsTypeDict(){
		if(GSTYPE == null){
			GSTYPE = new LinkedHashMap<String, String>();
			GSTYPE.put("GSTYPE_HEAVY", "重货");
			GSTYPE.put("GSTYPE_GENERAL", "普货");
			GSTYPE.put("GSTYPE_BULKY", "泡货");
			GSTYPE.put("GSTYPE_DEVICE", "设备");
			GSTYPE.put("GSTYPE_COAL", "煤");
			GSTYPE.put("GSTYPE_MINIRAL", "矿产");
			GSTYPE.put("GSTYPE_MATERIA", "建材");
			GSTYPE.put("GSTYPE_FOOD", "食品");
			GSTYPE.put("GSTYPE_VEGETABLE", "蔬菜");
			GSTYPE.put("GSTYPE_CHEM", "化工");
			GSTYPE.put("GSTYPE_CARTON", "纸箱");
			GSTYPE.put("GSTYPE_FRUIT", "水果");
			GSTYPE.put("GSTYPE_ARGOPRO", "农副产品");
			GSTYPE.put("GSTYPE_MEDICAL", "医药");
			GSTYPE.put("GSTYPE_DRIFT", "漂货");
			GSTYPE.put("GSTYPE_FURNITURE", "家具");
			GSTYPE.put("GSTYPE_THEAMAL", "保温材料");
			GSTYPE.put("GSTYPE_SOS", "危险品");
			GSTYPE.put("GSTYPE_FROZEN", "冻品");
			GSTYPE.put("GSTYPE_GB", "钢板");
			GSTYPE.put("GSTYPE_MH", "棉花");
			GSTYPE.put("GSTYPE_JB ", "卷板");
			GSTYPE.put("GSTYPE_MB", "木板");
			GSTYPE.put("GSTYPE_MC", "木材");
			GSTYPE.put("GSTYPE_KL", "颗粒");
			GSTYPE.put("GSTYPE_OTH", "其他");
		}
		return GSTYPE;
	}
	public static Map<String, String> getTruckLengthDict(){
		if(TRUCK_LENGTH == null){
			TRUCK_LENGTH = new LinkedHashMap<String, String>();
			TRUCK_LENGTH.put(BLANK, "不限");
			TRUCK_LENGTH.put("TKLEN_40", "4米");
			TRUCK_LENGTH.put("TKLEN_42", "4.2米");
			TRUCK_LENGTH.put("TKLEN_43", "4.3米");
			TRUCK_LENGTH.put("TKLEN_45", "4.5米");
			TRUCK_LENGTH.put("TKLEN_48", "4.8米");
			TRUCK_LENGTH.put("TKLEN_50", "5米");
			TRUCK_LENGTH.put("TKLEN_58", "5.8米");
			TRUCK_LENGTH.put("TKLEN_60", "6米");
			TRUCK_LENGTH.put("TKLEN_62", "6.2米");
			TRUCK_LENGTH.put("TKLEN_68", "6.8米");
			TRUCK_LENGTH.put("TKLEN_70", "7米");
			TRUCK_LENGTH.put("TKLEN_72", "7.2米");
			TRUCK_LENGTH.put("TKLEN_74", "7.4米");
			TRUCK_LENGTH.put("TKLEN_78", "7.8米");
			TRUCK_LENGTH.put("TKLEN_80", "8米");
			TRUCK_LENGTH.put("TKLEN_87", "8.7米");
			TRUCK_LENGTH.put("TKLEN_88", "8.8米");
			TRUCK_LENGTH.put("TKLEN_90", "9米");
			TRUCK_LENGTH.put("TKLEN_96", "9.6米");
			TRUCK_LENGTH.put("TKLEN_125", "12.5米");
			TRUCK_LENGTH.put("TKLEN_130", "13米");
			TRUCK_LENGTH.put("TKLEN_135", "13.5米");
			TRUCK_LENGTH.put("TKLEN_160", "16米");
			TRUCK_LENGTH.put("TKLEN_175", "17.5米");
		}
		return TRUCK_LENGTH;
	}

	public static Map<String, String> getTruckTypeDict(){
		if(TRUCK_TYPE == null){
			TRUCK_TYPE = new LinkedHashMap<String, String>();
			TRUCK_TYPE.put(BLANK, "不限");
			TRUCK_TYPE.put("TT_SEMI", "半挂车");
			TRUCK_TYPE.put("TT_REFRIGER", "冷藏车");
			TRUCK_TYPE.put("TT_INSULATED", "保温车");
			TRUCK_TYPE.put("TT_HIGHHURDLE", "高栏车");
			TRUCK_TYPE.put("TT_FLAT", "平板车");
			TRUCK_TYPE.put("TT_F4L8", "前四后八");
			TRUCK_TYPE.put("TT_F4L4", "前四后四");
			TRUCK_TYPE.put("TT_F4L10", "前四后十");
			TRUCK_TYPE.put("TT_CONTAINER", "集装箱");
			TRUCK_TYPE.put("TT_COMMON", "普通车");
			TRUCK_TYPE.put("TT_CABRIO", "敞篷车");
			TRUCK_TYPE.put("TT_XC", "厢车");
		}
		return TRUCK_TYPE;
	}
	
	private static void initCityDict() {
		PROVINCE = new LinkedHashMap<String, String>();
		CITY = new LinkedHashMap<String, Map<String, String>>();
//		PROVINCE.put(BLANK, "不限");
		PROVINCE.put("BJ", "北京");
		Map<String, String> cities;
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("BJ_BJ_DCQ", "东城区");
		cities.put("BJ_BJ_XCQ", "西城区");
		cities.put("BJ_BJ_CYQ", "朝阳区");
		cities.put("BJ_BJ_FTQ", "丰台区");
		cities.put("BJ_BJ_SJSQ", "石景山区");
		cities.put("BJ_BJ_HDQ", "海淀区");
		cities.put("BJ_BJ_MTGQ", "门头沟区");
		cities.put("BJ_BJ_FSQ", "房山区");
		cities.put("BJ_BJ_TZQ", "通州区");
		cities.put("BJ_BJ_SYQ", "顺义区");
		cities.put("BJ_BJ_CPQ", "昌平区");
		cities.put("BJ_BJ_MYX", "密云县");
		cities.put("BJ_BJ_YQX", "延庆县");
		cities.put("BJ_BJ_DXQ", "大兴区");
		cities.put("BJ_BJ_HRQ", "怀柔区");
		cities.put("BJ_BJ_PGQ", "平谷区");
		CITY.put("BJ", cities);

		PROVINCE.put("TJ", "天津");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("TJ_TJ_HPQ", "和平区");
		cities.put("TJ_TJ_HDQ", "河东区");
		cities.put("TJ_TJ_HXQ", "河西区");
		cities.put("TJ_TJ_NKQ", "南开区");
		cities.put("TJ_TJ_HBQ", "河北区");
		cities.put("TJ_TJ_HQQ", "红桥区");
		cities.put("TJ_TJ_TGQ", "塘沽区");
		cities.put("TJ_TJ_HGQ", "汉沽区");
		cities.put("TJ_TJ_DGQ", "大港区");
		cities.put("TJ_TJ_DLQ", "东丽区");
		cities.put("TJ_TJ_XQQ", "西青区");
		cities.put("TJ_TJ_JNQ", "津南区");
		cities.put("TJ_TJ_BCQ", "北辰区");
		cities.put("TJ_TJ_WQQ", "武清区");
		cities.put("TJ_TJ_BC", "宝坻区");
		CITY.put("TJ", cities);

		PROVINCE.put("HEB", "河北");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("HEB_SJZ", "石家庄");
		cities.put("HEB_TS", "唐山");
		cities.put("HEB_QHD", "秦皇岛");
		cities.put("HEB_HD", "邯郸");
		cities.put("HEB_XT", "邢台");
		cities.put("HEB_BD", "保定");
		cities.put("HEB_ZJK", "张家口");
		cities.put("HEB_CD", "承德");
		cities.put("HEB_CZ", "沧州");
		cities.put("HEB_LF", "廊坊");
		cities.put("HEB_HS", "衡水");
		CITY.put("HEB", cities);

		PROVINCE.put("SX", "山西");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("SX_TY", "太原");
		cities.put("SX_DT", "大同");
		cities.put("SX_YQ", "阳泉");
		cities.put("SX_ZZ", "长治");
		cities.put("SX_JC", "晋城");
		cities.put("SX_SZ", "朔州");
		cities.put("SX_JZ", "晋中");
		cities.put("SX_YC", "运城");
		cities.put("SX_XZ", "忻州");
		cities.put("SX_LF", "临汾");
		cities.put("SX_LL", "吕梁");
		CITY.put("SX", cities);

		PROVINCE.put("NMG", "内蒙古");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("NMG_HHHT", "呼和浩特");
		cities.put("NMG_BT", "包头");
		cities.put("NMG_WH", "乌海");
		cities.put("NMG_CF", "赤峰");
		cities.put("NMG_TL", "通辽");
		cities.put("NMG_EEDS", "鄂尔多斯");
		cities.put("NMG_HLBE", "呼伦贝尔");
		cities.put("NMG_BYNE", "巴彦淖尔");
		cities.put("NMG_WLCB", "乌兰察布");
		cities.put("NMG_XAM", "兴安盟");
		cities.put("锡林郭勒盟", "锡林郭勒盟");
		cities.put("NMG_ALSM", "阿拉善盟");
		CITY.put("NMG", cities);

		PROVINCE.put("LN", "辽宁");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("LN_SY", "沈阳");
		cities.put("LN_DL", "大连");
		cities.put("LN_AS", "鞍山");
		cities.put("LN_FS", "抚顺");
		cities.put("LN_BXI", "本溪");
		cities.put("LN_DD", "丹东");
		cities.put("LN_JZ", "锦州");
		cities.put("LN_YK", "营口");
		cities.put("LN_FX", "阜新");
		cities.put("LN_LY", "辽阳");
		cities.put("LN_PJ", "盘锦");
		cities.put("LN_TL", "铁岭");
		cities.put("LN_CY", "朝阳");
		cities.put("LN_HLD", "葫芦岛");
		CITY.put("LN", cities);

		PROVINCE.put("JL", "吉林");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("JL_ZC", "长春");
		cities.put("JL_JL", "吉林");
		cities.put("JL_SP", "四平");
		cities.put("JL_LY", "辽源");
		cities.put("JL_TH", "通化");
		cities.put("JL_BS", "白山");
		cities.put("JL_SY", "松原");
		cities.put("JL_BC", "白城");
		cities.put("JL_YBCZ", "延边朝州");
		CITY.put("JL", cities);

		PROVINCE.put("HLJ", "黑龙江");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("HLJ_HEB", "哈尔滨");
		cities.put("HLJ_QQHE", "齐齐哈尔");
		cities.put("HLJ_JX", "鸡西");
		cities.put("HLJ_HG", "鹤岗");
		cities.put("HLJ_SYS", "双鸭山");
		cities.put("HLJ_DQ", "大庆");
		cities.put("HLJ_YC", "伊春");
		cities.put("HLJ_JMS", "佳木斯");
		cities.put("HLJ_QTH", "七台河");
		cities.put("HLJ_MDJ", "牡丹江");
		cities.put("HLJ_SH", "黑河");
		cities.put("HLJ_SH", "绥化");
		cities.put("HLJ_DXAL", "大兴安岭");
		CITY.put("HLJ", cities);

		PROVINCE.put("SH", "上海");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("SH_SH_HPQ", "黄浦区");
		cities.put("SH_SH_LWQ", "卢湾区");
		cities.put("SH_SH_XHQ", "徐汇区");
		cities.put("SH_SH_ZNQ", "长宁区");
		cities.put("SH_SH_JAQ", "静安区");
		cities.put("SH_SH_PTQ", "普陀区");
		cities.put("SH_SH_ZBQ", "闸北区");
		cities.put("SH_SH_HKQ", "虹口区");
		cities.put("SH_SH_YPQ", "杨浦区");
		cities.put("SH_SH_MXQ", "闵行区");
		cities.put("SH_SH_BSQ", "宝山区");
		cities.put("SH_SH_JDQ", "嘉定区");
		cities.put("SH_SH_PDXQ", "浦东新区");
		cities.put("SH_SH_JSQ", "金山区");
		cities.put("SH_SH_SJQ", "松江区");
		cities.put("SH_SH_QPQ", "青浦区");
		cities.put("SH_SH_NHQ", "南汇区");
		cities.put("SH_SH_FXQ", "奉贤区");
		cities.put("SH_SH_CM", "崇明县");
		CITY.put("SH", cities);

		PROVINCE.put("JS", "江苏");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("JS_NJ", "南京");
		cities.put("JS_WX", "无锡");
		cities.put("JS_XZ", "徐州");
		cities.put("JS_CZ", "常州");
		cities.put("JS_SZ", "苏州");
		cities.put("JS_NT", "南通");
		cities.put("JS_LYG", "连云港");
		cities.put("JS_HA", "淮安");
		cities.put("JS_YC", "盐城");
		cities.put("JS_YZ", "扬州");
		cities.put("JS_ZJ", "镇江");
		cities.put("JS_TZ", "泰州");
		cities.put("JS_SQ", "宿迁");
		CITY.put("JS", cities);

		PROVINCE.put("ZJ", "浙江");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("ZJ_HZ", "杭州");
		cities.put("ZJ_NB", "宁波");
		cities.put("ZJ_WZ", "温州");
		cities.put("ZJ_JX", "嘉兴");
		cities.put("ZJ_HUZ", "湖州");
		cities.put("ZJ_SX", "绍兴");
		cities.put("ZJ_JH", "金华");
		cities.put("ZJ_QZ", "衢州");
		cities.put("ZJ_ZS", "舟山");
		cities.put("ZJ_TZ", "台州");
		cities.put("ZJ_LS", "丽水");
		CITY.put("ZJ", cities);

		PROVINCE.put("AH", "安徽");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("AH_HF", "合肥");
		cities.put("AH_WH", "芜湖");
		cities.put("AH_BB", "蚌埠");
		cities.put("AH_HN", "淮南");
		cities.put("AH_MAS", "马鞍山");
		cities.put("AH_HB", "淮北");
		cities.put("AH_TL", "铜陵");
		cities.put("AH_AQ", "安庆");
		cities.put("AH_HS", "黄山");
		cities.put("AH_CHZ", "滁州");
		cities.put("AH_FY", "阜阳");
		cities.put("AH_SZ", "宿州");
		cities.put("AH_CH", "巢湖");
		cities.put("AH_LA", "六安");
		cities.put("AH_BZ", "亳州");
		cities.put("AH_CZ", "池州");
		cities.put("AH_XC", "宣城");
		CITY.put("AH", cities);

		PROVINCE.put("FJ", "福建");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("FJ_FZ", "福州");
		cities.put("FJ_XM", "厦门");
		cities.put("FJ_PT", "莆田");
		cities.put("FJ_SM", "三明");
		cities.put("FJ_QZ", "泉州");
		cities.put("FJ_ZZ", "漳州");
		cities.put("FJ_NP", "南平");
		cities.put("FJ_LY", "龙岩");
		cities.put("FJ_ND", "宁德");
		CITY.put("FJ", cities);

		PROVINCE.put("JX", "江西");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("JX_NC", "南昌");
		cities.put("JX_JDZ", "景德镇");
		cities.put("JX_PX", "萍乡");
		cities.put("JX_JJ", "九江");
		cities.put("JX_XY", "新余");
		cities.put("JX_YT", "鹰潭");
		cities.put("JX_GZ", "赣州");
		cities.put("JX_JA", "吉安");
		cities.put("JX_YC", "宜春");
		cities.put("JX_FZ", "抚州");
		cities.put("JX_SR", "上饶");
		CITY.put("JX", cities);

		PROVINCE.put("SD", "山东");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("SD_JNA", "济南");
		cities.put("SD_QD", "青岛");
		cities.put("SD_ZB", "淄博");
		cities.put("SD_ZZ", "枣庄");
		cities.put("SD_DY", "东营");
		cities.put("SD_YT", "烟台");
		cities.put("SD_WF", "潍坊");
		cities.put("SD_JN", "济宁");
		cities.put("SD_TA", "泰安");
		cities.put("SD_WH", "威海");
		cities.put("SD_RZ", "日照");
		cities.put("SD_LW", "莱芜");
		cities.put("SD_LY", "临沂");
		cities.put("SD_DZ", "德州");
		cities.put("SD_LC", "聊城");
		cities.put("SD_BZ", "滨州");
		cities.put("SD_HZ", "菏泽");
		CITY.put("SD", cities);

		PROVINCE.put("HEN", "河南");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("HEN_ZZ", "郑州");
		cities.put("HEN_KF", "开封");
		cities.put("HEN_LY", "洛阳");
		cities.put("HEN_PDS", "平顶山");
		cities.put("HEN_AY", "安阳");
		cities.put("HEN_HB", "鹤壁");
		cities.put("HEN_XX", "新乡");
		cities.put("HEN_JZ", "焦作");
		cities.put("HEN_PY", "濮阳");
		cities.put("HEN_XC", "许昌");
		cities.put("HEN_LH", "漯河");
		cities.put("HEN_SMX", "三门峡");
		cities.put("HEN_NY", "南阳");
		cities.put("HEN_SQ", "商丘");
		cities.put("HEN_XY", "信阳");
		cities.put("HEN_ZK", "周口");
		cities.put("HEN_ZMD", "驻马店");
		CITY.put("HEN", cities);

		PROVINCE.put("HB", "湖北");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("HB_WH", "武汉");
		cities.put("HB_HS", "黄石");
		cities.put("HB_SY", "十堰");
		cities.put("HB_YC", "宜昌");
		cities.put("HB_XF", "襄樊");
		cities.put("HB_EZ", "鄂州");
		cities.put("HB_JM", "荆门");
		cities.put("HB_XG", "孝感");
		cities.put("HB_JZ", "荆州");
		cities.put("HB_HG", "黄冈");
		cities.put("HB_XN", "咸宁");
		cities.put("HB_SZ", "随州");
		cities.put("HB_ESZ", "恩施州");
		cities.put("HB_QJ", "潜江市");
		cities.put("HB_SNJLQ", "神农架林区");
		cities.put("HB_TM", "天门市");
		cities.put("HB_XT", "仙桃市");
		CITY.put("HB", cities);

		PROVINCE.put("HUN", "湖南");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("HUN_ZS", "长沙");
		cities.put("HUN_ZZ", "株洲");
		cities.put("HUN_XT", "湘潭");
		cities.put("HUN_HY", "衡阳");
		cities.put("HUN_SY", "邵阳");
		cities.put("HUN_YY", "岳阳");
		cities.put("HUN_CD", "常德");
		cities.put("HUN_ZJJ", "张家界");
		cities.put("HUN_YIY", "益阳");
		cities.put("HUN_CZ", "郴州");
		cities.put("HUN_YZ", "永州");
		cities.put("HUN_HH", "怀化");
		cities.put("HUN_LD", "娄底");
		cities.put("HUN_XXZ", "湘西州");
		CITY.put("HUN", cities);

		PROVINCE.put("GD", "广东");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("GD_GZ", "广州");
		cities.put("GD_SG", "韶关");
		cities.put("GD_SZ", "深圳");
		cities.put("GD_ZH", "珠海");
		cities.put("GD_ST", "汕头");
		cities.put("GD_FS", "佛山");
		cities.put("GD_JM", "江门");
		cities.put("GD_ZJ", "湛江");
		cities.put("GD_MM", "茂名");
		cities.put("GD_ZQ", "肇庆");
		cities.put("GD_HZ", "惠州");
		cities.put("GD_MZ", "梅州");
		cities.put("GD_SW", "汕尾");
		cities.put("GD_HY", "河源");
		cities.put("GD_YJ", "阳江");
		cities.put("GD_QY", "清远");
		cities.put("GD_DG", "东莞");
		cities.put("GD_ZS", "中山");
		cities.put("GD_CZ", "潮州");
		cities.put("GD_JY", "揭阳");
		cities.put("GD_YF", "云浮");
		CITY.put("GD", cities);

		PROVINCE.put("GX", "广西");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("GX_NN", "南宁");
		cities.put("GX_LZ", "柳州");
		cities.put("GX_GL", "桂林");
		cities.put("GX_WZ", "梧州");
		cities.put("GX_BH", "北海");
		cities.put("GX_FCG", "防城港");
		cities.put("GX_QZ", "钦州");
		cities.put("GX_GG", "贵港");
		cities.put("GX_YL", "玉林");
		cities.put("GX_BS", "百色");
		cities.put("GX_HZ", "贺州");
		cities.put("GX_HC", "河池");
		cities.put("GX_LB", "来宾");
		cities.put("GX_CZ", "崇左");
		CITY.put("GX", cities);

		PROVINCE.put("HN", "海南");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("HN_HK", "海口");
		cities.put("HN_SY", "三亚");
		cities.put("HN_SZXX", "省直辖县");
		CITY.put("HN", cities);

		PROVINCE.put("ZQ", "重庆");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("ZQ_ZQ_WZQ", "万州区");
		cities.put("ZQ_ZQ_FLQ", "涪陵区");
		cities.put("ZQ_ZQ_YZQ", "渝中区");
		cities.put("ZQ_ZQ_DDKQ", "大渡口区");
		cities.put("ZQ_ZQ_JBQ", "江北区");
		cities.put("ZQ_ZQ_SPBQ", "沙坪坝区");
		cities.put("ZQ_ZQ_JLPQ", "九龙坡区");
		cities.put("ZQ_ZQ_NAQ", "南岸区");
		cities.put("ZQ_ZQ_BBQ", "北碚区");
		cities.put("ZQ_ZQ_WSQ", "万盛区");
		cities.put("ZQ_ZQ_SQQ", "双桥区");
		cities.put("ZQ_ZQ_YBQ", "渝北区");
		cities.put("ZQ_ZQ_BNQ", "巴南区");
		cities.put("ZQ_ZQ_QJQ", "黔江区");
		cities.put("ZQ_ZQ_ZSQ", "长寿区");
		cities.put("ZQ_ZQ_QJX", "綦江县");
		cities.put("ZQ_ZQ_TNX", "潼南县");
		cities.put("ZQ_ZQ_TLX", "铜梁县");
		cities.put("ZQ_ZQ_DZX", "大足县");
		cities.put("ZQ_ZQ_RCX", "荣昌县");
		cities.put("ZQ_ZQ_BSX", "璧山县");
		cities.put("ZQ_ZQ_LPX", "梁平县");
		cities.put("ZQ_ZQ_CKX", "城口县");
		cities.put("ZQ_ZQ_FDX", "丰都县");
		cities.put("ZQ_ZQ_DJX", "垫江县");
		cities.put("ZQ_ZQ_WLX", "武隆县");
		cities.put("ZQ_ZQ_ZX", "忠县");
		cities.put("ZQ_ZQ_KX", "开县");
		cities.put("ZQ_ZQ_YUYX", "云阳县");
		cities.put("ZQ_ZQ_FJX", "奉节县");
		cities.put("ZQ_ZQ_WSX", "巫山县");
		cities.put("ZQ_ZQ_WXX", "巫溪县");
		cities.put("ZQ_ZQ_SZX", "石柱县");
		cities.put("ZQ_ZQ_XSX", "秀山县");
		cities.put("ZQ_ZQ_YYX", "酉阳县");
		cities.put("ZQ_ZQ_PSX", "彭水县");
		cities.put("ZQ_ZQ_JJ", "江津");
		cities.put("ZQ_ZQ_HC", "合川");
		cities.put("ZQ_ZQ_YC", "永川");
		cities.put("ZQ_ZQ_NC", "南川");
		CITY.put("ZQ", cities);

		PROVINCE.put("SC", "四川");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("SC_CD", "成都");
		cities.put("SC_ZG", "自贡");
		cities.put("SC_PZH", "攀枝花");
		cities.put("SC_LZ", "泸州");
		cities.put("SC_DY", "德阳");
		cities.put("SC_MY", "绵阳");
		cities.put("SC_GY", "广元");
		cities.put("SC_SN", "遂宁");
		cities.put("SC_NJ", "内江");
		cities.put("SC_LS", "乐山");
		cities.put("SC_NC", "南充");
		cities.put("SC_MS", "眉山");
		cities.put("SC_YB", "宜宾");
		cities.put("SC_GA", "广安");
		cities.put("SC_DZ", "达州");
		cities.put("SC_YA", "雅安");
		cities.put("SC_BZ", "巴中");
		cities.put("SC_ZY", "资阳");
		cities.put("SC_ABZ", "阿坝州");
		cities.put("SC_GZZZ", "甘孜藏族");
		cities.put("SC_LSYZ", "凉山彝族");
		CITY.put("SC", cities);

		PROVINCE.put("GZ", "贵州");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("GZ_GY", "贵阳");
		cities.put("GZ_LPS", "六盘水");
		cities.put("GZ_ZY", "遵义");
		cities.put("GZ_AS", "安顺");
		cities.put("GZ_TRDQ", "铜仁地区");
		cities.put("GZ_QXNZ", "黔西南州");
		cities.put("GZ_BJDQ", "毕节地区");
		cities.put("GZ_QDNZ", "黔东南州");
		cities.put("GZ_QNZ", "黔南州");
		CITY.put("GZ", cities);

		PROVINCE.put("YN", "云南");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("YN_KM", "昆明");
		cities.put("YN_QJ", "曲靖");
		cities.put("YN_YX", "玉溪");
		cities.put("YN_BS", "保山");
		cities.put("YN_ZT", "昭通");
		cities.put("YN_LJ", "丽江");
		cities.put("YN_SM", "思茅");
		cities.put("YN_LC", "临沧");
		cities.put("YN_CXZ", "楚雄州");
		cities.put("YN_HHZ", "红河州");
		cities.put("YN_WSZ", "文山州");
		cities.put("YN_XSBN", "西双版纳");
		cities.put("YN_DLZ", "大理州");
		cities.put("YN_DHZ", "德宏州");
		cities.put("YN_NJZ", "怒江州");
		cities.put("YN_DQZ", "迪庆州");
		cities.put("YN_PE", "普洱");
		CITY.put("YN", cities);

		PROVINCE.put("SHX", "陕西");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("SHX_XA", "西安");
		cities.put("SHX_TC", "铜川");
		cities.put("SHX_BJ", "宝鸡");
		cities.put("SHX_XY", "咸阳");
		cities.put("SHX_WN", "渭南");
		cities.put("SHX_YA", "延安");
		cities.put("SHX_HZ", "汉中");
		cities.put("SHX_YL", "榆林");
		cities.put("SHX_AK", "安康");
		cities.put("SHX_SL", "商洛");
		CITY.put("SHX", cities);

		PROVINCE.put("GS", "甘肃");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("GS_LZ", "兰州");
		cities.put("GS_JYG", "嘉峪关");
		cities.put("GS_JC", "金昌");
		cities.put("GS_BY", "白银");
		cities.put("GS_TS", "天水");
		cities.put("GS_WW", "武威");
		cities.put("GS_ZY", "张掖");
		cities.put("GS_PL", "平凉");
		cities.put("GS_JQ", "酒泉");
		cities.put("GS_QY", "庆阳");
		cities.put("GS_DX", "定西");
		cities.put("GS_LN", "陇南");
		cities.put("GS_LXZ", "临夏州");
		cities.put("GS_GNZ", "甘南州");
		CITY.put("GS", cities);

		PROVINCE.put("QH", "青海");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("QH_XN", "西宁");
		cities.put("QH_HDDQ", "海东地区");
		cities.put("QH_HBZ", "海北州");
		cities.put("QH_HUNZ", "黄南州");
		cities.put("QH_HNZ", "海南州");
		cities.put("QH_GLZ", "果洛州");
		cities.put("QH_YSZ", "玉树州");
		cities.put("QH_HXZ", "海西州");
		CITY.put("QH", cities);

		PROVINCE.put("NX", "宁夏");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("NX_YC", "银川");
		cities.put("NX_SZS", "石嘴山");
		cities.put("NX_WZ", "吴忠");
		cities.put("NX_GY", "固原");
		cities.put("NX_ZW", "中卫");
		CITY.put("NX", cities);

		PROVINCE.put("XJ", "新疆");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("BJ_BJ_DCQ", "东城区");
		cities.put("XJ_WLMQ", "乌鲁木齐");
		cities.put("XJ_KLMY", "克拉玛依");
		cities.put("XJ_TLFDQ", "吐鲁番地区");
		cities.put("XJ_HMDQ", "哈密地区");
		cities.put("XJ_CJZ", "昌吉州");
		cities.put("XJ_BETL", "博尔塔拉");
		cities.put("XJ_BYGL", "巴音郭楞");
		cities.put("XJ_AKSDQ", "阿克苏地区");
		cities.put("XJ_KZLS", "克孜勒苏");
		cities.put("XJ_KSDQ", "喀什地区");
		cities.put("XJ_HTDQ", "和田地区");
		cities.put("XJ_YLZ", "伊犁州");
		cities.put("XJ_TCDQ", "塔城地区");
		cities.put("XJ_ALTDQ", "阿勒泰地区");
		cities.put("XJ_SXX", "省辖县");
		CITY.put("XJ", cities);

		PROVINCE.put("TW", "台湾");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("TW_TW", "台湾");
		CITY.put("TW", cities);

		PROVINCE.put("XG", "香港");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("XG_XG", "香港");
		CITY.put("XG", cities);

		PROVINCE.put("AM", "澳门");
		cities = new LinkedHashMap<String, String>();
		cities.put(BLANK, "不限");
		cities.put("AM_AM", "澳门");
		CITY.put("AM", cities);

	}
}
