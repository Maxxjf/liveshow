package com.qcloud.liveshow.ui.mine.widget;

import android.view.View;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.mine.presenter.impl.MinePresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMineView;
import com.qcloud.liveshow.ui.profit.widget.MyDiamondsActivity;
import com.qcloud.liveshow.ui.profit.widget.MyProfitActivity;
import com.qcloud.liveshow.ui.profit.widget.ResetCashPasswordActivity;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.customview.ItemLayout;
import com.qcloud.liveshow.widget.customview.UserInfoLayout;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

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

    @BindString(R.string.money_str)
    String moneyStr;
    @BindString(R.string.tag_long_num)
    String longNumStr;
    @BindString(R.string.tag_id)
    String idTag;

    private UserBean mUser;

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
        initRxBusEvent();
    }

    @Override
    protected void beginLoad() {
        if (UserInfoUtil.mUser == null) {
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
        MyFansActivity.openActivity(getActivity(), StartFansEnum.MyFollow.getKey());
    }

    @Override
    public void onFansClick() {
        MyFansActivity.openActivity(getActivity(), StartFansEnum.MyFans.getKey());
    }

    @Override
    public void onProfitClick() {
        MyProfitActivity.openActivity(getActivity());
    }

    @Override
    public void onUserLevelClick() {
        MyLevelActivity.openActivity(getActivity());
    }

    @Override
    public void onDiamondsClick() {
        MyDiamondsActivity.openActivity(getActivity());
    }

    @Override
    public void onGiftsClick() {
        MyGiftsActivity.openActivity(getActivity());
    }

    @Override
    public void onInvitingFriendsClick() {
        ExtensionActivity.openActivity(getActivity());
    }

    @Override
    public void onExtensionCodeClick() {
        ToastUtils.ToastMessage(getActivity(), R.string.tag_user_center_extension_code);
        ResetCashPasswordActivity.openActivity(getActivity());
    }

    @Override
    public void onSetClick() {
        SettingActivity.openActivity(getActivity());
    }

    @Override
    public void refreshUserInfo(UserBean bean) {
        if (isInFragment && bean != null) {
            if (mLayoutUser != null) {
                mLayoutUser.refreshUserInfo(bean);
            }
            if (mLayoutProfit != null) {
                mLayoutProfit.setRemark(String.format(moneyStr, bean.getMoney()));
            }
            if (mTvFollow != null) {
                mTvFollow.setText(bean.getAttentionNumStr());
            }
            if (mTvFans != null) {
                mTvFans.setText(bean.getFansNumStr());
            }
            if (mLayoutLevel != null) {
                if (bean.isAnchor()) {
                    mLayoutLevel.setRemark(bean.getAnchorGrade());
                } else {
                    mLayoutLevel.setRemark(bean.getMemberGrade());
                }
            }
            if (mLayoutDiamonds != null) {
                mLayoutDiamonds.setRemark(bean.getVirtualCoin() + "个");
            }
            if (mLayoutGift != null) {
                mLayoutGift.setRemark(bean.getGiftNum() + "个");
            }
            if (mLayoutExtensionCode != null) {
                mLayoutExtensionCode.setRemark(String.format(idTag, bean.getIdAccount()));
            }
        }
    }
}
