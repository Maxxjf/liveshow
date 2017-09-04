package com.qcloud.liveshow.ui.mine.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.LevelNameAdapter;
import com.qcloud.liveshow.adapter.SubCommissionAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.ui.mine.presenter.impl.AnchorLevelPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IAnchorLevelView;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.CustomProgressBar;
import com.qcloud.qclib.widget.customview.RatioImageView;

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
    @Bind(R.id.tv_experience)
    TextView mTvExperience;
    @Bind(R.id.pb_level)
    CustomProgressBar mPbLevel;
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
        GlideUtil.loadCircleImage(getActivity(), mImgUser, "", R.drawable.icon_default_user, 0, 0, true, false);
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
