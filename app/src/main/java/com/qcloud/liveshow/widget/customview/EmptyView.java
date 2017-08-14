package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.base.BaseLinearLayout;
import com.qcloud.qclib.utils.StringUtils;

/**
 * 类说明：列表为空的布局
 * Author: Kuzan
 * Date: 2017/8/14 16:11.
 */
public class EmptyView extends BaseLinearLayout {

    private ImageView mImageIcon;
    private TextView mTvTip;
    private TextView mBtnRefresh;

    private OnRefreshListener mListener;

    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_empty;
    }

    @Override
    protected void initViewAndData() {
        mImageIcon = (ImageView) mView.findViewById(R.id.image_icon);
        mTvTip = (TextView) mView.findViewById(R.id.tv_tip);
        mBtnRefresh = (TextView) mView.findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onRefresh();
                }
            }
        });
    }

    public void noData(String tip) {

        mImageIcon.setImageResource(R.drawable.icon_no_data);

        if (mBtnRefresh.getVisibility() != GONE) {
            mBtnRefresh.setVisibility(GONE);
        }

        if (StringUtils.isNotEmptyString(tip)) {
            mTvTip.setText(tip);
        } else {
            mTvTip.setText(R.string.tip_no_data);
        }
    }

    public void noData(int res) {

        mImageIcon.setImageResource(R.drawable.icon_no_data);

        if (mBtnRefresh.getVisibility() != GONE) {
            mBtnRefresh.setVisibility(GONE);
        }

        if (res > 0) {
            mTvTip.setText(res);
        } else {
            mTvTip.setText(R.string.tip_no_data);
        }
    }

    public void noData(String tip, boolean isBtn) {

        mImageIcon.setImageResource(R.drawable.icon_no_data);

        if (isBtn) {
            mBtnRefresh.setVisibility(VISIBLE);
        } else {
            mBtnRefresh.setVisibility(GONE);
        }

        if (StringUtils.isNotEmptyString(tip)) {
            mTvTip.setText(tip);
        } else {
            mTvTip.setText(R.string.tip_no_data);
        }
    }

    public void noData(int res, boolean isBtn) {

        mImageIcon.setImageResource(R.drawable.icon_no_data);

        if (isBtn) {
            mBtnRefresh.setVisibility(VISIBLE);
        } else {
            mBtnRefresh.setVisibility(GONE);
        }

        if (res > 0) {
            mTvTip.setText(res);
        } else {
            mTvTip.setText(R.string.tip_no_data);
        }
    }

    public void noNetWork() {
        mImageIcon.setImageResource(R.drawable.icon_no_wifi);
        mTvTip.setText(R.string.tip_no_net);

        if (mBtnRefresh.getVisibility() != VISIBLE) {
            mBtnRefresh.setVisibility(VISIBLE);
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }
}
