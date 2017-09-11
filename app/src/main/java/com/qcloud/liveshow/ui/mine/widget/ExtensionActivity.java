package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.ExtensionPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IExtensionView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：邀请好友页面
 * Author: Kuzan
 * Date: 2017/9/11 16:14.
 */
public class ExtensionActivity extends SwipeBaseActivity<IExtensionView, ExtensionPresenterImpl> implements IExtensionView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.tv_experience_code)
    TextView mTvExperienceCode;
    @Bind(R.id.webView)
    BridgeWebView mWebView;
    @Bind(R.id.btn_we_chat)
    ImageView mBtnWeChat;
    @Bind(R.id.btn_wexin_circle)
    ImageView mBtnWexinCircle;
    @Bind(R.id.btn_facebook)
    ImageView mBtnFacebook;

    @Override
    protected int initLayout() {
        return R.layout.activity_extension;
    }

    @Override
    protected ExtensionPresenterImpl initPresenter() {
        return new ExtensionPresenterImpl();
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

    }

    @OnClick({R.id.btn_we_chat, R.id.btn_wexin_circle, R.id.btn_facebook})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onWeChatClick() {

    }

    @Override
    public void onWeiXinCircleClick() {

    }

    @Override
    public void onFacebookClick() {

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

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ExtensionActivity.class));
    }
}
