package com.qcloud.liveshow.ui.account.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.ui.account.presenter.impl.LoginPresenterImpl;
import com.qcloud.liveshow.ui.account.view.ILoginView;
import com.qcloud.liveshow.ui.main.widget.MainActivity;
import com.qcloud.liveshow.ui.main.widget.WebActivity;
import com.qcloud.qclib.widget.customview.ClearEditText;
import com.qcloud.qclib.widget.customview.LineTextView;

import butterknife.Bind;
import butterknife.OnClick;
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
        mBtnClause.setText(getString(R.string.tag_clause), LineTextView.BOTTOM);
    }

    @OnClick({R.id.btn_get_code, R.id.btn_login, R.id.btn_clause, R.id.btn_we_chat, R.id.btn_facebook})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onLoginClick() {
        MainActivity.openActivity(this, StartMainEnum.START_HOME.getKey());
    }

    @Override
    public void onGetCodeClick() {
        Timber.e("onGetCodeClick");
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

    }

    @Override
    public void getCodeSuccess() {

    }

    @Override
    public void getCodeFailure(String errMsg) {

    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {

    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
