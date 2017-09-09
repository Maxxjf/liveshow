package com.qcloud.liveshow.ui.anchor.view;

import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：主播前的准备
 * Author: Kuzan
 * Date: 2017/9/2 11:17.
 */
public interface IPreAnchorView extends BaseView {
    /**点击关闭*/
    void onExitClick();

    /**点击切换镜头*/
    void onSwitchCameraClick();

    /**点击更改封面*/
    void onChangeCoverClick();

    /**点击输入标题*/
    void onInputTitleClick();

    /**点击输入公告*/
    void onInputNoticeClick();

    /**点击清除标题*/
    void onClearTitleClick();

    /**点击清除公告*/
    void onClearNoticeClick();

    /**点击选择钻石币*/
    void onSelectDiamondsClick();

    /**点击开始时间*/
    void onTimeStartClick();

    /**点击结束时间*/
    void onTimeEndClick();

    /**点击开始直播*/
    void onBeginAnchorClick();
}
