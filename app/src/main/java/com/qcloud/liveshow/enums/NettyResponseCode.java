package com.qcloud.liveshow.enums;

/**
 * 类说明：响应码
 * Author: Kuzan
 * Date: 2017/11/1 15:36.
 */
public enum NettyResponseCode {
    SUCCESS            (0, "成功"),
    FAIL               (1, "失败"),
    AUTH_FAIL          (2, "鉴权失败"),
    DATA_ERROR         (3, "数据格式错误"),
    PARAMETER_MISSING  (4, "缺少必要参数"),
    IS_BLOCKED          (5, "禁言"),
    IS_BLACK            (6, "拉黑");

    private int key;
    private String value;

    NettyResponseCode(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static NettyResponseCode valueOf(int key) {
        switch (key) {
            case 0:
                return SUCCESS;
            case 1:
                return FAIL;
            case 2:
                return AUTH_FAIL;
            case 3:
                return DATA_ERROR;
            case 4:
                return PARAMETER_MISSING;
            case 5:
                return IS_BLOCKED;
            case 6:
                return IS_BLACK;
            default:
                return FAIL;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
