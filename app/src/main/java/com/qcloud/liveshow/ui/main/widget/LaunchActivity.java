package com.qcloud.liveshow.ui.main.widget;

import android.Manifest;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.ui.account.widget.LoginActivity;
import com.qcloud.liveshow.ui.main.presenter.impl.LaunchPresenterImpl;
import com.qcloud.liveshow.ui.main.view.ILaunchView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.permission.PermissionsManager;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.SystemBarUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * 类说明：启动页
 * Author: Kuzan
 * Date: 2017/10/25 9:42.
 */
public class LaunchActivity extends BaseActivity<ILaunchView, LaunchPresenterImpl> implements ILaunchView {

    // 权限申请
    private String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.RECORD_AUDIO};

    @Override
    protected int initLayout() {
        return R.layout.activity_launch;
    }

    @Override
    protected LaunchPresenterImpl initPresenter() {
        return new LaunchPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        SystemBarUtil.transparencyNavBar(this);
        initRxBusEvent();

        startTimer();
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                switch (rxBusEvent.getType()) {
                    case BaseApi.NOT_LOGIN_STATUS_TYPE:
                    case R.id.get_user_info_error:
                        Timber.e("未登录");
                        toLogin();
                        break;
                    case R.id.get_user_info_success:
                        toMain();
                        break;
                }
            }
        }));
    }

    private void startTimer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        requestPermission();
                    }
                });
    }

    /**
     * 申请应用需要的权限
     * */
    private void requestPermission() {
        final PermissionsManager manager = new PermissionsManager(this);
        manager.setLogging(true);
        manager.request(PERMISSIONS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            // 所有权限都开启aBoolean才为true，否则为false
                            Timber.e(getString(R.string.toast_permission_open));
                        } else {
                            ToastUtils.ToastMessage(LaunchActivity.this, R.string.toast_permission_refuse);
                        }
                        if (BaseApplication.isLogin()) {
                            UserInfoUtil.loadUserInfo();
                        } else {
                            toLogin();
                        }
                    }
                });
    }

    private void toMain() {
        MainActivity.openActivity(this, StartMainEnum.StartHome.getKey());
        finish();
    }

    private void toLogin() {
        LoginActivity.openActivity(this);
        finish();
    }
}
