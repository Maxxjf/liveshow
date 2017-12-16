package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.StringUtils;

import java.io.Serializable;

/**
 * 类说明：主播员信息
 * Author: Kuzan
 * Date: 2017/9/21 11:18.
 */
public class AnchorBean implements Serializable {
    long id;                // id
    String idAccount;       // 主播id
    String nickName;        // 昵称
    String headImg;         // 头像
    int sex;                // 性别 0男1女
    String signature;       // 个性签名
    boolean isAnchor;       // 是否是主播员
    String anchorGrade;     // 主播等级
    String anchorGradeIcon; // 主播等级图标
    String memberGradeIcon; // 会员等级
    String memberGrade;     // 会员等级
    int attentionNum;       // 关注人数量
    int fansNum;            // 粉丝数量

    public long getId() {
        return id;
    }

    public String getIdStr() {
        return String.valueOf(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdAccount() {
        return StringUtils.isEmptyString(idAccount) ? "" : idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
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

    public String getSignature() {
        return StringUtils.isEmptyString(signature) ? "" : signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isAnchor() {
        return isAnchor;
    }

    public void setAnchor(boolean anchor) {
        isAnchor = anchor;
    }

    public String getAnchorGrade() {
        return StringUtils.isEmptyString(anchorGrade) ? "" : anchorGrade;
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

    public String getMemberGradeIcon() {
        return memberGradeIcon;
    }

    public void setMemberGradeIcon(String memberGradeIcon) {
        this.memberGradeIcon = memberGradeIcon;
    }

    public String getMemberGrade() {
        return StringUtils.isEmptyString(memberGrade) ? "Lv.0" : memberGrade;
    }

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    public String getIcon() {
        return isAnchor ? anchorGradeIcon : memberGradeIcon;
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

    @Override
    public String toString() {
        return "AnchorBean{" +
                "id=" + id +
                ", idAccount='" + idAccount + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImg='" + headImg + '\'' +
                ", sex=" + sex +
                ", signature='" + signature + '\'' +
                ", isAnchor=" + isAnchor +
                ", anchorGrade='" + anchorGrade + '\'' +
                ", anchorGradeIcon='" + anchorGradeIcon + '\'' +
                ", memberGradeIcon='" + memberGradeIcon + '\'' +
                ", memberGrade='" + memberGrade + '\'' +
                ", attentionNum=" + attentionNum +
                ", fansNum=" + fansNum +
                '}';
    }
}
