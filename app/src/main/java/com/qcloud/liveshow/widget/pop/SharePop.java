package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.ShareAdapter;
import com.qcloud.qclib.base.BasePopupWindow;

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
    @Bind(R.id.list_share)
    RecyclerView mListShare;
    @Bind(R.id.btn_cancel)
    TextView mBtnCancel;

    private ShareAdapter mAdapter;

    public SharePop(Context context) {
        super(context);
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
        mAdapter = new ShareAdapter(mContext);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mListShare.setLayoutManager(manager);
        mListShare.setAdapter(mAdapter);
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
