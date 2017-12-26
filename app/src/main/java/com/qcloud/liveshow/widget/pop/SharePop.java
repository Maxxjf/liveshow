package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.base.BasePopupWindow;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：分享
 * Author: Kuzan
 * Date: 2017/8/29 17:41.
 */
public class SharePop extends BasePopupWindow {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.btn_cancel)
    TextView mBtnCancel;
    @Bind(R.id.btn_share_wechat)
    LinearLayout btnShareWechat;
    @Bind(R.id.btn_share_wechat_circle)
    LinearLayout btnShareWechatCircle;
    @Bind(R.id.btn_facebook)
    LinearLayout btnFacebook;

    public SharePop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_share;
    }

    @Override
    protected int getAnimId() {
        return R.layout.pop_fans_info;
    }

    @Override
    protected void initAfterViews() {

    }

    @OnClick(R.id.btn_share_wechat)
    void setBtnShareWechat() {
        mViewClick.onViewClick(btnShareWechat);

    }

    @OnClick(R.id.btn_share_wechat_circle)
    void setBtnShareWechatCircle() {
        mViewClick.onViewClick(btnShareWechatCircle);

    }

    @OnClick(R.id.btn_facebook)
    void setBtnShareFacebook() {
        mViewClick.onViewClick(btnFacebook);

    }

    @Override
    protected void init() {
        super.init();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        manager.getDefaultDisplay().getSize(size);
        int w = size.x;
        int h = size.y;
        if (w > h) {
            w = h;
        }
        setWidth(w * 9 / 10);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }
}
