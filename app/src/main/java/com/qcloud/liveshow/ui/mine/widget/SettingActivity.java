package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.account.widget.LoginActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.SettingPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.ISettingView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.TokenUtil;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：设置
 * Author: Kuzan
 * Date: 2017/8/18 18:55.
 */
public class SettingActivity extends SwipeBaseActivity<ISettingView, SettingPresenterImpl> implements ISettingView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_cache)
    TextView mTvCache;
    @Bind(R.id.btn_logout)
    TextView mBtnLogout;

    @Override
    protected int initLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected SettingPresenterImpl initPresenter() {
        return new SettingPresenterImpl();
    }

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        //SystemBarUtil.remeasureTitleBar(this, mTitleBar, ContextCompat.getColor(this, R.color.white));
    }

    @OnClick({R.id.layout_blacklist, R.id.layout_about_us, R.id.layout_clear_cache,
            R.id.layout_problem, R.id.btn_logout})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, SettingActivity.class));
    }

    @Override
    public void onBlacklistClick() {
        MyFansActivity.openActivity(this, StartFansEnum.BLACKLIST.getKey());
    }

    @Override
    public void onAboutUsClick() {
        ToastUtils.ToastMessage(this, "关于我们");
    }

    @Override
    public void onClearCacheClick() {
        ToastUtils.ToastMessage(this, "清除缓存");
    }

    @Override
    public void onProblemClick() {
        ToastUtils.ToastMessage(this, "常见问题");
    }

    @Override
    public void onLogoutClick() {
        logoutWarning();
    }

    @Override
    public void logoutSuccess() {
        ToastUtils.ToastMessage(this, R.string.toast_logout_success);
        UserInfoUtil.mUser = null;
        TokenUtil.clearToken();
        BaseApplication.getInstance().getAppManager().killAllActivity();
        LoginActivity.openActivity(this);
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

    private void logoutWarning() {
        TipsPop pop = new TipsPop(this);
        pop.setTips(R.string.tip_confirm_to_logout);
        pop.setCancelBtn(R.string.tip_cancel);
        pop.setOkBtn(R.string.tip_confirm);
        pop.showAtLocation(mBtnLogout, Gravity.CENTER, 0, 0);
        pop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                if (view.getId() == R.id.btn_ok) {
                    mPresenter.logout();
                }
            }
        });
    }
}
