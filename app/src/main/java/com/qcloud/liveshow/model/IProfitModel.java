package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
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

    /**设置提现密码获取验证码*/
    void getCodeBySetPassword(String phone, DataCallback<GetCodeResBean> callback);

    /**设置提现密码*/
    void setWithdrawCashPassword(String phone, String code, String withdrawPassword, DataCallback<ReturnEmptyBean> callback);

    /**设置提现密码获取验证码*/
    void getCodeByResetPassword(String phone, DataCallback<GetCodeResBean> callback);

    /**设置提现密码*/
    void resetWithdrawCashPassword(String phone, String code, String withdrawPassword, DataCallback<ReturnEmptyBean> callback);
}
