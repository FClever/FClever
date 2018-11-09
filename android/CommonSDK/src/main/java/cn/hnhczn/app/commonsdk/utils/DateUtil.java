package cn.hnhczn.app.commonsdk.utils;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fengmigncong on 2017/10/23.
 */

public class DateUtil {

    public static SimpleDateFormat m = new SimpleDateFormat("MM", Locale.getDefault());
    public static SimpleDateFormat d = new SimpleDateFormat("dd", Locale.getDefault());
    public static SimpleDateFormat e = new SimpleDateFormat("EEEE", Locale.getDefault());
    public static SimpleDateFormat md = new SimpleDateFormat("MM-dd", Locale.getDefault());
    public static SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat ymdSlash = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    public static SimpleDateFormat ymdDot = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
    public static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat ymdhmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    public static SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static SimpleDateFormat hm = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static SimpleDateFormat mdhm = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
    public static SimpleDateFormat mdhmLink = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());

    /**
     * 判断某一时间是否在一个区间内
     *
     * @param sourceTime 时间区间,半闭合,如[10:00-20:00)
     * @param curTime    需要判断的时间 如10:00
     * @return
     * @throws IllegalArgumentException
     */
    public static boolean isInTime(String sourceTime, String curTime) {
        if (sourceTime == null || !sourceTime.contains("-") || !sourceTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }
        if (curTime == null || !curTime.contains(":")) {
            throw new IllegalArgumentException("Illegal Argument arg:" + curTime);
        }
        String[] args = sourceTime.split("-");
        try {
            long now = hm.parse(curTime).getTime();
            long start = hm.parse(args[0]).getTime();
            long end = hm.parse(args[1]).getTime();
            if (args[1].equals("00:00")) {
                args[1] = "24:00";
            }
            if (end < start) {
                return now < end || now >= start;
            } else {
                return now >= start && now < end;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Illegal Argument arg:" + sourceTime);
        }

    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param DateStr1 DateStr1
     * @param DateStr1 DateStr1
     * @return
     */
    public static boolean isSameDate(String DateStr1, String DateStr2) {
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = ymdhms.parse(DateStr1);
            date2 = ymdhms.parse(DateStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

        return isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断 日期是昨天,今天还是明天 -1 0 1
     *
     * @param dateStr
     * @return
     */
    public static int YESTERDAY_INT = -1;
    public static int TODAY_INT = 0;
    public static int TOMORROW_INT = 1;

    public static int isYTM(String dateStr) {
        try {
            String nowDateStr = getDate(ymdhms);
            String nowDateMinStr = nowDateStr + " 00:00:00";
            String nowDateMaxStr = nowDateStr + " 23:59:59";
            Date nowDateMin = ymdhms.parse(nowDateMinStr);
            Date nowDateMax = ymdhms.parse(nowDateMaxStr);
            Date date = ymdhms.parse(dateStr);

            long nowDateMinLong = nowDateMin.getTime();
            long nowDateMaxLong = nowDateMax.getTime();
            long dateLong = date.getTime();
            if (dateLong < nowDateMinLong) {
                return YESTERDAY_INT;
            } else if (dateLong >= nowDateMinLong && dateLong <= nowDateMaxLong) {
                return TODAY_INT;
            } else if (dateLong > nowDateMaxLong) {
                return TOMORROW_INT;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TODAY_INT;
    }


    /**
     * 获取现在时间
     *
     * @return返回字符串格式
     */
    public static String getDate(SimpleDateFormat formatter) {
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /*
    * 获取星期
    * @param dateStr
    * @param type 1:
    * @return 返回周几
    * */
    public static String getWeek(String DateStr) {
        try {
            Date date = ymdhms.parse(DateStr);
            SimpleDateFormat dateFm = e;
            String week = "";
            switch (dateFm.format(date)) {
                case "星期一":
                    week = "周一";
                    break;
                case "星期二":
                    week = "周二";
                    break;
                case "星期三":
                    week = "周三";
                    break;
                case "星期四":
                    week = "周四";
                    break;
                case "星期五":
                    week = "周五";
                    break;
                case "星期六":
                    week = "周六";
                    break;
                case "星期日":
                    week = "周日";
                    break;
                default:
                    week = "";
                    break;
            }
            return ymd.format(date) + "（" + week + "）";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /*
   * 获取星期
   * @param dateStr
   * @return 返回int
   * */
    public static int getCurrentWeek() {
        int week;
        switch (e.format(new Date())) {
            case "星期一":
                week = 0;
                break;
            case "星期二":
                week = 1;
                break;
            case "星期三":
                week = 2;
                break;
            case "星期四":
                week = 3;
                break;
            case "星期五":
                week = 4;
                break;
            case "星期六":
                week = 5;
                break;
            case "星期日":
                week = 6;
                break;
            default:
                week = 0;
                break;
        }
        return week;
    }

    /**
     * 获取过去第几天的日期(- 操作) 或者 未来 第几天的日期( + 操作)
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        return ymdhms.format(today);
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /*获取服务区时间*/
    public static Date getNetDate() {
        URL url = null;//取得资源对象
        Date date = null;
        try {
            url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            date = new Date(ld); //转换为标准时间对象
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
        return date;
    }

    /**
     * 年月日时间格式转换
     *
     * @param date
     **/
    public static String formatToStr(Date date, SimpleDateFormat format) {
        return format.format(date);
    }

    public static Date formatToDate(Date d, SimpleDateFormat format) {
        Date date = null;
        try {
            date = format.parse(format.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatToStr(String dateStr,SimpleDateFormat before, SimpleDateFormat after) {
        Date date = null;
        try {
            date = before.parse(dateStr);
            return after.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatToDate(String dateStr, SimpleDateFormat after) {
        Date date = null;
        try {
            date = after.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /*
    * 获取时分
    * @param dateString
    * @return
    * */
    public static String getHHmmByDateString(String dateString) {
        String str = "00:00";
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(dateString);
        Date date = null;
        if (isNum.matches()) {
            date = new Date(Long.parseLong(dateString));
            return hm.format(date);
        } else {
            try {
                date = ymdhms.parse(dateString);
                return hm.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

}
