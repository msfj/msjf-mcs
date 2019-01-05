
package com.msjf.finance.mcs.modules.utils;

import com.msjf.finance.mcs.common.response.Response;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CheckUtil {



    public static boolean checkNull(Object b, String errmsg, Response rs) {
        boolean flag = true;
        if (b != null) {
            if (b instanceof String) {
                String str = (String) b;
                flag = ("").equals(str) ? true : ("null".equalsIgnoreCase(str) ? true : false);
            } else {
                flag = false;
            }
        }
        if (flag) {
            rs.fail(errmsg + "不能为空");
        }
        return flag;
    }
    // 判断是否为空
    public static boolean isNull(Object b) {
        if (b == null) {
            return true;
        }

        if (b instanceof String) {
            String strObj = StringUtil.valueOf(b);
            return "".equals(strObj);
        }
        return false;
    }

    /**
     * 字符是否为空或"null"
     * 
     * @param str
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null) {
            return true;
        }
        if ("null".equalsIgnoreCase(str)) {
            return true;
        }
        if ("".equals(str)) {
            return true;
        }
        return false;
    }

    // 判断Map是否为空
    public static boolean isNull(Map<?, ?> mp) {
        if (mp == null || mp.isEmpty() || mp.size() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断list 是否为null，size<=0
     *
     * @param list
     * @return
     */
    public static boolean isNull(List<?> list) {
        if (list == null || list.isEmpty() || list.size() <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否只有数字及字母
     *
     * @param str
     * @return
     */
    public static boolean isNumChar(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                continue;
            }
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 判断是否为整数
     *
     * @param str
     * @return true 是  false 否
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("[0-9]+(\\.0+)?");
        Matcher matcher = pattern.matcher((CharSequence) str);
        return matcher.matches();
    }

    /**
     * 判断是否为浮点数
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    /**
     * 判断是否为正确日期,
     *
     * @param str，格式为：yyyyMMdd
     * @return
     */
    public static boolean isDate(String str) {
        return isDate(str, "yyyyMMdd");
    }

    /**
     * 判断是否为正确日期
     *
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
     * @return
     * @throws
     * @Title: checkPattern
     * @Description: 格式检查
     */
    public static boolean checkPattern(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(input);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return
     * @throws
     * @Title: checkPattern
     * @Description: 格式包含
     */
    public static boolean checkContain(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(input);
        if (isNum.find()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 检查日期格式是否正确(YYYYMMDDHHMMSS)
     *
     * @param
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        try {
            if (str.length() != 14) {
                return false;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            df.setLenient(false);
            df.parse(str);
            return true;
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 检查日期格式是否正确(YYYYMMDD)
     *
     * @param
     * @return
     */
    public static boolean isValidShortDate(String str) {
        boolean convertSuccess = true;
        try {
            SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
            date.setLenient(false);
            date.parse(str);
        } catch (Exception e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static String changeDoubleToString(double d) {
        DecimalFormat fm1 = new DecimalFormat("#,###.00");
        return fm1.format(d);
    }

    /**
     * 检查手机号
     *
     * @param mobile
     * @param
     * @return
     */
    public static boolean checkMoblie(String mobile) {
        //检查手机号码必须为纯数字
        if (!checkPattern("^^\\d*$", mobile)) {
            return false;
        }
        return true;
    }

    /*
     * 交易权限校验接口
     * @customerno 客户内码
     * @businesscode 业务码
     * @catid 产品类别
     * @fundcode 产品代码
     * @individ 客户是机构还是个人
     */
    public static boolean checkTradeAuthority(String customerno, String businesscode, String catid, String fundcode,
            String individ) {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        HashMap<String, Object> publicMap = new HashMap<String, Object>();
        publicMap.put("customerno", customerno);
        publicMap.put("businesscode", businesscode);

        publicMap.put("categoryid", catid);
        publicMap.put("commodityid", fundcode);
        publicMap.put("custtype", individ);
        /*try {
            //验证白名单
            mapList = CommonPerUtil.queryListMap("check_trade_authority_whitepaper", publicMap);
            if (mapList.size() == 0) {
                //白名单权限验证不通过，无权限
                //转让不限制白名单控制
                if (!businesscode.equals(MacroDefine.BUSINESS_TRD.BUSINESS_APP_TRANS_SALE.getValue())) return false;
            }
            //验证交易权限
            mapList = CommonPerUtil.queryListMap("check_trade_authority", publicMap);
        } catch (Exception e) {
            LogUtil.error(e);
        }*/
        if (mapList.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断至多两位小数且大于0
     *
     * @param obj
     * @return boolean (至多两位小数且大于0返回true)
     */
    public static boolean judgeTwoDecimal(Object obj) {
        boolean flag = false;
        try {
            if (obj != null) {
                String source = obj.toString();
                // 判断是否是整数或者是携带一位或者两位的小数
                Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,2})?)$");
                if (pattern.matcher(source).matches()) {
                    flag = true;
                    BigDecimal sourceB = new BigDecimal(source);
                    if (sourceB.compareTo(new BigDecimal(0)) == 0) {
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;

    }

    /**
     * 验证网址Url
     *
     * @param url 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isUrl(String url) {
        try {
            URL urls = new URL(url);
            URI uri = urls.toURI();
            if (uri.getScheme().equals("https") || uri.getScheme().equals("http")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
        //String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        // return Pattern.matches(regex, url);
    }

    /**
     * 校验入参是否缺少，是否为空
     * @param keys 入参名称，字符串数组
     * @param paramsMap 传入的参数
     * @return
     */
    public static String isParamsEmpty(String[] keys, Map<String, Object> paramsMap) throws RuntimeException {
        for (String key : keys) {
            if (isNull(paramsMap.get(key))) {
                return key;
            }
            if (CheckUtil.isNull(StringUtil.valueOf(paramsMap.get(key)))) {
                return key;
            }
        }
        return null;
    }

    /**
     * 是否是数字
     * @param str
     * @param length
     * @return
     */
    public static boolean isNum(String str, int length) throws RuntimeException {
        boolean flag = length < 1;
        String module = flag ? "^([\\d])$" : "^([\\d]{" + length + "})$";
        Pattern p = Pattern.compile(module);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static void main(String[] args) {

        System.out.println(CheckUtil.isInteger("12q345678901"));
    }

}
