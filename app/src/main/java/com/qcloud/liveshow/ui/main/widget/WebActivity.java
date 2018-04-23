package com.qcloud.liveshow.ui.main.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.main.presenter.impl.WebPresenterImpl;
import com.qcloud.liveshow.ui.main.view.IWebView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.FrameConfig;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.NetUtils;
import com.qcloud.qclib.utils.StringUtils;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：网页有关
 * Author: Kuzan
 * Date: 2017/8/8 18:51.
 */
public class WebActivity extends BaseActivity<IWebView, WebPresenterImpl> implements IWebView {
    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.progress_bridge)
    ProgressBar mProgressBridge;
    @Bind(R.id.webView)
    BridgeWebView mWebView;

    private int mType = 1;

    @Override
    protected int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected WebPresenterImpl initPresenter() {
        return new WebPresenterImpl();
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
        initWebView();

        mTitleBar.setTitle(getIntent().getStringExtra("TITLE"));

        String webUrl = getIntent().getStringExtra("WEB_URL");
        if (StringUtils.isNotEmptyString(webUrl)) {
            displayWeb(webUrl);
        } else {
            mType = getIntent().getIntExtra("TYPE", 1);
            mPresenter.getRuleWebUrl(mType);
        }
    }

    /**
     * 网页设置
     * */
    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        // 设置 缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);

        mWebView.setWebChromeClient(new MyWebChromeClient(mProgressBridge));
        //mWebView.setVisibility(View.GONE);

        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Timber.e(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebView.send("hello");
    }

    @Override
    public void displayWeb(String webUrl) {
        //服务器地址
        String address="";
        //检查服务器是否可用
        try {
            address= FrameConfig.server.split("/")[2];
            if (address.contains(":")){
                address=address.split(":")[0];
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        startLoadingDialog();
        NetUtils.isNetWorkAvailable(address, new Comparable<Boolean>() {
            @Override
            public int compareTo(@NonNull Boolean aBoolean) {
                stopLoadingDialog();
                if (aBoolean){
                    if (isRunning && mWebView != null && StringUtils.isNotEmptyString(webUrl)) {
                        Timber.e(webUrl);
                        mWebView.loadUrl(webUrl);
                    }
                }else {
                    ToastUtils.ToastMessage(WebActivity.this,"服务器出错，请重新试试");
                }
                return 0;
            }
        });

    }

    /**
     * 获取网页标题已经网页加载进度展示
     */
    class MyWebChromeClient extends WebChromeClient {
        private ProgressBar mProgress;

        private MyWebChromeClient(ProgressBar progressBar) {
            this.mProgress = progressBar;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgress.setVisibility(View.GONE);

                mWebView.setVisibility(View.VISIBLE);
            } else {
                if (mProgress.getVisibility() == View.GONE) {
                    mProgress.setVisibility(View.VISIBLE);
                }
                mProgress.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.clearCache(true);
            mWebView.clearHistory();
            mWebView.clearFormData();
            mWebView.destroy();
        }
        super.onDestroy();
    }

    public static void openActivity(Context context, String title, String webUrl) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("WEB_URL", webUrl);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, String title, int type) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
    }
}
