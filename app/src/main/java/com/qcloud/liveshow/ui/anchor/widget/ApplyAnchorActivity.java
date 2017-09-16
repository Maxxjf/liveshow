package com.qcloud.liveshow.ui.anchor.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.beans.SubmitApplyBean;
import com.qcloud.liveshow.enums.ClauseRuleEnum;
import com.qcloud.liveshow.ui.anchor.presenter.impl.ApplyAnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IApplyAnchorView;
import com.qcloud.liveshow.ui.main.widget.WebActivity;
import com.qcloud.liveshow.widget.pop.SelectPicturePop;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.beans.UploadFileBean;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.ValidateUtil;
import com.qcloud.qclib.widget.customview.LineTextView;
import com.qcloud.qclib.widget.customview.RatioImageView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 类说明：申请主播
 * Author: Kuzan
 * Date: 2017/8/30 16:16.
 */
public class ApplyAnchorActivity extends BaseActivity<IApplyAnchorView, ApplyAnchorPresenterImpl> implements IApplyAnchorView {

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

    @BindString(R.string.toast_has_been_sent_to)
    String hasBeenSendTo;
    @BindString(R.string.btn_get_code)
    String getCode;
    @BindString(R.string.btn_get_code_after_second)
    String getCodeAfter;

    private Disposable mDisposable;

    private final int REQUEST_CODE = 0x011;
    private SelectPicturePop mPicturePop;

    private String mRealName;
    private String mNickname;
    private int mSex = 0;
    private String mContactWay;
    private String mCode;
    private String mPassword;
    private String mImage;
    private SubmitApplyBean mApplyBean;

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

        mRgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if (i == mBtnMan.getId()) {
                    mSex = 0;
                } else {
                    mSex = 1;
                }
            }
        });
    }

    private void initTitleBar() {
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_right) {
                    onSubmitClick();
                } else {
                    showExitPop();
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
                    ImageSelectUtil.startCamera(ApplyAnchorActivity.this, REQUEST_CODE, screenW, screenW);
                } else if (view.getId() == R.id.btn_album) {
                    ImageSelectUtil.openPhoto(ApplyAnchorActivity.this, REQUEST_CODE, screenW, screenW);
                }
            }
        });
    }

    private void showExitPop() {
        final TipsPop pop = new TipsPop(this);
        pop.setTips(R.string.toast_give_up_to_apply);
        pop.showAtLocation(mImgHeader, Gravity.CENTER, 0, 0);
        pop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                if (view.getId() == R.id.btn_ok) {
                    finish();
                } else {
                    pop.dismiss();
                }
            }
        });
    }

    /**
     * 启动定时器
     * */
    private void startTimer() {
        Observable observable = Observable.interval(1, TimeUnit.SECONDS).take(60).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        mDisposable = observable.doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(getCode);
                    mBtnGetCode.setEnabled(true);
                }
            }
        }).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(String.format(getCodeAfter, (60 - aLong)));
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(getCode);
                    mBtnGetCode.setEnabled(true);
                }
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Timber.e("onComplete");
                if (mBtnGetCode != null) {
                    mBtnGetCode.setText(getCode);
                    mBtnGetCode.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.btn_get_code, R.id.img_header, R.id.btn_clause})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onGetCodeClick() {
        if (checkContactWay()) {
            mPresenter.getCode(mContactWay);
            mBtnGetCode.setEnabled(false);
            startTimer();
        }
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
        if (check()) {
            mPresenter.submitApply(mApplyBean);
        }
    }

    @Override
    public void onClauseClick() {
        WebActivity.openActivity(this, "免责条款", ClauseRuleEnum.AnchorRule.getKey());
    }

    @Override
    public void getCodeSuccess(String code) {
        if (isRunning) {
            //ToastUtils.ToastMessage(this, String.format(hasBeenSendTo, mContactWay));
            TipsPop pop = new TipsPop(this);
            pop.setTips("验证码为" + code);
            pop.showCancel(false);
            pop.showAtLocation(mBtnGetCode, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void getCodeFailure(String errMsg) {
        if (isRunning) {
            ToastUtils.ToastMessage(mContext, errMsg);
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            if (mBtnGetCode != null) {
                mBtnGetCode.setText(getCode);
                mBtnGetCode.setEnabled(true);
            }
        }
    }

    @Override
    public void uploadSuccess(UploadFileBean bean) {
        if (isRunning && bean != null) {
            mImage = bean.getFileId();
            stopLoadingDialog();
        }
    }

    @Override
    public void submitSuccess() {
        if (isRunning) {
            ToastUtils.ToastMessage(this, R.string.toast_apply_success_submit);
            finish();
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            stopLoadingDialog();
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    private boolean check() {
        mRealName = mEtRealName.getText().toString().trim();
        mNickname = mEtNickname.getText().toString().trim();
        mCode = mEtVerificationCode.getText().toString().trim();
        mPassword = mEtSetPassword.getText().toString().trim();

        if (StringUtils.isEmptyString(mRealName)) {
            ToastUtils.ToastMessage(this, R.string.input_real_name_hint);
            mEtRealName.requestFocus();
            return false;
        }

        if (StringUtils.isEmptyString(mNickname)) {
            ToastUtils.ToastMessage(this, R.string.input_nickname_hint);
            mEtNickname.requestFocus();
            return false;
        }

        if (!checkContactWay()) {
            return false;
        }

        if (StringUtils.isEmptyString(mCode)) {
            ToastUtils.ToastMessage(this, R.string.toast_input_code);
            mEtVerificationCode.requestFocus();
            return false;
        }

        if (StringUtils.isEmptyString(mPassword)) {
            ToastUtils.ToastMessage(this, R.string.input_six_number_hint);
            mEtSetPassword.requestFocus();
            return false;
        }

        if (!ValidateUtil.isNumPasswordAndSix(mPassword)) {
            ToastUtils.ToastMessage(this, R.string.input_six_number_hint);
            mEtSetPassword.requestFocus();
            return false;
        }

        if (StringUtils.isEmptyString(mImage)) {
            ToastUtils.ToastMessage(this, R.string.toast_upload_head_img);
            return false;
        }

        mApplyBean = new SubmitApplyBean();
        mApplyBean.setName(mRealName);
        mApplyBean.setNickName(mNickname);
        mApplyBean.setSex(mSex);
        mApplyBean.setPhone(mContactWay);
        mApplyBean.setCode(mCode);
        mApplyBean.setWithdrawPassword(mPassword);
        mApplyBean.setHeadImg(mImage);

        return true;
    }

    private boolean checkContactWay() {
        mContactWay = mEtContactWay.getText().toString().trim();

        if (StringUtils.isEmptyString(mContactWay)) {
            ToastUtils.ToastMessage(this, R.string.input_contact_way_hint);
            mEtContactWay.requestFocus();
            return false;
        }

        if (!ValidateUtil.isMobilePhone(mContactWay)) {
            ToastUtils.ToastMessage(this, R.string.toast_right_mobile_phone);
            mEtContactWay.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == REQUEST_CODE) {
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtil.SELECT_RESULT);
                if (images != null && !images.isEmpty()) {
                    if (isRunning) {
                        if (mImgHeader != null) {
                        GlideUtil.loadImage(mContext, mImgHeader,
                                images.get(0), R.drawable.bitmap_user_head, 0, 0, true, false);
                        }
                        mPresenter.uploadHeadImg(images.get(0));
                        startLoadingDialog();
                    }
                } else {
                    ToastUtils.ToastMessage(mContext, R.string.toast_get_picture_failure);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            showExitPop();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ApplyAnchorActivity.class));
    }
}
