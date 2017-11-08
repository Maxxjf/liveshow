package com.qcloud.liveshow.beans;

import android.os.Parcel;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.StringUtils;

import java.io.Serializable;

/**
 * 类说明：粉丝成员
 * Author: Kuzan
 * Date: 2017/9/12 15:03.
 */
public class MemberBean implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

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
    String signature;   // 个性签名
    boolean isAttention = true;// 是否关注 默认为true是黑名单里使用
    int attentionNum;   // 关注的人
    int fansNum;        // 粉丝数量
    String idAccount;   // 直播id


    protected MemberBean(Parcel in) {
        id = in.readLong();
        nickName = in.readString();
        headImg = in.readString();
        sex = in.readInt();
        isAnchor = in.readByte() != 0;
        anchorGrade = in.readString();
        anchorGradeIcon = in.readString();
        memberGrade = in.readString();
        memberGradeIcon = in.readString();
        memberSum = in.readDouble();
        signature = in.readString();
        isAttention = in.readByte() != 0;
        attentionNum = in.readInt();
        fansNum = in.readInt();
        idAccount = in.readString();
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
                ", signature='" + signature + '\'' +
                ", isAttention=" + isAttention +
                ", attentionNum=" + attentionNum +
                ", fansNum=" + fansNum +
                ", idAccount='" + idAccount + '\'' +
                '}';
    }

}
