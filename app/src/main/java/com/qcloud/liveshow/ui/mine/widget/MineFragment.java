package com.qcloud.liveshow.ui.mine.widget;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.mine.presenter.impl.MinePresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMineView;

/**
 * 类说明：我的
 * Author: Kuzan
 * Date: 2017/8/10 9:58.
 */
public class MineFragment extends BaseFragment<IMineView, MinePresenterImpl> implements IMineView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MinePresenterImpl initPresenter() {
        return new MinePresenterImpl();
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected void beginLoad() {

    }
}
