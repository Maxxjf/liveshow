package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.qcloud.liveshow.R;

/**
 * 类说明：一个软键盘和自定义输入面板（如表情）切换的EditText，实现了面板和键盘等高，且切换时界面不抖动
 * Author: Kuzan
 * Date: 2017/11/17 9:50.
 */
public class KeyBackEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener, TextWatcher {
    private Drawable _right;
    private OnTouchListener _t;
    private OnFocusChangeListener _f;
    private Context mContext;

    private OnBackPressedListener mListener;

    public KeyBackEditText(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public KeyBackEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public KeyBackEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        _right = getCompoundDrawables()[2];
        if (_right == null) {
            _right = ContextCompat.getDrawable(mContext, R.drawable.et_delete);
        }
        _right.setBounds(0, 0, _right.getIntrinsicWidth(), _right.getIntrinsicHeight());
        setCompoundDrawablePadding(10);
        super.setOnFocusChangeListener(this);
        super.setOnTouchListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        this._f = l;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this._t = l;
    }

    private void setClearIconVisible(boolean visible) {
        Drawable temp = visible ? _right : null;
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(drawables[0], drawables[1], temp, drawables[3]);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setClearIconVisible(hasFocus && !TextUtils.isEmpty(getText()));
        if (_f != null) {
            _f.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {

            boolean tapped = event.getX() > (getWidth() - getPaddingRight() - _right.getIntrinsicWidth())
                    && event.getX() < getWidth()
                    && event.getY() > (getHeight() - _right.getIntrinsicHeight()) / 2
                    && event.getY() < (getHeight() - _right.getIntrinsicHeight()) / 2 + _right.getIntrinsicHeight();
            if (tapped) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setText("");
                }
                return true;
            }
        }
        if (_t != null) {
            return _t.onTouch(v, event);
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //ignore
    }

    @Override
    public void afterTextChanged(Editable s) {
        setClearIconVisible(isFocused() && !TextUtils.isEmpty(s));
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
    }

    public void setOnBackPressedListener(OnBackPressedListener listener) {
        mListener = listener;
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mListener != null) {
                if (mListener.onBackPressed()) {
                    return true;
                }
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }
}
