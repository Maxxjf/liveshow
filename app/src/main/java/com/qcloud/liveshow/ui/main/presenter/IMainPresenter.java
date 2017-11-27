package com.qcloud.liveshow.ui.main.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：首页
 * Author: Kuzan
 * Date: 2017/8/1 19:17.
 */
public interface IMainPresenter extends BtnClickPresenter {
    /**获取主播申请状态*/
    void getApplyStatus();

    /**提交绑定分佣关系*/
    void submitBinding(String code);

}
