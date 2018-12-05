package com.liuxl.utils;


import com.liuxl.enumType.ErrorCodeEnum;
import com.liuxl.exception.CommonException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description: 时间格式工具类
 *
 * @author liuxl
 * @date 2018/12/5
 */
public class DateUtil {


    public static final String sdf1reg = "^\\d{2,4}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}$";
    public static final String sdf1str = "yyyyMMddHHmmss";

    public static final String sdf2reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
    public static final String sdf2str = "yyyy-MM-dd HH:mm:ss";

    public static final String sdf3reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$";
    public static final String sdf3str = "yyyy-MM-dd";

    public static final String sdf31reg = "^\\d{2,4}\\.\\d{1,2}\\.\\d{1,2}$";
    public static final String sdf31str = "yyyy.MM.dd";

    public static final String sdf4reg = "^\\d{2,4}\\-\\d{1,2}$";
    public static final String sdf4str = "yyyy-MM";

    public static final String sdf5reg = "^\\d{2,4}\\d{1,2}\\d{1,2}$";
    public static final String sdf5str = "yyyyMMdd";

    //日期格式 20170228
    public static final String sdf6reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$";

    public static final String sdf6str = "yyyy/MM/dd";

    //国标日期格式，如：20170608T093022
    //	public static final String sdf7reg =""(\\d{4})(\\d{2})(\\d{2})(\\T\b)(\\d{2})(\\d{2})(\\d{2})";";;
    public static final String sdf7str = "yyyyMMdd'T'HHmmss";

    public static final String dateFormatError = "输入的日期格式不正确";

    /**
     * <p/>
     * 将日期字符串解析成日期对象，支持一下格式
     * <li>yyyy-MM-dd HH:mm:ss
     * <li>yyyy-MM-dd
     * <li>yyyy/MM/dd HH:mm:ss
     * <li>yyyy/MM/dd
     * </p>
     *
     * @param str
     * @return
     */
    public static Date parse(String str){
        Date date = null;
        Pattern p1 = Pattern.compile(sdf1reg);
        Matcher m1 = p1.matcher(str);
        Pattern p2 = Pattern.compile(sdf2reg);
        Matcher m2 = p2.matcher(str);
        Pattern p3 = Pattern.compile(sdf3reg);
        Matcher m3 = p3.matcher(str);
        Pattern p31 = Pattern.compile(sdf31reg);
        Matcher m31 = p31.matcher(str);
        Pattern p4 = Pattern.compile(sdf4reg);
        Matcher m4 = p4.matcher(str);
        Pattern p5 = Pattern.compile(sdf5reg);
        Matcher m5 = p5.matcher(str);

        SimpleDateFormat sdf = null;
        try {
            if (m5.matches()) {
                sdf = new SimpleDateFormat(sdf5str);
            } else if (m1.matches()) {
                sdf = new SimpleDateFormat(sdf1str);
            } else if (m2.matches()) {
                sdf = new SimpleDateFormat(sdf2str);
            } else if (m3.matches()) {
                sdf = new SimpleDateFormat(sdf3str);
            } else if (m4.matches()) {
                sdf = new SimpleDateFormat(sdf4str);
            } else if (m31.matches()) {
                sdf = new SimpleDateFormat(sdf31str);
            } else {
                //国标日期格式
                sdf = new SimpleDateFormat(sdf7str);
            }
            if (sdf != null) {
                date = sdf.parse(str);
            }
        }catch (ParseException e){
            throw new CommonException(e, ErrorCodeEnum.PARSE_DATE_ERROR);
        }


        return date;
    }

    //获取一周前日期

    public static Date getPreviousWeek(Date date) {
        return getDate(date, -7);
    }

    /**
     * 根据日期偏移天数取得日期。offset > 0 ,往后延迟offset天， offset < 0 向前推进 offset天
     *
     * @param date:基日期
     * @param offset:日期天数偏移量
     * @return
     */
    public static Date getDate(Date date, int offset) {
        if (date == null)
            return date;
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return calendar.getTime();
    }
    /*
     *获取当年年份
     * */

