package com.qcloud.liveshow.ui.anchor.view;

import com.qcloud.qclib.base.BaseView;

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
}
