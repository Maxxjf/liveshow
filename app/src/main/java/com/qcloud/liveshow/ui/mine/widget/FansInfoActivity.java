package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.ui.home.widget.FansMessageActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.FansInfoPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IFansInfoView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 类说明：粉丝信息
 * Author: iceberg
 * Date: 2017/11/9 9:26.
 */
public class FansInfoActivity extends SwipeBaseActivity<IFansInfoView, FansInfoPresenterImpl> implements IFansInfoView {


    @Bind(R.id.img_user)
    RatioImageView imgUser;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.img_user_sex)
    ImageView imgUserSex;
    @Bind(R.id.img_anchor_level)
    ImageView imgAnchorLevel;
    @Bind(R.id.img_user_level)
    ImageView imgUserLevel;
    @Bind(R.id.tv_user_id)
    TextView tvUserId;
    @Bind(R.id.layout_user)
    FrameLayout layoutUser;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.tv_follow)
    TextView tvFollow;
    @Bind(R.id.layout_follow)
    LinearLayout layoutFollow;
    @Bind(R.id.tv_fans)
    TextView tvFans;
    @Bind(R.id.layout_fans)
    LinearLayout layoutFans;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.btn_send_message)
    TextView btnSendMessage;

    @BindString(R.string.tag_id)
    String idTag;


    @Override
    protected int initLayout() {
        return R.layout.activity_fans_info;
    }

    @Override
    protected FansInfoPresenterImpl initPresenter() {
        return new FansInfoPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        /**解决状态栏与内容重叠*/
        SystemBarUtil.remeasureTitleBar(this, titleBar);
        MemberBean mMemberBean = (MemberBean) getIntent().getSerializableExtra("mMemberBean");
        if (mMemberBean != null) {
            refreshUserInfo(mMemberBean);
        } else {
            ToastUtils.ToastMessage(mContext, "加载用户错误");

        }
    }

    @OnClick(R.id.btn_send_message)
    void onClick(View v) {
        mPresenter.onBtnClick(v.getId());
    }

    @Override
    public void refreshUserInfo(MemberBean bean) {
        if (isRunning && bean != null) {
            GlideUtil.loadImage(this, imgUser, bean.getHeadImg(), R.drawable.bitmap_user, 0, 0, true, false);

            tvUserName.setText(bean.getNickName());
            imgUserSex.setImageResource(bean.getSexIcon());
            if (StringUtils.isNotEmptyString(bean.getAnchorGradeIcon())) {
                imgAnchorLevel.setVisibility(VISIBLE);
                GlideUtil.loadCircleImage(this, imgAnchorLevel, bean.getAnchorGradeIcon(), R.drawable.icon_anchor_level_1,
                        0, 0, true, false);
            } else {
                imgAnchorLevel.setVisibility(GONE);
            }
            if (StringUtils.isNotEmptyString(bean.getMemberGradeIcon())) {
                imgUserLevel.setVisibility(VISIBLE);
                GlideUtil.loadCircleImage(this, imgUserLevel, bean.getMemberGradeIcon(), R.drawable.icon_user_level_v,
                        0, 0, true, false);
            } else {
                imgUserLevel.setVisibility(GONE);
            }
            tvUserId.setText(String.format(idTag, bean.getIdAccount()));
            tvFollow.setText(bean.getAttentionNumStr());
            tvFans.setText(bean.getFansNumStr());

            tvNickname.setText(bean.getNickName());
            tvSex.setText(bean.getSexStr());
            tvSignature.setText(bean.getSignature());
        }
    }

    @Override
    public void onClickSendMessage() {
        FansMessageActivity.openActivity(mContext);
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isShow) {
            ToastUtils.ToastMessage(this, errMsg);
        } else {
            Timber.e(errMsg);
        }
    }

    public static void openActivity(Context context, MemberBean bean) {
        Intent intent = new Intent(context, FansInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mMemberBean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
