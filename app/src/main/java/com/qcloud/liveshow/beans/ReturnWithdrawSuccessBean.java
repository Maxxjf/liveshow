package com.qcloud.liveshow.beans;

/**
 * 类说明：取现成功
 * Author: iceberg
 * Date: 2017/12/4
 */
public class ReturnWithdrawSuccessBean {
    boolean isSuccess;      // 是否成功
    String email;          // 邮箱

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ReturnWithdrawSuccessBean{" +
                "isSuccess=" + isSuccess +
                ", email='" + email + '\'' +
                '}';
    }
}
