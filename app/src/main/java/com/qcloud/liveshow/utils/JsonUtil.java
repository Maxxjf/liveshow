package com.qcloud.liveshow.utils;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：Json有关工具类
 * Author: Kuzan
 * Date: 2017/11/10 18:57.
 */
public class JsonUtil {
    /**
     * 判断是否json字符串
     * */
    public static boolean isGoodJson(String json) {
        if (StringUtils.isEmptyString(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }
}
