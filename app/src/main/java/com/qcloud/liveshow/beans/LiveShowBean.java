package com.qcloud.liveshow.beans;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/8/22 11:31.
 */
public class LiveShowBean {
    CreatorBean creator;
    long id;
    String name;
    String share_addr;
    String stream_addr;
    long online_users;

    public CreatorBean getCreator() {
        return creator;
    }

    public void setCreator(CreatorBean creator) {
        this.creator = creator;
    }

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

    public String getShare_addr() {
        return share_addr;
    }

    public void setShare_addr(String share_addr) {
        this.share_addr = share_addr;
    }

    public String getStream_addr() {
        return stream_addr;
    }

    public void setStream_addr(String stream_addr) {
        this.stream_addr = stream_addr;
    }

    public long getOnline_users() {
        return online_users;
    }

    public void setOnline_users(long online_users) {
        this.online_users = online_users;
    }

    @Override
    public String toString() {
        return "LiveShowBean{" +
                "creator=" + creator +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", share_addr='" + share_addr + '\'' +
                ", stream_addr='" + stream_addr + '\'' +
                ", online_users=" + online_users +
                '}';
    }
}
