package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.enums.UserHeaderEnum;
import com.qcloud.qclib.base.BaseLinearLayout;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.OnClick;

/**
 * 类说明：用户头像
 * Author: Kuzan
 * Date: 2017/8/18 9:02.
 */
public class UserHeadImageView extends BaseLinearLayout {
    private RatioImageView mImgUserHead; // 用户头像
    private ImageView mImgAnchorLevel; // 主播等级

    public UserHeadImageView(Context context) {
        this(context, null);
    }

    public UserHeadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserHeadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_user_head;
    }

    @Override
    protected void initViewAndData() {
        mImgUserHead = (RatioImageView) mView.findViewById(R.id.img_user_head);
        mImgAnchorLevel = (ImageView) mView.findViewById(R.id.img_anchor_level);
    }

    @OnClick(R.id.layout_user)
    void onUserClick(View view) {
        if (mViewClick != null) {
            mViewClick.onViewClick(view);
        }
    }

    /**
     * 设置用户头像布局的等级icon大小
     * */
    public void setLevelModel(UserHeaderEnum model) {
        if (mImgAnchorLevel != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mImgAnchorLevel.getLayoutParams();
            if (model == UserHeaderEnum.Big) {
                params.width = R.dimen.margin_4;
                params.height = R.dimen.margin_4;
            } else if (model == UserHeaderEnum.Small) {
                params.width = R.dimen.margin_2;
                params.height = R.dimen.margin_2;
            } else {
                params.width = R.dimen.margin_3;
                params.height = R.dimen.margin_3;
            }
            mImgAnchorLevel.setLayoutParams(params);
        }
    }

    /**
     * 加载图片
     *
     * @param imgUrl 图片地址
     * @param levelUrl  主播等级
     * @param size 图片大小
     * */
    public void loadImage(String imgUrl, String levelUrl, int size) {
        if (size <= 0) {
            size = 60;
        }
        if (mImgUserHead != null) {
            mImgUserHead.setWidth(size);
            GlideUtil.loadCircleImage(mContext, mImgUserHead, imgUrl, R.drawable.bitmap_user_head,
                    0, 0, true, false);
        }

        if (mImgAnchorLevel != null) {
            if (StringUtils.isEmptyString(levelUrl)) {
                mImgAnchorLevel.setVisibility(GONE);
            } else {
                mImgAnchorLevel.setVisibility(VISIBLE);
                GlideUtil.loadCircleImage(mContext, mImgAnchorLevel, levelUrl, R.drawable.icon_anchor_level_1,
                        0, 0, true, false);
            }
        }
    }

    /**
     * 加载图片
     *
     * @param imgUrl 图片地址
     * @param levelRes  主播等级
     * @param size 图片大小
     * */
    public void loadImage(String imgUrl, @DrawableRes int levelRes, int size) {
        if (size <= 0) {
            size = 60;
        }
        if (mImgUserHead != null) {
            mImgUserHead.setWidth(size);
            GlideUtil.loadCircleImage(mContext, mImgUserHead, imgUrl, R.drawable.bitmap_user_head,
                    0, 0, true, true);
        }

        if (mImgAnchorLevel != null) {
            if (levelRes > 0) {
                mImgAnchorLevel.setVisibility(VISIBLE);
                mImgAnchorLevel.setImageResource(levelRes);
            } else {
                mImgAnchorLevel.setVisibility(GONE);
            }
        }
    }

    /**
     * 加载图片
     *
     * @param imgUrl 图片地址
     * @param level  主播等级
     * @param size 图片大小
     * */
    public void loadImageByLevel(String imgUrl, int level, int size) {
        if (size <= 0) {
            size = 160;
        }
        if (mImgUserHead != null) {
            mImgUserHead.setWidth(size);
            GlideUtil.loadCircleImage(mContext, mImgUserHead, imgUrl, R.drawable.bitmap_user_head,
                    0, 0, true, true);
        }

        if (mImgAnchorLevel != null) {
            if (level > 0) {
                mImgAnchorLevel.setVisibility(VISIBLE);
                switch (level) {
                    case 1:
                        mImgAnchorLevel.setImageResource(R.drawable.icon_man);
                        break;
                }
            } else {
                mImgAnchorLevel.setVisibility(GONE);
            }
        }
    }
}
