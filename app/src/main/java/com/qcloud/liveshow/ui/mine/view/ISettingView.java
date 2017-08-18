package com.qcloud.liveshow.ui.mine.view;

/**
 * 类说明：设置
 * Author: Kuzan
 * Date: 2017/8/18 18:40.
 */
public interface ISettingView {
    /**黑名单*/
    void onBlacklistClick();

    /**关于我们*/
    void onAboutUsClick();

    /**清除缓存*/
    void onClearCacheClick();

    /**常见问题*/
    void onProblemClick();

    /**退出登录*/
    void onLogoutClick();
}
