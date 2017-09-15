package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.AnchorGradeBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.MemberGradeBean;
import com.qcloud.liveshow.beans.MyGiftsBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.qclib.beans.BaseResponse;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.network.OkGoRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 类说明：我的有关
 * Author: Kuzan
 * Date: 2017/9/12 15:28.
 */
public class IMineApi {
    /**获取我的礼物列表*/
    public static Observable<BaseResponse<MyGiftsBean>> getGiftPage(HttpParams params) {
        Type type = new TypeToken<BaseResponse<MyGiftsBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_GIFT_PAGE, type, params);
    }

    /**获取我的关注*/
    public static Observable<BaseResponse<ReturnDataBean<MemberBean>>> getAttentionPage(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<MemberBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_MY_FOLLOW, type, params);
    }

    /**获取我的粉丝*/
    public static Observable<BaseResponse<ReturnDataBean<MemberBean>>> getFansPage(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<MemberBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_MY_FANS, type, params);
    }

    /**获取我的黑名单*/
    public static Observable<BaseResponse<ReturnDataBean<MemberBean>>> getBlacklistPage(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnDataBean<MemberBean>>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_MY_BLACKLIST, type, params);
    }

    /**关注/取消关注*/
    public static Observable<BaseResponse<ReturnEmptyBean>> attention(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.ATTENTION, type, params);
    }

    /**加入/移出黑名单*/
    public static Observable<BaseResponse<ReturnEmptyBean>> inOutBlacklist(HttpParams params) {
        Type type = new TypeToken<BaseResponse<ReturnEmptyBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.IN_OUT_BLACKLIST, type, params);
    }

    /**会员等级*/
    public static Observable<BaseResponse<MemberGradeBean>> getMemberGrade(HttpParams params) {
        Type type = new TypeToken<BaseResponse<MemberGradeBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_MEMBER_GRADE, type, params);
    }

    /**主播等级*/
    public static Observable<BaseResponse<AnchorGradeBean>> getAnchorGrade(HttpParams params) {
        Type type = new TypeToken<BaseResponse<AnchorGradeBean>>(){}.getType();

        return OkGoRequest.getRequest(UrlConstants.GET_ANCHOR_GRADE, type, params);
    }
}
