package com.example.uicustomviews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * Created by LewJun on 2018/01/31.
 */
public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public static Date getToday() {
        return new Date();
    }

    public static String getDate1(Date date) {
        return sdf.format(date);
    }

    public static Date getDate1(String yyyyMmdd) {
        try {
            return sdf.parse(yyyyMmdd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getToday();
    }

    public static Date add(Date date, int day) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
}
