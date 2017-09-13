package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GiftBean;
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
}
