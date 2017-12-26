package com.qcloud.liveshow.utils;

import android.content.Context;

import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.model.IIMModel;

/**
 * 类说明：聊天管理工具
 * Author: iceberg
 * Date: 2017/12/22.
 */
public class CharManager {
    private static CharManager me;
    IIMModel mModel;
    private Context mContext;
    private CharManager(){}
    private synchronized CharManager getInstance(){
        if (me!=null){
            me=new CharManager();
        }
        return me;
    }
    private void sendMessage(NettyReceiveGroupBean message,CharCallBack callBack){
        mModel.sendGroupChat(message.getRoom_number(),message.getContent().getText());

    }






    interface CharCallBack{
        void onSuccess();
        void onError();
        void onProgress();
    }

}
