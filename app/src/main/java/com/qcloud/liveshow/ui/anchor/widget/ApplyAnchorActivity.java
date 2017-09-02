package com.qcloud.liveshow.ui.anchor.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.anchor.presenter.impl.ApplyAnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IApplyAnchorView;
import com.qcloud.liveshow.widget.pop.SelectPicturePop;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.LineTextView;
import com.qcloud.qclib.widget.customview.RatioImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：申请主播
 * Author: Kuzan
 * Date: 2017/8/30 16:16.
 */
public class ApplyAnchorActivity extends SwipeBaseActivity<IApplyAnchorView, ApplyAnchorPresenterImpl> implements IApplyAnchorView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_real_name_tag)
    TextView mTvRealNameTag;
    @Bind(R.id.et_real_name)
    EditText mEtRealName;
    @Bind(R.id.tv_nickname_tag)
    TextView mTvNicknameTag;
    @Bind(R.id.et_nickname)
    EditText mEtNickname;
    @Bind(R.id.tv_sex_tag)
    TextView mTvSexTag;
    @Bind(R.id.btn_man)
    RadioButton mBtnMan;
    @Bind(R.id.btn_lady)
    RadioButton mBtnLady;
    @Bind(R.id.rg_sex)
    RadioGroup mRgSex;
    @Bind(R.id.tv_contact_way_tag)
    TextView mTvContactWayTag;
    @Bind(R.id.et_contact_way)
    EditText mEtContactWay;
    @Bind(R.id.btn_get_code)
    TextView mBtnGetCode;
    @Bind(R.id.tv_verification_code_tag)
    TextView mTvVerificationCodeTag;
    @Bind(R.id.et_verification_code)
    EditText mEtVerificationCode;
    @Bind(R.id.tv_set_password_tag)
    TextView mTvSetPasswordTag;
    @Bind(R.id.et_set_password)
    EditText mEtSetPassword;
    @Bind(R.id.btn_clause)
    LineTextView mBtnClause;
    @Bind(R.id.img_header)
    RatioImageView mImgHeader;

    private final int REQUEST_CODE = 0x011;
    private SelectPicturePop mPicturePop;

    @Override
    protected int initLayout() {
        return R.layout.activity_apply_anchor;
    }

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected ApplyAnchorPresenterImpl initPresenter() {
        return new ApplyAnchorPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        initTitleBar();

        initTagWidth();
    }

    private void initTitleBar() {
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_right) {
                    onSubmitClick();
                } else {
                    finish();
                }
            }
        });
    }

    /**
     * 重置标签长度
     * */
    private void initTagWidth() {
        mTvSetPasswordTag.post(new Runnable() {
            @Override
            public void run() {
                int width = mTvSetPasswordTag.getWidth();
                resetWidth(mTvRealNameTag, width);
                resetWidth(mTvNicknameTag, width);
                resetWidth(mTvSexTag, width);
                resetWidth(mTvContactWayTag, width);
                resetWidth(mTvVerificationCodeTag, width);
            }
        });
    }

    private void resetWidth(View view, int width) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        view.setLayoutParams(lp);
    }

    private void initSelectPicturePop() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int screenW = dm.widthPixels * 9 / 10;   // 获取分辨率宽度

        mPicturePop = new SelectPicturePop(this);
        mPicturePop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                if (view.getId() == R.id.btn_take_a_picture) {
                    ImageSelectUtil.startCamena(ApplyAnchorActivity.this, REQUEST_CODE, screenW, screenW);
                } else if (view.getId() == R.id.btn_album) {
                    ImageSelectUtil.openPhoto(ApplyAnchorActivity.this, REQUEST_CODE, screenW, screenW);
                }
            }
        });
    }

    @OnClick({R.id.btn_get_code, R.id.img_header})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onGetCodeClick() {
        ToastUtils.ToastMessage(ApplyAnchorActivity.this, "获取验证码");
    }

    @Override
    public void onUploadHeaderClick() {
        if (mPicturePop == null) {
            initSelectPicturePop();
        }
        mPicturePop.showAtLocation(mImgHeader, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onSubmitClick() {
        ToastUtils.ToastMessage(ApplyAnchorActivity.this, "提交");
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == REQUEST_CODE) {
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtil.SELECT_RESULT);
                if (images != null && !images.isEmpty()) {
                    if (isRunning && mImgHeader != null) {
                        GlideUtil.loadImage(mContext, mImgHeader,
                                images.get(0), R.drawable.icon_user_head_default, 0, 0, true, false);
                    }
                    //mPresenter.uploadFile(images.get(0));
                } else {
                    ToastUtils.ToastMessage(mContext, R.string.toast_get_picture_failure);
                }
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ApplyAnchorActivity.class));
    }
}
