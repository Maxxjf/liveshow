package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.GetCodeResBean;
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
    public void getCode(String mobile, DataCallback<GetCodeResBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("phone", mobile);

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
        mParams.put("phone", mobile);
        mParams.put("code", code);

        BaseApi.dispose(IUserApi.loginNormal(mParams), callback);
    }

    /**
     * 第三方登录
     *
     * @param iconurl   头像链接
     * @param name      昵称
     * @param openId    微信openId
     * @param sex       性别 0:男, 1:女
     * @param type      类型 0:微信 1:FaceBook
     * @param callback
     *
     * @time 2017/9/6 16:55
     */
    @Override
    public void loginOther(String iconurl, String name, String openId, int sex, int type, DataCallback<LoginBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("iconurl", iconurl);
        mParams.put("name", name);
        mParams.put("openId", openId);
        mParams.put("sex", sex);
        mParams.put("type", type);

        BaseApi.dispose(IUserApi.loginOther(mParams), callback);
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

    /**
     * 退出登录
     *
     * @time 2017/9/6 17:20
     */
    @Override
    public void logout(DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IUserApi.logout(mParams), callback);
    }

    /**
     * 编辑用户
     *
     * @param headImg
     *          用户头像
     * @param nickName
     *          昵称
     * @param sex
     *          性别 0男 1女
     * @param signature
     *          个性签名
     * @param callback
     *
     * @time 2017/9/7 17:32
     */
    @Override
    public void edit(String headImg, String nickName, int sex, String signature, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("headImg", headImg);
        mParams.put("nickName", nickName);
        mParams.put("sex", sex);
        mParams.put("signature", signature);

        BaseApi.dispose(IUserApi.edit(mParams), callback);
    }
}
