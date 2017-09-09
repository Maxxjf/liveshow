package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.anchor.presenter.IPreAnchorPresenter;
import com.qcloud.liveshow.ui.anchor.view.IPreAnchorView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：主播前的准备
 * Author: Kuzan
 * Date: 2017/9/2 11:18.
 */
public class PreAnchorPresenterImpl extends BasePresenter<IPreAnchorView> implements IPreAnchorPresenter {

    public PreAnchorPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_exit:
                mView.onExitClick();
                break;
            case R.id.btn_switch_camera:
                mView.onSwitchCameraClick();
                break;
            case R.id.btn_begin:
                mView.onBeginAnchorClick();
                break;
            case R.id.layout_change_cover:
                mView.onChangeCoverClick();
                break;
            case R.id.tv_title:
                mView.onInputTitleClick();
                break;
            case R.id.tv_notice:
                mView.onInputNoticeClick();
                break;
            case R.id.img_title_clear:
                mView.onClearTitleClick();
                break;
            case R.id.img_notice_clear:
                mView.onClearNoticeClick();
                break;
            case R.id.tv_toll_standard:
                mView.onSelectDiamondsClick();
                break;
            case R.id.btn_time_start:
                mView.onTimeStartClick();
                break;
            case R.id.btn_time_end:
                mView.onTimeEndClick();
                break;
        }
    }
}
