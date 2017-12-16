package com.qcloud.liveshow.ui.mine.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.LevelNameAdapter;
import com.qcloud.liveshow.adapter.SubCommissionAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.AnchorGradeBean;
import com.qcloud.liveshow.beans.LevelBean;
import com.qcloud.liveshow.ui.mine.presenter.impl.AnchorLevelPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IAnchorLevelView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：主播等级
 * Author: Kuzan
 * Date: 2017/9/2 17:59.
 */
public class AnchorLevelFragment extends BaseFragment<IAnchorLevelView, AnchorLevelPresenterImpl> implements IAnchorLevelView {
    @Bind(R.id.img_user)
    RatioImageView mImgUser;
    @Bind(R.id.img_level)
    ImageView mImgLevel;
    @Bind(R.id.list_level)
    RecyclerView mListLevel;
    @Bind(R.id.list_sub_commission)
    RecyclerView mListSubCommission;
    @Bind(R.id.tv_contact_way)
    TextView mTvContactWay;

    private LevelNameAdapter mNameAdapter;
    private SubCommissionAdapter mSubCommissionAdapter;

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
        initLevelNameLayout();
        initSubCommissionLayout();
    }

    @Override
    protected void beginLoad() {
        refreshUser();
        mPresenter.getAnchorGrade();
    }

    private void initLevelNameLayout() {
        mListLevel.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mNameAdapter = new LevelNameAdapter(getActivity());
        mListLevel.setAdapter(mNameAdapter);
    }

    private void initSubCommissionLayout() {
        mListSubCommission.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mSubCommissionAdapter = new SubCommissionAdapter(getActivity());
        mListSubCommission.setAdapter(mSubCommissionAdapter);
    }

    @Override
    public void refreshUser() {
        if (isInFragment && UserInfoUtil.mUser != null) {
            GlideUtil.loadCircleImage(getActivity(), mImgUser, UserInfoUtil.mUser.getHeadImg(),
                    R.drawable.bitmap_user_head, 0, 0, true, false);
        }
    }

    @Override
    public void refreshData(AnchorGradeBean bean) {
        if (isInFragment && bean != null) {
            if (StringUtils.isEmptyString(bean.getAnchorGradeIcon())){
                mImgLevel.setVisibility(View.GONE);
            }else {
                GlideUtil.loadCircleImage(getActivity(), mImgLevel, bean.getAnchorGradeIcon(),
                        R.drawable.icon_anchor_level_1, 0, 0, true, false);
            }
            mTvContactWay.setText(bean.getContact());
        }
    }

    @Override
    public void replaceLevel(List<LevelBean> beans) {
        if (isInFragment && beans != null) {
            if (mNameAdapter != null) {
                mNameAdapter.replaceList(beans);
            }
            if (mSubCommissionAdapter != null) {
                mSubCommissionAdapter.replaceList(beans);
            }
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
