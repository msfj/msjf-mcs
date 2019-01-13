package com.msjf.finance.mcs.modules.utils;

import com.google.common.primitives.Ints;
import com.msjf.finance.msjf.core.response.Response;
import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    // 获取系统日期及时间 formatString 格式：yyyyMMdd hh:mm:ss
    public static String getUserDate(String fromat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(fromat);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 取得指定日期 （dateFrom日期格式与strFormat必须一致）
     *
     * @param dateFrom
     *            指定日期的字符串 (20080810)
     * @param amount
     *            日期增加变量 正数为加一天，负数为减一天
     * @param strFormat
     * @return
     */
    public static String getSelfDay(String dateFrom, int amount, String strFormat) {
        SimpleDateFormat formatYMD = new SimpleDateFormat(strFormat);

        Date date = null;
        try {
            date = formatYMD.parse(dateFrom);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        return formatYMD.format(cal.getTime());
    }

    /**
     *
     * @Title: addSeconds
     * @Description: 增加秒数
     * @param str
     * @param formatter
     * @return
     * @throws
     */
//    public static String addSeconds(String str, String formatter, int seconds) {
//        DateTimeFormatter sd = DateTimeFormat.forPattern(formatter);
//        DateTime dt = DateTime.parse(str, sd);
//        DateTime dateTime = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(),
//                dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond());
//        return dateTime.plusSeconds(seconds).toString(formatter);
//    }
       public static String addSeconds(String str, String formatter, int seconds, Response rs) {
           try {
               SimpleDateFormat df = new SimpleDateFormat(formatter);
               Date date = df.parse(str);
               date.setTime(date.getTime() +seconds* 1000);
               return df.format(date);
           }catch (Exception e){
               throw new RuntimeException(e);
           }
       }

    /**
     *
     * @Title: addDays
     * @Description: 增加自然日天数
     * @param str
     *            当前日期
     * @param formatter
     *            日期格式
     * @param days
     *            天数
     * @return
     * @throws
     */
//    public static String addDays(String str, String formatter, int days) {
//        DateTimeFormatter sd = DateTimeFormat.forPattern(formatter);
//        DateTime dt = DateTime.parse(str, sd);
//        DateTime dateTime = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(),
//                dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond());
//        return dateTime.plusDays(days).toString(formatter);
//    }

    /**
     *
     * @Title: addDays
     * @Description: 增加自然日天数
     * @param str
     *            当前日期
     * @param formatter
     *            日期格式
     * @param months
     *            月数
     * @return
     * @throws
     */
//    public static String addMonths(String str, String formatter, int months) {
//        DateTimeFormatter sd = DateTimeFormat.forPattern(formatter);
//        DateTime dt = DateTime.parse(str, sd);
//        DateTime dateTime = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(),
//                dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond());
//        return dateTime.plusMonths(months).toString(formatter);
//    }

    /**
     *
     * @Title: getDaysBetween
     * @Description: 计算两个日期相差天数
     * @param beginDate
     * @param endDate
     * @param formatter
     * @return
     * @throws
     */
//    public static int getDaysBetween(String beginDate, String endDate, String formatter) {
//        DateTimeFormatter sd = DateTimeFormat.forPattern(formatter);
//        DateTime dtBegin = DateTime.parse(beginDate, sd);
//        DateTime dtEnd = DateTime.parse(endDate, sd);
//        LocalDate start = new LocalDate(dtBegin.getYear(), dtBegin.getMonthOfYear(), dtBegin.getDayOfMonth());
//        LocalDate end = new LocalDate(dtEnd.getYear(), dtEnd.getMonthOfYear(), dtEnd.getDayOfMonth());
//        int days = Days.daysBetween(start, end).getDays();
//        return days;
//    }

    /**
     *
     * @Title: getDaysBetween
     * @Description: 计算两个日期相差月数
     * @param beginDate
     * @param endDate
     * @param formatter
     * @return
     * @throws
     */
//    public static int getMonthsBetween(String beginDate, String endDate, String formatter) {
//        DateTimeFormatter sd = DateTimeFormat.forPattern(formatter);
//        DateTime dtBegin = DateTime.parse(beginDate, sd);
//        DateTime dtEnd = DateTime.parse(endDate, sd);
//        LocalDate start = new LocalDate(dtBegin.getYear(), dtBegin.getMonthOfYear(), dtBegin.getDayOfMonth());
//        LocalDate end = new LocalDate(dtEnd.getYear(), dtEnd.getMonthOfYear(), dtEnd.getDayOfMonth());
//        int months = Months.monthsBetween(start, end).getMonths();
//        return months;
//    }

    /**
     *
     * 获取当前日期当月第一天、最后一天
     * @param str
     * @param formatter
     * @param mode 1 当月第一天  -1 当月最后一天
     * @return
     */
//    public static String getDayOfMonth4CurDate(String str, String formatter, String mode) {
//        DateTimeFormatter sd = DateTimeFormat.forPattern(formatter);
//        DateTime dt = DateTime.parse(str, sd);
//        DateTime dateTime = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(),
//                dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond());
//        if ("1".equals(mode)) {
//            return dateTime.dayOfMonth().withMinimumValue().toString(formatter);
//        } else if ("-1".equals(mode)) {
//            return dateTime.dayOfMonth().withMaximumValue().toString(formatter);
//        }
//        return dateTime.toString(formatter);
//    }

    /**
     *
     * 获取当前日期当月第一天、最后一天
     * @param str
     * @param formatter
     * @param mode 1 当年第一天  -1 当年最后一天
     * @return
     */
//    public static String getDayOfYear4CurDate(String str, String formatter, String mode) {
//        DateTimeFormatter sd = DateTimeFormat.forPattern(formatter);
//        DateTime dt = DateTime.parse(str, sd);
//        DateTime dateTime = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), dt.getHourOfDay(),
//                dt.getMinuteOfHour(), dt.getSecondOfMinute(), dt.getMillisOfSecond());
//        if ("1".equals(mode)) {
//            return dateTime.dayOfYear().withMinimumValue().toString(formatter);
//        } else if ("-1".equals(mode)) {
//            return dateTime.dayOfYear().withMaximumValue().toString(formatter);
//        }
//        return dateTime.toString(formatter);
//    }

    /**
     * 字符串类型的时间 格式化为另一种格式的时间字符串
     *
     * @param srcDate "20181203153000"
     * @param oldFormat 如 "yyyyMMddHHmmss"
     * @param toFormat "yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String dateFormat(String srcDate, String oldFormat,String toFormat) {
        try {
            java.util.Date date = new SimpleDateFormat(oldFormat).parse(srcDate);
            return new SimpleDateFormat(toFormat).format(date);
        } catch (Exception e) {
            //LogUtil.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取两个日期的时间差"yyyy-MM-dd HH:mm:ss"
     */
    public static long getDiffDates(String date1, String date2, String format) {
        try {
            SimpleDateFormat dfs = new SimpleDateFormat(format);

            java.util.Date begin = dfs.parse(date1);

            java.util.Date end = dfs.parse(date2);

            return end.getTime() - begin.getTime();
        } catch (Exception e) {
            //LogUtil.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @Title: checkCurTime
     * @Description: 检查 12:00:00 <当前物理时间< 23:59:59
     * @return
     * @throws
     */
    public static boolean checkCurTime() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        String dateString = formatter.format(date);
        if (Ints.tryParse(dateString) > Ints.tryParse("120000")
                && Ints.tryParse(dateString) < Ints.tryParse("235959")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @Title: getBetweenSeconds
     * @Description: 计算两个时间相差的秒数
     * @param time1
     * @param time2
     * @param format
     * @return
     * @throws
     */
    public static long getBetweenSeconds(String time1, String time2, String format) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date d1 = simpleDateFormat.parse(time1);
            Date d2 = simpleDateFormat.parse(time2);
            long between = (d1.getTime() - d2.getTime()) / 1000;
            return between;
        } catch (Exception e) {
            //LogUtil.error("日期格式转换失败！" + e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String setTimeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyyMMddHHmmss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param date 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String setDate2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String getTimeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    /**
     * 得到当前日期时间
     * @return yyyyMMddHHmmss
     */
    public static String getCurrDateTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }
    public static void main(String[] args){
        Response rs=new Response();
        System.out.println(addSeconds("20190102110101","yyyyMMddHHmmss",11,rs));
    }

}
