package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.MemberGradeBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：用户等级
 * Author: Kuzan
 * Date: 2017/9/2 17:54.
 */
public interface IUserLevelView extends BaseView {
    /**刷新用户信息*/
    void refreshUser();

    /**会员等级*/
    void refreshData(MemberGradeBean bean);
}
