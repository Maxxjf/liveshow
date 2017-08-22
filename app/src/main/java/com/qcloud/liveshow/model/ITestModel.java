package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.RetBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/8/22 11:38.
 */
public interface ITestModel {
    void loadData(DataCallback<RetBean> callback);
}
