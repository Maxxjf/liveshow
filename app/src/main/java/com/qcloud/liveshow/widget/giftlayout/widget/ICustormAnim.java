package com.qcloud.liveshow.widget.giftlayout.widget;

import android.animation.AnimatorSet;
import android.view.View;

/**
 * 类说明：我的自定义动画
 * Author: iceberg
 * Date: 2017/12/28
 */

public interface ICustormAnim {
    AnimatorSet startAnim(GiftFrameLayout giftFrameLayout, View rootView);
    AnimatorSet comboAnim(GiftFrameLayout giftFrameLayout, View rootView, boolean isFirst);
    AnimatorSet endAnim(GiftFrameLayout giftFrameLayout, View rootView);
}
