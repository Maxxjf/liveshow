package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：等级
 * Author: Kuzan
 * Date: 2017/9/15 9:59.
 */
public class LevelBean {
    long id;            // id
    String icon;        // 等级图标
    String name;        // 等级名称
    double proportion;  // 收益分成比例 %
    int upperLimit;     // 直播间收费上限

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return StringUtils.isEmptyString(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    @Override
    public String toString() {
        return "LevelBean{" +
                "id=" + id +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", proportion=" + proportion +
                ", upperLimit=" + upperLimit +
                '}';
    }
}
