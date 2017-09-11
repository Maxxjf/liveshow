package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.SystemMessageAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.home.presenter.impl.SystemMessagePresenterImpl;
import com.qcloud.liveshow.ui.home.view.ISystemMessageView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：系统消息
 * Author: Kuzan
 * Date: 2017/9/11 12:05.
 */
public class SystemMessageActivity extends SwipeBaseActivity<ISystemMessageView, SystemMessagePresenterImpl> implements ISystemMessageView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_message)
    PullRefreshRecyclerView mListMessage;

    private SystemMessageAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_system_message;
    }

    @Override
    protected SystemMessagePresenterImpl initPresenter() {
        return new SystemMessagePresenterImpl();
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
        initRefreshList();
    }

    private void initRefreshList() {
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
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, SystemMessageActivity.class));
    }
}
