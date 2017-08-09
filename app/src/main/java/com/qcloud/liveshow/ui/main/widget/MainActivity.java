package com.qcloud.liveshow.ui.main.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.ui.main.presenter.impl.MainPresenterImpl;
import com.qcloud.liveshow.ui.main.view.IMainView;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<IMainView, MainPresenterImpl> implements IMainView {

    @Bind(R.id.btn_home)
    ImageView mBtnHome;
    @Bind(R.id.btn_live_show)
    RelativeLayout mBtnLiveShow;
    @Bind(R.id.btn_mine)
    ImageView mBtnMine;


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
        clearEffect(mBtnHome);
    }

    @Override
    public void onLiveShowClick() {

    }

    @Override
    public void onMineClick() {
        clearEffect(mBtnMine);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int startEnum = intent.getIntExtra("START_ENUM", 1);
        switchStart(startEnum);
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
