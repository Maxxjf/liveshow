package com.qcloud.liveshow.ui.profit.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：我的钻石币
 * Author: Kuzan
 * Date: 2017/9/1 15:30.
 */
public interface IMyDiamondsPresenter extends BtnClickPresenter {
    /**获取钻石币充值套餐*/
    void getDiamondsList();
}
