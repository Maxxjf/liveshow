package com.qcloud.liveshow.beans;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/8/22 11:32.
 */
public class CreatorBean {
    long id;
    int level;
    int gender;
    String nick;
    String portrait;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @Override
    public String toString() {
        return "CreatorBean{" +
                "id=" + id +
                ", level=" + level +
                ", gender=" + gender +
                ", nick='" + nick + '\'' +
                ", portrait='" + portrait + '\'' +
                '}';
    }
}
