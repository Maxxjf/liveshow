package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.NetUtils;
import com.qcloud.qclib.widget.customview.BaseEmptyView;

/**
 * 类说明：没有黑名单布局
 * Author: iceberg
 * Date: 2018/1/3
 */
public class NoBlackListView extends BaseEmptyView {

    public NoBlackListView(Context context) {
        this(context, null);
    }

    public NoBlackListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoBlackListView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        return R.string.tip_no_balck_list;
    }

    @Override
    protected int paddingTop() {
        return 180;
    }
}
