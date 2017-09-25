package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：收益记录
 * Author: Kuzan
 * Date: 2017/9/25 11:44.
 */
public class ProfitRecordBean {
    String brandCode;       // 银行卡号
    String money;           // 金额
    String time;            // 时间
    String type;            // 收益类型

    public boolean isBrandCodeEmpty() {
        return StringUtils.isEmptyString(brandCode);
    }

    public String getBrandCode() {
        return StringUtils.isEmptyString(brandCode) ? "" : brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getMoney() {
        return StringUtils.isEmptyString(money) ? "" : money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return StringUtils.isEmptyString(type) ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProfitRecordBean{" +
                "brandCode='" + brandCode + '\'' +
                ", money='" + money + '\'' +
                ", time='" + time + '\'' +
                ", type=" + type +
                '}';
    }
}
