package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.model.IUserModel;
import com.qcloud.liveshow.net.IUserApi;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.network.OkGoRequest;

/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/9/6 11:34.
 */
public class UserModelImpl implements IUserModel {
    /**请求参数*/
    private HttpParams mParams;

    public UserModelImpl() {
        mParams = OkGoRequest.getAppParams();
        OkGoRequest.createService();
    }

    /**
     * 获取验证码
     *
     * @time 2017/9/6 12:07
     */
    @Override
    public void getCode(String mobile, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("number", mobile);

        BaseApi.dispose(IUserApi.getCode(mParams), callback);
    }

    /**
     * 手机号登录
     *
     * @time 2017/9/6 12:11
     */
    @Override
    public void loginNormal(String mobile, String code, DataCallback<LoginBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("number", mobile);
        mParams.put("code", code);

        BaseApi.dispose(IUserApi.loginNormal(mParams), callback);
    }

    /**
     * 获取用户信息
     *
     * @time 2017/9/6 11:35
     */
    @Override
    public void loadUserInfo(DataCallback<UserBean> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IUserApi.loadUserInfo(mParams), callback);
    }
}
