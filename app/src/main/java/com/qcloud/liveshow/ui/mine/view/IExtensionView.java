package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：邀请页面
 * Author: Kuzan
 * Date: 2017/9/11 16:10.
 */
public interface IExtensionView extends BaseView {
    /**点击微信分享*/
    void onWeChatClick();
    /**点击朋友圈分享*/
    void onWeiXinCircleClick();
    /**点周facebook分享*/
    void onFacebookClick();
}
