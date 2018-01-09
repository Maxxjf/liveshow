package com.qcloud.liveshow.ui.room.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.beans.AnchorBean;
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
    @Bind(R.id.tv_anchor_state)
    TextView mTvAnchorState;
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
        refreshInfo();
    }

    private void refreshInfo() {
        AnchorBean bean= (AnchorBean) getIntent().getSerializableExtra("anchor");
        if (bean!=null){
            GlideUtil.loadCircleImage(this, mImgUserHead, bean.getHeadImg(), R.drawable.bitmap_user_head, 0, 0, true, false);
            GlideUtil.loadCircleImage(this, mImgAnchorLevel, bean.getAnchorGradeIcon(), R.drawable.bitmap_user_head, 0, 0, true, false);
            mTvNickname.setText(bean.getNickName());
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

    public static void openActivity(Context context, AnchorBean bean) {
        Intent intent=new Intent(context,RoomFinishActivity.class);
        intent.putExtra("anchor",bean);
        context.startActivity(intent);
    }
}
