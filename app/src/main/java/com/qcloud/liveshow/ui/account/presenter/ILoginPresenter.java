package com.qcloud.liveshow.ui.account.presenter;

import android.app.Activity;

import com.qcloud.qclib.base.BtnClickPresenter;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 类说明：登录页面数据处理
 * Author: Kuzan
 * Date: 2017/8/8 15:42.
 */
public interface ILoginPresenter extends BtnClickPresenter {

    /**点击登录*/
    void login(String mobile, String code);

    /**第三方登录*/
    void otherLogin(String iconurl, String name, String openId, int sex, int type);

    /**获取平台数据*/
    void loadPlatformInfo(Activity activity, SHARE_MEDIA media);
}
