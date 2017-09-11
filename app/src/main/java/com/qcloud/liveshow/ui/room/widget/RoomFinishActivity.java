package com.qcloud.liveshow.ui.room.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.room.presenter.impl.RoomFinishPresenterImpl;
import com.qcloud.liveshow.ui.room.view.IRoomFinishView;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：直播完成
 * Author: Kuzan
 * Date: 2017/9/11 15:40.
 */
public class RoomFinishActivity extends BaseActivity<IRoomFinishView, RoomFinishPresenterImpl> implements IRoomFinishView {

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
    @Bind(R.id.btn_go_home)
    TextView mBtnGoHome;

    @Override
    protected int initLayout() {
        return R.layout.activity_room_finish;
    }

    @Override
    protected RoomFinishPresenterImpl initPresenter() {
        return new RoomFinishPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        GlideUtil.loadCircleImage(this, mImgUserHead, "", R.drawable.bitmap_user_head, 0, 0, true, false);
    }

    @OnClick({R.id.btn_go_home})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onGoHomeClick() {
        finish();
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

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, RoomFinishActivity.class));
    }
}
