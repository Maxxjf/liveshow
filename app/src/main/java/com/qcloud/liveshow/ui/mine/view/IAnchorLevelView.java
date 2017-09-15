package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.AnchorGradeBean;
import com.qcloud.liveshow.beans.LevelBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：主播等级
 * Author: Kuzan
 * Date: 2017/9/2 17:57.
 */
public interface IAnchorLevelView extends BaseView {
    /**刷新用户信息*/
    void refreshUser();

    /**主播等级*/
    void refreshData(AnchorGradeBean bean);

    /**主播等级列表*/
    void replaceLevel(List<LevelBean> beans);
}
