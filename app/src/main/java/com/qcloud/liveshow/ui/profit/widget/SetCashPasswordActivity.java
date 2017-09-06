package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.profit.presenter.impl.SetCashPasswordPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.ISetCashPasswordView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnCheckedChanged;
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
 * 类说明：设置提现密码
 * Author: Kuzan
 * Date: 2017/8/31 14:13.
 */
public class SetCashPasswordActivity extends SwipeBaseActivity<ISetCashPasswordView, SetCashPasswordPresenterImpl> implements ISetCashPasswordView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_contact_way_tag)
    TextView mTvContactWayTag;
    @Bind(R.id.et_contact_way)
    EditText mEtContactWay;
    @Bind(R.id.tv_verification_code_tag)
    TextView mTvVerificationCodeTag;
    @Bind(R.id.et_verification_code)
    EditText mEtVerificationCode;
    @Bind(R.id.btn_get_code)
    TextView mBtnGetCode;
    @Bind(R.id.tv_set_password_tag)
    TextView mTvSetPasswordTag;
    @Bind(R.id.et_set_password)
    EditText mEtSetPassword;
    @Bind(R.id.cb_see)
    CheckBox mCbSee;
    @Bind(R.id.btn_confirm)
    TextView mBtnConfirm;

    @BindString(R.string.btn_get_code)
    String getCode;
    @BindString(R.string.btn_get_code_after_second)
    String getCodeAfter;
    @BindString(R.string.btn_get_code_again)
    String getCodeAgain;

    private Disposable mDisposable;

    @Override
    protected int initLayout() {
        return R.layout.activity_set_cash_password;
    }

    @Override
    protected SetCashPasswordPresenterImpl initPresenter() {
        return new SetCashPasswordPresenterImpl();
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
    protected void initViewAndData() {
        initTagWidth();
    }

    /**
     * 重置标签长度
     * */
    private void initTagWidth() {
        mTvSetPasswordTag.post(new Runnable() {
            @Override
            public void run() {
                int width = mTvSetPasswordTag.getWidth();
                resetWidth(mTvContactWayTag, width);
                resetWidth(mTvVerificationCodeTag, width);
            }
        });
    }

    private void resetWidth(View view, int width) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }

    @OnClick({R.id.btn_get_code, R.id.btn_confirm})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @OnCheckedChanged(R.id.cb_see)
    void onSeeCheck(boolean isChecked) {
        if (isChecked) {
            //设置EditText文本为可见的
            mEtSetPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置EditText文本为隐藏的
            mEtSetPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mEtSetPassword.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = mEtSetPassword.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @Override
    public void onGetCodeClick() {
        mBtnGetCode.setEnabled(false);
        startTimer();
    }

    @Override
    public void onConfirmClick() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
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
                    mBtnGetCode.setText(getCodeAgain);
                    mBtnGetCode.setEnabled(true);
                }
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Timber.e("onComplete");
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(getCodeAgain);
                    mBtnGetCode.setEnabled(true);
                }
            }
        });
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, SetCashPasswordActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
