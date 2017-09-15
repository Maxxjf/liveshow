package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：会员等级
 * Author: Kuzan
 * Date: 2017/9/15 9:55.
 */
public class MemberGradeBean {
    String exp;             // 	一个虚拟币所得的经验值
    String memberGrade;     // 会员等级
	int memberUpgradeExp;   // 会员当前等级经验值
    int memberUpgradeExpSum;// 会员当前等级总经验值

    public String getExp() {
        return StringUtils.isEmptyString(exp) ? "" : exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getMemberGrade() {
        return StringUtils.isEmptyString(memberGrade) ? "Lv.0" : memberGrade;
    }

    public void setMemberGrade(String memberGrade) {
        this.memberGrade = memberGrade;
    }

    public int getMemberUpgradeExp() {
        return memberUpgradeExp;
    }

    public void setMemberUpgradeExp(int memberUpgradeExp) {
        this.memberUpgradeExp = memberUpgradeExp;
    }

    public int getMemberUpgradeExpSum() {
        return memberUpgradeExpSum;
    }

    public void setMemberUpgradeExpSum(int memberUpgradeExpSum) {
        this.memberUpgradeExpSum = memberUpgradeExpSum;
    }

    @Override
    public String toString() {
        return "MemberGradeBean{" +
                "exp='" + exp + '\'' +
                ", memberGrade='" + memberGrade + '\'' +
                ", memberUpgradeExp=" + memberUpgradeExp +
                ", memberUpgradeExpSum=" + memberUpgradeExpSum +
                '}';
    }
}
