package com.qcloud.liveshow.ui.account.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.beans.FacebookUserBean;
import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.beans.WeChatUserBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.enums.ClauseRuleEnum;
import com.qcloud.liveshow.enums.StartMainEnum;
import com.qcloud.liveshow.enums.ThirdLoginEnum;
import com.qcloud.liveshow.ui.account.presenter.impl.LoginPresenterImpl;
import com.qcloud.liveshow.ui.account.view.ILoginView;
import com.qcloud.liveshow.ui.main.widget.MainActivity;
import com.qcloud.liveshow.ui.main.widget.WebActivity;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.ConstantUtil;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.TokenUtil;
import com.qcloud.qclib.utils.ValidateUtil;
import com.qcloud.qclib.widget.customview.ClearEditText;
import com.qcloud.qclib.widget.customview.LineTextView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * 类说明：登录页面
 * Author: Kuzan
 * Date: 2017/8/8 15:46.
 */
public class LoginActivity extends BaseActivity<ILoginView, LoginPresenterImpl> implements ILoginView {
    @Bind(R.id.et_account)
    ClearEditText mEtAccount;
    @Bind(R.id.et_passwork)
    ClearEditText mEtPassword;
    @Bind(R.id.btn_login)
    TextView mBtnLogin;
    @Bind(R.id.btn_clause)
    LineTextView mBtnClause;
    @Bind(R.id.btn_we_chat)
    ImageView mBtnWeChat;
    @Bind(R.id.btn_facebook)
    ImageView mBtnFacebook;


    private Disposable mDisposable;

    private String account;
    private String passwork;

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
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    private void initView() {
        mBtnClause.setText(getString(R.string.tag_clause), LineTextView.BOTTOM);
        mEtAccount.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEtPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    private void toMain() {
        MainActivity.openActivity(this, StartMainEnum.StartHome.getKey());
        finish();
    }

    @OnClick({ R.id.btn_login, R.id.btn_clause, R.id.btn_we_chat, R.id.btn_facebook,R.id.tv_forget_password})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onLoginClick() {
        if (check()) {
            mPresenter.login(account, passwork);
        }
    }

    @Override
    public void onWeChatClick() {
        UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.WEIXIN, authListener);
    }

    @Override
    public void onFacebookClick() {
        UMShareAPI.get(this).doOauthVerify(this, SHARE_MEDIA.FACEBOOK, authListener);
    }

    @Override
    public void onClauseClick() {
        WebActivity.openActivity(this, "免责条款", ClauseRuleEnum.LoginRule.getKey());
    }

    @Override
    public void onForgetPasswordClick() {
        ForgetPassWordActivity.openActivity(this);
    }

    @Override
    public void loginSuccess(LoginBean bean) {
        if (isRunning) {
            if (bean != null) {
                TokenUtil.saveToken(bean.getToken());
                ToastUtils.ToastMessage(this, R.string.toast_login_success);
                UserInfoUtil.loadUserInfo();
                ConstantUtil.writeBoolean(AppConstants.IS_FIRST_LOGIN, bean.isFirst());
                toMain();
            } else {
                ToastUtils.ToastMessage(this, R.string.toast_login_failure);
            }
        }
    }

    @Override
    public void weChatUserInfo(WeChatUserBean bean) {
        stopLoadingDialog();
        if (isRunning && bean != null) {
            mPresenter.otherLogin(bean.getIconurl(), bean.getName(), bean.getOpenid(), bean.getSex(), ThirdLoginEnum.WEXIN.getKey());
        }
    }

    @Override
    public void facebookUserInfo(FacebookUserBean bean) {
        stopLoadingDialog();
        if (isRunning && bean != null) {
            mPresenter.otherLogin(bean.getIconurl(), bean.getName(), bean.getId()+"", 0, ThirdLoginEnum.FACEBOOK.getKey());
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
        passwork = mEtPassword.getText().toString().trim();

        if (!checkAccount()) {
            return false;
        }

        if (StringUtils.isEmptyString(passwork)) {
            ToastUtils.ToastMessage(this, R.string.input_password_hint);
            mEtPassword.requestFocus();
            return false;
        }
        if (!ValidateUtil.isAccount(passwork)) {
            ToastUtils.ToastMessage(this, R.string.toast_format_passage);
            mEtPassword.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * 检查账号
     * @return
     */
    public boolean checkAccount() {
        account = mEtAccount.getText().toString().trim();

        if (StringUtils.isEmptyString(account)) {
            ToastUtils.ToastMessage(this, R.string.input_account_hint);
            mEtAccount.requestFocus();
            return false;
        }

        if (!ValidateUtil.isAccount(account)) {
            ToastUtils.ToastMessage(this, R.string.toast_format_account);
            mEtAccount.requestFocus();
            return false;
        }

        return true;
    }


    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            startLoadingDialog();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Timber.e(data.toString());
            mPresenter.loadPlatformInfo(LoginActivity.this, platform);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            stopLoadingDialog();
            ToastUtils.ToastMessage(LoginActivity.this, "失败：" + t.getMessage());
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            stopLoadingDialog();
            ToastUtils.ToastMessage(LoginActivity.this, "取消了");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
