package com.qcloud.liveshow.beans;

/**
 * 类说明：登录返回
 * Author: Kuzan
 * Date: 2017/8/8 15:34.
 */
public class LoginBean {
    private int loginState;     // 登录状态
    private String token;       // token
    private boolean isFirst;    // 是否第一次登录

    public int getLoginState() {
        return loginState;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "loginState=" + loginState +
                ", token='" + token + '\'' +
                ", isFirst=" + isFirst +
                '}';
    }
}
