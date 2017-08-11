package com.qcloud.liveshow.ui.home.widget;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.home.presenter.impl.NewestPresenterImpl;
import com.qcloud.liveshow.ui.home.view.INewestView;

/**
 * 类说明：最新
 * Author: Kuzan
 * Date: 2017/8/11 11:34.
 */
public class NewestFragment extends BaseFragment<INewestView, NewestPresenterImpl> implements INewestView {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newest;
    }

    @Override
    protected NewestPresenterImpl initPresenter() {
        return new NewestPresenterImpl();
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
