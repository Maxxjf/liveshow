package com.qcloud.liveshow.beans;

/**
 * 类说明：成功返回
 * Author: Kuzan
 * Date: 2017/9/25 14:18.
 */
public class ReturnSuccessBean {
    boolean isSuccess;      // 是否成功
    boolean isSet;          // 是否设置提现密码

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }

    @Override
    public String toString() {
        return "ReturnSuccessBean{" +
                "isSuccess=" + isSuccess +
                ", isSet=" + isSet +
                '}';
    }
}
