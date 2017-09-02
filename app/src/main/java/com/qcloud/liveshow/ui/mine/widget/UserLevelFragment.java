package com.qcloud.liveshow.ui.mine.widget;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.mine.presenter.impl.UserLevelPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IUserLevelView;
import com.qcloud.qclib.toast.ToastUtils;

import timber.log.Timber;

/**
 * 类说明：用户等级
 * Author: Kuzan
 * Date: 2017/9/2 17:56.
 */
public class UserLevelFragment extends BaseFragment<IUserLevelView, UserLevelPresenterImpl> implements IUserLevelView {


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_level;
    }

    @Override
    protected UserLevelPresenterImpl initPresenter() {
        return new UserLevelPresenterImpl();
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
