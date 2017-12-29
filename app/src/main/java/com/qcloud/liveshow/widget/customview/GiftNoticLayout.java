package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.qclib.base.BaseLinearLayout;

import butterknife.Bind;

/**
 * 类说明：礼物公告的布局
 * Author: iceberg
 * Date: 2017/12/27
 */
public class GiftNoticLayout extends BaseLinearLayout {


    @Bind(R.id.gift_user)
    UserHeadImageView giftUser;
    @Bind(R.id.tv_gift_user_name)
    TextView tvGiftUserName;
    @Bind(R.id.tv_gift_name)
    TextView tvGiftName;
    @Bind(R.id.ll_gift_layout)
    LinearLayout llGiftLayout;
    @Bind(R.id.gift_number)
    SGTextView giftNumber;
    private Animation animation;
    private boolean animationIsStart = false;// 动画是否开启

    public GiftNoticLayout(Context context) {
        this(context, null);
    }


    public GiftNoticLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftNoticLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_gift_notic;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void initViewAndData() {
        animation = AnimationUtils.loadAnimation(mContext, R.anim.gift_out_anim);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationIsStart = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animationIsStart = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        giftNumber.setStyle("#ffffff", "#fc9520", "#fc9520", 3, 90);//渐变颜色
        giftNumber.setShadowLayer(2, 0, 2, "#ff000000");//阴影
    }

    public void setGiftUser(MemberBean bean) {
        if (bean != null) {
            giftUser.loadImage(bean.getHeadImg(), bean.getIcon(), 80);
            tvGiftUserName.setText(bean.getNickName());
        }
    }

    public void setGift(GiftBean bean) {
        if (bean != null) {
            tvGiftName.setText(bean.getName());
        }
    }

    public void startAnimstion() {
        if (!animationIsStart) {
            this.startAnimation(animation);
        }
    }

}
