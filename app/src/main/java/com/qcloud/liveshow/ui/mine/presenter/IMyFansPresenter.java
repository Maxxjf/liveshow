package com.qcloud.liveshow.ui.mine.presenter;

/**
 * 类说明：我的关注列表
 * Author: Kuzan
 * Date: 2017/8/18 11:14.
 */
public interface IMyFansPresenter {
    /**加载我的关注/我的粉丝/我的黑名单*/
    void getMyAttention(int type, int pageNum, int PageSize);

    /**关注/取消息关注 拉黑/移出拉黑*/
    void submitAttention(int type, long id, boolean isAttention);
}
