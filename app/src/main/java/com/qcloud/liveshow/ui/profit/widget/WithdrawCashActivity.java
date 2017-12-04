package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.ReturnWithdrawSuccessBean;
import com.qcloud.liveshow.enums.BankTypeEnum;
import com.qcloud.liveshow.ui.profit.presenter.impl.WithdrawCashPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IWithdrawCashView;
import com.qcloud.liveshow.widget.pop.BankPicker;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.ValidateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：提现到银行卡
 * Author: Kuzan
 * Date: 2017/8/31 17:25.
 */
public class WithdrawCashActivity extends SwipeBaseActivity<IWithdrawCashView, WithdrawCashPresenterImpl> implements IWithdrawCashView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_cash_tag)
    TextView mTvCashTag;
    @Bind(R.id.et_cash)
    EditText mEtCash;
    @Bind(R.id.tv_name_tag)
    TextView mTvNameTag;
    @Bind(R.id.et_name)
    EditText mEtName;
    @Bind(R.id.tv_bank_code_tag)
    TextView mTvBankCodeTag;
    @Bind(R.id.et_bank_code)
    EditText mEtBankCode;
    @Bind(R.id.tv_bank_tag)
    TextView mTvBankTag;
    @Bind(R.id.tv_bank)
    TextView mTvBank;
    @Bind(R.id.btn_confirm)
    TextView mBtnConfirm;
    @Bind(R.id.et_password)
    EditText mEtPassword;

    private BankPicker mPicker;
    private  BankTypeEnum mcurrentBank;
    private String cash;
    private String name;
    private String cardNumber;
    private Integer bankCode;
    private String password;

    @Override
    protected int initLayout() {
        return R.layout.activity_withdraw_cash;
    }

    @Override
    protected WithdrawCashPresenterImpl initPresenter() {
        return new WithdrawCashPresenterImpl();
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
     */
    private void initTagWidth() {
        mTvCashTag.post(new Runnable() {
            @Override
            public void run() {
                int width = mTvCashTag.getWidth();
                resetWidth(mTvNameTag, width);
                resetWidth(mTvBankCodeTag, width);
                resetWidth(mTvBankTag, width);
            }
        });
    }

    private void resetWidth(View view, int width) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }

    private void initBankPicker() {
        mPicker = new BankPicker(this);
        mPicker.setOnBankPickListener(new BankPicker.OnBankPickListener() {
            @Override
            public void onBankPicked(int index, BankTypeEnum bean) {
                if (mTvBank != null && bean != null) {
                    mcurrentBank=bean;
                    mTvBank.setText(bean.getName());
                }
            }
        });

    }

    @OnClick({R.id.tv_bank, R.id.btn_confirm})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onSelectBankClick() {
        List<BankTypeEnum> list = new ArrayList<>();
        list.add(BankTypeEnum.ABC);
        list.add(BankTypeEnum.ICBC);
        list.add(BankTypeEnum.BOC);
        list.add(BankTypeEnum.CMB);
        list.add(BankTypeEnum.BCM);
        list.add(BankTypeEnum.CCB);
        if (mPicker == null) {
            initBankPicker();
        }
        mPicker.refreshData(list);
        mPicker.showAtLocation(mTvBank, Gravity.BOTTOM, 0, 0);
    }

    @OnCheckedChanged(R.id.cb_see)
    void onSeeCheck(boolean isChecked) {
        if (isChecked) {
            //设置EditText文本为可见的
            mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //设置EditText文本为隐藏的
            mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        mEtPassword.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = mEtPassword.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @Override
    public void onConfirmClick() {
        if (check()) {
            mPresenter.withdraw2card(cash,name,cardNumber, bankCode,password);
        }
    }

    @Override
    public void withdraw2cardSuccess(ReturnWithdrawSuccessBean returnWithdrawSuccessBean) {
        ToastUtils.ToastMessage(this,returnWithdrawSuccessBean.isSuccess()?
                getResources().getString(R.string.toast_withdraw2card_success):getResources().getString(R.string.toast_withdraw2card_fail));
    }

    @Override
    public void withdraw2cardFails(String errMsg) {
        ToastUtils.ToastMessage(this,errMsg);
    }

    private boolean check() {
         cash = mEtCash.getText().toString().trim();
         name = mEtName.getText().toString().trim();
         cardNumber = mEtBankCode.getText().toString().trim();
        if (mcurrentBank!=null){
             bankCode = mcurrentBank.getKey();
        }
         password = mEtPassword.getText().toString().trim();

        if (StringUtils.isEmptyString(cash)||!ValidateUtil.isFitCash(Integer.parseInt(cash))) {
            ToastUtils.ToastMessage(this, R.string.input_withdraw_cash_num);
            mEtCash.requestFocus();
            return false;
        }

        if (StringUtils.isEmptyString(name)) {
            ToastUtils.ToastMessage(this, R.string.toast_input_name);
            mEtName.requestFocus();
            return false;
        }

        if (StringUtils.isEmptyString(cardNumber)) {
            ToastUtils.ToastMessage(this, R.string.toast_input_card_number);
            mEtBankCode.requestFocus();
            return false;
        }
        if (mcurrentBank==null) {
            ToastUtils.ToastMessage(this, R.string.toast_chose_bank_type);
            onSelectBankClick();
            return false;
        }

        if (!ValidateUtil.isNumPasswordAndSix(password)) {
            ToastUtils.ToastMessage(this, R.string.input_six_number_hint);
            mEtPassword.requestFocus();
            return false;
        }

        return true;
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

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, WithdrawCashActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
