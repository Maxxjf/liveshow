package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.MessageListAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.NettyMemberBean;
import com.qcloud.liveshow.ui.home.presenter.impl.MessageListPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IMessageListView;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：消息列表
 * Author: Kuzan
 * Date: 2017/8/30 11:22.
 */
public class MessageListActivity extends SwipeBaseActivity<IMessageListView, MessageListPresenterImpl> implements IMessageListView {

    @Bind(R.id.list_message)
    PullRefreshRecyclerView mListMessage;

    private MessageListAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_message_list;
    }

    @Override
    protected MessageListPresenterImpl initPresenter() {
        return new MessageListPresenterImpl();
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
        initListView();
    }

    private void initListView() {
        PullRefreshUtil.setRefresh(mListMessage, true, true);

        mListMessage.setLayoutManager(new LinearLayoutManager(this));
        mListMessage.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                mListMessage.refreshFinish();
            }
        });
        mListMessage.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mListMessage.refreshFinish();
            }
        });

        mAdapter = new MessageListAdapter(this);
        mListMessage.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FansMessageActivity.openActivity(MessageListActivity.this);
            }
        });
        mPresenter.getChatList();
    }


    @Override
    public void replaceList(List<NettyMemberBean> beans) {
        if (isRunning) {
            if (beans != null && mAdapter != null) {
                mAdapter.replaceList(beans);
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestory();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, MessageListActivity.class));
    }
}
