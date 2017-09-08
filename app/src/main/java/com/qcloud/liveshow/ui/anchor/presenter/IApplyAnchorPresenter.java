package com.qcloud.liveshow.ui.anchor.presenter;

import com.qcloud.liveshow.beans.SubmitApplyBean;
import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：申请主播
 * Author: Kuzan
 * Date: 2017/8/30 16:14.
 */
public interface IApplyAnchorPresenter extends BtnClickPresenter {
    /**获取验证码*/
    void getCode(String contactWay);

    /**上传头像*/
    void uploadHeadImg(String path);

    /**提交申请主播*/
    void submitApply(SubmitApplyBean bean);
}
