package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.NetUtils;
import com.qcloud.qclib.widget.customview.BaseEmptyView;

/**
 * 类说明：列表为空的布局
 * Author: Kuzan
 * Date: 2017/8/14 16:11.
 */
public class EmptyView extends BaseEmptyView {

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!NetUtils.isConnected(context)){
            noNetWork();
        }
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
        return R.string.tip_no_follow;
    }

    @Override
    protected int paddingTop() {
        return 180;
    }
}
