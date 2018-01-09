package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.ui.mine.presenter.impl.ExtensionPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IExtensionView;
import com.qcloud.liveshow.utils.ShareUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.toast.ToastUtils;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.BindString;
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
//    @Bind(R.id.webView)
//    BridgeWebView mWebView;
    @Bind(R.id.btn_we_chat)
    ImageView mBtnWeChat;
    @Bind(R.id.btn_wexin_circle)
    ImageView mBtnWexinCircle;
    @Bind(R.id.btn_facebook)
    ImageView mBtnFacebook;

    @BindString(R.string.tag_extension)
    String extension;

    private ShareUtil shareUtil;
    private UserBean user;
    private String experienceCode;

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
        shareUtil = new ShareUtil(this);
        user = UserInfoUtil.mUser;
        if (user!=null){
            extension=String.format(extension,user.getIdAccount());
            mTvExperienceCode.setText(extension);
            experienceCode=user.getIdAccount();
        }
        Spanned str = Html.fromHtml("      每邀请好友注册APP即可获得<font color='#FFDC35'>3个钻石币</font>，还在等什么？赶快行动！一起播！一起嗨~");
        ((TextView)findViewById(R.id.tv_description)).setText(str);
    }

    @OnClick({R.id.btn_we_chat, R.id.btn_wexin_circle, R.id.btn_facebook})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onWeChatClick() {
            shareUtil.shareWeb(SHARE_MEDIA.WEIXIN, UrlConstants.SHARP_Generalize_URL +"?idAccount="+experienceCode, "http://store.happytify.cc/uploads/20170928/85/854533C5512Ew600h624.jpeg",
                    "快来看我直播吧", "直播吃香蕉中.....");
    }

    @Override
    public void onWeiXinCircleClick() {
            shareUtil.shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE, UrlConstants.SHARP_Generalize_URL +"?idAccount="+experienceCode, "http://store.happytify.cc/uploads/20170928/85/854533C5512Ew600h624.jpeg",
                    "快来看我直播吧", "直播吃香蕉中.....");
    }

    @Override
    public void onFacebookClick() {
            shareUtil.shareWeb(SHARE_MEDIA.FACEBOOK, UrlConstants.SHARP_Generalize_URL +"?idAccount="+experienceCode, "http://store.happytify.cc/uploads/20170928/85/854533C5512Ew600h624.jpeg",
                    "快来看我直播吧", "直播吃香蕉中.....");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
    protected void onDestroy() {
        super.onDestroy();
        shareUtil.detach();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ExtensionActivity.class));
    }
}
