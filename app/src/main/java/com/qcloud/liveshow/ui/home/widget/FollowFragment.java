package com.qcloud.liveshow.ui.home.widget;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.home.presenter.impl.FollowPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IFollowView;

/**
 * 类说明：关注
 * Author: Kuzan
 * Date: 2017/8/11 11:35.
 */
public class FollowFragment extends BaseFragment<IFollowView, FollowPresenterImpl> implements IFollowView {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected FollowPresenterImpl initPresenter() {
        return new FollowPresenterImpl();
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected void beginLoad() {

    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {

    }
}
