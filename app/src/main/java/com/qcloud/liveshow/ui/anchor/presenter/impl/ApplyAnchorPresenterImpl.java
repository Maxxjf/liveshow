package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.anchor.presenter.IApplyAnchorPresenter;
import com.qcloud.liveshow.ui.anchor.view.IApplyAnchorView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：申请主播
 * Author: Kuzan
 * Date: 2017/8/30 16:15.
 */
public class ApplyAnchorPresenterImpl extends BasePresenter<IApplyAnchorView> implements IApplyAnchorPresenter {

    public ApplyAnchorPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_get_code:
                mView.onGetCodeClick();
                break;
            case R.id.img_header:
                mView.onUploadHeaderClick();
                break;
        }
    }
}
