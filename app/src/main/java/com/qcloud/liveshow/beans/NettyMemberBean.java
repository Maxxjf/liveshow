package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：用户信息
 * Author: Kuzan
 * Date: 2017/11/2 10:05.
 */
public class NettyMemberBean {
    String signature;       // 签名
    boolean user_is_anchor; // 是否主播
    String to_user_id;      // 接收者
    String nick_name;       // 昵称
    int sex;                // 性别
    String head_img;        // 用户头像
    String member_icon;     // 会员等级图标
    String last_send_message_datetime;  // 最后一次发送内容时间
    String anchor_grade_icon;   // 主播等级图标

    public String getSignature() {
        return StringUtils.isEmptyString(signature)?"":signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isUser_is_anchor() {
        return user_is_anchor;
    }

    public void setUser_is_anchor(boolean user_is_anchor) {
        this.user_is_anchor = user_is_anchor;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getNick_name() {
        return StringUtils.isEmptyString(nick_name)?"":nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getSex() {
        return sex;
    }

    public String getSexStr() {
        return sex == 0 ? "男" : "女";
    }

    public int getSexIcon() {
        return sex == 0 ? R.drawable.icon_man : R.drawable.icon_lady;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getMember_icon() {
        return member_icon;
    }

    public void setMember_icon(String member_icon) {
        this.member_icon = member_icon;
    }

    public String getLast_send_message_datetime() {
        return last_send_message_datetime;
    }

    public void setLast_send_message_datetime(String last_send_message_datetime) {
        this.last_send_message_datetime = last_send_message_datetime;
    }

    public String getAnchor_grade_icon() {
        return anchor_grade_icon;
    }

    public void setAnchor_grade_icon(String anchor_grade_icon) {
        this.anchor_grade_icon = anchor_grade_icon;
    }

    public String getIcon() {
        return isUser_is_anchor() ? anchor_grade_icon : member_icon;
    }

    @Override
    public String toString() {
        return "NettyUserBean{" +
                "signature='" + signature + '\'' +
                ", user_is_anchor=" + user_is_anchor +
                ", to_user_id='" + to_user_id + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", sex=" + sex +
                ", head_img='" + head_img + '\'' +
                ", member_icon='" + member_icon + '\'' +
                ", last_send_message_datetime='" + last_send_message_datetime + '\'' +
                ", anchor_grade_icon='" + anchor_grade_icon + '\'' +
                '}';
    }
}
