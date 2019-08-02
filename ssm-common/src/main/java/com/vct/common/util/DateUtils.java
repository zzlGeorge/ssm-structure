package com.vct.common.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtils {
    private final static Logger logger = Logger.getLogger(DateUtils.class);
    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 一天时间戳
     */
    public final static long ONE_DAY_TIME_STAMP = 1000 * 60 * 60 * 24;
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 日期字符转日期格式
     *
     * @param date
     * @return
     */
    public static Date string2Date(String date) {
        if (StringUtils.isBlank(date)) {
            logger.error("日期字符串为空！");
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            try {
                return df.parse(date + " 00:00:00");
            } catch (ParseException e1) {
                logger.error("日期字符转Date异常！", e);
                throw new RuntimeException("日期解析异常！dateStr=" + date, e);
            }
        }
    }

    /**
     * 计算距离现在多久，非精确
     *
     * @param date
     * @return
     */
    public static String getTimeBefore(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        } else if (hour > 0) {
            r += hour + "小时";
        } else if (min > 0) {
            r += min + "分";
        } else if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 计算距离现在多久，精确
     *
     * @param date
     * @return
     */
    public static String getTimeBeforeAccurate(Date date) {
        Date now = new Date();
        long l = now.getTime() - date.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day > 0) {
            r += day + "天";
        }
        if (hour > 0) {
            r += hour + "小时";
        }
        if (min > 0) {
            r += min + "分";
        }
        if (s > 0) {
            r += s + "秒";
        }
        r += "前";
        return r;
    }

    /**
     * 获取目标日期加减天数后的日期
     *
     * @param date
     * @param diff
     * @return
     */
    public static Date getDateDiff(Date date, int diff) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime() + diff * DateUtils.ONE_DAY_TIME_STAMP);
    }

    /**
     * 获取日期字符串的加减
     *
     * @param date
     * @param diff
     * @return
     */
    public static String getDateStringDiff(String date, int diff) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return DateUtils.format(DateUtils.getDateDiff(DateUtils.string2Date(date), diff), DateUtils.DATE_TIME_PATTERN);
    }

    /**
     * 比较时间 如：09:23:21 大于 09:23:00
     *
     * @param t1
     * @param t2
     * @return
     */
    public static boolean compareTime(String t1, String t2) {
        if (StringUtils.isBlank(t1) || StringUtils.isBlank(t2)) {
            return false;
        }
        t1 = t1.trim();
        t2 = t2.trim();
        String[] split1 = t1.split(":");
        String[] split2 = t2.split(":");
        if (split1.length != 3 || split2.length != 3) {
            return false;
        }

        for (int i = 0; i < split1.length; i++) {
            if (Integer.valueOf(split1[i]) > Integer.valueOf(split2[i])) {
                return true;
            }
        }

        return false;
    }
}
