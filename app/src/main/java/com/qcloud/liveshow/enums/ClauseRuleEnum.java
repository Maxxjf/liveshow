package com.qcloud.liveshow.enums;

/**
 * 类说明：免责条款
 * Author: Kuzan
 * Date: 2017/9/16 15:02.
 */
public enum ClauseRuleEnum {
    /**关于我们*/
    AboutUs(0, "关于我们"),
    /**注册免责*/
    LoginRule(1, "注册免责"),
    /**主播免责*/
    AnchorRule(2, "主播免责"),
    /**提现条款*/
    WithdrawalsClause(3, "提现条款"),
    /**充值条款*/
    RechargeRule(4, "充值条款"),
    /**提现规则*/
    WithdrawalsRule(5, "提现规则"),
    /**会员等级规则*/
    MemberLevelRule(6, "会员等级规则");

    private int key;
    private String value;

    ClauseRuleEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ClauseRuleEnum valueOf(int key) {
        switch (key) {
            case 0:
                return AboutUs;
            case 1:
                return LoginRule;
            case 2:
                return AnchorRule;
            case 3:
                return WithdrawalsClause;
            case 4:
                return RechargeRule;
            case 5:
                return WithdrawalsRule;
            case 6:
                return MemberLevelRule;
            default:
                return AboutUs;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
