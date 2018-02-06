package com.qcloud.liveshow.enums;

import com.qcloud.liveshow.utils.LanguageUtil;

/**
 * 类型：ToastEnum
 * Author: iceberg
 * Date: 2018/2/5.
 * TODO:
 */
public enum ToastEnum {
    toast_1("", "");

    private String valueZh;
    private String valueEn;
    ToastEnum(String valueZh, String valueEn) {
        this.valueZh = valueZh;
        this.valueEn = valueEn;
    }

    public String getValueZh() {
        return valueZh;
    }

    public void setValueZh(String valueZh) {
        this.valueZh = valueZh;
    }

    public String getValueEn() {
        return valueEn;
    }

    public void setValueEn(String valueEn) {
        this.valueEn = valueEn;
    }

    public String getValue() {
        String language = LanguageUtil.getCurrLanguage();
        switch (language){
            case "en":
                return valueEn;
        }
        return valueZh;
    }
}
