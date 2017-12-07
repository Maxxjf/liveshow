package com.qcloud.liveshow.ui.account.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.account.presenter.IForgetPassWordPresenter;
import com.qcloud.liveshow.ui.account.view.IForgetPassWordView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：${必须填}
 * Author: iceberg
 * Date: 2017/12/5.
 */
public class ForgetPassWordPresenterImpl extends BasePresenter<IForgetPassWordView> implements IForgetPassWordPresenter {
    IMineModel iimModel;
    public ForgetPassWordPresenterImpl(){
        iimModel=new MineModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId){
            case R.id.btn_get_code:
                mView.onClickGetCode();
                break;
            case R.id.btn_confirm:
                mView.onClickConfirm();
                break;

        }
    }

    @Override
    public void forgetPassword(String loginAccount,String email, String code, String newPassword) {
        iimModel.forgetPassword(loginAccount,email, code, newPassword, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                mView.updatePasswordSuccess();
            }

            @Override
            public void onError(int status, String errMsg) {
                mView.getCodeFail(errMsg);
            }
        });
    }

    @Override
    public void forgetPasswordCode(String loginAccount,String email) {
        iimModel.forgetPasswordCode(loginAccount,email, new DataCallback<GetCodeResBean>() {
            @Override
            public void onSuccess(GetCodeResBean bean) {
                mView.getCodeSuccess(bean.getCode());
            }

            @Override
            public void onError(int status, String errMsg) {
                mView.loadErr(true,errMsg);
            }
        });
    }
}
