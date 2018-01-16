package com.qcloud.liveshow.constant;

/**
 * 类说明：应用常量
 * Author: Kuzan
 * Date: 2017/8/1 19:09.
 */
public interface AppConstants {
    /**列表分页size大小*/
    int PAGE_SIZE = 10;
    /**保存是否首次打开的标记*/
    String IS_NO_FIRST_START = "is_no_first_start";
    /**是否第一次登录*/
    String IS_FIRST_LOGIN = "is_first_login";
//    /**APP是否第一次登录*/
//    String IS_APP_FIRST_LOGIN = "is_app_first_login";

    /**微信appId和appSecret*/
    String WX_APP_ID = "wx8d335bf844b9c273";
    String WX_APP_SECRET = "c23c1d9c477a29088e91ab1c72650be0";

    /**Netty 服务器*/
    String NETTY_HOST = "10.10.22.123";
//    String NETTY_HOST = "10.10.100.195";
    int NETTY_PORT = 2071;


    /**推流 服务器*/
    String IO_HOST = "221.4.216.90";
//    String IO_HOST = "10.10.22.123";
//    String IO_HOST = "10.10.22.120";
    int IO_PORT = 2081;

    /**拉流服务器*/
    String PUSH_HOST = "221.4.216.90";
    int PUSH_PORT = 2091;

    /**弹起键盘坡度*/
    int DISTANCE_SLOP = 180;
    /**最新的弹起键盘高度*/
    String LAST_KEYBOARD_HEIGHT = "last_keyboard_height";

    /**Realm版本*/
    int REALM_VERSION = 1;
}
