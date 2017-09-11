package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.mine.presenter.IExtensionPresenter;
import com.qcloud.liveshow.ui.mine.view.IExtensionView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：邀请页面
 * Author: Kuzan
 * Date: 2017/9/11 16:13.
 */
public class ExtensionPresenterImpl extends BasePresenter<IExtensionView> implements IExtensionPresenter {

    public ExtensionPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_we_chat:
                mView.onWeChatClick();
                break;
            case R.id.btn_wexin_circle:
                mView.onWeiXinCircleClick();
                break;
            case R.id.btn_facebook:
                mView.onFacebookClick();
                break;
        }
    }
}
