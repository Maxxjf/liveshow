package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
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
 * 类说明：弹窗
 * Author: Kuzan
 * Date: 2017/8/1 13:52.
 */
public class TipsPop extends BasePopupWindow {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.layout_title)
    LinearLayout mLayoutTitle;
    @Bind(R.id.tv_tips)
    TextView mTvTips;
    @Bind(R.id.layout_tips)
    LinearLayout mLayoutTips;
    @Bind(R.id.btn_cancel)
    TextView mBtnCancel;
    @Bind(R.id.layout_cancel)
    LinearLayout mLayoutCancel;
    @Bind(R.id.btn_ok)
    TextView mBtnOk;
    @Bind(R.id.layout_ok)
    LinearLayout mLayoutOk;
    @Bind(R.id.layout_btn)
    LinearLayout mLayoutBtn;
    @Bind(R.id.line)
    View mLine;

    public TipsPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_tips;
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

    /** 标题 */
    public void setTitleColor(@ColorInt int color) {
        if (mTvTitle != null) {
            mTvTitle.setTextColor(color);
        }
    }

    public void setTitle(String title) {
        if (mTvTitle != null) {
            if (StringUtils.isNotEmptyString(title)) {
                mTvTitle.setText(title);
            } else {
                mTvTitle.setText(R.string.tip_title);
            }
        }
    }

    public void setTitle(@StringRes int resId) {
        if (mTvTitle != null) {
            if (resId > 0) {
                mTvTitle.setText(resId);
            } else {
                mTvTitle.setText(R.string.tip_title);
            }
        }
    }

    /** 提示内容 */
    public void setTipsColor(@ColorInt int color) {
        if (mTvTips != null) {
            mTvTips.setTextColor(color);
        }
    }

    public void setTips(String tips) {
        if (mTvTips != null) {
            if (StringUtils.isNotEmptyString(tips)) {
                mTvTips.setText(tips);
            } else {
                mTvTips.setText(R.string.tip_title);
            }
        }
    }

    public void setTips(@StringRes int resId) {
        if (mTvTips != null) {
            if (resId > 0) {
                mTvTips.setText(resId);
            } else {
                mTvTips.setText(R.string.tip_title);
            }
        }
    }

    public void setTips(SpannableStringBuilder tips) {
        if (mTvTips != null) {
            mTvTips.setText(tips);
            mTvTips.setTextColor(ContextCompat.getColor(mContext, R.color.colorTitle));
        }
    }

    /** 取消 */
    public void setCancelBtnColor(@ColorInt int color) {
        if (mBtnCancel != null) {
            mBtnCancel.setTextColor(color);
        }
    }

    public void setCancelBtn(String btnNm) {
        if (mBtnCancel != null) {
            if (StringUtils.isNotEmptyString(btnNm)) {
                mBtnCancel.setText(btnNm);
            } else {
                mBtnCancel.setText(R.string.tip_cancel);
            }
        }
    }

    public void setCancelBtn(@StringRes int resId) {
        if (mBtnCancel != null) {
            if (resId > 0) {
                mBtnCancel.setText(resId);
            } else {
                mBtnCancel.setText(R.string.tip_cancel);
            }
        }
    }

    public void setCancelBtnBg(@DrawableRes int resId) {
        if (mBtnCancel != null) {
            if (resId > 0) {
                mBtnCancel.setBackgroundResource(resId);
            }
        }
    }

    /** 确认 */
    public void setOkBtnColor(@ColorInt int color) {
        if (mBtnOk != null) {
            mBtnOk.setTextColor(color);
        }
    }

    public void setOkBtn(String btnNm) {
        if (mBtnOk != null) {
            if (StringUtils.isNotEmptyString(btnNm)) {
                mBtnOk.setText(btnNm);
            } else {
                mBtnOk.setText(R.string.tip_confirm);
            }
        }
    }

    public void setOkBtn(@StringRes int resId) {
        if (mBtnOk != null) {
            if (resId > 0) {
                mBtnOk.setText(resId);
            } else {
                mBtnOk.setText(R.string.tip_confirm);
            }
        }
    }

    public void setOkBtnBg(@DrawableRes int resId) {
        if (mBtnOk != null) {
            if (resId > 0) {
                mBtnOk.setBackgroundResource(resId);
            }
        }
    }

    public void showTitle(boolean isShow) {
        if (mLayoutTitle != null) {
            mLayoutTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    public void showCancel(boolean isShow) {
        if (mLayoutCancel != null) {
            mLayoutCancel.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        if (mLine != null) {
            mLine.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }

    @OnClick(R.id.btn_ok)
    void onOkClick() {
        dismiss();
        if (mViewClick != null) {
            mViewClick.onViewClick(mBtnOk);
        }
    }
}
