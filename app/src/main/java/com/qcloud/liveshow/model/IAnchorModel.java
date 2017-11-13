package com.qcloud.liveshow.model;

import android.support.annotation.NonNull;

import com.qcloud.liveshow.beans.ApplyStatusBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.LiveInfoBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.beans.SubmitApplyBean;
import com.qcloud.liveshow.beans.SubmitStartLiveBean;
import com.qcloud.liveshow.beans.UserStatusBean;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：主播有关
 * Author: Kuzan
 * Date: 2017/9/8 11:05.
 */
public interface IAnchorModel {
    /**获取主播状态*/
    void getApplyStatus(DataCallback<ApplyStatusBean> callback);

    /**获取验证码*/
    void getCode(String contactWay, DataCallback<GetCodeResBean> callback);

    /**提交申请主播*/
    void submitApply(@NonNull SubmitApplyBean bean, DataCallback<ReturnEmptyBean> callback);

    /**创建直播间*/
    void createLive(@NonNull SubmitStartLiveBean bean, DataCallback<RoomBean> callback);

    /**获取我的守护列表*/
    void getGuardList(DataCallback<ReturnDataBean<MemberBean>> callback);

    /**添加守护/取消守护*/
    void inOutGuard(long memberId, boolean isGuard, DataCallback<ReturnEmptyBean> callback);

    /**结束直播间*/
    void finishLive(DataCallback<ReturnEmptyBean> callback);

    /**直播前获取直播消息*/
    void getLiveinfo(DataCallback<LiveInfoBean> callback);


    void getUserStatus(String memberId, String roomId, DataCallback<UserStatusBean> callback);
}
