package com.yimin.util;

import com.yimin.enums.KlineType;
import org.joda.time.DateTime;

public class KlineTimeUtil {

    /**
     * 获取K线时间
     *
     * @param lineType K线类型
     * @return
     */
    public static DateTime getKLineTime(KlineType lineType) {
        DateTime currentTime = new DateTime();
        int year = currentTime.getYear();
        int month = currentTime.getMonthOfYear();
        int day = currentTime.getDayOfMonth();
        int hours = currentTime.getHourOfDay();
        int minutes = currentTime.getMinuteOfHour();
        int seconds = currentTime.getSecondOfMinute();
        int week = currentTime.getDayOfWeek();
        switch (lineType) {
//             case ONE_SECONDS: // 1秒钟（分时）
            //     return new DateTime(year, month, day, hours, minutes, seconds, 000);
//             case FIVE_SECONDS: // 5秒钟（分时）
            //     return new DateTime(year, month, day, hours, minutes, seconds / 5 * 5, 000);
            case ONE_MINUTES: // 1分钟
                return new DateTime(year, month, day, hours, minutes, 0, 000);
            case FIVE_MINUTES: // 5分钟
                return new DateTime(year, month, day, hours, minutes / 5 * 5, 0, 000);
            case FIFTEEN_MINUTES: // 15分钟
                return new DateTime(year, month, day, hours, minutes / 15 * 15, 0, 000);
            case THIRTY_MINUTES: // 30分钟
                return new DateTime(year, month, day, hours, minutes / 30 * 30, 0, 000);
            case ONE_HOUR: // 1小时
                return new DateTime(year, month, day, hours, 0, 0, 000);
            case TWO_HOURS: // 2小时
                return new DateTime(year, month, day, hours / 2 * 2, 0, 0, 000);
            case FOUR_HOURS: // 4小时
                return new DateTime(year, month, day, hours / 4 * 4, 0, 0, 000);
            case SIX_HOURS: // 6小时
                return new DateTime(year, month, day, hours / 6 * 6, 0, 0, 000);
            case TWELVE_HOURS: // 12小时
                return new DateTime(year, month, day, hours / 12 * 12, 0, 0, 000);
            case ONE_DAY: // 1天
                return new DateTime(year, month, day, 0, 0, 0, 000);
            case ONE_WEEK: // 1周
                if (week != 7) {
                    return new DateTime(year, month, day, 0, 0, 0, 000).plusDays(-week);
                }
                return new DateTime(year, month, day, 0, 0, 0, 000);
            case ONE_MONTH: // 1月
                return new DateTime(year, month, 1, 0, 0, 0, 000);
            case ONE_YEAR: // 1年
                return new DateTime(year, 1, 1, 0, 0, 0, 000);
        }
        return null;
    }
}
