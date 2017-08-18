package com.qcloud.liveshow.ui.mine.widget;

import android.view.View;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.mine.presenter.impl.MinePresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMineView;
import com.qcloud.liveshow.ui.profit.widget.MyProfitActivity;
import com.qcloud.liveshow.widget.customview.ItemLayout;
import com.qcloud.liveshow.widget.customview.UserInfoLayout;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;

/**
 * 类说明：我的
 * Author: Kuzan
 * Date: 2017/8/10 9:58.
 */
public class MineFragment extends BaseFragment<IMineView, MinePresenterImpl> implements IMineView {
    @Bind(R.id.layout_user)
    UserInfoLayout mLayoutUser;
    @Bind(R.id.tv_follow)
    TextView mTvFollow;
    @Bind(R.id.tv_fans)
    TextView mTvFans;
    @Bind(R.id.layout_profit)
    ItemLayout mLayoutProfit;
    @Bind(R.id.layout_level)
    ItemLayout mLayoutLevel;
    @Bind(R.id.layout_diamonds)
    ItemLayout mLayoutDiamonds;
    @Bind(R.id.layout_gift)
    ItemLayout mLayoutGift;
    @Bind(R.id.layout_inviting_friends)
    ItemLayout mLayoutInvitingFriends;
    @Bind(R.id.layout_extension_code)
    ItemLayout mLayoutExtensionCode;
    @Bind(R.id.layout_set)
    ItemLayout mLayoutSet;

    @BindString(R.string.money)
    String moneyStr;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MinePresenterImpl initPresenter() {
        return new MinePresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        mLayoutProfit.setRemark(moneyStr+500);
    }

    @Override
    protected void beginLoad() {

    }

    @OnClick({R.id.layout_user, R.id.layout_follow, R.id.layout_fans, R.id.layout_profit, R.id.layout_level,
            R.id.layout_diamonds, R.id.layout_gift, R.id.layout_inviting_friends, R.id.layout_extension_code,
            R.id.layout_set})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onUserClick() {
        UserInfoActivity.openActivity(getActivity());
    }

    @Override
    public void onFollowClick() {
        MyFansActivity.openActivity(getActivity(), StartFansEnum.MY_FOLLOW.getKey());
    }

    @Override
    public void onFansClick() {
        MyFansActivity.openActivity(getActivity(), StartFansEnum.MY_FANS.getKey());
    }

    @Override
    public void onProfitClick() {
        MyProfitActivity.openActivity(getActivity());
    }

    @Override
    public void onUserLevelClick() {
        ToastUtils.ToastMessage(getActivity(), R.string.tag_user_center_level);
    }

    @Override
    public void onDiamondsClick() {
        ToastUtils.ToastMessage(getActivity(), R.string.tag_user_center_diamonds);
    }

    @Override
    public void onGiftsClick() {
        ToastUtils.ToastMessage(getActivity(), R.string.tag_user_center_gift);
    }

    @Override
    public void onInvitingFriendsClick() {
        ToastUtils.ToastMessage(getActivity(), R.string.tag_user_center_inviting_friends);
    }

    @Override
    public void onExtensionCodeClick() {
        ToastUtils.ToastMessage(getActivity(), R.string.tag_user_center_extension_code);
    }

    @Override
    public void onSetClick() {
        ToastUtils.ToastMessage(getActivity(), R.string.tag_user_center_set);
    }
}
