package com.qcloud.liveshow.utils;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.realm.RealmHelper;

import java.util.List;

/**
 * 类说明：消息列表工具类
 * Author: iceberg
 * Date: 2017/11/21.
 */
public class MessageUtil {
    /**单列实例*/
    private static MessageUtil instance;
    /**未读数量*/
    private int noRead;
    /**私聊列表*/
    private List<MemberBean> charList;

    public static synchronized  MessageUtil getInstance(){
        if (instance==null){
            instance=new MessageUtil();

        }
        return instance;
    }

    /**
     * 得到私聊列表
     * @return
     */
    public List<MemberBean> getCharList(){
        charList=RealmHelper.getInstance().queryBeans(MemberBean.class);
        return charList;
    }

    /**
     * 得到未读的列表数量
     * @return
     */
    public  int getNoReadNumber(){
        charList=RealmHelper.getInstance().queryListByValue(MemberBean.class,"isRead",false);
        noRead=charList.size();
        return noRead;
    }

}
