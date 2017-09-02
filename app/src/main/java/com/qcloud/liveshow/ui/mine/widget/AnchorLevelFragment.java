package com.qcloud.liveshow.ui.mine.widget;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.mine.presenter.impl.AnchorLevelPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IAnchorLevelView;
import com.qcloud.qclib.toast.ToastUtils;

import timber.log.Timber;

/**
 * 类说明：主播等级
 * Author: Kuzan
 * Date: 2017/9/2 17:59.
 */
public class AnchorLevelFragment extends BaseFragment<IAnchorLevelView, AnchorLevelPresenterImpl> implements IAnchorLevelView {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor_level;
    }

    @Override
    protected AnchorLevelPresenterImpl initPresenter() {
        return new AnchorLevelPresenterImpl();
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected void beginLoad() {

    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isInFragment) {
            if (isShow) {
                ToastUtils.ToastMessage(getActivity(), errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }
}
