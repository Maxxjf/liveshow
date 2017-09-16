package com.qcloud.liveshow.ui.profit.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.enums.ClauseRuleEnum;
import com.qcloud.liveshow.ui.main.widget.WebActivity;
import com.qcloud.liveshow.ui.profit.presenter.impl.MyProfitPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IMyProfitView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.LineTextView;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：我的收益
 * Author: Kuzan
 * Date: 2017/8/18 14:25.
 */
public class MyProfitActivity extends SwipeBaseActivity<IMyProfitView, MyProfitPresenterImpl> implements IMyProfitView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_curr_profit)
    TextView mTvCurrProfit;
    @Bind(R.id.btn_rule)
    LineTextView mBtnRule;
    @Bind(R.id.layout_profit_account)
    LinearLayout mLayoutProfitAccount;
    @Bind(R.id.tv_profit_account)
    TextView mTvProfitAccount;
    @Bind(R.id.tv_profit_gift)
    TextView mTvProfitGift;
    @Bind(R.id.tv_profit_extension)
    TextView mTvProfitExtension;
    @Bind(R.id.tv_profit_percent)
    TextView mTvProfitPercent;
    @Bind(R.id.btn_confirm_cash)
    TextView mBtnConfirmCash;
    @Bind(R.id.btn_buy_diamonds)
    TextView mBtnBuyDiamonds;
    @Bind(R.id.btn_cash_agreement)
    LineTextView mBtnCashAgreement;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_profit;
    }

    @Override
    protected MyProfitPresenterImpl initPresenter() {
        return new MyProfitPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        /**解决状态栏与内容重叠*/
        SystemBarUtil.remeasureTitleBar(this, mTitleBar);

        initTitleBar();

        mBtnRule.setText(getString(R.string.tag_cash_rules), LineTextView.BOTTOM);
        mBtnCashAgreement.setText(getString(R.string.tag_cash_agreement), LineTextView.BOTTOM);
    }

    private void initTitleBar() {
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_right) {
                    ProfitRecordActivity.openActivity(MyProfitActivity.this);
                } else {
                    finish();
                }
            }
        });
    }

    @OnClick({R.id.btn_rule, R.id.btn_confirm_cash, R.id.btn_buy_diamonds, R.id.btn_cash_agreement})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onCashRuleClick() {
        WebActivity.openActivity(this, "提现规则", ClauseRuleEnum.WithdrawalsRule.getKey());
    }

    @Override
    public void onConfirmCashClick() {
        WithdrawCashActivity.openActivity(this);
    }

    @Override
    public void onBuyDiamondsClick() {
        ToastUtils.ToastMessage(this, "买钻石币");
    }

    @Override
    public void onCashAgreementClick() {
        WebActivity.openActivity(this, "用户提现协议", ClauseRuleEnum.WithdrawalsClause.getKey());
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
        context.startActivity(new Intent(context, MyProfitActivity.class));
    }
}
