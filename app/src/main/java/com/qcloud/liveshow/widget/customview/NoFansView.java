package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.widget.customview.BaseEmptyView;

/**
 * 类说明：没有粉丝布局
 * Author: iceberg
 * Date: 2018/1/3
 */
public class NoFansView extends BaseEmptyView {

    public NoFansView(Context context) {
        this(context, null);
    }

    public NoFansView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoFansView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setIcon() {
        return R.drawable.icon_no_follow;
    }

    @Override
    protected int setTipColor() {
        return ContextCompat.getColor(mContext, R.color.colorText);
    }

    @Override
    protected int setDefaultTip() {
        return R.string.tip_no_fans_list;
    }

    @Override
    protected int paddingTop() {
        return 180;
    }
}
