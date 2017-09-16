package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.ProblemBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：常见问题
 * Author: Kuzan
 * Date: 2017/9/9 17:56.
 */
public interface IProblemView extends BaseView {
    /**刷新列表*/
    void replaceList(List<ProblemBean> beans, boolean isNext);

    /**添加数据到尾部*/
    void addListAtEnd(List<ProblemBean> beans, boolean isNext);
}
