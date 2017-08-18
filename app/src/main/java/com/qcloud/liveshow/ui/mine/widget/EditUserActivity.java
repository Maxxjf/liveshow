package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.EditUserPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IEditUserView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

import butterknife.Bind;

/**
 * 类说明：编辑用户信息
 * Author: Kuzan
 * Date: 2017/8/17 19:03.
 */
public class EditUserActivity extends SwipeBaseActivity<IEditUserView, EditUserPresenterImpl> implements IEditUserView {
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

    }

    private void initTitleBar() {
        mTitleBar.setOnBtnListener(new TitleBar.OnBtnListener() {
            @Override
            public void onBtnClick(View view) {
                if (view.getId() == R.id.btn_right) {
                    ToastUtils.ToastMessage(EditUserActivity.this, "保存");
                } else {
                    finish();
                }
            }
        });
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, EditUserActivity.class));
    }
}
