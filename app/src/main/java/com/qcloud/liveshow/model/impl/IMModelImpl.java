package com.qcloud.liveshow.model.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qcloud.liveshow.beans.NettyAuthBean;
import com.qcloud.liveshow.beans.NettyBaseResponse;
import com.qcloud.liveshow.beans.NettyChatListBean;
import com.qcloud.liveshow.beans.NettyGroupBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveSingleBean;
import com.qcloud.liveshow.beans.NettyRequestBean;
import com.qcloud.liveshow.beans.NettyRoomNum;
import com.qcloud.liveshow.enums.NettyActionType;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.netty.NettyClientBus;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.utils.DateUtils;
import com.qcloud.qclib.utils.TokenUtil;

import java.lang.reflect.Type;

/**
 * 类说明：IM通讯有关
 * Author: Kuzan
 * Date: 2017/11/2 10:24.
 */
public class IMModelImpl implements IIMModel {

    private Gson mGson;

    public IMModelImpl() {
        mGson = new Gson();
    }

    /**
     * 鉴权
     *
     * @param callback 返回鉴权信息
     *
     * @time 2017/11/2 10:24
     */
    @Override
    public void auth(DataCallback<NettyAuthBean> callback) {
        NettyAuthBean bean = new NettyAuthBean();
        bean.setToken(TokenUtil.getToken());

        NettyRequestBean<NettyAuthBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.AUTH.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        Type type = new TypeToken<NettyBaseResponse<NettyAuthBean>>(){}.getType();
        NettyClientBus.request(mGson.toJson(requestBean), callback, type);
    }

    /**
     * 获取会话列表
     *
     * @param callback 返回会话列表
     *
     * @time 2017/11/2 10:25
     */
    @Override
    public void getChatList(DataCallback<NettyChatListBean> callback) {
        NettyAuthBean bean = new NettyAuthBean();
        bean.setToken(TokenUtil.getToken());

        NettyRequestBean<NettyAuthBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.GET_PRIVATE_CHAT_LIST.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        Type type = new TypeToken<NettyBaseResponse<NettyChatListBean>>(){}.getType();
        NettyClientBus.request(mGson.toJson(requestBean), callback, type);
    }

    /**
     * 发送私聊消息
     *
     * @param userId 接收者用户
     * @param content 发送消息
     * @param callback 返回对方私聊内容
     *
     * @time 2017/11/2 10:25
     */
    @Override
    public void sendSingleChat(String userId, String content, DataCallback<NettyReceiveSingleBean> callback) {

    }

    /**
     * 发送群聊消息
     *
     * @param callback 返回群聊内容
     *
     * @time 2017/11/2 10:26
     */
    @Override
    public void sendGroupChat(DataCallback<NettyGroupBean> callback) {

    }

    /**
     * 发送群聊公告
     *
     * @param callback 返回群聊公告
     *
     * @time 2017/11/2 10:27
     */
    @Override
    public void sendGroupNotice(DataCallback<NettyNoticeBean> callback) {

    }

    /**
     * 加入群聊
     *
     * @param callback 返回群聊消息
     *
     * @time 2017/11/2 10:28
     */
    @Override
    public void joinGroup(String roomNum,  DataCallback<NettyGroupBean> callback) {
        NettyGroupBean bean = new NettyGroupBean();
        bean.setToken(TokenUtil.getToken());
        bean.setRoom_number(roomNum);

        NettyRequestBean<NettyGroupBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.IN_ROOM.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        Type type = new TypeToken<NettyRequestBean<NettyRoomNum>>(){}.getType();
        NettyClientBus.request(mGson.toJson(requestBean), callback, type);
    }

    /**
     * 退出群聊
     *
     * @param callback 退出群聊公告
     *
     * @time 2017/11/2 10:28
     */
    @Override
    public void outGroup(String roomNum, String userId, DataCallback<NettyNoticeBean> callback) {

    }
}
