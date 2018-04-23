package com.qcloud.liveshow.utils;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.realm.RealmHelper;

import java.util.List;

import timber.log.Timber;

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

    /**
     * 是否含已经在列表里
     */
    public boolean isInList(String userId){
        List<MemberBean> list=RealmHelper.getInstance().queryListByValue(MemberBean.class,"id",Long.parseLong(userId));
        if (list.size()==0){
            return false;
        }else if (list.size()==1){
            return true;
        }else{
            Timber.e("系列出现重复列表---不太可能的事发生了");
        }
        return false;
    }

    /**
     * 忽略全部未读
     */
    public void ignoreMessage(){
        charList=RealmHelper.getInstance().queryListByValue(MemberBean.class,"isRead",false);
        for (MemberBean member:charList){
            RealmHelper.getInstance().updateMemberIsRead(member.getId(),true);
        }
    }
    /**
     * 标记已读
     */
    public void charIsRead(long id,boolean isRead){
        RealmHelper.getInstance().updateMemberIsRead(id,isRead);
    }

//    /**
//     * 得到聊天
//     */
//    public MemberBean getMemberChar(String userId){
//        List<MemberBean> list=RealmHelper.getInstance().queryListByValue(MemberBean.class,"id",Long.parseLong(userId));
//        if (list.size()>0){
//            return list.get(0);
//        }
//        return new MemberBean();
//    }

}
