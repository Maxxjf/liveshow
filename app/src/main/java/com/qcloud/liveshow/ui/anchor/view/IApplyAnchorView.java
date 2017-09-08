package com.qcloud.liveshow.ui.anchor.view;

import com.qcloud.qclib.base.BaseView;
import com.qcloud.qclib.beans.UploadFileBean;

/**
 * 类说明：申请主播
 * Author: Kuzan
 * Date: 2017/8/30 16:13.
 */
public interface IApplyAnchorView extends BaseView {
    /**获取验证码*/
    void onGetCodeClick();

    /**点击上传图片*/
    void onUploadHeaderClick();

    /**点击提交*/
    void onSubmitClick();

    /**获取验证码成功*/
    void getCodeSuccess();

    /**获取验证码失败*/
    void getCodeFailure(String errMsg);

    /**上传头像成功*/
    void uploadSuccess(UploadFileBean bean);

    /**提交成功*/
    void submitSuccess();
}
