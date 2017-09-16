package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.utils.StringUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：播号弹窗
 * Author: Kuzan
 * Date: 2017/9/16 16:55.
 */
public class CallPop extends BasePopupWindow {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    @Bind(R.id.btn_ok)
    TextView mBtnOk;
    @Bind(R.id.layout_ok)
    LinearLayout mLayoutOk;
    @Bind(R.id.layout_btn)
    LinearLayout mLayoutBtn;

    private String mTelephone;

    public CallPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_call;
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
        setWidth(w * 7 / 8);
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

    public void setTitle(int res) {
        if (res > 0) {
            if (mTvTitle != null) {
                mTvTitle.setText(res);
            }
        }
    }

    public void setTitle(String title) {
        if (StringUtils.isNotEmptyString(title)) {
            if (mTvTitle != null) {
                mTvTitle.setText(title);
            }
        }
    }

    public void setTelephone(String telephone) {
        if (StringUtils.isNotEmptyString(telephone)) {
            mTelephone = telephone;
            if (mTvTips != null) {
                mTvTips.setText(mTelephone);
            }
        }
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }


    @OnClick(R.id.btn_ok)
    void onOkClick() {
        dismiss();
        if (TextUtils.isEmpty(mTelephone)) {
            mTelephone = "07563399366";
        }
        PackageManager pm = mContext.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.CALL_PHONE", "com.qicloud.fathercook"));
        if (permission) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mTelephone));
            mContext.startActivity(intent);
        }
    }
}
