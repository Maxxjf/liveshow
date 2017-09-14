package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.widget.customview.BaseEmptyView;

/**
 * 类说明：没有关注布局
 * Author: Kuzan
 * Date: 2017/9/13 18:24.
 */
public class NoMessageView extends BaseEmptyView {

    public NoMessageView(Context context) {
        this(context, null);
    }

    public NoMessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoMessageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setIcon() {
        return R.drawable.icon_no_message;
    }

    @Override
    protected int setTipColor() {
        return ContextCompat.getColor(mContext, R.color.colorText);
    }

    @Override
    protected int setDefaultTip() {
        return R.string.tip_no_message;
    }

    @Override
    protected int paddingTop() {
        return 180;
    }
}
