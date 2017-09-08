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

    /**
     * 用户有关
     * */
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
    /**编辑用户*/
    String EDIT_USER = URL_PREFIX_APP + "member/edit";

    /**
     * 直播间有关
     * */
    /**获取热门直播间*/
    String GET_HOT_LIST = URL_PREFIX_APP + "index/hotList";

    /**
     * 直播有关
     * */
    /**申请主播状态*/
    String GET_APPLY_STATUS = URL_PREFIX_APP + "anchor/applyStatus";
    /**获取验证码*/
    String GET_APPLY_CODE = URL_PREFIX_APP + "anchor/sendCode";
    /**提交申请*/
    String SUBMIT_APPLY = URL_PREFIX_APP + "anchor/apply";

    /**
     * 文件有关
     * */
    /**上传图片*/
    String UPLOAD_FILE = URL_PREFIX_APP + "file/add";
}
