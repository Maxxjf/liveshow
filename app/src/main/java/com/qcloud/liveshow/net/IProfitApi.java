package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GiftBean;
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
}
