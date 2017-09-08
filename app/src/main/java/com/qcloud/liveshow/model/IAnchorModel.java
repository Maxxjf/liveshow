package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.ApplyStatusBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.SubmitApplyBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：主播有关
 * Author: Kuzan
 * Date: 2017/9/8 11:05.
 */
public interface IAnchorModel {
    /**获取主播状态*/
    void getApplyStatus(DataCallback<ApplyStatusBean> callback);

    /**获取验证码*/
    void getCode(String contactWay, DataCallback<ReturnEmptyBean> callback);

    /**提交申请主播*/
    void submitApply(SubmitApplyBean bean, DataCallback<ReturnEmptyBean> callback);
}
