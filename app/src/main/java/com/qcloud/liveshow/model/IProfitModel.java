package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.ProfitBean;
import com.qcloud.liveshow.beans.ProfitRecordBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.ReturnGiftBean;
import com.qcloud.liveshow.beans.ReturnSuccessBean;
import com.qcloud.liveshow.beans.ReturnWithdrawSuccessBean;
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
    void isSetPassword(DataCallback<ReturnSuccessBean> callback);

    /**设置提现密码获取验证码*/
    void getCodeBySetPassword(String email, DataCallback<GetCodeResBean> callback);

    /**设置提现密码*/
    void setWithdrawCashPassword(String phone, String code, String withdrawPassword, DataCallback<ReturnEmptyBean> callback);

    /**设置提现密码获取验证码*/
    void getCodeByResetPassword(String phone, DataCallback<GetCodeResBean> callback);

    /**设置提现密码*/
    void resetWithdrawCashPassword(String phone, String code, String withdrawPassword, DataCallback<ReturnEmptyBean> callback);

    /**获取我的收益*/
    void getMyProfit(DataCallback<ProfitBean> callback);

    /**我的收益记录*/
    void getProfitRecord(int pageNum, int pageSize, DataCallback<ReturnDataBean<ProfitRecordBean>> callback);

    /**绑定分佣关系*/
    void submitBinding(String code, DataCallback<ReturnEmptyBean> callback);

    /**取现*/
    void withdraw2Card(String cash, String name, String cardNumber, Integer bankCode, String password, DataCallback<ReturnWithdrawSuccessBean> callback);

    /**发送礼物*/
    void sendGift(String giftId,String id,String roomId,DataCallback<ReturnGiftBean> callback);

}
