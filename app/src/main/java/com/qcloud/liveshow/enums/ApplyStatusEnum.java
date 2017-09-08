package com.qcloud.liveshow.enums;

/**
 * 类说明：申请主播状态
 * Author: Kuzan
 * Date: 2017/9/8 10:26.
 */
public enum ApplyStatusEnum {

    /**待审核*/
    Pending(0, "待审核"),
    /**审核通过*/
    Agree(1, "审核通过"),
    /**审核不通过*/
    Disagree(2, "审核不通过"),
    /**未提交申请*/
    NotApply(3, "未提交申请"),
    /**禁用*/
    Disable(4, "禁用");

    private int key;
    private String value;

    ApplyStatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static ApplyStatusEnum valueOf(int key) {
        switch (key) {
            case 0:
                return Pending;
            case 1:
                return Agree;
            case 2:
                return Disagree;
            case 3:
                return NotApply;
            case 4:
                return Disable;
            default:
                return Pending;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
