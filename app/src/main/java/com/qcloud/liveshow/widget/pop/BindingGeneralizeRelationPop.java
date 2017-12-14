package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.ClearEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：绑定分佣关系
 * Author: Kuzan
 * Date: 2017/9/25 14:52.
 */
public class BindingGeneralizeRelationPop extends BasePopupWindow {

    @Bind(R.id.et_code)
    ClearEditText mEtCode;

    private String mCode;

    public BindingGeneralizeRelationPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_binding_generalize_relation;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void init() {
        super.init();
        mEtCode.setImeOptions(EditorInfo.IME_ACTION_DONE);
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

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_ok:
                if (mViewClick != null) {
                    mViewClick.onViewClick(view);
                }

                // 隐藏软键盘
                hideInput();
                break;
        }
    }

    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtCode.getWindowToken(), 0);
    }

    /**推广码*/
    public String getCode() {
        mCode = mEtCode.getText().toString().trim();
        return StringUtils.isEmptyString(mCode) ? "" : mCode;
    }
}
