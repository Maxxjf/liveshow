package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.AnchorGradeBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.MemberGradeBean;
import com.qcloud.liveshow.beans.MyGiftsBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：我的有关
 * Author: Kuzan
 * Date: 2017/9/12 15:40.
 */
public interface IMineModel {
    /**获取我的礼物列表*/
    void getGiftPage(int pageNum, int pageSize, DataCallback<MyGiftsBean> callback);

    /**获取我的关注*/
    void getAttentionPage(int type, int pageNum, int pageSize, DataCallback<ReturnDataBean<MemberBean>> callback);

    /**关注/取消关注 加入/移出黑名单*/
    void submitAttention(int type, long id, boolean isAttention, DataCallback<ReturnEmptyBean> callback);

    /**会员等级*/
    void getMemberGrade(DataCallback<MemberGradeBean> callback);

    /**主播等级*/
    void getAnchorGrade(DataCallback<AnchorGradeBean> callback);
}
