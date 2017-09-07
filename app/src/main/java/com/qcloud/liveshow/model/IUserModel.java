package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/9/6 11:32.
 */
public interface IUserModel {
    /**获取验证码*/
    void getCode(String mobile, DataCallback<ReturnEmptyBean> callback);

    /**手机号登录*/
    void loginNormal(String mobile, String code, DataCallback<LoginBean> callback);

    /**第三方登录*/
    void loginOther(String iconurl, String name, String openId, int sex, int type, DataCallback<LoginBean> callback);

    /**获取用户信息*/
    void loadUserInfo(DataCallback<UserBean> callback);

    /**退出登录*/
    void logout(DataCallback<ReturnEmptyBean> callback);

    /**编辑用户*/
    void edit(String headImg, String nickName, int sex, String signature, DataCallback<ReturnEmptyBean> callback);
}
