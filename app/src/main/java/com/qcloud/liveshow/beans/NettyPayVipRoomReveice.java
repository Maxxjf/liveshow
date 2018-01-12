package com.qcloud.liveshow.beans;

/**
 * 类说明：收费直播接收数据类
 * Author: iceberg
 * Date: 2018/1/11.
 */
public class NettyPayVipRoomReveice {
     boolean canWatch;
     long virtualCoin;

    public boolean isCanWatch() {
        return canWatch;
    }

    public void setCanWatch(boolean canWatch) {
        this.canWatch = canWatch;
    }

    public long getVirtualCoin() {
        return virtualCoin;
    }

    public void setVirtualCoin(long virtualCoin) {
        this.virtualCoin = virtualCoin;
    }

    @Override
    public String toString() {
        return "NettyPayVipRoomReveice{" +
                "canWatch='" + canWatch + '\'' +
                ", virtualCoin='" + virtualCoin + '\'' +
                '}';
    }
}
