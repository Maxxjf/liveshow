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
    String number;         // 礼物获得数量
    int ordinal;        // 排序
    int type;           //1是大礼物，0是小礼物
    private String giftPrice;//礼物的价格
    private int hitCombo;//上一次要连击的礼物数
    private Long sendGiftTime;//发送礼物的时间
    private boolean currentStart;//是否从当前数开始连击
    private int giftCount;//一次发送礼物的数量
    private int jumpCombo;//跳到指定连击数，例如：从1直接显示3，这里的值就是2

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGiftPrice() {
        return giftPrice;
    }

    public void setGiftPrice(String giftPrice) {
        this.giftPrice = giftPrice;
    }

    public int getHitCombo() {
        return hitCombo;
    }

    public void setHitCombo(int hitCombo) {
        this.hitCombo = hitCombo;
    }

    public Long getSendGiftTime() {
        return sendGiftTime;
    }

    public void setSendGiftTime(Long sendGiftTime) {
        this.sendGiftTime = sendGiftTime;
    }

    public boolean isCurrentStart() {
        return currentStart;
    }

    public void setCurrentStart(boolean currentStart) {
        this.currentStart = currentStart;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public int getJumpCombo() {
        return jumpCombo;
    }

    public void setJumpCombo(int jumpCombo) {
        this.jumpCombo = jumpCombo;
    }

    public long getId() {
        return id;
    }
    public String getIdStr(){
        return String.valueOf(id);
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
                ", number='" + number + '\'' +
                ", ordinal=" + ordinal +
                ", type=" + type +
                ", giftPrice='" + giftPrice + '\'' +
                ", hitCombo=" + hitCombo +
                ", sendGiftTime=" + sendGiftTime +
                ", currentStart=" + currentStart +
                ", giftCount=" + giftCount +
                ", jumpCombo=" + jumpCombo +
                '}';
    }
}
