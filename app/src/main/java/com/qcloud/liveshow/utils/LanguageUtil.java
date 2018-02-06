package com.qcloud.liveshow.utils;

import java.util.Locale;

/**
 * 类型：LanguageUtil
 * Author: iceberg
 * Date: 2018/2/5.
 * TODO:
 */
public class LanguageUtil {

    public static String getCurrLanguage() {
        try {
            return Locale.getDefault().getLanguage();
        } catch (Exception e) {
            e.printStackTrace();
            return "en";
        }
    }
}
