package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.mine.presenter.impl.UserInfoPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IUserInfoView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：个人信息
 * Author: Kuzan
 * Date: 2017/8/15 9:29.
 */
public class UserInfoActivity extends SwipeBaseActivity<IUserInfoView, UserInfoPresenterImpl> implements IUserInfoView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.img_user)
    RatioImageView mImgUser;
    @Bind(R.id.tv_user_name)
    TextView mTvUserName;
    @Bind(R.id.img_user_sex)
    ImageView mImgUserSex;
    @Bind(R.id.img_anchor_level)
    ImageView mImgAnchorLevel;
    @Bind(R.id.img_user_level)
    ImageView mImgUserLevel;
    @Bind(R.id.tv_user_id)
    TextView mTvUserId;
    @Bind(R.id.btn_edit_info)
    TextView mBtnEditInfo;
    @Bind(R.id.tv_follow)
    TextView mTvFollow;
    @Bind(R.id.layout_follow)
    LinearLayout mLayoutFollow;
    @Bind(R.id.tv_fans)
    TextView mTvFans;
    @Bind(R.id.layout_fans)
    LinearLayout mLayoutFans;

    @Override
    protected int initLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected UserInfoPresenterImpl initPresenter() {
        return new UserInfoPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        /**解决状态栏与内容重叠*/
        SystemBarUtil.remeasureTitleBar(this, mTitleBar);
    }

    @OnClick({R.id.btn_edit_info, R.id.layout_follow, R.id.layout_fans })
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onEditClick() {
        EditUserActivity.openActivity(this);
    }

    @Override
    public void onFollowClick() {
        MyFansActivity.openActivity(this, StartFansEnum.MY_FOLLOW.getKey());
    }

    @Override
    public void onFansClick() {
        MyFansActivity.openActivity(this, StartFansEnum.MY_FANS.getKey());
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isShow) {
            ToastUtils.ToastMessage(this, errMsg);
        } else {
            Timber.e(errMsg);
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, UserInfoActivity.class));
    }
}
