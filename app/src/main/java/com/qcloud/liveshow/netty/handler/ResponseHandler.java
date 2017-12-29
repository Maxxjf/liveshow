package com.qcloud.liveshow.netty.handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyAuthBean;
import com.qcloud.liveshow.beans.NettyBaseResponse;
import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.netty.callback.ResponseListener;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.utils.NettyUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.NettyDataCallback;
import com.qcloud.qclib.rxbus.BusProvider;

import java.lang.reflect.Type;

import io.netty.channel.ChannelHandlerContext;
import io.reactivex.annotations.NonNull;
import timber.log.Timber;

/**
 * 类说明：数据响应处理
 * Author: Kuzan
 * Date: 2017/11/1 13:42.
 */
public class ResponseHandler implements ResponseListener, IResponseMethod {
    /**
     * 从服务器返回的消息
     */
    @Override
    public boolean channelRead(ChannelHandlerContext ctx, JsonElement msgConfig) throws Exception {
        Timber.e("Read Message: " + msgConfig);
        if (msgConfig != null && msgConfig.isJsonObject()) {
            dispose(msgConfig);
            return true;
        }
        return false;
    }


    /**
     * 数据处理，初步解析
     */
    private void dispose(@NonNull JsonElement jsonStr) {
        Type type = new TypeToken<NettyBaseResponse>() {
        }.getType();
        NettyBaseResponse data = new Gson().fromJson(jsonStr, type);
        if (data != null) {
            Timber.i("action_type = %d", data.getAction_type());
            switch (data.getAction_type()) {
                case 0: // 鉴权
                    disposeAuth(jsonStr);
                    break;
                case 1: // 群聊
                    disposeGroup(jsonStr);
                    break;
                case 2: // 私聊
                    disposePrivate(jsonStr);
                    break;
                case 3: // 送礼物
                    disposeGift(jsonStr);
                    break;
                case 12://直播公告
                    disposeNotice(jsonStr);
                    break;
                case 104:   // 获取私聊列表
                    disposeChatList(jsonStr);
                    break;
                case 203: // 有用户从直播室群聊退出
                    disposeUserOutGroup(jsonStr);
                    break;
                case 204:   // 有用户加入直播室群聊
                    disposeGroupMember(jsonStr);
                    break;
            }
        }
    }
    /**
     * 鉴权
     *
     * @time 2017/11/4 10:19
     */
    @Override
    public void disposeAuth(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyAuthBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<NettyAuthBean>() {
            @Override
            public void onSuccess(NettyAuthBean nettyAuthBean, String uuid) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_auth_success).build());
                NettyUtil.saveIsAuth(true);
            }

            @Override
            public void onError(int status, String errMsg) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_auth_failure)
                        .setObj(errMsg).build());
                NettyUtil.clearIsAuth();
            }
        });
    }

    /**
     * 群聊
     *
     * @time 2017/11/6 14:02
     */
    @Override
    public void disposeGroup(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyReceiveGroupBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<NettyReceiveGroupBean>() {
            @Override
            public void onSuccess(NettyReceiveGroupBean bean, String uuid) {
                if (bean==null){
                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_group_message_send_success).setObj(uuid).build());
                    return;
                }
                if (UserInfoUtil.mUser!=null&&bean.getUser()!=null&&bean.getUser().getIdAccount().equals(UserInfoUtil.mUser.getIdAccount())){//判断是不是本人
//                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_group_message_send_success).setObj(uuid).build());
                }else {
                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_group_chat).setObj(bean).build());
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }

    /**
     * 私聊
     *
     * @time 2017/11/8 14:45
     */
    @Override
    public void disposePrivate(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyReceivePrivateBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<NettyReceivePrivateBean>() {
            @Override
            public void onSuccess(NettyReceivePrivateBean bean, String uuid) {

                if (bean.getChat_id()!=null){//收到别人的消息

                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_private_chat).setObj(bean).build());
                }else {//自己消息发送成功
                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_message_send_success).setObj(uuid).build());
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }
    /**
     * 私聊
     *
     * @time 2017/11/8 14:45
     */
    @Override
    public void disposeGift(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyGiftBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<NettyGiftBean>() {
            @Override
            public void onSuccess(NettyGiftBean bean, String uuid) {

                if (bean!=null){//收到别人的消息
                    BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_gift_show).setObj(bean).build());
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }

    /**
     * 处理会话列表
     *
     * @time 2017/11/6 13:49
     */
    @Override
    public void disposeChatList(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<MemberBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<MemberBean>() {
            @Override
            public void onSuccess(MemberBean bean, String uuid) {
                RealmHelper.getInstance().addOrUpdateBean(bean);//添加到本地数据
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_get_chat_list_success)
                        .setObj(bean).build());
            }

            @Override
            public void onError(int status, String errMsg) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_get_chat_list_failure)
                        .setObj(errMsg).build());
            }
        });
    }

    /**
     * 有人退群
     *
     * @time 2017/11/8 16:32
     */
    @Override
    public void disposeUserOutGroup(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyNoticeBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<NettyNoticeBean>() {
            @Override
            public void onSuccess(NettyNoticeBean bean, String uuid) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_notice_out_group).setObj(bean).build());
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }

    /**
     * 公告
     *
     * @time 2017/11/16 9:44
     */
    @Override
    public void disposeNotice(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyLiveNoticeBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<NettyLiveNoticeBean>() {
            @Override
            public void onSuccess(NettyLiveNoticeBean bean, String uuid) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_notice).setObj(bean).build());
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }

    /**
     * 看直播成员
     *
     * @time 2017/11/7 19:53
     */
    @Override
    public void disposeGroupMember(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyRoomMemberBean>>() {
        }.getType();
        NettyDispose.dispose(msgConfig, type, new NettyDataCallback<NettyRoomMemberBean>() {
            @Override
            public void onSuccess(NettyRoomMemberBean bean, String uuid) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_room_member_join).setObj(bean).build());
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }


}
