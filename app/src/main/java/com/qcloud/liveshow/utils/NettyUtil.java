package com.qcloud.liveshow.utils;

import com.qcloud.qclib.utils.ConstantUtil;

/**
 * 类说明：Netty工具类
 * Author: Kuzan
 * Date: 2017/11/6 14:57.
 */
public class NettyUtil {
    public static final String IS_AUTH = "is_auth";

    /**
     * 保存token
     * */
    public static void saveIsAuth(boolean isAuth) {
        ConstantUtil.writeBoolean(IS_AUTH, isAuth);
    }

    /**
     * 清掉token
     * */
    public static void clearIsAuth() {
        ConstantUtil.writeBoolean(IS_AUTH, false);
    }

    /**
     * 获取token
     * */
    public static boolean isAuth() {
        return ConstantUtil.getBoolean(IS_AUTH, false);
    }
}
