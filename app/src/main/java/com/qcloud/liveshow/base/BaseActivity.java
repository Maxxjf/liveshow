package com.qcloud.liveshow.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.account.widget.LoginActivity;
import com.qcloud.qclib.AppManager;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * 类说明：activity基类
 * Author: Kuzan
 * Date: 2017/8/1 13:50.
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    protected Context mContext;
    protected BaseApplication application = BaseApplication.getInstance();
    //protected CompositeDisposable mDisposable;
    protected Bus mEventBus = BusProvider.getInstance();
    protected T mPresenter;

    private List<BaseFragment> fragments;
    private Fragment mCurrFragment;

    protected boolean isRunning;

    private LoadingDialog loadingDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBarTint();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(initLayout());

        mContext = this;
        ButterKnife.bind(this);
        application.getAppManager().addActivity(this);
        // 注册eventBus
        if (mEventBus == null) {
            mEventBus = BusProvider.getInstance();
        }
        mEventBus.register(this);

        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }

        isRunning = true;

        initViewAndData();
        checkLogin();
    }

    public  void checkLogin(){
        mEventBus.registerSubscriber(this,mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                switch (rxBusEvent.getType()){
                    case BaseApi.NOT_LOGIN_STATUS_TYPE:
                        AppManager.getInstance().killAllActivity();
                        LoginActivity.openActivity(mContext);
                }
            }
        }));
    };

    public void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            if (fragments == null) {
                fragments = new ArrayList<>();
            }

            if (!fragments.contains(fragment)) {
                fragments.add(fragment);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
        ButterKnife.unbind(this);
        application.getAppManager().killActivity(this);

        // 如果订阅了相关事件，在onDestroy时取消订阅，防止RxJava可能会引起的内存泄漏问题
        if (mEventBus != null) {
            mEventBus.unregister(this);
            mEventBus = null;
        }

        isRunning = false;

        if (fragments != null) {
            for (BaseFragment fragment : fragments) {
                detach(fragment);
            }
            fragments.clear();
            fragments = null;
        }
    }

    public void detach(BaseFragment fragment) {
        if (fragments != null && fragments.contains(fragment)) {
            fragment.detach();
        }
    }

    public void remove(BaseFragment fragment) {
        if (fragments != null && fragments.contains(fragment)) {
            fragments.remove(fragment);
        }
    }

    protected void replaceFragment(Fragment toFragment, int containerId, boolean isAnim) {
        if (toFragment == null) {
            Log.w("BaseActivity", "将要替换的fragment不存在");
            return;
        }

        replaceFragment(toFragment, containerId, isAnim,
                com.qcloud.qclib.R.anim.left_right_in,
                com.qcloud.qclib.R.anim.left_right_out);
    }

    /**
     * 改变当前的fragment
     *
     * @param toFragment    需要加载的fragment
     * @param containerId fragment的布局容器id
     * @param isInAnim    是否需要切换动画
     */
    protected void replaceFragment(Fragment toFragment, int containerId, boolean isInAnim,
                                   int inAnim, int outAnim) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isInAnim) {
            ft.setCustomAnimations(inAnim, outAnim);
        }

        String toTag = toFragment.getClass().getSimpleName();

        if (mCurrFragment != null) {
            ft.hide(mCurrFragment);
        }

        if (!toFragment.isAdded()) {
            ft.add(containerId, toFragment, toTag);
        } else {
            ft.show(toFragment);
        }

        //ft.replace(containerId, fragment, fragment.getClass().getName());
        ft.commitAllowingStateLoss();

        mCurrFragment = toFragment;
    }

    /**
     * 设置状态栏颜色
     * */
    protected void initSystemBarTint() {
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            SystemBarUtil.transparencyStatusBar(this);
        } else {
            // 设置状态栏字体颜色为深色
            if (isStatusBarTextDark()) {
                if (SystemBarUtil.isSupportStatusBarDarkFont()) {
                    // 设置状态栏颜色
                    SystemBarUtil.setStatusBarColor(this, setStatusBarColor(), false, isPaddingStatus());
                    SystemBarUtil.setStatusBarLightMode(this, true);
                } else {
                    Timber.e("当前设备不支持状态栏字体变色");
                    // 设置状态栏颜色为主题颜色
                    SystemBarUtil.setStatusBarColor(this, getDarkColorPrimary(), false, isPaddingStatus());
                }
            } else {
                // 设置状态栏颜色
                SystemBarUtil.setStatusBarColor(this, setStatusBarColor(), false, isPaddingStatus());
            }
        }
    }

    /**
     * 获取主题色
     * */
    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取深主题色
     * */
    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 模版方法，通过该方法获取该Activity的view的layoutId
     */
    protected abstract int initLayout();

    /**
     * 实例化presenter
     *
     * @return presenter
     */
    protected abstract T initPresenter();

    /**
     * 初始化界面和数据
     */
    protected abstract void initViewAndData();

    /** 子类可以重写改变状态栏颜色 */
    protected int setStatusBarColor() {
        return getColorPrimary();
    }

    /** 子类可以重写决定是否使用透明状态栏 */
    protected boolean translucentStatusBar() {
        return false;
    }

    /** 子类可以重写决定是否使用状态栏深色字体 */
    protected boolean isStatusBarTextDark() {
        return false;
    }

    /** 子类可以重写决定是否解决状态栏与标题栏重叠问题 */
    protected boolean isPaddingStatus() {
        return true;
    }

    public void startLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(mContext);
        }
        loadingDialog.show();
    }

    public void stopLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }
}
