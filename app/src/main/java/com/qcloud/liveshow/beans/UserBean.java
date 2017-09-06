package com.qcloud.liveshow.beans;

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
    private String registerTime;    // 注册时间
    private String lastTime;    // 最后登录时间
    private String freeTime;    // 成为免费会员时间
    private long memberGradeId; // 会员等级id
    private long anchorGradeId; // 主播等级id
    private int type;   // 会员类型  第四位会员 第三位免费 第二位主播
    private int state;  // 是否启用(普通会员) 0:禁用 1:启用
    private int anchorState;    // 是否启用(主播) 0:禁用 1:启用
    private int freeState;  // 是否启用(免费会员) 0:禁用 1:启用

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
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(String freeTime) {
        this.freeTime = freeTime;
    }

    public long getMemberGradeId() {
        return memberGradeId;
    }

    public void setMemberGradeId(long memberGradeId) {
        this.memberGradeId = memberGradeId;
    }

    public long getAnchorGradeId() {
        return anchorGradeId;
    }

    public void setAnchorGradeId(long anchorGradeId) {
        this.anchorGradeId = anchorGradeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAnchorState() {
        return anchorState;
    }

    public void setAnchorState(int anchorState) {
        this.anchorState = anchorState;
    }

    public int getFreeState() {
        return freeState;
    }

    public void setFreeState(int freeState) {
        this.freeState = freeState;
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
                ", registerTime='" + registerTime + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", freeTime='" + freeTime + '\'' +
                ", memberGradeId=" + memberGradeId +
                ", anchorGradeId=" + anchorGradeId +
                ", type=" + type +
                ", state=" + state +
                ", anchorState=" + anchorState +
                ", freeState=" + freeState +
                '}';
    }
}
