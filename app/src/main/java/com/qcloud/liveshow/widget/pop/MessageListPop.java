package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.PopMessageAdapter;
import com.qcloud.liveshow.beans.NettyMemberBean;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：消息列表弹窗
 * Author: Kuzan
 * Date: 2017/8/29 15:36.
 */
public class MessageListPop extends BasePopupWindow {
    @Bind(R.id.list_message)
    RecyclerView mListMessage;
    @Bind(R.id.refresh_view)
    PullRefreshView mRefreshView;

    private PopMessageAdapter mAdapter;

    private onPopItemClick mItemClick;

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
        PullRefreshUtil.setRefresh(mRefreshView, true, true);
        mRefreshView.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshView.refreshFinish();
            }
        });
        mRefreshView.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshView.refreshFinish();
            }
        });

        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PopMessageAdapter(mContext);
        mListMessage.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mItemClick != null) {
                    mItemClick.onItemClick(i, "哈哈");
                }
            }
        });
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

    public void replaceList(List<NettyMemberBean> beans) {
        if (beans != null && mAdapter != null) {
            mAdapter.replaceList(beans);
        }
    }

    @OnClick(R.id.btn_ignore_message)
    void onIgnoreClick(View view) {

    }

    /**
     * item点击事件抽象方法
     */
    public void setOnPopItemClick(onPopItemClick viewClick) {
        this.mItemClick = viewClick;
    }

    public interface onPopItemClick {
        void onItemClick(int position, String item);
    }
}
