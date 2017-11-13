package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.ApplyStatusBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.LiveInfoBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.beans.UserStatusBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.qclib.beans.BaseResponse;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.network.OkGoRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 类说明：直播有关
 * Author: Kuzan
 * Date: 2017/9/8 10:33.
 */
public class IAnchorApi {
    /**申请主播状态*/
    public static Observable<BaseResponse<ApplyStatusBean>> getApplyStatus(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ApplyStatusBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_APPLY_STATUS, type, params);
    }

    /**获取验证码*/
    public static Observable<BaseResponse<GetCodeResBean>> getCode(HttpParams params) {
        Type type = new TypeToken<BaseResponse<GetCodeResBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_APPLY_CODE, type, params);
    }

    /**申请成为主播*/
    public static Observable<BaseResponse<ReturnEmptyBean>> submitApply(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.SUBMIT_APPLY, type, params);
    }

    /**创建直播间*/
    public static Observable<BaseResponse<RoomBean>> createLive(HttpParams params) {
        Type type = new TypeToken<BaseResponse<RoomBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.CREATE_LIVE, type, params);
    }

    /**获取我的守护列表*/
    public static Observable<BaseResponse<ReturnDataBean<MemberBean>>> getGuardList(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<MemberBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_GUARD_LIST, type, params);
    }

    /**添加守护/取消守护*/
    public static Observable<BaseResponse<ReturnEmptyBean>> inOutGuard(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.IN_OUT_GRARD, type, params);
    }

    /**结束直播*/
    public static Observable<BaseResponse<ReturnEmptyBean>> finishLive(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.FINISH_LIVE, type, params);
    }

    /**开始直播前获取直播消息*/
    public static Observable<BaseResponse<LiveInfoBean>> getLiveinfo(HttpParams params) {
        Type type = new TypeToken<BaseResponse<LiveInfoBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_LIVEINFO, type, params);
    }

    /**开始直播前获取直播消息*/
    public static Observable<BaseResponse<UserStatusBean>> getUserStatus(HttpParams params) {
        Type type = new TypeToken<BaseResponse<UserStatusBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_USER_STATUS, type, params);
    }
}
