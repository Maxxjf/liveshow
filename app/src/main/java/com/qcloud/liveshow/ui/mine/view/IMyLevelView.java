package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.LevelViewPageBean;

import java.util.List;

/**
 * 类说明：我的等级
 * Author: Kuzan
 * Date: 2017/9/2 17:37.
 */
public interface IMyLevelView {
    void replaceList(List<LevelViewPageBean> beans);

    void isNoAnchor();
}
