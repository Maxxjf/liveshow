package com.qcloud.liveshow.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.qcloud.liveshow.BuildConfig;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.account.widget.LoginActivity;
import com.qcloud.liveshow.utils.FileLoggingTree;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.AppManager;
import com.qcloud.qclib.FrameConfig;
import com.qcloud.qclib.utils.ConstantUtil;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.utils.TokenUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * 类说明：BaseApplication
 * Author: Kuzan
 * Date: 2017/8/1 13:51.
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static AppManager mAppManager; // Activity 管理器

    @Override
    public void onCreate() {
        super.onCreate();

        FrameConfig.initSystemConfig(this, R.raw.config);

        mApplication = this;
        mAppManager = AppManager.getInstance();

        initOkGo();

        // 初始化缓存
        ConstantUtil.initSharedPreferences(mApplication);

        // 腾讯Bugly crash异常捕捉
        CrashReport.initCrashReport(getApplicationContext(), "d05326ee5c", false);

        // 在这里先使用Timber.plant注册一个Tree，然后调用静态的.d .v 去使用
        // Timber.d("test Timber %d",10);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new FileLoggingTree());
        }

        // 友盟分享
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        PlatformConfig.setWeixin(AppConstants.WX_APP_ID, AppConstants.WX_APP_SECRET);
    }

    /**
     * 初始化网络请求
     * */
    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        builder.hostnameVerifier(new SafeHostnameVerifier());

        // 其他统一的配置
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(2);                              //全局统一超时重连次数，不需要可以设置为0
    }

    private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            //验证主机名是否匹配
            return true;
        }
    }

    public static BaseApplication getInstance() {
        return mApplication;
    }

    public AppManager getAppManager() {
        return mAppManager;
    }

    /**
     * 判断是否已登录
     *
     * @return
     */
    public static boolean isLogin() {
        return StringUtils.isNotEmptyString(TokenUtil.getToken());
    }

    /**
     * 登录验证
     * 未登录跳转登录页面，已登录则往后执行
     *
     * @return true：已登录，往下执行，false：未登录，跳转登录页面
     */
    public static boolean loginAuth() {
        if (!isLogin() || UserInfoUtil.mUser == null) {
            LoginActivity.openActivity(mAppManager.getTopActivity());
            return false;
        }
        return true;
    }
}
