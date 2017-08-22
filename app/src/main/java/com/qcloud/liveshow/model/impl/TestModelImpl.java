package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.RetBean;
import com.qcloud.liveshow.model.ITestModel;
import com.qcloud.liveshow.net.ITestApi;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.network.OkGoRequest;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/8/22 11:39.
 */
public class TestModelImpl implements ITestModel {

    private HttpParams mParams;
    private HttpHeaders mHeaders;
    private OkGoRequest mGoRequest;

    public TestModelImpl() {
        mParams = OkGoRequest.getAppParams();
        mHeaders = new HttpHeaders();
        OkGoRequest.createService();
    }

    @Override
    public void loadData(DataCallback<RetBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("uid", "133825214");
        mParams.put("interest", 1);
        BaseApi.dispose2(ITestApi.getData(mParams), callback);
    }
}
