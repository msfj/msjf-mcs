/*
 * Copyright @ 1998-2017 Shenzhen Kingdom Technology CO.,LTD.
 * All Rights Reserved
 *
 *      http://www.szkingdom.com/
 *      http://www.szrhtj.com/
 *
 */

package com.szkingdom.rhtj.kpfsp.core.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;


public final class StringUtil {
    
    
    /**
     * 转换对象为字符串类型
     * 
     * @param obj
     * @return
     */
    public static String valueOf(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            String strObj = obj.toString().trim();
            if ("null".equalsIgnoreCase(strObj)) {
                return "";
            }
            return strObj;
        }
        if (obj instanceof BigDecimal) {
            BigDecimal bigObj = (BigDecimal) obj;
            return bigObj.toString();
        }

        return obj.toString().trim();
    }

    /**
     * 
     * @Title: getMatcherStringByRegex
     * @Description: 根据正则表达式获取第一个匹配的字符串
     * @param regex
     *            正则表达式
     * @param input
     *            输入字符串
     * @return 第一个匹配的字符串
     * @throws
     */
    public static  String getMatcherStringByRegex(String regex, String input) {
        String matcherStr = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matcherStr = matcher.group();
            return matcherStr;
        }
        return matcherStr;
    }
    
    /**
     * 
    
     * @Description: 根据传入的json字符串返回对应的Map
     * @param regex 正则表达式
     * @param input 输入字符串
     * @return map
     * @throws
     */
    @SuppressWarnings("unchecked")
	public static  Map<String,Object> getMapByString(String input) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map = (Map<String, Object>) JSON.parse(input);
        return map;
    }
    
}
