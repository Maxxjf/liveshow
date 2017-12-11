package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.base.BasePopupWindow;

import butterknife.OnClick;

/**
 * 类说明：选择图片弹窗
 * Author: Kuzan
 * Date: 2017/8/30 18:14.
 */
public class SelectPasswordPop extends BasePopupWindow {
    public SelectPasswordPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_select_password;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void initAfterViews() {

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

    @OnClick(R.id.btn_input_again)
    public void onCameraClick(View view) {
        if (mViewClick != null) {
            mViewClick.onViewClick(view);
        }
        dismiss();
    }


    @OnClick(R.id.btn_reset_password)
    public void onAlbumClick(View view) {
        if (mViewClick != null) {
            mViewClick.onViewClick(view);
        }
        dismiss();
    }


    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }
}
