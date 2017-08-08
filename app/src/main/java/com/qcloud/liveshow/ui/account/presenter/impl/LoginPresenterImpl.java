package com.qcloud.liveshow.ui.account.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.account.presenter.ILoginPresenter;
import com.qcloud.liveshow.ui.account.view.ILoginView;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：登录有关数据处理
 * Author: Kuzan
 * Date: 2017/8/8 15:44.
 */
public class LoginPresenterImpl extends BasePresenter<ILoginView> implements ILoginPresenter {
    public LoginPresenterImpl() {

    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_get_code:
                mView.onGetCodeClick();
                break;
            case R.id.btn_login:
                mView.onLoginClick();
                break;
            case R.id.btn_clause:
                mView.onClauseClick();
                break;
            case R.id.btn_we_chat:
                mView.onWeChatClick();
                break;
            case R.id.btn_facebook:
                mView.onFacebookClick();
                break;
        }
    }

    @Override
    public void getCode(String mobile) {

    }

    @Override
    public void login(String mobile, String code) {
        mView.loginSuccess(null);
    }
}
