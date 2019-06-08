package com.dunzung.cloud.framework.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 * 日期处理
 *
 * @author duanzj
 */
public class DateUtils {

    public final static String DATE_PATTERN = "yyyy-MM-dd";
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_STR_PATTERN = "yyyyMMddHHmmss";
    public final static String DATE_HHTIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static Date strToDate(String str) {
        return strToDate(str, DATE_PATTERN);
    }

    public static Date strToDate(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToStr(Date date,String pattern) {
        return format(date, pattern);
    }

    public static String format(Date date, String pattern) {
        try{
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }catch (Exception e){
            return null;
        }
    }

    public static String dateStrToStr(String dateStr) {
        try{
            Date date=strToDate(dateStr,DATE_HHTIME_PATTERN);
            return dateToStr(date,DATE_STR_PATTERN);
        }catch (Exception e){
            return null;
        }
    }



}
