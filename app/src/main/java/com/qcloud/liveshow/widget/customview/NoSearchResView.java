package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.widget.customview.BaseEmptyView;

/**
 * 类说明：没有交易布局
 * Author: Kuzan
 * Date: 2017/9/13 18:24.
 */
public class NoSearchResView extends BaseEmptyView {

    public NoSearchResView(Context context) {
        this(context, null);
    }

    public NoSearchResView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoSearchResView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int setIcon() {
        return R.drawable.icon_no_search_res;
    }

    @Override
    protected int setTipColor() {
        return ContextCompat.getColor(mContext, R.color.colorText);
    }

    @Override
    protected int setDefaultTip() {
        return R.string.tip_no_search_res;
    }

    @Override
    protected int paddingTop() {
        return 180;
    }
}
