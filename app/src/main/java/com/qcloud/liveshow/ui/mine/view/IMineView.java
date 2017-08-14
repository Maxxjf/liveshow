package com.qcloud.liveshow.ui.mine.view;

/**
 * 类说明：我的
 * Author: Kuzan
 * Date: 2017/8/10 9:57.
 */
public interface IMineView {
    /**点击用户*/
    void onUserClick();

    /**点击关注*/
    void onFollowClick();

    /**点击粉丝*/
    void onFansClick();

    /**点击我的收益*/
    void onProfitClick();

    /**点击我的等级*/
    void onUserLevelClick();

    /**点击我的钻石币*/
    void onDiamondsClick();

    /**点击我的礼物*/
    void onGiftsClick();

    /**点击邀请好友*/
    void onInvitingFriendsClick();

    /**点击推广码*/
    void onExtensionCodeClick();

    /**点击设置*/
    void onSetClick();
}
