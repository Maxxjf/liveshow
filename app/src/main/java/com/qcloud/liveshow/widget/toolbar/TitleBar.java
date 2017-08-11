package com.qcloud.liveshow.widget.toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类说明：应用的统一标题栏
 * Author: Kuzan
 * Date: 2016/8/4 11:05
 */
public class TitleBar extends Toolbar {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ib_left)
    ImageButton ibLeft;
    @Bind(R.id.btn_left)
    TextView btnLeft;
    @Bind(R.id.ib_right)
    ImageButton ibRight;
    @Bind(R.id.btn_right)
    TextView btnRight;
    @Bind(R.id.btn_search)
    ImageView mBtnSearch;
    @Bind(R.id.et_search)
    EditText mEtSearch;
    @Bind(R.id.tv_search)
    TextView mTvSearch;
    @Bind(R.id.layout_search)
    LinearLayout mLayoutSearch;

    private Context mContext;

    private boolean isLeft;
    private int leftIcon;
    private boolean isLeftText;
    private int leftText;
    private boolean isRight;
    private int rightIcon;
    private boolean isRightText;
    private int rightText;
    private boolean isTitle;
    private int title;
    private boolean isSearch;
    private int titleColor;
    private int barBackground;

    private String searchValue;

    private OnBtnListener mListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setCustomAttributes(attrs);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.toolbar_title, null);
        addView(rootView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.toolbar_height)));
        ButterKnife.bind(this, rootView);

        // 标题
        if (isTitle) {
            tvTitle.setVisibility(VISIBLE);
            tvTitle.setText(title);
        } else {
            tvTitle.setVisibility(GONE);
        }

        if (titleColor > 0) {
            tvTitle.setTextColor(titleColor);
        } else {
            tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorTitle));
        }

        // 左图标
        if (isLeft) {
            ibLeft.setVisibility(VISIBLE);
            ibLeft.setImageResource(leftIcon);
        } else {
            ibLeft.setVisibility(GONE);
        }

        // 左文字
        if (isLeftText) {
            btnLeft.setVisibility(VISIBLE);
            btnLeft.setText(leftText);
        } else {
            btnLeft.setVisibility(GONE);
        }

        // 右图标
        if (isRight) {
            ibRight.setVisibility(VISIBLE);
            ibRight.setImageResource(rightIcon);
        } else {
            ibRight.setVisibility(GONE);
        }

        // 右文字
        if (isRightText) {
            btnRight.setVisibility(VISIBLE);
            btnRight.setText(rightText);
        } else {
            btnRight.setVisibility(GONE);
        }

        // 搜索
        if (isSearch) {
            mLayoutSearch.setVisibility(VISIBLE);
        } else {
            mLayoutSearch.setVisibility(GONE);
        }

        if (barBackground > 0) {
            rootView.setBackgroundColor(barBackground);
        } else {
            rootView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH ||
                        (arg2 != null && arg2.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    onSearchClick();
                }
                return false;
            }
        });
    }

    /**
     * 获取自定义属性值
     */
    @SuppressLint("Recycle")
    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray typedArray = this.mContext.obtainStyledAttributes(attrs, R.styleable.BaseBar);
        // 左图标
        isLeft = typedArray.getBoolean(R.styleable.BaseBar_is_left, false);
        leftIcon = typedArray.getResourceId(R.styleable.BaseBar_left_icon, R.drawable.icon_normal_back);

        // 左文字
        isLeftText = typedArray.getBoolean(R.styleable.BaseBar_is_left_text, false);
        leftText = typedArray.getResourceId(R.styleable.BaseBar_left_text, R.string.save);

        // 右图标
        isRight = typedArray.getBoolean(R.styleable.BaseBar_is_right, false);
        rightIcon = typedArray.getResourceId(R.styleable.BaseBar_right_icon, 0);

        // 右文字
        isRightText = typedArray.getBoolean(R.styleable.BaseBar_is_right_text, false);
        rightText = typedArray.getResourceId(R.styleable.BaseBar_right_text, R.string.save);

        // 标题
        isTitle = typedArray.getBoolean(R.styleable.BaseBar_is_title, false);
        title = typedArray.getResourceId(R.styleable.BaseBar_title_text, R.string.app_name);

        // 搜索
        isSearch = typedArray.getBoolean(R.styleable.BaseBar_is_search, false);

        titleColor = typedArray.getColor(R.styleable.BaseBar_title_color, ContextCompat.getColor(mContext, R.color.colorTitle));
        barBackground = typedArray.getColor(R.styleable.BaseBar_bar_background, ContextCompat.getColor(mContext, R.color.white));
    }

    /**标题*/
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    /**标题*/
    public void setTitle(int titleRes) {
        tvTitle.setText(titleRes);
    }

    /**左图标*/
    public void setLeftIcon(boolean isLeft, int res) {
        if (ibLeft != null) {
            if (isLeft) {
                ibLeft.setVisibility(VISIBLE);
                ibLeft.setImageResource(res);
            } else {
                ibLeft.setVisibility(INVISIBLE);
            }
        }
    }

    /**右图标*/
    public void setRightIcon(boolean isRight, int res) {
        if (ibRight != null) {
            if (isRight) {
                ibRight.setVisibility(VISIBLE);
                ibRight.setImageResource(res);
            } else {
                ibRight.setVisibility(INVISIBLE);
            }
        }
    }

    /**左文字*/
    public void setLeftText(String res) {
        if (isLeftText) {
            btnLeft.setVisibility(VISIBLE);
            btnLeft.setText(res);
        }
    }

    /**左文字*/
    public void setLeftText(int res) {
        if (isLeftText) {
            btnLeft.setVisibility(VISIBLE);
            btnLeft.setText(res);
        }
    }

    /**右文字*/
    public void setRightText(String res) {
        if (isRightText) {
            btnRight.setVisibility(VISIBLE);
            btnRight.setText(res);
        }
    }

    /**右文字*/
    public void setRightText(int res) {
        if (isRightText) {
            btnRight.setVisibility(VISIBLE);
            btnRight.setText(res);
        }
    }

    /**搜索图标*/
    public void setSearchIcon(int res) {
        if (mBtnSearch != null) {
            mBtnSearch.setImageResource(res);
        }
    }

    /**搜索框背景*/
    public void setSearchBg(int res) {
        if (mLayoutSearch != null) {
            mLayoutSearch.setBackgroundResource(res);
        }
    }

    /**搜索hint*/
    public void setSearchHint(int res) {
        if (mEtSearch != null) {
            mEtSearch.setHint(res);
        }
    }

    /**搜索内容*/
    public void setSearchValue(String value) {
        if (mEtSearch != null) {
            searchValue = value;
            mEtSearch.setText(value);
        }
    }

    /**是否可输入搜索*/
    public void setSearchEditable(boolean isEdit) {
        if (isEdit) {
            if (mEtSearch != null) {
                mEtSearch.setVisibility(VISIBLE);
            }
            if (mTvSearch != null) {
                mTvSearch.setVisibility(GONE);
            }
        } else {
            if (mEtSearch != null) {
                mEtSearch.setVisibility(GONE);
            }
            if (mTvSearch != null) {
                mTvSearch.setVisibility(VISIBLE);
            }
        }
    }

    /**隐藏标题*/
    public void hideTitle() {
        if (tvTitle != null) {
            tvTitle.setVisibility(INVISIBLE);
        }
    }

    /**显示标题*/
    public void showTitle() {
        if (tvTitle != null) {
            tvTitle.setVisibility(VISIBLE);
        }
    }

    /**隐藏左图标*/
    public void hideLeft() {
        if (ibLeft != null) {
            ibLeft.setVisibility(INVISIBLE);
        }
    }

    /**显示左图标*/
    public void showLeft() {
        if (ibLeft != null) {
            ibLeft.setVisibility(VISIBLE);
        }
    }

    /**隐藏右图标*/
    public void hideRight() {
        if (ibRight != null) {
            ibRight.setVisibility(INVISIBLE);
        }
    }

    /**显示右图标*/
    public void showRight() {
        if (ibRight != null) {
            ibRight.setVisibility(VISIBLE);
        }
    }

    /**获取搜索内容*/
    public String getSearchValue() {
        searchValue = mEtSearch.getText().toString();
        return StringUtils.isEmptyString(searchValue) ? "" : searchValue;
    }

    @OnClick(R.id.ib_left)
    void backClick() {
        hideInput();
        if (mListener != null) {
            mListener.onBtnClick(ibLeft);
        } else {
            ((Activity) mContext).finish();
        }
    }

    @OnClick(R.id.btn_right)
    void rightClick() {
        if (mListener != null) {
            mListener.onBtnClick(btnRight);
        }
    }

    @OnClick(R.id.ib_right)
    void rightImageClick() {
        if (mListener != null) {
            mListener.onBtnClick(ibRight);
        }
    }

    @OnClick(R.id.btn_search)
    public void onSearchClick() {
        if (mListener != null) {
            mListener.onBtnClick(mBtnSearch);
        }

        // 隐藏软键盘
        if (!TextUtils.isEmpty(searchValue)) {
            hideInput();
        }
    }

    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtSearch.getWindowToken(), 0);
    }

    public interface OnBtnListener {
        void onBtnClick(View view);
    }

    public void setOnBtnListener(OnBtnListener listener) {
        this.mListener = listener;
    }
}
