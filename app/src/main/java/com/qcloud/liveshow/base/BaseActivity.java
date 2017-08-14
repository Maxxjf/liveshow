package com.qcloud.liveshow.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.utils.SystemBarTintManager;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;
import com.qcloud.qclib.widget.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

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
        //mDisposable = new CompositeDisposable();
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
    }

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
//        if (mDisposable != null && mDisposable.isDisposed()) {
//            mDisposable.dispose();
//        }
        mEventBus.unregister(this);
        mEventBus = null;

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

    /**
     * 设置状态栏颜色
     * */
    protected void initSystemBarTint() {
        Window window = getWindow();
        if (translucentStatusBar()) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            return;
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(setStatusBarColor());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintColor(setStatusBarColor());
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(setStatusBarColor());
            tintManager.setStatusBarDarkMode(true, this);
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
