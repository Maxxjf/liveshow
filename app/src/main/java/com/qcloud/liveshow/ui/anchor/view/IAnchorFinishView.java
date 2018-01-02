package com.qcloud.liveshow.ui.anchor.view;

import com.qcloud.liveshow.beans.FinishIncomeBean;
import com.qcloud.qclib.base.BaseView;

/**
 * 类说明：直播完成页面
 * Author: Kuzan
 * Date: 2017/9/2 16:45.
 */
public interface IAnchorFinishView extends BaseView {
    /**点击返回首页*/
    void onGoHomeClick();
    /**加载数据*/
    void loadData(FinishIncomeBean bean);
}
