package online.shenjian.cloud.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 通用工具类
 *
 * @author shenjian
 * @since 2023/7/27
 */
public class CommonUtils {

    /**
     * BMI 身体健康指数
     *
     * @param weight 身高CM
     * @param height 体重KG
     * @return
     */
    public static Double getBMI(Float weight, Float height) {
        if (weight == null || height == null) {
            return 0D;
        }
        try {
            double bmi = 0;
            if (weight != 0 && weight != 0) {
                bmi = weight / (Math.pow(height / 100, 2));
            }
            return getDoubleByScale(bmi, 2);
        } catch(Exception ex) {
            return 0D;
        }
    }

    /**
     * 获取指定精度值
     *
     * @param val
     * @param scale
     * @return
     */
    public static Double getDoubleByScale(double val, int scale) {
        BigDecimal bd = new BigDecimal(val);
        return Double.parseDouble(bd.setScale(scale,  RoundingMode.HALF_UP).toString());
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthday
     * @return
     */
    public static String getAgeByBirthday(Date birthday) {
        Date now = new Date();
        int age = now.getYear() - birthday.getYear();
        int month = 0;
        if (now.getMonth() < birthday.getMonth() || (now.getMonth() == birthday.getMonth() && now.getDate() < birthday.getDate())) {
            age--;
            month = now.getMonth() + 11 - birthday.getMonth();
        } else if (now.getMonth() > birthday.getMonth()) {
            month = now.getMonth() - birthday.getMonth();
        } else if (now.getMonth() == birthday.getMonth()) {
            if (now.getDate() >= birthday.getDate()) {
                month = 0;
            } else {
                age--;
                month = 11;
            }
        }
        if (month == 0) {
            return age + " 岁";
        } else {
            return age + " 岁 " + month + "个月";
        }
    }
}
