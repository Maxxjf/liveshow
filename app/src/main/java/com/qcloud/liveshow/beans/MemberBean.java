package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.StringUtils;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 类说明：粉丝成员
 * Author: Kuzan
 * Date: 2017/9/12 15:03.
 */
public class MemberBean extends RealmObject implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;
    @PrimaryKey
    long id;            // 会员id
    String nickName;    // 昵称
    String headImg;     // 头像
    int sex;            // 性别 0:男 1:女
    boolean isAnchor;   // 是否是主播
    String anchorGrade; // 主播等级
    String anchorGradeIcon;// 主播等级图标
    String memberGrade; // 会员等级
    String memberGradeIcon; // 会员等级图标
    double memberSum;   // 该会员贡献总收益
    boolean isRead =true;     //消息已读未读
    String signature;   // 个性签名
    boolean isAttention = true;// 是否关注 默认为true是黑名单里使用
    int attentionNum;   // 关注的人
    int fansNum;        // 粉丝数量
    String idAccount;   // 直播id
    String last_send_message_datetime;//最后一次接受私聊信息时间
    boolean isBlack; //是否黑名单
    boolean isForbidden;//是否被禁言
    boolean isGuard;//是否被设置守护
    int identity;//当前人身份 0:观众 1:守护 2:主播

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public boolean isForbidden() {
        return isForbidden;
    }

    public void setForbidden(boolean forbidden) {
        isForbidden = forbidden;
    }

    public boolean isGuard() {
        return isGuard;
    }

    public void setGuard(boolean guard) {
        isGuard = guard;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getLast_send_message_datetime() {
        return last_send_message_datetime;
    }

    public void setLast_send_message_datetime(String last_send_message_datetime) {
        this.last_send_message_datetime = last_send_message_datetime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getIdAccount() {
        return StringUtils.isEmptyString(idAccount)? "" : idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public int getAttentionNum() {
        return attentionNum;
    }

    public String getAttentionNumStr() {
        return String.valueOf(attentionNum);
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public String getFansNumStr() {
        return String.valueOf(fansNum);
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public long getId() {
        return id;
    }

    public String getIdStr() {
        return String.valueOf(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return StringUtils.isEmptyString(nickName) ? "" : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
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

    public boolean isAnchor() {
        return isAnchor;
    }

    public void setAnchor(boolean anchor) {
        isAnchor = anchor;
    }

    public String getAnchorGrade() {
        return anchorGrade;
    }

    public void setAnchorGrade(String anchorGrade) {
        this.anchorGrade = anchorGrade;
    }

    public String getAnchorGradeIcon() {
        return anchorGradeIcon;
    }

    public void setAnchorGradeIcon(String anchorGradeIcon) {
        this.anchorGradeIcon = anchorGradeIcon;
    }

    public String getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    public String getMemberGradeIcon() {
        return memberGradeIcon;
    }

    public void setMemberGradeIcon(String memberGradeIcon) {
        this.memberGradeIcon = memberGradeIcon;
    }

    public String getIcon() {
        return isAnchor ? anchorGradeIcon : memberGradeIcon;
    }

    public double getMemberSum() {
        return memberSum;
    }

    public void setMemberSum(double memberSum) {
        this.memberSum = memberSum;
    }

    public String getSignature() {
        return StringUtils.isEmptyString(signature) ? "" : signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public void refreshAttention() {
        isAttention = !isAttention;
    }
    public void refreshForbidden() {
        isForbidden = !isForbidden;
    }
    public void refreshBlack() {
        isBlack = !isBlack;
    }
    public void refreshGuard() {
        isGuard = !isGuard;
    }

    @Override
    public String toString() {
        return "MemberBean{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", headImg='" + headImg + '\'' +
                ", sex=" + sex +
                ", isAnchor=" + isAnchor +
                ", anchorGrade='" + anchorGrade + '\'' +
                ", anchorGradeIcon='" + anchorGradeIcon + '\'' +
                ", memberGrade='" + memberGrade + '\'' +
                ", memberGradeIcon='" + memberGradeIcon + '\'' +
                ", memberSum=" + memberSum +
                ", isRead=" + isRead +
                ", signature='" + signature + '\'' +
                ", isAttention=" + isAttention +
                ", attentionNum=" + attentionNum +
                ", fansNum=" + fansNum +
                ", idAccount='" + idAccount + '\'' +
                ", last_send_message_datetime='" + last_send_message_datetime + '\'' +
                ", isBlack=" + isBlack +
                ", isForbidden=" + isForbidden +
                ", isGuard=" + isGuard +
                ", identity=" + identity +
                '}';
    }

}
