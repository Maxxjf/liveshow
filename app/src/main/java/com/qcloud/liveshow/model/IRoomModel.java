package com.qcloud.liveshow.model;

import com.qcloud.liveshow.beans.HotRoomBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：直播间有关
 * Author: Kuzan
 * Date: 2017/9/7 15:26.
 */
public interface IRoomModel {
    /**获取热门直播间*/
    void getHotRoom(int pageNum, int pageSize, DataCallback<HotRoomBean> callback);

    /**获取最新直播间*/
    void getNewestRoom(int pageNum, int pageSize, DataCallback<ReturnDataBean<RoomBean>> callback);

    /**获取关注直播间*/
    void getFollowRoom(int pageNum, int pageSize, DataCallback<ReturnDataBean<RoomBean>> callback);

    /**搜索直播间*/
    void getSearchRoom(String keyword, int pageNum, int pageSize, DataCallback<ReturnDataBean<RoomBean>> callback);
}
