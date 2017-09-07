package com.qcloud.liveshow.widget.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.rxbus.BusProvider;

import timber.log.Timber;

/**
 * 类说明：滑动隐藏底部bar
 * Author: Kuzan
 * Date: 2017/8/10 16:13.
 */
public class ScrollHideBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = -1;

    /** Tracking direction of user motion */
    private int mScrollingDirection;
    /** Tracking last threshold crossed */
    private int mScrollTrigger;

    /** 累计滚动距离 */
    private int mScrollDistance;
    /** 触发动画的距离 */
    private int mScrollThreshold;

    private ObjectAnimator mAnimator;

    public ScrollHideBehavior() {

    }

    public ScrollHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(new int[] {R.attr.actionBarSize});
        mScrollThreshold = a.getDimensionPixelSize(0, 0) / 2;
        a.recycle();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target,
                                  int dx, int dy, int[] consumed) {
        //Timber.e("dx = %d", dx);
//        if (dy > 0 && mScrollingDirection != DIRECTION_UP) {
//            mScrollingDirection = DIRECTION_UP;
//            mScrollDistance = 0;
//        } else if (dy < 0 && mScrollingDirection != DIRECTION_DOWN) {
//            mScrollingDirection = DIRECTION_DOWN;
//            mScrollDistance = 0;
//        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
//        mScrollDistance += dyConsumed;
//        if (mScrollDistance > mScrollThreshold && mScrollTrigger != DIRECTION_UP) {
//            mScrollTrigger = DIRECTION_UP;
//            restartAnimator(child, getTargetHideValue(coordinatorLayout, child));
//        } else if (mScrollDistance < -mScrollThreshold && mScrollTrigger != DIRECTION_DOWN) {
//            mScrollTrigger = DIRECTION_DOWN;
//            restartAnimator(child, 0f);
//        }
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target,
                                 float velocityX, float velocityY, boolean consumed) {
        Timber.e("onNestedFling consumed = " + consumed);
        Timber.e("mScrollTrigger = %d", mScrollTrigger);
        Timber.e("velocityY = %2.1f", velocityY);
        if (consumed) {
            if (velocityY > 0 && mScrollTrigger != DIRECTION_UP) {
                mScrollTrigger = DIRECTION_UP;
                restartAnimator(child, getTargetHideValue(coordinatorLayout, child));
            } else if (velocityY < 0 && mScrollTrigger != DIRECTION_DOWN) {
                mScrollTrigger = DIRECTION_DOWN;
                restartAnimator(child, 0f);
            }
        }

        return false;
    }

    private void restartAnimator(View target, float value) {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        if (value == 0) {
            BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.show_hide_title_bar).setObj(true).build());
        } else {
            BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.show_hide_title_bar).setObj(false).build());
        }

        mAnimator = ObjectAnimator
                .ofFloat(target, View.TRANSLATION_Y, value)
                .setDuration(250);
        mAnimator.start();
    }

    private float getTargetHideValue(ViewGroup parent, View target) {
        if (target instanceof AppBarLayout) {
            return -target.getHeight();
        } else if (target instanceof FloatingActionButton) {
            return parent.getHeight() - target.getTop();
        }else {
            return target.getHeight();
        }
    }
}
