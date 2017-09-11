package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.base.BasePopupWindow;

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

    @OnClick(R.id.btn_manager)
    void onManagerClick() {
        if (mViewClick != null) {
            mViewClick.onViewClick(mBtnManager);
        }
    }
}
