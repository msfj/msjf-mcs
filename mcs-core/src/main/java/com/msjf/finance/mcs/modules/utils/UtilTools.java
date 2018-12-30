package com.msjf.finance.mcs.modules.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xiaoleilu.hutool.json.JSONObject;
import org.apache.axis2.databinding.types.soapencoding.DateTime;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("utilTools")
@SuppressWarnings({"unchecked","rawtypes"})
public class UtilTools {

    /** 日期格式 yyyyMMdd */
    public static final String DATE_FMT_DATE = "yyyyMMdd";
    /** 时间格式 HHmmss */
    public static final String DATE_FMT_TIME = "HHmmss";
    /** 日期+时间格式 yyyyMMddhhmmss */
    public static final String DATE_FMT_DATETIME = "yyyyMMddHHmmss";
    public static final SimpleDateFormat df = new SimpleDateFormat( "yyyyMMddHHmmss");
    public static final SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat time = new SimpleDateFormat("HHmmss");

    /**
     * 对象是否为空
     * @param obj
     * @return boolean
     */
    public static boolean isNull(Object obj){
        if(obj == null) return true;
        return false;
    }
    /**
     * 字符串是否为空
     * @param str
     * @return boolean
     */
    public static boolean isEmpty(String str){
        if(str == null) return true;
        if("".equals(str)) return true;
        if("null".equals(str)) return true;
        return false;
    }
    /**
     * 生成UUID到参数MAP
     * @param map
     * @param prefix
     */
    public static void checkSysRowId(Map map, String prefix) {
        String sys_row_id = (String) map.get("sys_row_id");
        if (sys_row_id == null || "".equals(sys_row_id)) {
            sys_row_id = UUID.randomUUID().toString();
            if (prefix != null) {
                sys_row_id = prefix + sys_row_id;
            }
            map.put("sys_row_id", sys_row_id);
        }
    }

    /**
     * List是否为空
     * @param array 列表
     * @return boolean
     */
    public static boolean isEmpty( List array){
        if(array == null) return true;
        if(array.isEmpty()) return true;
        if(array.size() == 0) return true;
        return false;
    }

    /**
     * Map是否为空
     * @param map 对象
     * @return boolean
     */
    public static boolean isEmpty( Map map){
        if(map == null) return true;
        if(map.isEmpty()) return true;
        if(map.size() == 0) return true;
        return false;
    }

    /**
     *  转换为字符串
     * @param obj
     * @return String
     */
    public static String toStr(Object obj) throws RuntimeException{
        if (obj == null) return "";
        if ("null".equals(obj)) return "";

        if (obj instanceof BigDecimal) {
            BigDecimal bigObj = (BigDecimal) obj;
            return bigObj.toString();
        }

        return String.valueOf(obj);
    }


    /**
     * 校验入参是否缺少，是否为空
     * @param keys 入参名称，字符串数组
     * @param paramsMap	传入的参数
     * @return
     */
    public static String isParamsEmpty(String[] keys,Map<String,Object> paramsMap) throws RuntimeException{
        for (String key : keys) {
            if (isNull(paramsMap.get(key))) {
                return key;
            }
            if (isEmpty(toStr(paramsMap.get(key)))) {
                return key;
            }
        }
        return null;
    }


    /**
     * String类型转换为BigDecimal类型，默认保留2位小数
     * 建议：double类型，float类型先转换为String类型，防止精度丢失
     * @param num 数值
     * @param precision 精度，默认为2位小数
     * @param roundingMode 舍入模式
     * 		    4-eg:保留两位小数,舍弃数值>=5进位(1.015 >> 1.02;1.0149 >> 1.01);
     * 			5-eg:保留两位小数,舍弃数值  >5进位 (1.015 >> 1.01;1.0151 >> 1.02);
     * 			6-eg:保留两位小数,舍入结果如(1.015 >> 1.02;1.025 >> 1.02)；即进位数为奇数舍入模式同4，进位数为偶数舍入模式同5
     * @return BigDecimal
     */
    public static BigDecimal toBigDec(String num,int precision,int roundingMode) throws RuntimeException{
        if(isNull(precision)) precision = 2;
        if(isNull(roundingMode)) roundingMode = BigDecimal.ROUND_HALF_UP;//四舍五入
        BigDecimal bigDec = new BigDecimal(num);
        bigDec = bigDec.setScale(precision, roundingMode);
        return bigDec;
    }

