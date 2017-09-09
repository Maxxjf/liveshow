package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.ApplyStatusBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.qclib.beans.BaseResponse;
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
}
