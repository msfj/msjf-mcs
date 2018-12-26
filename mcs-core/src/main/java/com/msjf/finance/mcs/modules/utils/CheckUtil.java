
package com.szkingdom.rhtj.kpfsp.core.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.websuites.core.exception.WsRuntimeException;
import com.websuites.core.response.IResult;
import com.websuites.core.response.Result;
import com.websuites.utils.LogUtil;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 业务无关通用检查方法
 *
 * @author  lijs
 * @time 2018年3月5日18:30:322
 * @version 1.0
 */
public final class CheckUtil {

    /**
     * @author liaowc
     * @Title: checkNoNull
     * @Description: 检查参数是否为空
     */
    public static boolean checkNull(Object b, String errmsg, IResult rs) {
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
            rs.failed(errmsg + "不能为空");
        }
        return flag;
    }

    /**
     * @author liaowc
     * @Title: checkArgument
     * @Description: 检查参数是否满足条件
     */
    public static boolean checkArgument(boolean expression, String errcode, IResult rs) {
        if (expression) {
            String msg = getErrorDetail(errcode);
            rs.setErrorMessage(msg);
            rs.setErrorCode(errcode);
            rs.setReturnCode(errcode);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查参数是否满足条件
     *
     * @param expression
     * @param errcode
     * @param replacement 替换字符串
     * @param rs
     * @return
     */
    public static boolean checkArgument(boolean expression, String errcode, String replacement, IResult rs) {
        if (expression) {
            String msg = getErrorDetail(errcode);
            rs.setErrorMessage(msg.replace("$param$", replacement));
            rs.setErrorCode(errcode);
            rs.setReturnCode(errcode);
            return true;
        } else {
            return false;
        }
    }

    // 根据错误编码获取错误详情
    protected static String getErrorDetail(final String errcode) {
        IResult rs = new Result();
        try {
//            HashMap<String, ?> mapParam = Maps.newHashMap();
            //            BexUtil.callBexDisabledAutoClear(mapParam, "f_get_spm_errcode", rs);
            @SuppressWarnings("unchecked")
            List<HashMap<String, String>> listResult = (List<HashMap<String, String>>) rs.getResult();
            String errordetail = Iterators.find(listResult.iterator(), new Predicate<HashMap<String, String>>() {

                @Override
                public boolean apply(HashMap<String, String> arg0) {
                    return arg0.get("returncode").equals(errcode);
                }
            }).get("errordetail");
            return errordetail;
        } catch (Exception e) {
            rs.failed("获取错误码[" + errcode + "]失败!");
            LogUtil.error("获取错误码[" + errcode + "]失败!");
            throw new WsRuntimeException();
        }
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
     * 检查参数是否满足条件
     *
     * @param expression
     * @param errcode
     * @param replacement 替换字符串
     * @param rs
     * @return
     */
    public static boolean checkArgument(boolean expression, String errcode, String[] replacement, IResult rs) {
        if (expression) {
            String msg = getErrorDetail(errcode);
            for (int i = 0; i < replacement.length; i++) {
                String replacArg = replacement[i];
                msg = msg.replace("$param" + i + "$", replacArg);
            }
            rs.setErrorMessage(msg);
            rs.setErrorCode(errcode);
            rs.setReturnCode(errcode);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查日期格式是否正确(YYYYMMDDHHMMSS)
     *
     * @param sDate
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
     * @param sDate
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
     * @param rs
     * @return
     */
    public static boolean checkMoblie(String mobile, IResult rs) {
        //检查手机号码必须为纯数字
        if (checkArgument(!checkPattern("^^\\d*$", mobile), "9424", rs)) {
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
    public static String isParamsEmpty(String[] keys, Map<String, Object> paramsMap) throws WsRuntimeException {
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
    public static boolean isNum(String str, int length) throws WsRuntimeException {
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
