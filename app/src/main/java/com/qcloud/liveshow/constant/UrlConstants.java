package com.qcloud.liveshow.constant;

/**
 * 类说明：接口Url常量
 * Author: Kuzan
 * Date: 2017/8/1 19:10.
 */
public interface UrlConstants {
    /**app请求前缀*/
    String URL_PREFIX = "fep/";
    /**带app的url前缀*/
    String URL_PREFIX_APP = URL_PREFIX + "app/";

    /**获取验证码*/
    String GET_CODE = URL_PREFIX_APP + "login/sendCode";
    /**普通登录*/
    String LOGIN_NORMAL = URL_PREFIX_APP + "login/normalLogin";
    /**第三方登录*/
    String LOGIN_OTHER = URL_PREFIX_APP + "login/otherLogin";
    /**获取用户信息*/
    String GET_USER_INFO = URL_PREFIX_APP + "member/personalcenter";
    /**退出登录*/
    String LOGOUT = URL_PREFIX_APP + "member/logout";
}
