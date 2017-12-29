package com.qcloud.liveshow.utils;

import android.app.Activity;

import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.dialog.LoadingDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * 类说明：分享工具类
 * Author: iceberg
 * Date: 2017/11/30.
 */
public class ShareUtil {
    Activity mContext;
    LoadingDialog loading;
    public ShareUtil(Activity context) {
        this.mContext = context;
        if (loading==null){
            loading=new LoadingDialog(mContext);
        }
    }

    /**
     * 内存泄露处理方法
     * 在使用分享或者授权的Activity中，写在onDestory()方法：
     */
    public void detach() {
        UMShareAPI.get(mContext).release();
    }

    /**
     * 分享文字
     */
    public void shareText(SHARE_MEDIA sharePlatfrom, String msg) {
        loading.show();
        new ShareAction(mContext)
                .setPlatform(sharePlatfrom)//传入平台
                .withText(msg)
                .setCallback(shareListener)
                .share();
    }


    /**
     * 分享图片
     */
    public void shareImg(SHARE_MEDIA sharePlatfrom, String imgUrl) {
        loading.show();
        UMImage image = new UMImage(mContext, imgUrl);//网络图片
        new ShareAction(mContext)
                .setPlatform(sharePlatfrom)//传入平台
                .withMedia(image)
                .setCallback(shareListener)
                .share();
    }

    /**
     * 分享网页
     */
    public void shareWeb(SHARE_MEDIA sharePlatfrom, String webUrl, String imageUrl, String title, String descrption) {
        loading.show();
        UMImage image = new UMImage(mContext, imageUrl);//网络图片
        UMWeb web = new UMWeb(webUrl);
        web.setTitle(title);//标题
        web.setThumb(image);  //图片
        web.setDescription(descrption);//描述
        new ShareAction(mContext)
                .setPlatform(sharePlatfrom)//传入平台
                .withMedia(web)
                .setCallback(shareListener)//回调监听器
                .share();
    }

    /**
     * 分享的监听
     */

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            loading.dismiss();
            ToastUtils.ToastMessage(mContext, "分享成功");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            loading.dismiss();
            ToastUtils.ToastMessage(mContext, "失败" + t.getMessage());
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override///
        public void onCancel(SHARE_MEDIA platform) {
            loading.dismiss();
            ToastUtils.ToastMessage(mContext, "取消了");

        }
    };

}

