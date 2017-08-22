package com.qcloud.liveshow.ui.home.view;

import com.qcloud.liveshow.beans.LiveShowBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 11:25.
 */
public interface IHotView extends BaseView {
    void replaceBanner(List<String> list);

    void replaceList(List<LiveShowBean> beans);
}
