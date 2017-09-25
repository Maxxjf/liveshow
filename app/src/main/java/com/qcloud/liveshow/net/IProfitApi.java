package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.ProfitBean;
import com.qcloud.liveshow.beans.ProfitRecordBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.ReturnSuccessBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.qclib.beans.BaseResponse;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.network.OkGoRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 类说明：收益有关
 * Author: Kuzan
 * Date: 2017/9/12 15:13.
 */
public class IProfitApi {
    /**获取充值套餐列表*/
    public static Observable<BaseResponse<ReturnDataBean<DiamondsBean>>> getDiamondsList(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<DiamondsBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_PAY_MEAL_LIST, type, params);
    }

    /**获取礼物列表*/
    public static Observable<BaseResponse<ReturnDataBean<GiftBean>>> getGiftsList(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<GiftBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_GIFT_LIST, type, params);
    }

    /**是否已设置提现密码*/
    public static Observable<BaseResponse<ReturnSuccessBean>> isSetPassword(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnSuccessBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.IS_SET_PASSWORD, type, params);
    }

    /**设置提现密码获取验证码*/
    public static Observable<BaseResponse<GetCodeResBean>> getCodeBySetPassword(HttpParams params) {
        Type type = new TypeToken<BaseResponse<GetCodeResBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_CODE_BY_SET_PASSWORD, type, params);
    }

    /**设置提现密码*/
    public static Observable<BaseResponse<ReturnEmptyBean>> setWithdrawCashPassword(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.SET_WITHDRAW_CASH_PASSWORD, type, params);
    }

    /**重置提现密码获取验证码*/
    public static Observable<BaseResponse<GetCodeResBean>> getCodeByResetPassword(HttpParams params) {
        Type type = new TypeToken<BaseResponse<GetCodeResBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_CODE_BY_RESET_PASSWORD, type, params);
    }

    /**重置提现密码*/
    public static Observable<BaseResponse<ReturnEmptyBean>> resetWithdrawCashPassword(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.RESET_WITHDRAW_CASH_PASSWORD, type, params);
    }

    /**我的收益*/
    public static Observable<BaseResponse<ProfitBean>> getMyProfit(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ProfitBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_MY_PROFIT, type, params);
    }

    /**收益记录*/
    public static Observable<BaseResponse<ReturnDataBean<ProfitRecordBean>>> getProfitRecord(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<ProfitRecordBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_PROFTI_RECORD, type, params);
    }

    /**绑定分佣关系*/
    public static Observable<BaseResponse<ReturnEmptyBean>> submitBinding(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.SUBMIT_BINDING, type, params);
    }
}
