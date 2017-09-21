package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.mine.presenter.impl.UserInfoPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IUserInfoView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    @Bind(R.id.tv_nickname)
    TextView mTvNickname;
    @Bind(R.id.tv_sex)
    TextView mTvSex;
    @Bind(R.id.tv_signature)
    TextView mTvSignature;

    @BindString(R.string.tag_id)
    String idTag;

    private UserBean mUser;

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

        initRxBusEvent();
        if (UserInfoUtil.mUser == null) {
            startLoadingDialog();
            UserInfoUtil.loadUserInfo();
        } else {
            mUser = UserInfoUtil.mUser;
            refreshUserInfo(mUser);
        }
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                stopLoadingDialog();
                if (rxBusEvent.getType() == R.id.get_user_info_success) {
                    mUser = (UserBean) rxBusEvent.getObj();
                    refreshUserInfo(mUser);
                }
            }
        }));
    }

    @OnClick({R.id.btn_edit_info, R.id.layout_follow, R.id.layout_fans})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onEditClick() {
        EditUserActivity.openActivity(this);
    }

    @Override
    public void onFollowClick() {
        MyFansActivity.openActivity(this, StartFansEnum.MyFollow.getKey());
    }

    @Override
    public void onFansClick() {
        MyFansActivity.openActivity(this, StartFansEnum.MyFans.getKey());
    }

    @Override
    public void refreshUserInfo(UserBean bean) {
        if (isRunning && bean != null) {
            GlideUtil.loadImage(this, mImgUser, mUser.getHeadImg(), R.drawable.bitmap_user, 0, 0, true, false);

            mTvUserName.setText(bean.getName());
            mImgUserSex.setImageResource(bean.getSexIcon());
            if (StringUtils.isNotEmptyString(bean.getAnchorGradeIcon())) {
                mImgAnchorLevel.setVisibility(VISIBLE);
                GlideUtil.loadCircleImage(this, mImgAnchorLevel, bean.getAnchorGradeIcon(), R.drawable.icon_anchor_level_1,
                        0, 0, true, false);
            } else {
                mImgAnchorLevel.setVisibility(GONE);
            }
            if (StringUtils.isNotEmptyString(bean.getMemberGradeIcon())) {
                mImgUserLevel.setVisibility(VISIBLE);
                GlideUtil.loadCircleImage(this, mImgUserLevel, bean.getMemberGradeIcon(), R.drawable.icon_user_level_v,
                        0, 0, true, false);
            } else {
                mImgUserLevel.setVisibility(GONE);
            }
            mTvUserId.setText(String.format(idTag, bean.getIdAccount()));
            mTvFollow.setText(bean.getAttentionNumStr());
            mTvFans.setText(bean.getFansNumStr());

            mTvNickname.setText(bean.getNickName());
            mTvSex.setText(bean.getSexStr());
            mTvSignature.setText(bean.getSignature());
        }
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
