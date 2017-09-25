package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.ProfitBean;
import com.qcloud.liveshow.beans.ProfitRecordBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.ReturnSuccessBean;
import com.qcloud.liveshow.model.IProfitModel;
import com.qcloud.liveshow.net.IProfitApi;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.network.OkGoRequest;

/**
 * 类说明：收益有关
 * Author: Kuzan
 * Date: 2017/9/12 15:18.
 */
public class ProfitModelImpl implements IProfitModel {

    /**请求参数*/
    private HttpParams mParams;

    public ProfitModelImpl() {
        mParams = OkGoRequest.getAppParams();
        OkGoRequest.createService();
    }

    /**
     * 获取钻石币充值记录
     *
     * @param callback
     *
     * @time 2017/9/13 10:41
     */
    @Override
    public void getDiamondsList(DataCallback<ReturnDataBean<DiamondsBean>> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IProfitApi.getDiamondsList(mParams), callback);
    }

    /**
     * 获取礼物列表
     *
     * @param callback
     *
     * @time 2017/9/13 15:07
     */
    @Override
    public void getGiftList(DataCallback<ReturnDataBean<GiftBean>> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IProfitApi.getGiftsList(mParams), callback);
    }

    /**
     * 是否设置提现密码
     *
     * @time 2017/9/25 14:21
     */
    @Override
    public void isSetPassword(DataCallback<ReturnSuccessBean> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IProfitApi.isSetPassword(mParams), callback);
    }

    /**
     * 设置提现密码获取验证码
     *
     * @param phone
     * @param callback
     *
     * @time 2017/9/25 10:29
     */
    @Override
    public void getCodeBySetPassword(String phone, DataCallback<GetCodeResBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("phone", phone);

        BaseApi.dispose(IProfitApi.getCodeBySetPassword(mParams), callback);
    }

    /**
     * 设置提现密码
     *
     * @param phone
     * @param code
     * @param withdrawPassword
     * @param callback
     *
     * @time 2017/9/25 10:31
     */
    @Override
    public void setWithdrawCashPassword(String phone, String code, String withdrawPassword, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("phone", phone);
        mParams.put("code", code);
        mParams.put("withdrawPassword", withdrawPassword);

        BaseApi.dispose(IProfitApi.setWithdrawCashPassword(mParams), callback);
    }

    /**
     * 重置提现密码获取验证码
     *
     * @param phone
     * @param callback
     *
     * @time 2017/9/25 10:31
     */
    @Override
    public void getCodeByResetPassword(String phone, DataCallback<GetCodeResBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("phone", phone);

        BaseApi.dispose(IProfitApi.getCodeByResetPassword(mParams), callback);
    }

    /**
     * 重置提现密码
     *
     * @param phone
     * @param code
     * @param withdrawPassword
     * @param callback
     *
     * @time 2017/9/25 10:32
     */
    @Override
    public void resetWithdrawCashPassword(String phone, String code, String withdrawPassword, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("phone", phone);
        mParams.put("code", code);
        mParams.put("withdrawPassword", withdrawPassword);

        BaseApi.dispose(IProfitApi.resetWithdrawCashPassword(mParams), callback);
    }

    /**
     * 获取我的收益
     *
     * @time 2017/9/25 11:53
     */
    @Override
    public void getMyProfit(DataCallback<ProfitBean> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IProfitApi.getMyProfit(mParams), callback);
    }

    /**
     * 获取收益记录
     *
     * @param pageNum
     * @param pageSize
     * @param callback
     *
     * @time 2017/9/25 11:54
     */
    @Override
    public void getProfitRecord(int pageNum, int pageSize, DataCallback<ReturnDataBean<ProfitRecordBean>> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IProfitApi.getProfitRecord(mParams), callback);
    }

    /**
     * 绑定分佣关系
     *
     * @time 2017/9/25 15:11
     */
    @Override
    public void submitBinding(String code, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("code", code);

        BaseApi.dispose(IProfitApi.submitBinding(mParams), callback);
    }
}
