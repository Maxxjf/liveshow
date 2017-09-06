package com.qcloud.liveshow.ui.account.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.ui.account.presenter.impl.LoginPresenterImpl;
import com.qcloud.liveshow.ui.account.view.ILoginView;
import com.qcloud.liveshow.ui.main.widget.MainActivity;
import com.qcloud.liveshow.ui.main.widget.WebActivity;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.TokenUtil;
import com.qcloud.qclib.utils.ValidateUtil;
import com.qcloud.qclib.widget.customview.ClearEditText;
import com.qcloud.qclib.widget.customview.LineTextView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 类说明：登录页面
 * Author: Kuzan
 * Date: 2017/8/8 15:46.
 */
public class LoginActivity extends BaseActivity<ILoginView, LoginPresenterImpl> implements ILoginView {
    @Bind(R.id.et_mobile)
    ClearEditText mEtMobile;
    @Bind(R.id.et_code)
    ClearEditText mEtCode;
    @Bind(R.id.btn_get_code)
    TextView mBtnGetCode;
    @Bind(R.id.btn_login)
    TextView mBtnLogin;
    @Bind(R.id.btn_clause)
    LineTextView mBtnClause;
    @Bind(R.id.btn_we_chat)
    ImageView mBtnWeChat;
    @Bind(R.id.btn_facebook)
    ImageView mBtnFacebook;

    @BindString(R.string.toast_has_been_sent_to)
    String hasBeenSendTo;
    @BindString(R.string.btn_get_code)
    String getCode;
    @BindString(R.string.btn_get_code_after_second)
    String getCodeAfter;

    private Disposable mDisposable;

    private String mobile;
    private String code;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenterImpl initPresenter() {
        return new LoginPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;    // 使用透明标题栏
    }

    @Override
    protected void initViewAndData() {
        initRxBusEvent();
        if (BaseApplication.isLogin()) {
            UserInfoUtil.loadUserInfo();
        } else {
            initView();
        }
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                if (rxBusEvent.getType() == BaseApi.NOT_LOGIN_STATUS_TYPE) {
                    Timber.e("未登录");
                    initView();
                } else if (rxBusEvent.getType() == R.id.get_user_info_success) {
                    toMain();
                }
            }
        }));
    }

    private void initView() {
        mBtnClause.setText(getString(R.string.tag_clause), LineTextView.BOTTOM);
    }

    private void toMain() {
        MainActivity.openActivity(this, StartMainEnum.START_HOME.getKey());
        finish();
    }

    @OnClick({R.id.btn_get_code, R.id.btn_login, R.id.btn_clause, R.id.btn_we_chat, R.id.btn_facebook})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onLoginClick() {
        if (check()) {
            mPresenter.login(mobile, code);
        }
    }

    @Override
    public void onGetCodeClick() {
        if (checkMobile()) {
            mPresenter.getCode(mobile);
            mBtnGetCode.setEnabled(false);
            startTimer();
        }
    }

    @Override
    public void onWeChatClick() {
        Timber.d("onWeChatClick");
    }

    @Override
    public void onFacebookClick() {
        Timber.i("onFacebookClick");
    }

    @Override
    public void onClauseClick() {
        WebActivity.openActivity(this, "责任条款", "http://jiahua.test.qi-cloud.com/fep/app/merchandise/getGoodsMapDetails?id=334699649996685312");
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        if (isRunning) {
            if (bean != null) {
                TokenUtil.saveToken(bean.getToken());
                ToastUtils.ToastMessage(this, R.string.toast_login_success);
                UserInfoUtil.loadUserInfo();
                toMain();
            } else {
                ToastUtils.ToastMessage(this, R.string.toast_login_failure);
            }
        }
    }

    @Override
    public void getCodeSuccess() {
        if (isRunning) {
            ToastUtils.ToastMessage(this, String.format(hasBeenSendTo, mobile));
        }
    }

    @Override
    public void getCodeFailure(String errMsg) {
        if (isRunning) {
            ToastUtils.ToastMessage(mContext, errMsg);
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            if (mBtnGetCode != null) {
                mBtnGetCode.setText(getCode);
                mBtnGetCode.setEnabled(true);
            }
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

    private boolean check() {
        code = mEtCode.getText().toString().trim();

        if (!checkMobile()) {
            return false;
        }

        if (StringUtils.isEmptyString(code)) {
            ToastUtils.ToastMessage(this, R.string.toast_input_code);
            mEtCode.requestFocus();
            return false;
        }

        return true;
    }

    public boolean checkMobile() {
        mobile = mEtMobile.getText().toString().trim();

        if (StringUtils.isEmptyString(mobile)) {
            ToastUtils.ToastMessage(this, R.string.input_mobile_hint);
            mEtMobile.requestFocus();
            return false;
        }

        if (!ValidateUtil.isMobilePhone(mobile)) {
            ToastUtils.ToastMessage(this, R.string.toast_right_mobile_phone);
            mEtMobile.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * 启动定时器
     * */
    private void startTimer() {
        Observable observable = Observable.interval(1, TimeUnit.SECONDS).take(60).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        mDisposable = observable.doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(getCode);
                    mBtnGetCode.setEnabled(true);
                }
            }
        }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(String.format(getCodeAfter, (60 - aLong)));
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(getCode);
                    mBtnGetCode.setEnabled(true);
                }
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Timber.e("onComplete");
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(getCode);
                    mBtnGetCode.setEnabled(true);
                }
            }
        });
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
