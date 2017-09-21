package com.qcloud.liveshow.ui.room.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/23 11:43.
 */
public interface IRoomControlPresenter extends BtnClickPresenter {
    /**关注该主播员*/
    void submitAttention(long id, boolean isAttention);
}
