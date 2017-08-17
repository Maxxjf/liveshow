package com.qcloud.liveshow.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.utils.SwipeBackUtils;
import com.qcloud.liveshow.widget.swipeback.SwipeBackActivityBase;
import com.qcloud.liveshow.widget.swipeback.SwipeBackActivityHelper;
import com.qcloud.liveshow.widget.swipeback.SwipeBackLayout;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.dialog.LoadingDialog;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * 类说明：侧滑返回实现
 * Author: Kuzan
 * Date: 2017/8/17 14:37.
 */
public abstract class SwipeBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity implements SwipeBackActivityBase {

    protected Context mContext;
    protected BaseApplication application = BaseApplication.getInstance();
    protected Bus mEventBus = BusProvider.getInstance();
    protected T mPresenter;

    private SwipeBackActivityHelper mHelper;

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

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        isRunning = true;

        initViewAndData();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
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
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
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
                    SystemBarUtil.setStatusBarColor(this, setStatusBarColor(), false);
                    SystemBarUtil.setStatusBarLightMode(this, true);
                } else {
                    Timber.e("当前设备不支持状态栏字体变色");
                    // 设置状态栏颜色为主题颜色
                    SystemBarUtil.setStatusBarColor(this, getDarkColorPrimary(), false);
                }
            } else {
                // 设置状态栏颜色
                SystemBarUtil.setStatusBarColor(this, setStatusBarColor(), false);
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
