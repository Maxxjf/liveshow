package com.qcloud.liveshow.beans;

/**
 * 类说明：Facebook用户信息
 * Author: Kuzan
 * Date: 2017/9/7 11:07.
 */
public class FacebookUserBean {
    long id;
    String linkUri;
    String name;
    String first_name;
    String middle_name;
    String last_name;
    String iconurl;
    String accessToken;
    String profilePictureUri;
    long uid;
    String expiration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLinkUri() {
        return linkUri;
    }

    public void setLinkUri(String linkUri) {
        this.linkUri = linkUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(String profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "FacebookUserBean{" +
                "id=" + id +
                ", linkUri='" + linkUri + '\'' +
                ", name='" + name + '\'' +
                ", first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", iconurl='" + iconurl + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", profilePictureUri='" + profilePictureUri + '\'' +
                ", uid=" + uid +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
