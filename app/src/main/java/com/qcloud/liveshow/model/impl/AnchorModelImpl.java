package com.qcloud.liveshow.model.impl;

import android.support.annotation.NonNull;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.ApplyStatusBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.SubmitApplyBean;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.net.IAnchorApi;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.network.OkGoRequest;

/**
 * 类说明：主播有关
 * Author: Kuzan
 * Date: 2017/9/8 11:06.
 */
public class AnchorModelImpl implements IAnchorModel {
    /**请求参数*/
    private HttpParams mParams;

    public AnchorModelImpl() {
        mParams = OkGoRequest.getAppParams();
        OkGoRequest.createService();
    }

    /**
     * 获取申请主播状态
     *
     * @time 2017/9/8 11:07
     */
    @Override
    public void getApplyStatus(DataCallback<ApplyStatusBean> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IAnchorApi.getApplyStatus(mParams), callback);
    }

    /**
     * 获取验证码
     *
     * @param contactWay
     *              联系方式
     *
     * @time 2017/9/8 11:59
     */
    @Override
    public void getCode(String contactWay, DataCallback<GetCodeResBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("phone", contactWay);

        BaseApi.dispose(IAnchorApi.getCode(mParams), callback);
    }

    /**
     * 申请成为主播
     *
     * @param bean
     *          申请信息
     *
     * @time 2017/9/8 11:59
     */
    @Override
    public void submitApply(@NonNull SubmitApplyBean bean, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("code", bean.getCode());
        mParams.put("headImg", bean.getHeadImg());
        mParams.put("name", bean.getName());
        mParams.put("nickName", bean.getNickName());
        mParams.put("phone", bean.getPhone());
        mParams.put("sex", bean.getSex());
        mParams.put("withdrawPassword", bean.getWithdrawPassword());

        BaseApi.dispose(IAnchorApi.submitApply(mParams), callback);
    }
}
