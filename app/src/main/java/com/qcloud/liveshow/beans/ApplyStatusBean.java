package com.qcloud.liveshow.beans;

/**
 * 类说明：申请成为主播
 * Author: Kuzan
 * Date: 2017/9/8 10:35.
 */
public class ApplyStatusBean {
    int state;  // 申请主播状态 0:待审核 1:审核通过 2:审核不通过 3:未提交申请 4:禁用

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ApplyStatusBean{" +
                "state=" + state +
                '}';
    }
}
