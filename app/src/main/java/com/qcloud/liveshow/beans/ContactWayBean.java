package com.qcloud.liveshow.beans;

/**
 * 类说明：联系方式
 * Author: Kuzan
 * Date: 2017/9/16 16:31.
 */
public class ContactWayBean {
    String contact;     // 联系方式

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "ContactWayBean{" +
                "contact='" + contact + '\'' +
                '}';
    }
}
