package com.qcloud.liveshow.beans;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：用户信息
 * Author: Kuzan
 * Date: 2017/9/6 11:03.
 */
public class UserBean {
    private long id;    // id
    private String headImg; // 头像
    private String name;    // 名称
    private String nickName;    // 昵称
    private String signature;   // 个性签名
    private int sex;    // 性别 0:男 1:女
    private String phone;   // 手机号
    private String withdrawPassword;    // 提现密码
    private int exp;    // 经验值
    private String idAccount;   // id账号
    private String memberGrade; // 会员等级名称
    private String memberGradeIcon; // 会员等级icon
    private String anchorGrade; // 主播等级名称
    private String anchorGradeIcon; // 主播等级icon
    private boolean isAnchor;   // 是否是主播
    private long attentionNum;    // 关注数量
    private long virtualCoin;     // 钻石币数量
    private long fansNum;    // 粉丝数量
    private long giftNum;    // 礼物数量
    private double money;   // 收益余额


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSexStr() {
        return sex == 0 ? "男" : "女";
    }

    public int getSexIcon() {
        return sex == 0 ? R.drawable.icon_man : R.drawable.icon_lady;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWithdrawPassword() {
        return withdrawPassword;
    }

    public void setWithdrawPassword(String withdrawPassword) {
        this.withdrawPassword = withdrawPassword;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getIdAccount() {
        return StringUtils.isEmptyString(idAccount) ? "" : idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getMemberGrade() {
        return StringUtils.isEmptyString(memberGrade)? "Lv.0" : memberGrade;
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

    public String getAnchorGrade() {
        return StringUtils.isEmptyString(anchorGrade)? "" : anchorGrade;
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

    public boolean isAnchor() {
        return isAnchor;
    }

    public void setAnchor(boolean anchor) {
        isAnchor = anchor;
    }

    public String getIcon() {
        return isAnchor ? anchorGradeIcon : memberGradeIcon;
    }

    public long getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(long attentionNum) {
        this.attentionNum = attentionNum;
    }

    public String getAttentionNumStr() {
        return String.valueOf(attentionNum);
    }

    public long getVirtualCoin() {
        return virtualCoin;
    }

    public void setVirtualCoin(long virtualCoin) {
        this.virtualCoin = virtualCoin;
    }

    public String getVirtualCoinStr() {
        return String.valueOf(virtualCoin);
    }

    public long getFansNum() {
        return fansNum;
    }

    public void setFansNum(long fansNum) {
        this.fansNum = fansNum;
    }

    public String getFansNumStr() {
        return String.valueOf(fansNum);
    }

    public long getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(long giftNum) {
        this.giftNum = giftNum;
    }

    public String getGiftNumStr() {
        return String.valueOf(giftNum);
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", headImg='" + headImg + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", signature='" + signature + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", withdrawPassword='" + withdrawPassword + '\'' +
                ", exp=" + exp +
                ", idAccount='" + idAccount + '\'' +
                ", memberGrade='" + memberGrade + '\'' +
                ", memberGradeIcon='" + memberGradeIcon + '\'' +
                ", anchorGrade='" + anchorGrade + '\'' +
                ", anchorGradeIcon='" + anchorGradeIcon + '\'' +
                ", isAnchor=" + isAnchor +
                ", attentionNum=" + attentionNum +
                ", virtualCoin=" + virtualCoin +
                ", fansNum=" + fansNum +
                ", giftNum=" + giftNum +
                ", money=" + money +
                '}';
    }
}
