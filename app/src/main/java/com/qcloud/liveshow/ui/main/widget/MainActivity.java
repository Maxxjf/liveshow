package com.qcloud.liveshow.ui.main.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.enums.StartHomeEnum;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.ui.anchor.widget.ApplyAnchorActivity;
import com.qcloud.liveshow.ui.home.widget.HomeFragment;
import com.qcloud.liveshow.ui.main.presenter.impl.MainPresenterImpl;
import com.qcloud.liveshow.ui.main.view.IMainView;
import com.qcloud.liveshow.ui.mine.widget.MineFragment;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<IMainView, MainPresenterImpl> implements IMainView {

    @Bind(R.id.btn_home)
    ImageView mBtnHome;
    @Bind(R.id.btn_live_show)
    ImageView mBtnLiveShow;
    @Bind(R.id.btn_mine)
    ImageView mBtnMine;

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
    }

    @OnClick({R.id.btn_home, R.id.btn_live_show, R.id.btn_mine})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onHomeClick() {
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance(StartHomeEnum.START_HOT.getKey());
        }
        replaceFragment(mHomeFragment, R.id.fragment_container, false);
        clearEffect(mBtnHome);
    }

    @Override
    public void onLiveShowClick() {
        ApplyAnchorActivity.openActivity(this);
    }

    @Override
    public void onMineClick() {
        if (mMineFragment == null) {
            mMineFragment = new MineFragment();
        }
        replaceFragment(mMineFragment, R.id.fragment_container, false);
        clearEffect(mBtnMine);
    }

    private void switchStart(int startEnum) {
        if (startEnum == StartMainEnum.START_HOME.getKey()) {
            onHomeClick();
        } else if (startEnum == StartMainEnum.START_LIVE_SHOW.getKey()) {
            onLiveShowClick();
        } else if (startEnum == StartMainEnum.START_MINE.getKey()) {
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
