package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.HotRoomBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：直播间有关
 * Author: Kuzan
 * Date: 2017/9/7 15:26.
 */
public interface IRoomModel {
    /**获取热门直播间*/
    void getHotRoom(int pageNum, int pageSize, DataCallback<HotRoomBean> callback);
}
