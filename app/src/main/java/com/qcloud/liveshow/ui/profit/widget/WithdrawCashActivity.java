package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.BankBean;
import com.qcloud.liveshow.ui.profit.presenter.impl.WithdrawCashPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IWithdrawCashView;
import com.qcloud.liveshow.widget.pop.BankPicker;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
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

    private BankPicker mPicker;

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
            public void onBankPicked(int index, BankBean bean) {
                if (mTvBank != null && bean != null) {
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
        List<BankBean> list = new ArrayList<>();
        BankBean bean = new BankBean();
        bean.setId(0);
        bean.setName("建设银行");
        list.add(bean);
        bean = new BankBean();
        bean.setId(1);
        bean.setName("广发银行");
        list.add(bean);
        bean = new BankBean();
        bean.setId(2);
        bean.setName("工商银行");
        list.add(bean);
        bean = new BankBean();
        bean.setId(3);
        bean.setName("中国银行");
        list.add(bean);
        bean = new BankBean();
        bean.setId(4);
        bean.setName("招商银行");
        list.add(bean);
        bean = new BankBean();
        bean.setId(5);
        bean.setName("农业银行");
        list.add(bean);

        if (mPicker == null) {
            initBankPicker();
        }
        mPicker.refreshData(list);
        mPicker.showAtLocation(mTvBank, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onConfirmClick() {

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
}
