package com.qcloud.liveshow.ui.account.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.qcloud.liveshow.ui.account.presenter.impl.ForgetPassWordPresenterImpl;
import com.qcloud.liveshow.ui.account.view.IForgetPassWordView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.ValidateUtil;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 类说明：忘记/重置密码
 * Author: iceberg
 * Date: 2017/12/5.
 */
public class ForgetPassWordActivity extends SwipeBaseActivity<IForgetPassWordView, ForgetPassWordPresenterImpl> implements IForgetPassWordView {
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.tv_verification_code_tag)
    TextView tvVerificationCodeTag;
    @Bind(R.id.et_verification_code)
    EditText etVerificationCode;
    @Bind(R.id.btn_get_code)
    TextView btnGetCode;
    @Bind(R.id.tv_set_password_tag)
    TextView tvSetPasswordTag;
    @Bind(R.id.et_set_password)
    EditText etSetPassword;
    @Bind(R.id.cb_see)
    CheckBox cbSee;
    @Bind(R.id.btn_confirm)
    TextView btnConfirm;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.et_account)
    EditText etAccount;

    @BindString(R.string.tag_input_contact_way)
    String contactWay;
    @BindString(R.string.btn_get_code)
    String getCode;
    @BindString(R.string.btn_get_code_after_second)
    String getCodeAfter;
    @BindString(R.string.btn_get_code_again)
    String getCodeAgain;

    private Disposable mDisposable;
    private String code;
    private String passWord;
    private String account;

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

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_forget_passwork;
    }

    @Override
    protected ForgetPassWordPresenterImpl initPresenter() {
        return new ForgetPassWordPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        initTagWidth();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ForgetPassWordActivity.class));
    }

    /**
     * 重置标签长度
     */
    private void initTagWidth() {
        tvSetPasswordTag.post(new Runnable() {
            @Override
            public void run() {
                int width = tvSetPasswordTag.getWidth();
                resetWidth(tvVerificationCodeTag, width);
                resetWidth(tvAccount, width);
            }
        });
    }

    private void resetWidth(View view, int width) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }

    @OnCheckedChanged(R.id.cb_see)
    void onSeeCheck(boolean isChecked) {
        if (isChecked) {
            //设置EditText文本为可见的
            etSetPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置EditText文本为隐藏的
            etSetPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        etSetPassword.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = etSetPassword.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }


    @Override
    public void getCodeSuccess(String code) {
        if (isRunning) {
            ToastUtils.ToastMessage(this, getResources().getString(R.string.toast_has_been_sent));
            startTimer();
        }
    }

    @Override
    public void getCodeFail(String errMsg) {
        if (isRunning) {
            ToastUtils.ToastMessage(mContext, errMsg);
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            if (btnGetCode != null) {
                btnGetCode.setText(getCode);
                btnGetCode.setEnabled(true);
            }
        }
    }

    @Override
    public void updatePasswordSuccess() {
        ToastUtils.ToastMessage(this,getResources().getString(R.string.toast_reset_password_success));
        finish();
    }

    @Override
    public void onClickGetCode() {
        if (checkEmailAndAccound()) {
            mPresenter.forgetPasswordCode(account);
            btnGetCode.setEnabled(false);

        }
    }

    @Override
    public void onClickConfirm() {
        if (check()) {
            mPresenter.forgetPassword(account,code,passWord);
        }
    }

    private void startTimer() {
        mDisposable = Observable.interval(1, TimeUnit.SECONDS).take(60)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (btnGetCode != null) {
                            btnGetCode.setText(getCode);
                            btnGetCode.setEnabled(true);
                        }
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (btnGetCode != null) {
                            btnGetCode.setText(String.format(getCodeAfter, 60 - aLong));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (btnGetCode != null) {
                            btnGetCode.setText(getCodeAgain);
                            btnGetCode.setEnabled(true);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (btnGetCode != null) {
                            btnGetCode.setText(getCodeAgain);
                            btnGetCode.setEnabled(true);
                        }
                    }
                });
    }

    private boolean checkEmailAndAccound() {
        account = etAccount.getText().toString().trim();
        if (StringUtils.isEmptyString(account)) {
            loadErr(true, getResources().getString(R.string.input_account_hint));
            return false;
        }
        return true;
    }

    private boolean check() {
        code = etVerificationCode.getText().toString().trim();
        passWord = etSetPassword.getText().toString().trim();
        if (!checkEmailAndAccound()) {
            return false;
        }
        if (StringUtils.isEmptyString(code)) {
            loadErr(true, getResources().getString(R.string.toast_input_code));
            return false;
        }
        if (StringUtils.isEmptyString(passWord)) {
            loadErr(true, getResources().getString(R.string.input_password_hint));
            return false;
        }
        if(!ValidateUtil.isAccount(passWord)){
            ToastUtils.ToastMessage(mContext,getResources().getString(R.string.toast_format_passage));
            return false;
        }
        return true;
    }


    @OnClick({R.id.btn_get_code, R.id.btn_confirm})
    public void onClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