    /**
     * Object类型转换为BigDecimal类型
     * @param num 数值
     * @param precision 精度，默认为2位小数
     * @param roundingMode 舍入模式
     * 		    4-eg:保留两位小数,舍弃数值>=5进位(1.015 >> 1.02;1.0149 >> 1.01);
     * 			5-eg:保留两位小数,舍弃数值  >5进位 (1.015 >> 1.01;1.0151 >> 1.02);
     * 			6-eg:保留两位小数,舍入结果如(1.015 >> 1.02;1.025 >> 1.02)；即进位数为奇数舍入模式同4，进位数为偶数舍入模式同5
     * @return BigDecimal
     */
    public static BigDecimal toBigDec(Object num,int precision,int roundingMode) throws RuntimeException{
        if(isNull(precision)) precision = 2;
        if(isNull(roundingMode)) roundingMode = BigDecimal.ROUND_HALF_UP;//四舍五入
        BigDecimal bigDec = new BigDecimal(toStr(num));
        bigDec = bigDec.setScale(precision, roundingMode);
        return bigDec;
    }

    /**
     * map中的值全部转换为String类型
     * @param (Map<String,Object>)paramsMap
     * @return Map<String,String>
     */
    public static Map<String,String> obj2StrMap(Map<String,Object> paramsMap) throws RuntimeException{
        Map<String, String> rsMap = new HashMap<String, String>();
        Iterator it = paramsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String mapKey = toStr(entry.getKey());
            String mapValue = toStr(entry.getValue());
            rsMap.put(mapKey, mapValue.trim());
        }
        return rsMap;
    }


//    /**
//     * json字符串转换为java Map对象
//     * @param jsonStr
//     * @return
//     */
//    public static Map<String,Object> json2map(String jsonStr) throws WsRuntimeException{
//        Map<String, Object> jsonMap = new HashMap<String, Object>();
//        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
//        Iterator it = jsonObj.keys();
//        while (it.hasNext()) {
//            String name = (String) it.next();
//            jsonMap.put(name, jsonObj.get(name));
//        }
//        return jsonMap;
//    }

    /**
     * 解析jsonarray to list
     * @param jsonStr
     */

