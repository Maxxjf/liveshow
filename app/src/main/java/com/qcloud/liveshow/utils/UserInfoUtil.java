package com.qcloud.liveshow.utils;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseApplication;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.model.impl.UserModelImpl;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.rxbus.BusProvider;

import timber.log.Timber;

/**
 * 类说明：用户信息
 * Author: Kuzan
 * Date: 2017/9/6 11:01.
 */
public class UserInfoUtil {
    public static UserBean mUser;

    public static void loadUserInfo() {
        new UserModelImpl().loadUserInfo(new DataCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean) {
                if (userBean != null) {
                    mUser = userBean;
                    BaseApplication.getInstance().userBean=userBean;
                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.get_user_info_success).setObj(userBean).build());
                } else {
                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.get_user_info_error).setObj("获取有户信息失败").build());
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e("获取用户信息失败，status = %d, errMsg = %s", status, errMsg);
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.get_user_info_error).setObj(errMsg).build());
            }
        });
    }
}
