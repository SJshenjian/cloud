package online.shenjian.cloud.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**
 * 通用实体与DTO互相转换工具类
 */
public class CommonDtoUtils {


    public static <T> T transform(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            String jsonSource = JSON.toJSONString(source);
            return JSONObject.parseObject(jsonSource, targetClass);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static <T> List<T> transformList(List<?> listSource, Class<T> targetClass) {
        if (listSource == null) {
            return null;
        }
        try {
            String jsonSource = JSON.toJSONString(listSource);
            return JSONArray.parseArray(jsonSource, targetClass);
        } catch (Exception ex) {
            throw ex;
        }
    }
}