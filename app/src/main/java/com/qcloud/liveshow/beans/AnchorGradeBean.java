package com.qcloud.liveshow.beans;

import com.qcloud.qclib.utils.StringUtils;

import java.util.List;

/**
 * 类说明：主播等级
 * Author: Kuzan
 * Date: 2017/9/15 9:58.
 */
public class AnchorGradeBean {
    String anchorGradeIcon;     // 主播等级图标
    List<LevelBean> anchorGradeList;    // 主播所有等级集合
    String contact;     // 主播等级 联系方式

    public String getAnchorGradeIcon() {
        return anchorGradeIcon;
    }

    public void setAnchorGradeIcon(String anchorGradeIcon) {
        this.anchorGradeIcon = anchorGradeIcon;
    }

    public List<LevelBean> getAnchorGradeList() {
        return anchorGradeList;
    }

    public void setAnchorGradeList(List<LevelBean> anchorGradeList) {
        this.anchorGradeList = anchorGradeList;
    }

    public String getContact() {
        return StringUtils.isEmptyString(contact) ? "" : contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "AnchorGradeBean{" +
                "anchorGradeIcon='" + anchorGradeIcon + '\'' +
                ", anchorGradeList=" + anchorGradeList +
                ", contact='" + contact + '\'' +
                '}';
    }
}
