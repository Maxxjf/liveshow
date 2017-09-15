package com.qcloud.liveshow.ui.mine.widget;

import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.MemberGradeBean;
import com.qcloud.liveshow.ui.mine.presenter.impl.UserLevelPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IUserLevelView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.CustomProgressBar;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：用户等级
 * Author: Kuzan
 * Date: 2017/9/2 17:56.
 */
public class UserLevelFragment extends BaseFragment<IUserLevelView, UserLevelPresenterImpl> implements IUserLevelView {

    @Bind(R.id.img_user)
    RatioImageView mImgUser;
    @Bind(R.id.tv_level)
    TextView mTvLevel;
    @Bind(R.id.tv_experience)
    TextView mTvExperience;
    @Bind(R.id.pb_level)
    CustomProgressBar mPbLevel;
    @Bind(R.id.tv_how_to_go_up)
    TextView mTvHowToGoUp;

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
        refreshUser();
        mPresenter.getMemberGrade();
    }

    @Override
    public void refreshUser() {
        if (isInFragment && UserInfoUtil.mUser != null) {
            GlideUtil.loadCircleImage(getActivity(), mImgUser, UserInfoUtil.mUser.getHeadImg(),
                    R.drawable.bitmap_user_head, 0, 0, true, false);
        }
    }

    @Override
    public void refreshData(MemberGradeBean bean) {
        if (isInFragment && bean != null) {
            mTvLevel.setText(bean.getMemberGrade());
            int diff = bean.getMemberUpgradeExpSum() - bean.getMemberUpgradeExp();
            mTvExperience.setText(String.valueOf(diff));

            mPbLevel.setMax(bean.getMemberUpgradeExpSum() > 0 ? bean.getMemberUpgradeExpSum() : 1000);
            mPbLevel.setProgress(bean.getMemberUpgradeExp());

            mTvHowToGoUp.setText(bean.getExp());
        }
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
