package com.qcloud.liveshow.netty.handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.NettyAuthBean;
import com.qcloud.liveshow.beans.NettyBaseResponse;
import com.qcloud.liveshow.beans.NettyChatListBean;
import com.qcloud.liveshow.netty.callback.ResponseListener;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;
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
public class ResponseHandler implements ResponseListener {

    @Override
    public boolean channelRead(ChannelHandlerContext ctx, JsonElement msgConfig) throws Exception {
        Timber.i("Read Message: " + msgConfig);
        if (msgConfig != null && msgConfig.isJsonObject()) {
            dispose(msgConfig);
            return true;
        }
        return false;
    }

    /**
     * 数据处理，初步解析
     * */
    private void dispose(@NonNull JsonElement jsonStr) {
        Type type = new TypeToken<NettyBaseResponse>(){}.getType();
        NettyBaseResponse data = new Gson().fromJson(jsonStr, type);
        if (data != null) {
            Timber.i("action_type = %d", data.getAction_type());
            switch (data.getAction_type()) {
                case 0:
                    disposeAuth(jsonStr);
                    break;
                case 104:
                    disposeChatList(jsonStr);
                    break;
            }
        }
    }

    /**
     * 鉴权
     *
     * @time 2017/11/4 10:19
     */
    private void disposeAuth(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyAuthBean>>(){}.getType();
        NettyDispose.dispose(msgConfig, type, new DataCallback<NettyAuthBean>() {
            @Override
            public void onSuccess(NettyAuthBean nettyAuthBean) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_auth_success).build());
            }

            @Override
            public void onError(int status, String errMsg) {
                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_auth_failure)
                        .setObj(errMsg).build());
            }
        });
    }

    /**
     * 处理会话列表
     * */
    private void disposeChatList(JsonElement msgConfig) {
        Type type = new TypeToken<NettyBaseResponse<NettyChatListBean>>(){}.getType();
        NettyDispose.dispose(msgConfig, type, new DataCallback<NettyChatListBean>() {
            @Override
            public void onSuccess(NettyChatListBean bean) {
                Timber.e(bean+"");
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
}
