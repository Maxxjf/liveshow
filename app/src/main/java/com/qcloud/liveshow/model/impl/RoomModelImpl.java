package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.HotRoomBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.model.IRoomModel;
import com.qcloud.liveshow.net.IRoomApi;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.network.OkGoRequest;

/**
 * 类说明：直播间有关
 * Author: Kuzan
 * Date: 2017/9/7 15:27.
 */
public class RoomModelImpl implements IRoomModel {

    /**请求参数*/
    private HttpParams mParams;

    public RoomModelImpl() {
        mParams = OkGoRequest.getAppParams();
        OkGoRequest.createService();
    }

    /**
     * 获取热门直播间
     *
     * @time 2017/9/7 15:28
     */
    @Override
    public void getHotRoom(int pageNum, int pageSize, DataCallback<HotRoomBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IRoomApi.getHotList(mParams), callback);
    }

    /**
     * 获取最新直播间列表
     *
     * @time 2017/9/19 15:56
     */
    @Override
    public void getNewestRoom(int pageNum, int pageSize, DataCallback<ReturnDataBean<RoomBean>> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IRoomApi.getNewestList(mParams), callback);
    }

    /**
     * 获取关注直播列表
     *
     * @time 2017/9/19 15:56
     */
    @Override
    public void getFollowRoom(int pageNum, int pageSize, DataCallback<ReturnDataBean<RoomBean>> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IRoomApi.getFollowList(mParams), callback);
    }
}
