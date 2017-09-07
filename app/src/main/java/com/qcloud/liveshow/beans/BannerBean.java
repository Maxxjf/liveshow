package com.qcloud.liveshow.beans;

/**
 * 类说明：轮播图
 * Author: Kuzan
 * Date: 2017/9/7 15:20.
 */
public class BannerBean {
    String img;
    String handleUrl;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHandleUrl() {
        return handleUrl;
    }

    public void setHandleUrl(String handleUrl) {
        this.handleUrl = handleUrl;
    }

    @Override
    public String toString() {
        return "BannerBean{" +
                "img='" + img + '\'' +
                ", handleUrl='" + handleUrl + '\'' +
                '}';
    }
}