    public static Integer getCurrentYear() {
        Date date = new Date();
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /*
     *获取当年年份
     * */

    public static Integer getYear(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    /*
     *获取当年年份
     * */

    public static Integer getCurrentMonth() {
        Date date = new Date();
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static Integer getMonth(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    /*
     *获取当日期是几号
     * */

    public static Integer getCurrentDay() {
        Date date = new Date();
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static Integer getDay(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /*
     * 获取当前是第几季度
     * */

    public static Integer getCurrentQuarter() {
        Date date = new Date();
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 1 && month <= 3) {
            return 1;
        } else if (month >= 4 && month <= 6) {
            return 2;
        } else if (month > 7 && month <= 9) {
            return 3;
        } else if (month > 10 && month <= 12) {
            return 4;
        }
        return null;
    }

    /**
     * 取得Calendar实例
     *
     * @return
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getCurrentDateForStr() {
        SimpleDateFormat sdf = new SimpleDateFormat(sdf2str);
        return sdf.format(new Date());
    }

    public static Long getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(sdf1str);
        return Long.valueOf(sdf.format(new Date()));
    }
    //获取不带时间的 日期

    public static String getDateNoTime() {
        return getDateNoTime(new Date());
    }
    //获取不带时间的 日期

    public static String getDateNoTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(sdf5str);

        return sdf.format(date);
    }

    //获取一天的最开始时间

    public static String getStartFirstDate() {
        return getStartFirstDate(new Date());
    }

    public static String getStartFirstDate(Date date) {
        return getDateNoTime(date) + "000000";
    }

    //获取一天的最后的时间

    public static String getEndLastDate() {
        return getEndLastDate(new Date());
    }
    //获取一天的最后的时间

    public static String getEndLastDate(Date date) {
        return getDateNoTime(date) + "235959";
    }

    public static Long dateToSysDate(String str) {
        Date date = parse(str);
        return Long.valueOf(format(date, sdf1str));
    }

    public static Integer getQuarterFirstDay(String year, String quarter) {
        if ("1".equals(quarter)) {
            return Integer.valueOf(year + "0101");
        } else if ("2".equals(quarter)) {
            return Integer.valueOf(year + "0401");
        } else if ("3".equals(quarter)) {
            return Integer.valueOf(year + "0701");
        } else if ("4".equals(quarter)) {
            return Integer.valueOf(year + "1001");
        } else if ("5".equals(quarter)) {
            return Integer.valueOf(String.valueOf(Integer.valueOf(year) + 1) + "0101");
        }
        return null;
    }

    public static long dateDiff(String type, String date1, String date2) throws ParseException {
        if (null == type || "".equals(type)) {
            type = "dd";
        }
        if (null == date1 || "".equals(date1)) {
            return 0;
        }
        if (null == date2 || "".equals(date2)) {
            return 0;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        if ("dd".equals(type)) {
            df = new SimpleDateFormat("yyyy-MM-dd");
        } else if ("hh".equals(type)) {
            df = new SimpleDateFormat("yyyy-MM-dd HH");
        } else if ("mi".equals(type)) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else if ("ss".equals(type)) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        Date d2 = df.parse(date2);
        Date d1 = df.parse(date1);
        return dateDiff(type, d1, d2);
    }

    public static long dateDiff(String type, Date d1, Date d2) {
        if (null == type || "".equals(type)) {
            type = "dd";
        }
        int param = 0;
        long diff = 0;
        if ("dd".equals(type)) {
            param = 1000 * 60 * 60 * 24;
        } else if ("hh".equals(type)) {
            param = 1000 * 60 * 60;
        } else if ("mi".equals(type)) {
            param = 1000 * 60;
        } else if ("ss".equals(type)) {
            param = 1000;
        }
        diff = d2.getTime() - d1.getTime();
        diff = diff / param;
        return diff;
    }

    public static String dateFormat(long date, String format) {
        return dateFormat(String.valueOf(date), format);
    }

    public static String dateFormat(String date, String format) {

        try {
            if (StringUtil.isEmpty(date)) {
                return "";
            } else {
                Date d = parse(date);
                SimpleDateFormat csdf = new SimpleDateFormat(format);
                return csdf.format(d);
            }
        } catch (Throwable e) {
            return "";
        }

    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static Date getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        //		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return c.getTime();
    }

    /**
     * 方法作用：将日期格式转换成8位长整形数字
     * 适用条件：比较日期大小
     *
     * @param val
     * @return
     */
    public static long getDate2Long(Date val) {
        return Long.parseLong(new SimpleDateFormat(sdf5str).format(val));
    }

    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
}
