package com.qcloud.liveshow.widget.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.base.BaseLinearLayout;

/**
 * 类说明：个人中心的item布局
 * Author: Kuzan
 * Date: 2017/8/14 17:47.
 */
public class ItemLayout extends BaseLinearLayout {
    private ImageView mImgIcon;
    private TextView mTvItemName;
    private TextView mTvItemRemark;
    private ImageView mImgArrow;

    private int itemIcon;
    private boolean isItemArrow;
    private int itemArrow;
    private int itemName;
    private int itemRemark;
    private int itemRemarkColor;
    private int itemBackground;

    public ItemLayout(Context context) {
        this(context, null);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setCustomAttributes(attrs);

        initLayout();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_item;
    }

    @Override
    protected void initViewAndData() {
        mImgIcon = (ImageView) mView.findViewById(R.id.img_icon);
        mTvItemName = (TextView) mView.findViewById(R.id.tv_item_name);
        mTvItemRemark = (TextView) mView.findViewById(R.id.tv_item_remark);
        mImgArrow = (ImageView) mView.findViewById(R.id.btn_arrow);
    }

    /**
     * 获取自定义属性值
     */
    @SuppressLint("Recycle")
    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray typedArray = this.mContext.obtainStyledAttributes(attrs, R.styleable.ItemAttr);

        itemIcon = typedArray.getResourceId(R.styleable.ItemAttr_item_icon, R.drawable.icon_set);

        isItemArrow = typedArray.getBoolean(R.styleable.ItemAttr_is_item_arrow, true);
        itemArrow = typedArray.getResourceId(R.styleable.ItemAttr_item_arrow, R.drawable.icon_normal_right_arrow);

        itemName = typedArray.getResourceId(R.styleable.ItemAttr_item_name, 0);

        itemRemark = typedArray.getResourceId(R.styleable.ItemAttr_item_remark, 0);
        itemRemarkColor = typedArray.getColor(R.styleable.ItemAttr_item_remark_color, ContextCompat.getColor(mContext, R.color.colorText));

        itemBackground = typedArray.getColor(R.styleable.ItemAttr_item_background, ContextCompat.getColor(mContext, R.color.white));
    }

    private void initLayout() {
        if (itemIcon > 0) {
            mImgIcon.setImageResource(itemIcon);
        }

        if (isItemArrow) {
            if (mImgArrow.getVisibility() == GONE) {
                mImgArrow.setVisibility(VISIBLE);
            }
            if (itemArrow > 0) {
                mImgArrow.setImageResource(itemArrow);
            } else {
                mImgArrow.setImageResource(R.drawable.icon_normal_right_arrow);
            }
        } else {
            if (mImgArrow.getVisibility() == VISIBLE) {
                mImgArrow.setVisibility(GONE);
            }
        }

        if (itemName > 0) {
            mTvItemName.setText(itemName);
        }

        if (itemRemark > 0) {
            mTvItemRemark.setText(itemRemark);
        }

        mTvItemRemark.setTextColor(itemRemarkColor);
        mView.setBackgroundColor(itemBackground);
    }

    public void setRemark(String remark) {
        if (mTvItemRemark != null) {
            mTvItemRemark.setText(remark);
        }
    }

    public void setRemark(int remarkRes) {
        if (mTvItemRemark != null) {
            mTvItemRemark.setText(remarkRes);
        }
    }
}
