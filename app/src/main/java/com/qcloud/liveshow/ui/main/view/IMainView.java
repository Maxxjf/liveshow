package com.qcloud.liveshow.ui.main.view;

import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：首页
 * Author: Kuzan
 * Date: 2017/8/1 19:16.
 */
public interface IMainView extends BaseView {
    /**点击首页*/
    void onHomeClick();

    /**点击直播*/
    void onLiveShowClick();

    /**点击我的*/
    void onMineClick();

    /**显示审核中*/
    void showPending();

    /**显示审核通过*/
    void showAgree();

    /**显示审核不通过*/
    void showDisagree();

    /**显示未提交申请*/
    void showNotApply();

    /**显示禁用*/
    void showDisable();
}
