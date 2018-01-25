package com.qcloud.liveshow.beans;

/**
 * 类说明：判断该用户身份(主播,守护,观众)
 * Author: iceberg
 * Date: 2017/11/9.
 */
public class UserStatusBean {
    int identity;    //当前人身份 0:观众 1:守护 2:主播	number
    boolean isBlack;    //被查看人是否被拉黑 true:已拉黑 false:未拉黑	boolean
    boolean isForbidden;//	被查看人是否被禁言 true:已禁言 false:未禁言	boolean
    boolean isGuard;    //被查看人是否被设置为守护 true:是守护 false:不是守护	boolean
    boolean isAttention;    //被查看人是否被关注 true:已关注 false:未关注

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public boolean isForbidden() {
        return isForbidden;
    }

    public void setForbidden(boolean forbidden) {
        isForbidden = forbidden;
    }

    public boolean isGuard() {
        return isGuard;
    }

    public void setGuard(boolean guard) {
        isGuard = guard;
    }

    @Override
    public String toString() {
        return "UserStatusBean{" +
                "identity=" + identity +
                ", isBlack=" + isBlack +
                ", isForbidden=" + isForbidden +
                ", isGuard=" + isGuard +
                ", isAttention=" + isAttention +
                '}';
    }
}
