package com.qcloud.liveshow.beans;

import java.util.List;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/8/22 11:35.
 */
public class RetBean {
    int dm_error;
    String error_msg;
    List<LiveShowBean> lives;

    public int getDm_error() {
        return dm_error;
    }

    public void setDm_error(int dm_error) {
        this.dm_error = dm_error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<LiveShowBean> getLives() {
        return lives;
    }

    public void setLives(List<LiveShowBean> lives) {
        this.lives = lives;
    }

    @Override
    public String toString() {
        return "RetBean{" +
                "dm_error=" + dm_error +
                ", error_msg='" + error_msg + '\'' +
                ", lives=" + lives +
                '}';
    }
}
