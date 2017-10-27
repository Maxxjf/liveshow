package com.qcloud.liveshow.ui.anchor.presenter;

import com.qcloud.liveshow.beans.SubmitStartLiveBean;
import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：主播前的准备
 * Author: Kuzan
 * Date: 2017/9/2 11:18.
 */
public interface IPreAnchorPresenter extends BtnClickPresenter {
    /**上传封面*/
    void uploadCoverImg(String path);

    /**创建直播间*/
    void createLive(SubmitStartLiveBean bean);

    /**创建直播间前获取信息*/
    void getLiveinfo();
}
