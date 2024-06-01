package online.shenjian.cloud.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 *
 */
public class DateUtils {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化当前日期
     *
     * @return
     */
    public static String formatNowDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }



    /**
     * 获取时间戳(秒)
     *
     * @return
     */
    public static long getNowWithSeconds() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 获取时间戳(毫秒)
     */
    public static long getNowWithMilli() {
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
