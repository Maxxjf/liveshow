package com.qcloud.liveshow.ui.account.presenter.impl;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.FacebookUserBean;
import com.qcloud.liveshow.beans.LoginBean;
import com.qcloud.liveshow.beans.WeChatUserBean;
import com.qcloud.liveshow.model.IUserModel;
import com.qcloud.liveshow.model.impl.UserModelImpl;
import com.qcloud.liveshow.ui.account.presenter.ILoginPresenter;
import com.qcloud.liveshow.ui.account.view.ILoginView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.utils.GsonUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.reflect.Type;
import java.util.Map;

import timber.log.Timber;

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
    public void login(String account, String passwork) {
        mModel.loginNormal(account, passwork, new DataCallback<LoginBean>() {
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

    /**
     * 第三方登录
     * */
    @Override
    public void otherLogin(String iconurl, String name, String openId, int sex, int type) {
        mModel.loginOther(iconurl, name, openId, sex, type, new DataCallback<LoginBean>() {
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

    @Override
    public void loadPlatformInfo(Activity activity, SHARE_MEDIA media) {
        Timber.e("media = " + media);
        UMShareAPI.get(activity).getPlatformInfo(activity, media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                if (mView == null) {
                    return;
                }
                Gson gson = new Gson();
                String jsonStr = GsonUtil.mapToJson(map);
                if (share_media == SHARE_MEDIA.WEIXIN) {
                    Type type = new TypeToken<WeChatUserBean>() {
                    }.getType();
                    WeChatUserBean bean = gson.fromJson(jsonStr, type);
                    if (bean != null) {
                        mView.weChatUserInfo(bean);
                    } else {
                        mView.loadErr(true, "获取微信用户信息失败");
                    }
                } else {
                    Type type = new TypeToken<FacebookUserBean>() {
                    }.getType();
                    FacebookUserBean bean = gson.fromJson(jsonStr, type);
                    if (bean != null) {
                        mView.facebookUserInfo(bean);
                    } else {
                        mView.loadErr(true, "获取Facebook用户信息失败");
                    }
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                if (mView != null) {
                    mView.loadErr(true, "获取用户信息失败");
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
}
