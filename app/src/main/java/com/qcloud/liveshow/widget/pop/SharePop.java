package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.utils.ShareUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.base.BasePopupWindow;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：分享
 * Author: Kuzan
 * Date: 2017/8/29 17:41.
 */
public class SharePop extends BasePopupWindow {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.btn_cancel)
    TextView mBtnCancel;
    private final String roomId;
    private final ShareUtil shareUtil;
    private String idAccount;
    private String imageUrl;
    public SharePop(Context context,String roomId) {
        super(context);
        this.roomId=roomId;
        if (UserInfoUtil.mUser!=null){
            idAccount =UserInfoUtil.mUser.getIdAccount();
            imageUrl =UserInfoUtil.mUser.getHeadImg();
        }
        shareUtil = new ShareUtil(mActivity);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_share;
    }

    @Override
    protected int getAnimId() {
        return R.layout.pop_fans_info;
    }

    @Override
    protected void initAfterViews() {

    }

    @OnClick(R.id.btn_share_wechat)
    void setBtnShareWechat() {
        shareUtil.shareWeb(SHARE_MEDIA.WEIXIN, UrlConstants.SHARP_LIVR_URL +"?idAccount="+ idAccount+"&roomId="+roomId,
                "http://store.happytify.cc/uploads/20170928/85/854533C5512Ew600h624.jpeg",
                "快来看我直播吧", "直播吃蕉.....");
    }

    @OnClick(R.id.btn_share_wechat_circle)
    void setBtnShareWechatCircle() {
        shareUtil.shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE, UrlConstants.SHARP_LIVR_URL +"?idAccount="+ idAccount+"&roomId="+roomId,
                "http://store.happytify.cc/uploads/20170928/85/854533C5512Ew600h624.jpeg",
                "快来看我直播吧", "直播吃蕉.....");
    }

    @OnClick(R.id.btn_facebook)
    void setBtnShareFacebook() {
        shareUtil.shareWeb(SHARE_MEDIA.FACEBOOK, UrlConstants.SHARP_LIVR_URL +"?idAccount="+ idAccount+"&roomId="+roomId,
                "http://store.happytify.cc/uploads/20170928/85/854533C5512Ew600h624.jpeg",
                "快来看我直播吧", "直播吃蕉.....");
    }

    @Override
    protected void init() {
        super.init();
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        manager.getDefaultDisplay().getSize(size);
        int w = size.x;
        int h = size.y;
        if (w > h) {
            w = h;
        }
        setWidth(w * 9 / 10);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        dismiss();
    }
}
