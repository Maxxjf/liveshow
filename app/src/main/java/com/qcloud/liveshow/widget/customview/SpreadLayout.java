package com.qcloud.liveshow.widget.customview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 类说明：可展开和收起的布局
 * Author: Kuzan
 * Date: 2017/9/9 18:09.
 */
public class SpreadLayout extends LinearLayout {
    private float zoom = 0;
    private boolean isSpread = false;

    public SpreadLayout(Context context) {
        super(context);
    }

    public SpreadLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SpreadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (zoom >= 0) {
            setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredHeight() * zoom));
        }
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
        requestLayout();
    }

    public void setSpread(boolean isSpread, boolean anim) {
        this.isSpread = isSpread;
        if (isSpread) {
            if (anim) {
                ObjectAnimator.ofFloat(this, "zoom", getZoom(), 1).setDuration(300).start();
            } else {
                setZoom(1);
            }
        } else {
            if (anim) {
                ObjectAnimator.ofFloat(this, "zoom", getZoom(), 0).setDuration(300).start();
            } else {
                setZoom(0);
            }
        }
    }

    public boolean isSpread() {
        return isSpread;
    }
}
