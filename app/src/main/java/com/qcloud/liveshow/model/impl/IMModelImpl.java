package com.qcloud.liveshow.model.impl;

import com.google.gson.Gson;
import com.qcloud.liveshow.beans.NettyAuthBean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyDeleteMessageBean;
import com.qcloud.liveshow.beans.NettyGetUserInfo;
import com.qcloud.liveshow.beans.NettyGroupBean;
import com.qcloud.liveshow.beans.NettyPayVipRoom;
import com.qcloud.liveshow.beans.NettyRequestBean;
import com.qcloud.liveshow.beans.NettySendPrivateBean;
import com.qcloud.liveshow.beans.NettyShupUpBean;
import com.qcloud.liveshow.enums.RequestDataEnum;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.netty.NettyClientBus;
import com.qcloud.qclib.utils.DateUtils;
import com.qcloud.qclib.utils.TokenUtil;

import java.util.UUID;

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
        requestBean.setAction_type(RequestDataEnum.ActionType.AUTH.getKey());
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
        requestBean.setAction_type(RequestDataEnum.ActionType.GET_PRIVATE_CHAT_LIST.getKey());
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
    public void sendPrivateChat(String userId, String content,String uuid) {
        NettyContentBean contentBean = new NettyContentBean(content);
        NettySendPrivateBean bean =new NettySendPrivateBean();
        bean.setToken(TokenUtil.getToken());
        bean.setTo_user_id(userId);
        bean.setContent(contentBean);

        NettyRequestBean<NettySendPrivateBean> requestBean=new NettyRequestBean<>();
        requestBean.setAction_type(RequestDataEnum.ActionType.PRIVATE_CHAT.getKey());
        requestBean.setUuid(uuid);
        requestBean.setData(bean);
        NettyClientBus.request(mGson.toJson(requestBean));

    }

    /**
     * 发送群聊消息
     *
     * @time 2017/11/2 10:26
     */
    @Override
    public void sendGroupChat(String roomNum, String content,String uuid) {
        NettyContentBean contentBean = new NettyContentBean(content);
        NettyGroupBean bean = new NettyGroupBean();
        bean.setRoom_number(roomNum);
        bean.setToken(TokenUtil.getToken());
        bean.setContent(contentBean);

        NettyRequestBean<NettyGroupBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(RequestDataEnum.ActionType.GROUP_CHAT.getKey());
        requestBean.setUuid(uuid);
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
        requestBean.setAction_type(RequestDataEnum.ActionType.GROUP_CHAT.getKey());
        requestBean.setUuid(""+ UUID.randomUUID());
        requestBean.setData(bean);
        NettyClientBus.request(mGson.toJson(requestBean));
    }

    /**
     * 发送群聊公告
     *
     * @time 2017/11/2 10:27
     */
    @Override
    public void sendGroupNotice(String roomNum, String content) {
        NettyContentBean contentBean = new NettyContentBean(content);
        NettyGroupBean bean = new NettyGroupBean();
        bean.setRoom_number(roomNum);
        bean.setToken(TokenUtil.getToken());
        bean.setContent(contentBean);

        NettyRequestBean<NettyGroupBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(RequestDataEnum.ActionType.ROOM_NOTICE.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);
        NettyClientBus.request(mGson.toJson(requestBean));
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
        requestBean.setAction_type(RequestDataEnum.ActionType.IN_ROOM.getKey());
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
        requestBean.setAction_type(RequestDataEnum.ActionType.OUT_ROOM.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }
    /**
     * 禁言
     *
     * @time 2017/11/2 10:28
     */
    @Override
    public void shutUp(String roomNum, String memberId, boolean isForbidden) {
        NettyShupUpBean bean=new NettyShupUpBean();
        bean.setRoom_number(roomNum);
        bean.setMember_id(memberId);
        bean.setIs_forbidden(isForbidden);

        NettyRequestBean<NettyShupUpBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(RequestDataEnum.ActionType.ROOM_FORBIDDEN_CHAT.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }

    /**
     * 删除私聊列表
     *
     * @time 2017/11/20 14:28
     */
    @Override
    public void deleteMessage(String to_user_id) {
        NettyDeleteMessageBean bean=new NettyDeleteMessageBean();
        bean.setToken(TokenUtil.getToken());
        bean.setTo_user_id(to_user_id);

        NettyRequestBean<NettyDeleteMessageBean> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(RequestDataEnum.ActionType.DELETE_MESSAGE_CHAT.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }
    /**
     * 收费直播计算（每一分钟调用一次）
     * @time 2017/11/20 14:28
     */
    @Override
    public void payVipRoom(String roomId) {
        NettyPayVipRoom bean=new NettyPayVipRoom();
        bean.setToken(TokenUtil.getToken());
        bean.setRoom_id(roomId);

        NettyRequestBean<NettyPayVipRoom> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(RequestDataEnum.ActionType.PAY_VIP_ROOM.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }
    /**
     * 获取用户信息
     */
    @Override
    public void getUser(String user_id) {
        NettyGetUserInfo bean=new NettyGetUserInfo();
        bean.setToken(TokenUtil.getToken());
        bean.setUser_id(user_id);

        NettyRequestBean<NettyGetUserInfo> requestBean = new NettyRequestBean<>();
        requestBean.setAction_type(RequestDataEnum.ActionType.GET_USER_INFO.getKey());
        requestBean.setUuid(DateUtils.getTimeStamp());
        requestBean.setData(bean);

        NettyClientBus.request(mGson.toJson(requestBean));
    }

}
