package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.SystemMessageAdapter;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：系统消息弹窗
 * Author: Kuzan
 * Date: 2017/9/11 10:02.
 */
public class SystemMessagePop extends BasePopupWindow {

    @Bind(R.id.list_message)
    PullRefreshRecyclerView mListMessage;

    private SystemMessageAdapter mAdapter;

    public SystemMessagePop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_system_message;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void initAfterViews() {
        PullRefreshUtil.setRefresh(mListMessage, false, true);
        mListMessage.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mListMessage.refreshFinish();
            }
        });

        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SystemMessageAdapter(mContext);
        mListMessage.setAdapter(mAdapter);
    }

    @Override
    protected void init() {
        super.init();
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int height = (int) mContext.getResources().getDimension(R.dimen.margin_50);
        setHeight(height);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    @OnClick(R.id.btn_back)
    void onBackClick() {
        dismiss();
    }
}
