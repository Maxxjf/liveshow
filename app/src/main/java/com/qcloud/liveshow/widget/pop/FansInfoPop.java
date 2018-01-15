package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：粉丝信息弹窗
 * Author: Kuzan
 * Date: 2017/8/29 14:50.
 */
public class FansInfoPop extends BasePopupWindow {
    @Bind(R.id.btn_manager)
    TextView mBtnManager;
    @Bind(R.id.tv_fans_name)
    TextView mTvFansName;
    @Bind(R.id.img_fans_sex)
    ImageView mImgFansSex;
    @Bind(R.id.tv_fans_id)
    TextView mTvFansId;
    @Bind(R.id.tv_fans_signature)
    TextView mTvFansSignature;
    @Bind(R.id.tv_follow)
    TextView mTvFollow;
    @Bind(R.id.layout_follow)
    LinearLayout mLayoutFollow;
    @Bind(R.id.tv_fans)
    TextView mTvFans;
    @Bind(R.id.layout_fans)
    LinearLayout mLayoutFans;
    @Bind(R.id.btn_follow)
    TextView mBtnFollow;
    @Bind(R.id.btn_letter)
    TextView mBtnLetter;
    @Bind(R.id.img_user_head)
    RatioImageView imgUserHead;
    @Bind(R.id.img_anchor_level)
    ImageView imgAnchorLevel;
    @Bind(R.id.layout_user)
    FrameLayout layoutUser;

    private MemberBean mCurrMember;

    public FansInfoPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_fans_info;
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
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int height = (int) mContext.getResources().getDimension(R.dimen.margin_50);
        setHeight(height);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    @OnClick({R.id.btn_manager,R.id.btn_letter,R.id.btn_follow})
    void onManagerClick(View view) {
        if (mViewClick != null) {
            switch (view.getId()){
                case R.id.btn_manager:
                    mViewClick.onViewClick(mBtnManager);
                    break;
                case R.id.btn_letter:
                    mViewClick.onViewClick(mBtnLetter);
                    break;
                case R.id.btn_follow:
                    mViewClick.onViewClick(mBtnFollow);
                    break;
            }

        }
        dismiss();
    }

    /**
     * 设置数据
     * */
    public void refreshData(MemberBean bean) {
        if (bean != null) {
            mCurrMember = bean;
            mTvFansId.setText(bean.getIdAccount());
            GlideUtil.loadCircleImage(mContext, imgUserHead, bean.getHeadImg(),
                    R.drawable.bitmap_user_head, 0, 0, true, false);
            mTvFansName.setText(bean.getNickName());
            mImgFansSex.setImageResource(bean.getSexIcon());
            GlideUtil.loadCircleImage(mContext, imgAnchorLevel, bean.getIcon(),
                    R.drawable.icon_anchor_level_1, 0, 0, true, false);
            mTvFansSignature.setText(bean.getSignature());
            mTvFans.setText(bean.getFansNumStr());
            mTvFollow.setText(bean.getAttentionNumStr());
            mBtnFollow.setText(bean.isAttention()?R.string.tag_un_follow:R.string.tag_follow);
        } else {
            throw new NullPointerException("实体类不可为空");
        }
    }

    /**
     * 获取当前成员信息
     * */
    public MemberBean getCurrMember() {
        return mCurrMember;
    }
}
