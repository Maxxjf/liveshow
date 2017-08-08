package com.qcloud.liveshow.beans;

/**
 * 类说明：登录返回
 * Author: Kuzan
 * Date: 2017/8/8 15:34.
 */
public class LoginBean {
    private int loginState; // 登录状态
    private String token;   // token

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

    @Override
    public String toString() {
        return "LoginBean{" +
                "loginState=" + loginState +
                ", token='" + token + '\'' +
                '}';
    }
}
