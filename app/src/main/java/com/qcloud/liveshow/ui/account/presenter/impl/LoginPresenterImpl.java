package com.qcloud.liveshow.ui.account.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.model.IUserModel;
import com.qcloud.liveshow.model.impl.UserModelImpl;
import com.qcloud.liveshow.ui.account.presenter.ILoginPresenter;
import com.qcloud.liveshow.ui.account.view.ILoginView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：登录有关数据处理
 * Author: Kuzan
 * Date: 2017/8/8 15:44.
 */
public class LoginPresenterImpl extends BasePresenter<ILoginView> implements ILoginPresenter {
    private IUserModel mModel;

    public LoginPresenterImpl() {
        mModel = new UserModelImpl();
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
        mModel.getCode(mobile, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                if (mView != null) {
                    mView.getCodeSuccess();
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.getCodeFailure(errMsg);
                }
            }
        });
    }

    @Override
    public void login(String mobile, String code) {
        mModel.loginNormal(mobile, code, new DataCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean bean) {
                if (mView != null) {
                    mView.loginSuccess(bean);
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }
}
