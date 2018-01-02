package com.qcloud.liveshow.ui.profit.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.DiamondsAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.enums.ClauseRuleEnum;
import com.qcloud.liveshow.ui.main.widget.WebActivity;
import com.qcloud.liveshow.ui.profit.presenter.impl.MyDiamondsPresenterImpl;
import com.qcloud.liveshow.ui.profit.view.IMyDiamondsView;
import com.qcloud.liveshow.utils.BasicsUtil;
import com.qcloud.liveshow.utils.PaypalUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.pop.CallPop;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.LineTextView;
import com.qcloud.qclib.widget.layoutManager.FullyGridLayoutManager;

import org.json.JSONException;

import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：我的钻石币
 * Author: Kuzan
 * Date: 2017/9/1 15:31.
 */
public class MyDiamondsActivity extends SwipeBaseActivity<IMyDiamondsView, MyDiamondsPresenterImpl> implements IMyDiamondsView {

    @Bind(R.id.img_banner)
    ImageView mImgBanner;
    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_curr_diamonds)
    TextView mTvCurrDiamonds;
    @Bind(R.id.layout_profit_account)
    LinearLayout mLayoutProfitAccount;
    @Bind(R.id.list_diamonds)
    RecyclerView mListDiamonds;
    @Bind(R.id.btn_recharge_agreement)
    LineTextView mBtnRechargeAgreement;
    @Bind(R.id.tv_customer_service)
    TextView mTvCustomerService;

    @BindDimen(R.dimen.margin_3)
    int dp15;

    private DiamondsAdapter mAdapter;

    private CallPop mCallPop;
    private String mTelephone;
    private PaypalUtil paypalUtil;
    @Override
    protected int initLayout() {
        return R.layout.activity_my_diamonds;
    }

    @Override
    protected MyDiamondsPresenterImpl initPresenter() {
        return new MyDiamondsPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        /**解决状态栏与内容重叠*/
        SystemBarUtil.remeasureTitleBar(this, mTitleBar);
        paypalUtil=PaypalUtil.getInstance(this);
        initTitleBar();
        initDiamondsList();

        mBtnRechargeAgreement.setText(getString(R.string.tag_recharge_agreement), LineTextView.BOTTOM);

        if (UserInfoUtil.mUser != null) {
            mTvCurrDiamonds.setText(UserInfoUtil.mUser.getVirtualCoinStr());
        }

        mTelephone = BasicsUtil.mContactWay;
        if (StringUtils.isNotEmptyString(mTelephone)) {
            mTvCustomerService.setText(mTelephone);
        }

        loadData();
    }

    /**
     * 加载数据
     * */
    private void loadData() {
        startLoadingDialog();
        mPresenter.getDiamondsList();
    }

    private void initTitleBar() {
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_right) {
                    DiamondsRecordActivity.openActivity(MyDiamondsActivity.this);
                } else {
                    finish();
                }
            }
        });
    }

    private void initDiamondsList() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3);
        mListDiamonds.setLayoutManager(manager);

        mAdapter = new DiamondsAdapter(this);
        mListDiamonds.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DiamondsBean bean = mAdapter.refreshSelect(i);
                if (bean != null) {
                    ToastUtils.ToastMessage(mContext, bean.getName()+"");
                }
            }
        });
    }

    /**
     * 显示播打电话弹窗
     * */
    private void initCallPop() {
        mCallPop = new CallPop(this);
    }

    @OnClick({R.id.btn_recharge_agreement, R.id.tv_customer_service,R.id.tv_diamonds})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onRechargeClick() {
        WebActivity.openActivity(this, "充值协议", ClauseRuleEnum.RechargeRule.getKey());
    }




    @Override
    public void onDiamondsClick() {
        paypalUtil.goToPlay(this,"0.01");
//        AlipayUtil alipayUtil=new AlipayUtil(this);
//        alipayUtil.pay("0.01");
//        alipayUtil.pay("0.02");

    }
    @Override
    public void onCustomerServiceClick() {
        if (StringUtils.isNotEmptyString(mTelephone)) {
            if (mCallPop == null) {
                initCallPop();
            }
            mCallPop.setTelephone(mTelephone);
            mCallPop.showAtLocation(mTvCustomerService, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void replaceDiamondsList(List<DiamondsBean> beans) {
        if (isRunning) {
            stopLoadingDialog();
            if (beans != null) {
                mAdapter.replaceList(beans);
            }
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            startLoadingDialog();
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm =
                    data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Timber.e(confirm.toJSONObject().toString(4));
                    Timber.e(confirm.getPayment().toJSONObject().toString(4));
        //这里可以把PayPal带回来的json数据传给服务器以确认你的款项是否收到或者收全
        //可以直接把 confirm.toJSONObject() 这个带给服务器，
        //得到服务器返回的结果，你就可以跳转成功页面或者做相应的处理了
                } catch (JSONException e) {
                    Timber.e("一个极其不可能的失败发生了:");
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Timber.e("用户取消了.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Timber.e( "提交了无效的支付或PayPal配置。请参见文档。");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        paypalUtil.unRegister(this);
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, MyDiamondsActivity.class));
    }
}
