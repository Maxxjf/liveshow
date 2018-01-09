package com.qcloud.liveshow.ui.profit.view;

import com.qcloud.liveshow.beans.DiamondsRecordBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：钻石币交易记录
 * Author: Kuzan
 * Date: 2017/9/1 16:26.
 */
public interface IDiamondsRecordView extends BaseView {
    void replaceList(List<DiamondsRecordBean> beans, boolean isNext);

    void addListAtEnd(List<DiamondsRecordBean> beans, boolean isNext);

    void showEmptyView();

    void hideEmptyView();
}
