package com.qcloud.liveshow.beans;

/**
 * 类说明：提交申请主播
 * Author: Kuzan
 * Date: 2017/9/8 11:54.
 */
public class SubmitApplyBean {
    String code;        // 验证码
    String headImg;     // 头像/自拍照
    String name;        // 真实姓名
    String nickName;    // 昵称
    String phone;       // 联系方式
    int sex;            // 性别 0:男 1:女
    String withdrawPassword;    // 提现密码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getWithdrawPassword() {
        return withdrawPassword;
    }

    public void setWithdrawPassword(String withdrawPassword) {
        this.withdrawPassword = withdrawPassword;
    }

    @Override
    public String toString() {
        return "SubmitApplyBean{" +
                "code='" + code + '\'' +
                ", headImg='" + headImg + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", withdrawPassword='" + withdrawPassword + '\'' +
                '}';
    }
}
