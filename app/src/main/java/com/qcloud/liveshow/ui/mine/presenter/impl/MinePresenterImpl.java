package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.mine.presenter.IMinePresenter;
import com.qcloud.liveshow.ui.mine.view.IMineView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：我的
 * Author: Kuzan
 * Date: 2017/8/10 9:57.
 */
public class MinePresenterImpl extends BasePresenter<IMineView> implements IMinePresenter {

    public MinePresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.layout_user:
                mView.onUserClick();
                break;
            case R.id.layout_follow:
                mView.onFollowClick();
                break;
            case R.id.layout_fans:
                mView.onFansClick();
                break;
            case R.id.layout_profit:
                mView.onProfitClick();
                break;
            case R.id.layout_level:
                mView.onUserLevelClick();
                break;
            case R.id.layout_diamonds:
                mView.onDiamondsClick();
                break;
            case R.id.layout_gift:
                mView.onGiftsClick();
                break;
            case R.id.layout_inviting_friends:
                mView.onInvitingFriendsClick();
                break;
            case R.id.layout_extension_code:
                mView.onExtensionCodeClick();
                break;
            case R.id.layout_set:
                mView.onSetClick();
                break;
        }
    }
    @Override
    public void onResume() {
        UserInfoUtil.loadUserInfo();
    }
}
