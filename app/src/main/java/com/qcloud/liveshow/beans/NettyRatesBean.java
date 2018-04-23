package com.qcloud.liveshow.beans;

/**
 * 类型：NettyRatesBean
 * Author: iceberg
 * Date: 2018/3/8.
 * 直播收费标准
 */
public class NettyRatesBean {
    int rates;//收费标准

    public int getRates() {
        return rates;
    }

    public void setRates(int rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "NettyRatesBean{" +
                "rates='" + rates + '\'' +
                '}';
    }
}
