package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.GuarderAdapter;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;

import butterknife.Bind;

/**
 * 类说明：守护者弹窗
 * Author: Kuzan
 * Date: 2017/9/11 14:58.
 */
public class GuarderPop extends BasePopupWindow {
    @Bind(R.id.tv_guarder_title)
    TextView mTvGuarderTitle;
    @Bind(R.id.tv_all_guarder_num)
    TextView mTvAllGuarderNum;
    @Bind(R.id.tv_curr_guarder_num)
    TextView mTvCurrGuarderNum;
    @Bind(R.id.list_guarder)
    RecyclerView mListGuarder;
    @Bind(R.id.refresh_view)
    PullRefreshView mRefreshView;

    private GuarderAdapter mAdapter;

    public GuarderPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_guarder;
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

        mListGuarder.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new GuarderAdapter(mContext);
        mListGuarder.setAdapter(mAdapter);
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

}
