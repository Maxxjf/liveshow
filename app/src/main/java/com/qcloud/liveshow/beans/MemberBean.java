package com.qcloud.liveshow.beans;

/**
 * 类说明：粉丝成员
 * Author: Kuzan
 * Date: 2017/9/12 15:03.
 */
public class MemberBean {
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
    boolean isAttention;// 是否关注

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
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

    public double getMemberSum() {
        return memberSum;
    }

    public void setMemberSum(double memberSum) {
        this.memberSum = memberSum;
    }

    public String getSignature() {
        return signature;
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
                '}';
    }
}
