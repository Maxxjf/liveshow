package com.qcloud.liveshow.beans;

/**
 * 类说明：礼物列表
 * Author: Kuzan
 * Date: 2017/9/12 15:01.
 */
public class GiftBean {
    long id;            // id
    String name;        // 礼物名称
    String image;       // 礼物图片
    int virtualCoin;    // 虚拟币
    String giftKey;     // 礼物key
    int number;         // 礼物获得数量
    int ordinal;        // 排序

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVirtualCoin() {
        return virtualCoin;
    }

    public void setVirtualCoin(int virtualCoin) {
        this.virtualCoin = virtualCoin;
    }

    public String getGiftKey() {
        return giftKey;
    }

    public void setGiftKey(String giftKey) {
        this.giftKey = giftKey;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public String toString() {
        return "GiftBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", virtualCoin=" + virtualCoin +
                ", giftKey='" + giftKey + '\'' +
                ", number=" + number +
                ", ordinal=" + ordinal +
                '}';
    }
}
