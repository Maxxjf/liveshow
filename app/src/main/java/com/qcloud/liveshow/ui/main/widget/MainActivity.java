package com.qcloud.liveshow.ui.main.widget;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.enums.StartHomeEnum;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.ui.anchor.widget.AnchorActivity;
import com.qcloud.liveshow.ui.anchor.widget.ApplyAnchorActivity;
import com.qcloud.liveshow.ui.home.widget.HomeFragment;
import com.qcloud.liveshow.ui.main.presenter.impl.MainPresenterImpl;
import com.qcloud.liveshow.ui.main.view.IMainView;
import com.qcloud.liveshow.ui.mine.widget.MineFragment;
import com.qcloud.liveshow.utils.BasicsUtil;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.permission.PermissionsManager;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class MainActivity extends BaseActivity<IMainView, MainPresenterImpl> implements IMainView {

    @Bind(R.id.btn_home)
    ImageView mBtnHome;
    @Bind(R.id.btn_live_show)
    ImageView mBtnLiveShow;
    @Bind(R.id.btn_mine)
    ImageView mBtnMine;

    private String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE};

    private HomeFragment mHomeFragment;
    private MineFragment mMineFragment;

    private long exitTime = 0;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenterImpl initPresenter() {
        return new MainPresenterImpl();
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
    protected boolean isPaddingStatus() {
        return false;
    }

    @Override
    protected void initViewAndData() {
        int startEnum = getIntent().getIntExtra("START_ENUM", 1);
        switchStart(startEnum);

        requestPermission();
        loadBasicData();
    }

    /**
     * 申请应用需要的权限
     * */
    private void requestPermission() {
        PermissionsManager manager = new PermissionsManager(this);
        manager.setLogging(true);
        manager.request(PERMISSIONS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 所有权限都开启aBoolean才为true，否则为false
                            Timber.e(getString(R.string.toast_permission_open));
                        } else {
                            ToastUtils.ToastMessage(MainActivity.this, R.string.toast_permission_refuse);
                        }
                    }
                });
    }

    /**
     * 加载基础数据
     * */
    private void loadBasicData() {
        BasicsUtil.loadDiamonds();
        BasicsUtil.loadGift();
        BasicsUtil.getContactWay();
    }

    @OnClick({R.id.btn_home, R.id.btn_live_show, R.id.btn_mine})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onHomeClick() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance(StartHomeEnum.StartHot.getKey());
        }
        replaceFragment(mHomeFragment, R.id.fragment_container, false);
        clearEffect(mBtnHome);
    }

    @Override
    public void onLiveShowClick() {
        if (BaseApplication.loginAuth()) {
            mPresenter.getApplyStatus();
            startLoadingDialog();
        }
    }

    @Override
    public void onMineClick() {
        if (mMineFragment == null) {
            mMineFragment = new MineFragment();
        }
        replaceFragment(mMineFragment, R.id.fragment_container, false);
        clearEffect(mBtnMine);
    }

    @Override
    public void showPending() {
        // 审核中
        Timber.e("审核中");
        stopLoadingDialog();
        if (isRunning && mBtnLiveShow != null) {
            TipsPop pop = new TipsPop(this);
            pop.setTips(R.string.toast_apply_pending);
            pop.showCancel(false);
            pop.showAtLocation(mBtnLiveShow, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void showAgree() {
        // 审核通过
        Timber.e("审核通过");
        stopLoadingDialog();
        AnchorActivity.openActivity(this);
    }

    @Override
    public void showDisagree() {
        // 审核不通过
        Timber.e("审核不通过");
        stopLoadingDialog();
        if (isRunning && mBtnLiveShow != null) {
            final TipsPop pop = new TipsPop(this);
            pop.setTips(R.string.toast_apply_disagree);
            pop.setCancelBtn(R.string.btn_no);
            pop.setOkBtn(R.string.btn_yes);
            pop.showAtLocation(mBtnLiveShow, Gravity.CENTER, 0, 0);
            pop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
                @Override
                public void onViewClick(View view) {
                    if (view.getId() == R.id.btn_ok) {
                        ApplyAnchorActivity.openActivity(MainActivity.this);
                    } else {
                        pop.dismiss();
                    }
                }
            });
        }
    }

    @Override
    public void showNotApply() {
        // 未提交审核
        Timber.e("未提交审核");
        stopLoadingDialog();
        ApplyAnchorActivity.openActivity(MainActivity.this);
    }

    @Override
    public void showDisable() {
        // 禁用
        Timber.e("禁用");
        stopLoadingDialog();
        if (isRunning && mBtnLiveShow != null) {
            TipsPop pop = new TipsPop(this);
            pop.setTips(R.string.toast_apply_disable);
            pop.showCancel(false);
            pop.showAtLocation(mBtnLiveShow, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            stopLoadingDialog();
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    private void switchStart(int startEnum) {
        if (startEnum == StartMainEnum.StartHome.getKey()) {
            onHomeClick();
        } else if (startEnum == StartMainEnum.StartLiveShow.getKey()) {
            onLiveShowClick();
        } else if (startEnum == StartMainEnum.StartMine.getKey()) {
            onMineClick();
        } else {
            onHomeClick();
        }
    }

    private void clearEffect(ImageView btnTab) {
        if (btnTab == mBtnHome) {
            mBtnHome.setImageResource(R.drawable.icon_home_select);
            mBtnMine.setImageResource(R.drawable.icon_mine_normal);
        } else if (btnTab == mBtnMine) {
            mBtnHome.setImageResource(R.drawable.icon_home_normal);
            mBtnMine.setImageResource(R.drawable.icon_mine_select);
        } else {
            mBtnHome.setImageResource(R.drawable.icon_home_select);
            mBtnMine.setImageResource(R.drawable.icon_mine_normal);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int startEnum = intent.getIntExtra("START_ENUM", 1);
        switchStart(startEnum);
    }

    public static void openActivity(Context context, int startEnum) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("START_ENUM", startEnum);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.ToastMessage(this, R.string.toast_exit_app);
                exitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getInstance().getAppManager().AppExit(this);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
