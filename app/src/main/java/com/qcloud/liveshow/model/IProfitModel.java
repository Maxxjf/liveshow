package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：收益有关
 * Author: Kuzan
 * Date: 2017/9/12 15:16.
 */
public interface IProfitModel {
    /**获取钻石币充值套餐列表*/
    void getDiamondsList(DataCallback<ReturnDataBean<DiamondsBean>> callback);

    /**获取礼物列表*/
    void getGiftList(DataCallback<ReturnDataBean<GiftBean>> callback);
}
