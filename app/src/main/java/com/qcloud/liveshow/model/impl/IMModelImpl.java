package com.qcloud.liveshow.model.impl;

import com.google.gson.Gson;
import com.qcloud.liveshow.beans.NettyAuthBean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyGroupBean;
import com.qcloud.liveshow.beans.NettyRequestBean;
import com.qcloud.liveshow.beans.NettySendPrivateBean;
import com.qcloud.liveshow.enums.NettyActionType;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.netty.NettyClientBus;
import com.qcloud.qclib.utils.DateUtils;
import com.qcloud.qclib.utils.TokenUtil;

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
     * @time 2017/11/2 10:24
     */
    @Override
    public void auth() {
        NettyAuthBean bean = new NettyAuthBean();
        bean.setToken(TokenUtil.getToken());

        NettyRequestBean<NettyAuthBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.AUTH.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }

    /**
     * 获取会话列表
     *
     * @time 2017/11/2 10:25
     */
    @Override
    public void getChatList() {
        NettyAuthBean bean = new NettyAuthBean();
        bean.setToken(TokenUtil.getToken());

        NettyRequestBean<NettyAuthBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.GET_PRIVATE_CHAT_LIST.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }

    /**
     * 发送私聊消息
     *
     * @param userId 接收者用户
     * @param content 发送消息
     *
     * @time 2017/11/2 10:25
     */
    @Override
    public void sendPrivateChat(String userId, String content) {
        NettyContentBean contentBean = new NettyContentBean(content);
        NettySendPrivateBean bean =new NettySendPrivateBean();
        bean.setToken(TokenUtil.getToken());
        bean.setTo_user_id(userId);
        bean.setContent(contentBean);

        NettyRequestBean<NettySendPrivateBean> requestBean=new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.PRIVATE_CHAT.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);
        NettyClientBus.request(mGson.toJson(requestBean));

    }

    /**
     * 发送群聊消息
     *
     * @time 2017/11/2 10:26
     */
    @Override
    public void sendGroupChat(String roomNum, String content) {
        NettyContentBean contentBean = new NettyContentBean(content);
        NettyGroupBean bean = new NettyGroupBean();
        bean.setRoom_number(roomNum);
        bean.setToken(TokenUtil.getToken());
        bean.setContent(contentBean);

        NettyRequestBean<NettyGroupBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.GROUP_CHAT.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);
        NettyClientBus.request(mGson.toJson(requestBean));
    }

    /**
     * 发送群聊公告
     *
     * @time 2017/11/2 10:27
     */
    @Override
    public void sendGroupNotice() {

    }

    /**
     * 加入群聊
     *
     * @time 2017/11/2 10:28
     */
    @Override
    public void joinGroup(String roomNum) {
        NettyGroupBean bean = new NettyGroupBean();
        bean.setToken(TokenUtil.getToken());
        bean.setRoom_number(roomNum);

        NettyRequestBean<NettyGroupBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.IN_ROOM.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }

    /**
     * 退出群聊
     *
     * @time 2017/11/2 10:28
     */
    @Override
    public void outGroup(String roomNum) {
        NettyGroupBean bean = new NettyGroupBean();
        bean.setToken(TokenUtil.getToken());
        bean.setRoom_number(roomNum);

        NettyRequestBean<NettyGroupBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(NettyActionType.OUT_ROOM.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }
}
