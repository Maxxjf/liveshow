package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.qclib.beans.BaseResponse;
import com.qcloud.qclib.network.OkGoRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 类说明：用户有关
 * Author: Kuzan
 * Date: 2017/9/6 11:37.
 */
public class IUserApi {
    /**获取验证码*/
    public static Observable<BaseResponse<ReturnEmptyBean>> getCode(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_CODE, type, params);
    }

    /**普通登录*/
    public static Observable<BaseResponse<LoginBean>> loginNormal(HttpParams params) {
        Type type = new TypeToken<BaseResponse<LoginBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.LOGIN_NORMAL, type, params);
    }

    /**第三方登录*/
    public static Observable<BaseResponse<LoginBean>> loginOther(HttpParams params) {
        Type type = new TypeToken<BaseResponse<LoginBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.LOGIN_OTHER, type, params);
    }

    /**获取用户信息*/
    public static Observable<BaseResponse<UserBean>> loadUserInfo(HttpParams params) {
        Type type = new TypeToken<BaseResponse<UserBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_USER_INFO, type, params);
    }

    /**退出登录*/
    public static Observable<BaseResponse<ReturnEmptyBean>> logout(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.LOGOUT, type, params);
    }

    /**编辑用户*/
    public static Observable<BaseResponse<ReturnEmptyBean>> edit(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.EDIT_USER, type, params);
    }
}
