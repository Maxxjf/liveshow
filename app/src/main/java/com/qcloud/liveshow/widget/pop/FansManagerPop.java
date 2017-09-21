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
 * 类说明：粉丝管理页
 * Author: Kuzan
 * Date: 2017/9/21 16:25.
 */
public class FansManagerPop extends BasePopupWindow {
    public FansManagerPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_fans_manager;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
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
    protected void initAfterViews() {

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    @OnClick({R.id.btn_set_guarder, R.id.btn_my_guarder_list, R.id.btn_gag,
            R.id.btn_add_blacklist, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_set_guarder:
            case R.id.btn_my_guarder_list:
            case R.id.btn_gag:
            case R.id.btn_add_blacklist:
                if (mViewClick != null) {
                    mViewClick.onViewClick(view);
                }
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}
