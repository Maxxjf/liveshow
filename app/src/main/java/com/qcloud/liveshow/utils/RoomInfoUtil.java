package com.qcloud.liveshow.utils;

import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.model.impl.RoomModelImpl;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

import java.util.List;

import timber.log.Timber;

/**
 * 类说明：用户信息
 * Author: Kuzan
 * Date: 2017/9/6 11:01.
 */
public class RoomInfoUtil {
    public static List<RoomBean> mRoomBean;

    /**
     * 获取用户信息
     * */
    public static void loadRoomInfo(String memberId) {
        new RoomModelImpl().getRoomInfo(memberId,new DataCallback<ReturnDataBean<RoomBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<RoomBean> bean) {
                if (bean != null) {
                    mRoomBean = bean.getList();
                } else {

                }
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e("获取房间失败，status = %d, errMsg = %s", status, errMsg);
//                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.get_user_info_error).setObj(errMsg).build());
            }
        });
    }

}
