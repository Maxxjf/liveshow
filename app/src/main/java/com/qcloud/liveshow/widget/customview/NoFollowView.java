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
public class NoFollowView extends BaseEmptyView {

    public NoFollowView(Context context) {
        this(context, null);
    }

    public NoFollowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoFollowView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        return R.string.tip_no_follow;
    }

    @Override
    protected int paddingTop() {
        return 180;
    }

    @Override
    protected boolean showBtn() {
        return true;
    }

    @Override
    protected int setBtnName() {
        return R.string.go_to_hot;
    }

    @Override
    protected int setBtnColor() {
        return ContextCompat.getColor(mContext,R.color.colorStart);
    }

    @Override
    protected float setBtnWidth() {
        return getResources().getDimension(R.dimen.margin_40);
    }

    @Override
    protected int setBtnBg() {
        return R.drawable.frame_orange_circular;
    }
}
