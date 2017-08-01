package com.qcloud.liveshow.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.widget.dialog.LoadingDialog;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

/**
 * 类说明：BaseFragment
 *          预加载问题，切换到其他页面时停止加载数据（可选）
 * Author: Kuzan
 * Date: 2017/8/1 13:50.
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {
    /** 视图是否已经初初始化*/
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected Context mContext;
    protected View mView;

    public T mPresenter;

    protected CompositeDisposable mDisposable;

    protected boolean isInFragment;

    private LoadingDialog loadingDialog = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }

        if (mPresenter != null) {
            mPresenter.attach((V) this);
        }

        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }

        if (getActivity() instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            activity.addFragment(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isInFragment = true;

        this.mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
        }
        ButterKnife.bind(this, mView);

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInit) {
            initViewAndData();
            isInit = true;

            /**初始化的时候去加载数据**/
            isCanLoadData();
        }
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        isInFragment = false;
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            beginLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    public void detach() {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        // 如果订阅了相关事件，在onDestroy时取消订阅，防止RxJava可能会引起的内存泄漏问题
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }


    /**
     * 模版方法，通过该方法获取该fragment的view的layoutid
     */
    protected abstract int getLayoutId();

    /**
     * 实例化presenter
     *
     * @return presenter
     */
    protected abstract T initPresenter();

    /**
     * 模版方法，在activity初始化之后调用
     */
    protected abstract void initViewAndData();


    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void beginLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以调用此方法
     */
    protected void stopLoad() {

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
