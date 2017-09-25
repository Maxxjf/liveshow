package com.qcloud.liveshow.ui.profit.view;

import com.qcloud.liveshow.beans.ProfitRecordBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：收益明细
 * Author: Kuzan
 * Date: 2017/8/18 16:04.
 */
public interface IProfitRecordView extends BaseView {
    /**替换数据*/
    void replaceList(List<ProfitRecordBean> beans, boolean isNext);

    /**添加数据*/
    void addListAtEnd(List<ProfitRecordBean> beans, boolean isNext);

    /**显示空布局*/
    void showEmptyView(String tip);

    /**隐藏空布局*/
    void hideEmptyView();
}
