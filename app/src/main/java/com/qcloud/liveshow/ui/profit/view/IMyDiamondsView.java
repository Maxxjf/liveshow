package com.qcloud.liveshow.ui.profit.view;

import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：我的钻石币
 * Author: Kuzan
 * Date: 2017/9/1 15:29.
 */
public interface IMyDiamondsView extends BaseView {
    /**充值协议*/
    void onRechargeClick();

    /**服务热线*/
    void onCustomerServiceClick();

    /**钻石币充值套餐*/
    void replaceDiamondsList(List<DiamondsBean> beans);
}
