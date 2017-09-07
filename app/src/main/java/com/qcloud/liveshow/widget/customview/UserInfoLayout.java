package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.qclib.base.BaseLinearLayout;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

/**
 * 类说明：用户信息
 * Author: Kuzan
 * Date: 2017/8/14 17:33.
 */
public class UserInfoLayout extends BaseLinearLayout {
    private RatioImageView mImgUserHead;
    private TextView mTvUserName;
    private TextView mTvUserId;
    private ImageView mImgUserSex;
    private ImageView mImgAnchorLevel;  // 主播等级
    private ImageView mImgUserLevel;    // 用户等级

    private String idTag;

    public UserInfoLayout(Context context) {
        this(context, null);
    }

    public UserInfoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserInfoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_user_info;
    }

    @Override
    protected void initViewAndData() {
        mImgUserHead = (RatioImageView) mView.findViewById(R.id.img_user_head);
        mTvUserName = (TextView) mView.findViewById(R.id.tv_user_name);
        mTvUserId = (TextView) mView.findViewById(R.id.tv_user_id);
        mImgUserSex = (ImageView) mView.findViewById(R.id.img_user_sex);
        mImgAnchorLevel = (ImageView) mView.findViewById(R.id.img_anchor_level);
        mImgUserLevel = (ImageView) mView.findViewById(R.id.img_user_level);

        idTag = getResources().getString(R.string.tag_id);
    }

    public void refreshUserInfo(UserBean bean) {
        if (bean != null) {
            GlideUtil.loadCircleImage(mContext, mImgUserHead, bean.getHeadImg(), R.drawable.icon_user_head_default,
                    0, 0, true, false);
            mTvUserName.setText(bean.getNickName());
            mTvUserId.setText(String.format(idTag, bean.getIdAccount()));
            if (bean.getSex() == 0) {
                mImgUserSex.setImageResource(R.drawable.icon_man);
            } else {
                mImgUserSex.setImageResource(R.drawable.icon_lady);
            }
            if (StringUtils.isNotEmptyString(bean.getAnchorGradeIcon())) {
                mImgAnchorLevel.setVisibility(VISIBLE);
                GlideUtil.loadCircleImage(mContext, mImgAnchorLevel, bean.getAnchorGradeIcon(), R.drawable.icon_anchor_level_1,
                        0, 0, true, false);
            } else {
                mImgAnchorLevel.setVisibility(GONE);
            }
            if (StringUtils.isNotEmptyString(bean.getMemberGradeIcon())) {
                mImgUserLevel.setVisibility(VISIBLE);
                GlideUtil.loadCircleImage(mContext, mImgUserLevel, bean.getMemberGradeIcon(), R.drawable.icon_user_level_v,
                        0, 0, true, false);
            } else {
                mImgUserLevel.setVisibility(GONE);
            }
        }

    }
}
