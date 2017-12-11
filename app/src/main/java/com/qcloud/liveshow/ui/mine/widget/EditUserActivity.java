package com.qcloud.liveshow.ui.mine.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.ui.mine.presenter.impl.EditUserPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IEditUserView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.pop.SelectPicturePop;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.beans.UploadFileBean;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.imageselect.utils.ImageSelectUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * 类说明：编辑用户信息
 * Author: Kuzan
 * Date: 2017/8/17 19:03.
 */
public class EditUserActivity extends BaseActivity<IEditUserView, EditUserPresenterImpl> implements IEditUserView {
    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.img_user_head)
    RatioImageView mImgUserHead;
    @Bind(R.id.tv_user_id)
    TextView mTvUserId;
    @Bind(R.id.btn_man)
    RadioButton mBtnMan;
    @Bind(R.id.btn_lady)
    RadioButton mBtnLady;
    @Bind(R.id.rg_sex)
    RadioGroup mRgSex;
    @Bind(R.id.et_nickname)
    EditText mEtNickname;
    @Bind(R.id.et_signature)
    EditText mEtSignature;

    private UserBean mUser;
    private boolean isEdit = false;

    /**调用摄像头回调*/
    private final int REQUEST_CODE = 0x002;
    private SelectPicturePop mPicturePop;

    private String mUserImg;
    private String mNickname;
    private String mSignature;
    private int mSex;

    @Override
    protected int initLayout() {
        return R.layout.activity_edit_user;
    }

    @Override
    protected EditUserPresenterImpl initPresenter() {
        return new EditUserPresenterImpl();
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
    protected void initViewAndData() {
        initTitleBar();

        initRxBusEvent();
        if (UserInfoUtil.mUser == null) {
            startLoadingDialog();
            UserInfoUtil.loadUserInfo();
        } else {
            mUser = UserInfoUtil.mUser;
            refreshUserInfo(mUser);
        }

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

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                stopLoadingDialog();
                if (rxBusEvent.getType() == R.id.get_user_info_success) {
                    mUser = (UserBean) rxBusEvent.getObj();
                    refreshUserInfo(mUser);
                }
            }
        }));
    }

    private void initTitleBar() {
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_right) {
                    assignment();
                    mPresenter.edit(mUserImg, mNickname, mSex, mSignature);
                } else {
                    if (!isEdit) {
                        showExitPop();
                    } else {
                        finish();
                    }
                }
            }
        });
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
                    ImageSelectUtil.startCamera(EditUserActivity.this, REQUEST_CODE, screenW, screenW);
                } else if (view.getId() == R.id.btn_album) {
                    ImageSelectUtil.openPhoto(EditUserActivity.this, REQUEST_CODE, screenW, screenW);
                }
            }
        });
    }

    private void showExitPop() {
        final TipsPop pop = new TipsPop(this);
        pop.setTips(R.string.toast_give_up_to_edit);
        pop.showAtLocation(mImgUserHead, Gravity.CENTER, 0, 0);
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
     * 赋值
     * */
    private void assignment() {
        if (mEtNickname != null) {
            mNickname = mEtNickname.getText().toString().trim();
        }
        if (mEtSignature != null) {
            mSignature = mEtSignature.getText().toString().trim();
        }
    }

    @OnClick({R.id.img_user_head})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onUserHeadClick() {
        if (mPicturePop == null) {
            initSelectPicturePop();
        }
        mPicturePop.showAtLocation(mImgUserHead, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void refreshUserInfo(UserBean bean) {
        if (isRunning && bean != null) {
            GlideUtil.loadCircleImage(mContext, mImgUserHead, bean.getHeadImg(), R.drawable.bitmap_user_head,
                    0, 0, true, false);
            mTvUserId.setText(bean.getIdAccount());

            mSex = bean.getSex();
            mNickname = bean.getNickName();
            mSignature = bean.getSignature();

            if (mSex == 0) {
                mRgSex.check(mBtnMan.getId());
            } else {
                mRgSex.check(mBtnLady.getId());
            }
            mEtNickname.setText(mNickname);
            mEtSignature.setText(mSignature);
        }
    }

    @Override
    public void uploadSuccess(UploadFileBean bean) {
        if (isRunning && bean != null) {
            mUserImg = bean.getFileId();
        }
    }

    @Override
    public void editSuccess() {
        if (isRunning) {
            ToastUtils.ToastMessage(this, R.string.toast_edit_success);
            UserInfoUtil.loadUserInfo();
            isEdit = true;
            finish();
        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_CODE) {
                ArrayList<String> images = data.getStringArrayListExtra(ImageSelectUtil.SELECT_RESULT);
                if (images != null && !images.isEmpty()) {
                    Timber.e("images:"+ images.toString());
                    if (isRunning) {
                        if (mImgUserHead != null) {
                            GlideUtil.loadCircleImage(EditUserActivity.this, mImgUserHead,
                                    images.get(0), R.drawable.bitmap_user_head, 0, 0, true, false);
                        }
                        mPresenter.uploadHeadImg(images.get(0));
                    }
                } else {
                    ToastUtils.ToastMessage(mContext, R.string.toast_get_picture_failure);
                }
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, EditUserActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPicturePop != null && mPicturePop.isShowing()) {
            mPicturePop.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!isEdit) {
                showExitPop();
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