//    public static List<Map<String,Object>> getJSONArray(String jsonStr) throws RuntimeException{
//        JSONArray jsonArray = JSON.parseArray(jsonStr);
//        List<Map<String, Object>> payWayList = new ArrayList<Map<String, Object>>();
//
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject jsonObject = JSONObject.fromObject(jsonArray
//                    .getString(i).toString());
//            HashMap map = new HashMap();
//            Iterator it = jsonObject.keys();
//            while (it.hasNext()) {
//                String name = (String) it.next();
//                map.put(name, jsonObject.get(name));
//            }
//            payWayList.add(map);
//        }
//        return payWayList;
//    }

    /**
     * 获取当前系统上一自然日时间
     * @return String
     */
    public static String getPreTime() {

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss"); //设置时间格式
        String preTime = sdf.format(dBefore);    //格式化前一天
        System.out.println(preTime);

        return preTime;
    }

    /**
     * 获取当前系统时间
     * @return String
     */
    public static String getSysTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date();
        String strDatetime 	= df.format(curDate);
        return strDatetime;
    }
    /**
     * 获取当前系统时间
     * @param pattern 日期格式化模版
     * @return String
     */
    public static String getSysTime(String pattern){
        if(isNull(pattern)) return getSysTime();
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date curDate = new Date();
        String strDatetime 	= df.format(curDate);
        return strDatetime;
    }

    /**
     * 生成 length长度的随机码
     * @param a
     * @param pwdLength
     * @return int
     */
    public static  String getRandomCode(BigDecimal a ,int length){
        Random random = new Random();
        BigDecimal b = new BigDecimal( random.nextInt(10) );
        BigDecimal ten = new BigDecimal(10);
        BigDecimal max = new BigDecimal(1);
        max = max.scaleByPowerOfTen(length-1);//10的n-1幂
        a = a.multiply(ten).add(b);
        while (max.compareTo(a) > 0){
            return UtilTools.getRandomCode(a,length);
        }
        return a.toString();
    }


    /**
     * 是否是手机号
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile){
        return isNum(mobile,11);
    }


    /**
     * 是否是数字
     * @param str
     * @param length
     * @return
     */
    public static boolean isNum(String str,int length) throws RuntimeException{
        boolean flag  = length < 1 ;
        String module = flag ? "^([\\d])$" : "^([\\d]{"+length+"})$" ;
        Pattern p = Pattern.compile(module);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 登录名校验
     * 必须为1-length长度的字符串，首字母必须为英文字母，区分大小写
     * @param str
     * @return
     */
    public static boolean isMatcheLoginName(String str,int length) throws RuntimeException{
        Pattern p = Pattern.compile("^[a-zA-Z][0-9a-zA-Z_]{1,"+ length +"}");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 是否超出范围
     * @param str
     * @param length
     * @return
     */
    public static boolean isOutOfRange(String str,int length) throws RuntimeException{
        return str.length() > length;
    }



//    /**
//     * map 转换为对应的实体
//     * @param entityMap
//     * @param object
//     * @throws WsRuntimeException
//     */
//    public static void convert(Map entityMap,Object object) throws WsRuntimeException{
//        Map paraMap = UtilTools.obj2StrMap(entityMap);
//        try {
//            BeanUtils.populate(object, paraMap);
//        } catch (IllegalAccessException e) {
//            throw new WsRuntimeException(e);
//        } catch (InvocationTargetException e) {
//            throw new WsRuntimeException(e);
//        }
//    }
//
//    /**
//     * bean 转换为Map
//     * @param bean
//     * @return
//     * @throws WsRuntimeException
//     */
//    public static Map describe(Object bean) throws WsRuntimeException{
//        try {
//            return BeanUtils.describe(bean);
//        } catch (Exception e) {
//            throw new WsRuntimeException(e);
//        }
//    }

    /**
     * 解析int对象
     * @param intObj
     * @return
     * @throws WsRuntimeException
     */
    public static int parseInt(Object intObj) throws RuntimeException{
        return Integer.parseInt(toStr(intObj));
    }

    /**
     * 不大于0
     *
     * @param number
     * @return
     */
    public static boolean notGreaterZero(BigDecimal number) {
        return number.compareTo(new BigDecimal(0)) < 1;
    }
    /**
     * 检验是否为纯数字的字符串
     * @param str
     * @return
     */
    public static boolean onlyNumStr(String str){
        if(isEmpty(str)){
            return false;
        }
        String reg = "^([0-9]+)|((-?\\d+)(\\.\\d+)?)$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    /**
     * 校验是否为大于0的数字
     * @param keys 入参名称，字符串数组
     * @param paramsMap	传入的参数
     * @return
     */
    public static String onlyNumArray(String[] keys,Map paramsMap) throws RuntimeException{
        for (String key : keys) {
            if (!onlyNumStr(toStr(paramsMap.get(key)))) {
                return key;
            }
            if (notGreaterZero(toBigDecimal(toStr(paramsMap.get(key))))) {
                return key;
            }

        }
        return null;
    }

    /**
     * 数值类对象转换为BigDecimal
     * @param obj
     * @return
     */
    public static BigDecimal toBigDecimal(Object obj) throws RuntimeException{
        String strObj = toStr(obj);
        BigDecimal decStrObj = new BigDecimal(0);
        decStrObj.setScale(2, RoundingMode.HALF_UP);//保留两位小数,四舍五入
        try {
            decStrObj = new BigDecimal(strObj);
            decStrObj = decStrObj.setScale(2,RoundingMode.HALF_UP);//保留两位小数,四舍五入
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decStrObj;
    }

    /**
     * 检查一个数组中是否包含某个特定的值
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean useLoop(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue)) {
                return true;
            }
        }
        return false;
    }


//    /**
//     * 将javabean实体类转为map类型，然后返回一个map类型的值
//     * @param obj
//     * @return
//     */
//    public static Map<String, Object> entityToMap(Object obj) {
//        Map<String, Object> params = new HashMap<String, Object>(0);
//        try {
//            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
//            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
//            for (int i = 0; i < descriptors.length; i++) {
//                String name = descriptors[i].getName();
//                if (!"class".equals(name)) {
//                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return params;
//    }
    /**
     * 比较两个日期之间的大小
     *
     * @param d1
     * @param d2
     * @return 前者大于后者返回true 反之false
     */
    public static boolean compareDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        int result = c1.compareTo(c2);
        if (result >= 0)
            return true;
        else
            return false;
    }

    /**
     * 判断是否为正确日期
     * @param str        日期字符串
     * @param dateFormat 日期格式
     * @return
     */
    public static boolean isDate(String str, String dateFormat) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(dateFormat);
            Date d = f.parse(str);
            String s = f.format(d);
            return s.equals(str);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 移除map中不包含keys的键值对
     * @param keys
     * @param param
     * @throws WsRuntimeException
     */
    public static void remove(String[] keys, Map param) throws RuntimeException{
        List keyList = new ArrayList();
        Iterator it = param.keySet().iterator();
        while (it.hasNext()) {
            String mapKey = (String) it.next();
            boolean flag = false;
            for (String key : keys) {
                if( mapKey.equals(key) ){
                    flag = true;
                }
            }
            if( !flag ){
                keyList.add(mapKey);
            }
        }
        for (Object obj : keyList) {
            param.remove(obj);
        }
    }

    /**
     * 移除map中包含keys的空键值对
     * @param keys
     * @param param
     * @throws WsRuntimeException
     */
    public static void removeEmpty(String[] keys, Map param) throws RuntimeException{
        for (String key : keys) {
            String val = UtilTools.toStr(param.get(key));
            if(UtilTools.isEmpty(val)){
                param.remove(key);
            }
        }
    }

    /**
     * 移除入参map中传入的空字符串
     * @param param
     * @throws WsRuntimeException
     */
    public static void removeEmptyStr(Map param) throws RuntimeException{
        Iterator it = param.keySet().iterator();
        while (it.hasNext()) {
            String key = (String)it.next();
            Object val = param.get(key);
            if( val instanceof String){
                if( UtilTools.isEmpty(UtilTools.toStr(val))){
                    it.remove();
                }
            }
        }
    }

    /**
     * 检查传入的金额，金额必须大于0
     * @param num
     * @return
     * @throws WsRuntimeException
     */
    public static boolean checkMoney(Object num) throws RuntimeException{
        String str = toStr(num);
        Pattern p = Pattern.compile("^(([1-9]+)|([1-9][0-9]+)|([0-9]+\\.[0-9]{1,2}))$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 模糊会员真实姓名
     * @param memberName 真实姓名
     * @return 模糊后的姓名
     */
    public static String fuzzyMemberName(String memberName){
        String p = "******************";
        if(!isEmpty(memberName)){
            memberName = p.substring(0,2)+memberName.substring(memberName.length()-1);
        }else{
            memberName = "***";
        }

        return memberName;
    }

    /**
     * 模糊证件号码
     * @param certificateNo 证件号码
     * @return
     */
    public static String fuzzyCertNo(String certificateNo){
        String p = "******************";
        int lencno = certificateNo.length();

        if(lencno < 3){
            certificateNo = p;
        }

        if(lencno >=3 && lencno <=6){
            certificateNo = p.substring(0,16)+certificateNo.substring(lencno-2);
        }
        if(lencno > 6){
            certificateNo = certificateNo.substring(0,4)+p.substring(0,12)+certificateNo.substring(lencno-3);
        }
        return certificateNo;
    }

    /**
     * 模糊手机号码
     * @param mobile 手机号码
     * @return
     */
    public static String fuzzyMobile(String mobile){
        String p = "******************";
        int lenphoneno = mobile.length();
        if(!isEmpty(mobile)){
            mobile = p.substring(0,8)+mobile.substring(lenphoneno-3);
        }else{
            mobile = p;
        }
        return mobile;

    }

    /**
     * 模糊银行卡信息(只显示后4位数)
     * @param bankaccount 银行卡号
     * @return
     */
    public static String fuzzyBankAccount(String bankaccount){
        String p = "******************";
        int lencno = bankaccount.length();
        if(lencno <= 4){
            bankaccount = p;
        }
        if(lencno > 4 ){
            bankaccount = p.substring(0,16)+bankaccount.substring(lencno-4);
        }
        return bankaccount;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字长度为2,英文字符长度为1
     *
     * @param String
     *            s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static double getLength(String s) {
        double valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            String temp = s.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为2
                valueLength += 2;
            }
            else {
                // 其他字符长度为1
                valueLength += 1;
            }
        }
        // 进位取整
        return Math.ceil(valueLength);
    }

    /**
     * 金额大于0
     * @param amount
     * @return
     * @throws WsRuntimeException
     */
    public static boolean aboveZero(BigDecimal amount) throws RuntimeException{
        BigDecimal zero = new BigDecimal(0);
        int a = amount.compareTo(zero);
        if( a == 1){
            return true;
        }
        return false;
    }

    /**
     * 判断是否为手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if(isEmpty(mobiles)){
            return false;
        }
        String reg = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /**
     * 判断是否为电话号码
     * @param
     * @return
     */
    public static boolean isTelNO(String tel) {
        if(isEmpty(tel)){
            return false;
        }
        String reg = "\\d{3}-\\d{7,8}|\\d{4}-\\{7,8}";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(tel);
        return m.matches();
    }
    /**
     * 是否邮政编码
     * @param postalcode
     * @return
     */
    public static boolean isPostalCode(String postalcode) {
        if(isEmpty(postalcode)){
            return false;
        }
        String reg = "[1-9]\\d{5}(?!\\d)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(postalcode);
        return m.matches();
    }

    /**
     * @MethodName: add
     * @Param: AmountUtil
     * @Author: WANGWJ
     * @Date:
     * @Return:
     * @Descb: 日期累加
     * @Throws:
     */
    public static Date add(Date date,int type,int value){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }
    /**
     * 移除map中keys数组中所有的键值对
     * @param keys
     * @param param
     * @throws
     */
    public static void removeKeys(String[] keys, Map param) throws RuntimeException{
        for (String key : keys) {
            param.remove(key);
        }
    }

    /**
     * 根据传入的年、月，获取当月的开始日期和结束日期
     * @param year  2016
     * @param mouth 7
     * @return 20160701 20160731
     */
    public static Map<String, String> getMouth(int year, int mouth) {
        int dayCount = 0;
        mouth = mouth < 1 ? 1 : mouth;
        mouth = mouth > 12 ? 12 : mouth;
        Calendar cl = Calendar.getInstance();// 实例化一个日历对象
        cl.set(Calendar.YEAR, year);//
        cl.set(Calendar.MONTH, mouth - 1);//
        dayCount = cl.getActualMaximum(Calendar.DATE);
        Map<String, String> dateMap = new HashMap<String, String>();
        String mouth1 = (mouth + 1) > 10 ? mouth + "" : "0" + mouth;
        dateMap.put("startDate", year + "" + mouth1 + "01");
        dateMap.put("endDate", year + "" + mouth1 + "" + dayCount);
        return dateMap;
    }


    public static boolean checkRule(String reg , String data_use){
        byte[] temp_base64 = null;
        BASE64Decoder decoder = new BASE64Decoder();

        //当获得加密的正则表达式json串不为空时，将正则表达式解密
        if(reg != null && !"".equals(reg)){
            try {
                temp_base64 = decoder.decodeBuffer(reg);
                reg = new String(temp_base64, "utf-8");
                reg=reg.replace("\\", "\\\\");
                //解密之后不为空将json串转成map
                if(reg != null && !"".equals(reg)){
                    Map map_temp_base64 = new HashMap();
                    map_temp_base64 = JSON.parseObject(reg);
                    //在map中获得正则表达式
                    if(map_temp_base64 != null && map_temp_base64.size() != 0){
                        reg = (String)map_temp_base64.get("reg");
                        if(reg != null && !"".equals(reg)){
                            reg = reg.replace("\\\\", "\\");
                        }else{
                            return true;
                        }
                    }else{
                        return true;
                    }

                }

                if(reg != null && !"".equals(reg)){
                    if(data_use == null){
                        data_use = "";
                    }
                    data_use = data_use.trim();
                    //判断是否符合正则表达式
                    Pattern p = Pattern.compile(reg);
                    Matcher m = p.matcher(data_use);
                    if(m.matches() == false){
                        return false;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }  catch (IOException e1){
                e1.printStackTrace();
                return false;
            }

        }
        return true;
    }
}
