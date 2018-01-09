package com.qcloud.liveshow.ui.main.widget;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.ui.account.widget.LoginActivity;
import com.qcloud.liveshow.ui.main.presenter.impl.LaunchPresenterImpl;
import com.qcloud.liveshow.ui.main.view.ILaunchView;
import com.qcloud.liveshow.utils.RoomInfoUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.permission.PermissionsManager;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.ConstantUtil;
import com.qcloud.qclib.utils.SystemBarUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    // 权限申请 7.0
    private String[] PERMISSIONS2 = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
//            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
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
        getShareDate();
    }

    /**
     * 分享页唤醒APP的时候有数据
     */
    private void getShareDate(){
        Uri uriData = this.getIntent().getData();
        if (uriData!=null){
            String mydata = uriData.getQueryParameter("memberId");
            Timber.e("uriData:"+uriData);
            Timber.e("mydata:"+mydata);
            RoomInfoUtil.loadRoomInfo(mydata);
        }
    }
    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, rxBusEvent -> {
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
        }));
    }

    private void startTimer() {
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> requestPermission());
    }

    /**
     * 申请应用需要的权限
     * */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            PERMISSIONS=PERMISSIONS2;
        }
        final PermissionsManager manager = new PermissionsManager(this);
        manager.setLogging(true);
        manager.request(PERMISSIONS)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        // 所有权限都开启aBoolean才为true，否则为false
                        Timber.e(getString(R.string.toast_permission_open));
                    } else {
                        ToastUtils.ToastMessage(LaunchActivity.this, R.string.toast_permission_refuse);
                    }
                    toApp();
                });
    }

    private void toApp() {
        if (ConstantUtil.getBoolean(AppConstants.IS_NO_FIRST_START, false)) {
            if (BaseApplication.isLogin()) {
                UserInfoUtil.loadUserInfo();
            } else {
                toLogin();
            }
        } else {
            toGuide();
        }
    }

    private void toMain() {
        MainActivity.openActivity(this, StartMainEnum.StartHome.getKey());
        finish();
    }

    private void toLogin() {
        LoginActivity.openActivity(this);
        finish();
    }

    private void toGuide() {
        GuideActivity.openActivity(this);
        finish();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, LaunchActivity.class));
    }
}
