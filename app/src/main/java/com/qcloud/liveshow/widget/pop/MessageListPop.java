package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.PopMessageAdapter;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：消息列表弹窗
 * Author: Kuzan
 * Date: 2017/8/29 15:36.
 */
public class MessageListPop extends BasePopupWindow {
    @Bind(R.id.list_message)
    PullRefreshRecyclerView mListMessage;

    private PopMessageAdapter mAdapter;

    public MessageListPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_message_list;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void initAfterViews() {
        PullRefreshUtil.setRefresh(mListMessage, false, false);
        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PopMessageAdapter(mContext);
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

    @OnClick(R.id.btn_ignore_message)
    void onIgnoreClick(View view) {

    }
}
