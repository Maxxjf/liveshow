package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：我的关注列表
 * Author: Kuzan
 * Date: 2017/8/18 11:14.
 */
public interface IMyFansView extends BaseView {
    /**刷新列表*/
    void replaceList(List<MemberBean> beans, boolean isNext);
    /**添加列表到末尾*/
    void addListAtEnd(List<MemberBean> beans, boolean isNext);
    /**关注成功*/
    void isAttentionSuccess();
    /**隐藏空布局*/
    void hideEmptyView();
    /**显示空布局*/
    void showEmptyView();
}
