package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.HotRoomBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.qclib.beans.BaseResponse;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.network.OkGoRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 类说明：直播间有关
 * Author: Kuzan
 * Date: 2017/9/7 15:16.
 */
public class IRoomApi {
    /**获取热门直播间*/
    public static Observable<BaseResponse<HotRoomBean>> getHotList(HttpParams params) {
        Type type = new TypeToken<BaseResponse<HotRoomBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_HOT_LIST, type, params);
    }

    /**获取最新直播间*/
    public static Observable<BaseResponse<ReturnDataBean<RoomBean>>> getNewestList(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<RoomBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_NEWEST_LIST, type, params);
    }

    /**获取关注直播间*/
    public static Observable<BaseResponse<ReturnDataBean<RoomBean>>> getFollowList(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<RoomBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_FOLLOE_LIST, type, params);
    }
}
