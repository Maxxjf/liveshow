package com.qcloud.liveshow.ui.mine.view;

import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.qclib.base.BaseView;

import java.util.List;

/**
 * 类说明：我的礼物列表
 * Author: Kuzan
 * Date: 2017/8/31 11:15.
 */
public interface IMyGiftsView extends BaseView {
    /**我的礼物列表*/
    void replaceMyGiftList(List<GiftBean> beans);
    /**礼物贡献榜单*/
    void replaceList(List<MemberBean> beans, boolean isNext);
    /**礼物贡献榜单*/
    void addListAtEnd(List<MemberBean> beans, boolean isNext);
    /**刷新礼物收益*/
    void refreshGiftProfit(double sum);
}
