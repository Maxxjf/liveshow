package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.MyGiftsBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.net.IMineApi;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.network.OkGoRequest;

/**
 * 类说明：我的有关
 * Author: Kuzan
 * Date: 2017/9/12 15:42.
 */
public class MineModelImpl implements IMineModel {
    /**请求参数*/
    private HttpParams mParams;

    public MineModelImpl() {
        mParams = OkGoRequest.getAppParams();
        OkGoRequest.createService();
    }

    /**
     * 获取我的礼物列表
     *
     * @param pageNum
     * @param pageSize
     * @param callback
     *
     * @time 2017/9/12 15:19
     */
    @Override
    public void getGiftPage(int pageNum, int pageSize, DataCallback<MyGiftsBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IMineApi.getGiftPage(mParams), callback);
    }

    /**
     * 获取我的关注
     *
     * @param pageNum
     * @param pageSize
     * @param callback
     *
     * @time 2017/9/12 16:43
     */
    @Override
    public void getFollowPage(int pageNum, int pageSize, DataCallback<ReturnDataBean<MemberBean>> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IMineApi.getAttentionPage(mParams), callback);
    }

    /**
     * 获取我的粉丝
     *
     * @param pageNum
     * @param pageSize
     * @param callback
     *
     * @time 2017/9/12 16:44
     */
    @Override
    public void getFansPage(int pageNum, int pageSize, DataCallback<ReturnDataBean<MemberBean>> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IMineApi.getFansPage(mParams), callback);
    }

    /**
     * 获取我的黑名单
     *
     * @param pageNum
     * @param pageSize
     * @param callback
     *
     * @time 2017/9/12 16:44
     */
    @Override
    public void getBlacklistPage(int pageNum, int pageSize, DataCallback<ReturnDataBean<MemberBean>> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IMineApi.getBlacklistPage(mParams), callback);
    }

    /**
     * 关注/取消关注
     *
     * @param id            粉丝id
     * @param isAttention   是否已关注
     * @param callback
     *
     * @time 2017/9/12 16:45
     */
    @Override
    public void attention(long id, boolean isAttention, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("id", id);
        mParams.put("isAttention", isAttention);

        BaseApi.dispose(IMineApi.attention(mParams), callback);
    }

    /**
     * 移出黑名单
     *
     * @param id    粉丝id
     * @param callback
     *
     * @time 2017/9/12 16:46
     */
    @Override
    public void moveOut(long id, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("id", id);

        BaseApi.dispose(IMineApi.moveOut(mParams), callback);
    }
}
