package com.qcloud.liveshow.ui.anchor.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.anchor.presenter.impl.AnchorFinishPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IAnchorFinishView;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：直播完成
 * Author: Kuzan
 * Date: 2017/9/2 16:47.
 */
public class AnchorFinishActivity extends BaseActivity<IAnchorFinishView, AnchorFinishPresenterImpl> implements IAnchorFinishView {

    @Bind(R.id.img_user_head)
    RatioImageView mImgUserHead;
    @Bind(R.id.img_anchor_level)
    ImageView mImgAnchorLevel;
    @Bind(R.id.layout_user)
    FrameLayout mLayoutUser;
    @Bind(R.id.tv_nickname)
    TextView mTvNickname;
    @Bind(R.id.tv_id)
    TextView mTvId;
    @Bind(R.id.layout_id)
    LinearLayout mLayoutId;
    @Bind(R.id.tv_anchor_state)
    TextView mTvAnchorState;
    @Bind(R.id.tv_curr_income)
    TextView mTvCurrIncome;
    @Bind(R.id.tv_audience)
    TextView mTvAudience;
    @Bind(R.id.tv_gift_profit)
    TextView mTvGiftProfit;
    @Bind(R.id.tv_curr_profit)
    TextView mTvCurrProfit;
    @Bind(R.id.btn_go_home)
    TextView mBtnGoHome;

    @Override
    protected int initLayout() {
        return R.layout.activity_anchor_finish;
    }

    @Override
    protected AnchorFinishPresenterImpl initPresenter() {
        return new AnchorFinishPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        GlideUtil.loadCircleImage(this, mImgUserHead, "", R.drawable.icon_default_user, 0, 0, true, false);
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    @OnClick({R.id.btn_go_home})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onGoHomeClick() {
        finish();
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, AnchorFinishActivity.class);
        context.startActivity(intent);
    }
}
