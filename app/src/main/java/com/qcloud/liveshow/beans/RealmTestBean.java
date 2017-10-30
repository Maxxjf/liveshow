package com.qcloud.liveshow.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 类说明：测试Realm
 * Author: Kuzan
 * Date: 2017/10/30 17:27.
 */
public class RealmTestBean extends RealmObject {
    @PrimaryKey
    private long id;    // id
    private String name;    // 名称

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

    @Override
    public String toString() {
        return "RealmTestBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
