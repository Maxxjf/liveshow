package com.qcloud.liveshow.beans;

/**
 * 类说明：用户信息
 * Author: Kuzan
 * Date: 2017/11/2 10:05.
 */
public class NettyMemberBean {
    String signature;       // 签名
    boolean isAnchor; // 是否主播
    String id;      // 接收者
    String nickName;       // 昵称
    int sex;                // 性别
    String headImg;        // 用户头像
    String memberGradeIcon;     // 会员等级图标
    String last_send_message_datetime;  // 最后一次发送内容时间
    String anchorGradeIcon;   // 主播等级图标
    String anchorGrade;   // 主播等级
    boolean isRead;   // 主播等级

}
