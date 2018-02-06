package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.MessageListAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.ui.home.presenter.impl.MessageListPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IMessageListView;
import com.qcloud.liveshow.widget.customview.NoMessageView;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.rxbus.BusProvider;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.NetUtils;

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

    private NoMessageView mEmptyView;
    private List<MemberBean> beans;
    private boolean isSameMember;//加入列表时判断是否有同个成员
    private TipsPop pop;//是否删除消息的提示框
    private MemberBean mCurrentBean;//点击的那个Bean

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

        loadData();
    }

    private void initListView() {
        PullRefreshUtil.setRefresh(mListMessage, true, false);

        mListMessage.setLayoutManager(new LinearLayoutManager(this));
        mListMessage.setOnPullDownRefreshListener(() -> loadData());

        mAdapter = new MessageListAdapter(this);
        mListMessage.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((parent, view, position, id) -> {
            mCurrentBean = mAdapter.getList().get(position);
            mCurrentBean.setRead(true);
            mAdapter.notifyDataSetChanged();
            mPresenter.flagIsRead(mCurrentBean);
            FansMessageActivity.openActivity(MessageListActivity.this, mCurrentBean);
        });
        mAdapter.setOnHolderClick((view, memberBean, position) -> {

        });
        mAdapter.setOnItemLongClickListener((parent, view, position, id) -> {
            mCurrentBean=mAdapter.getList().get(position);
            showTipsPop();
            return false;
        });
        mEmptyView = new NoMessageView(this);
        mListMessage.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
        mEmptyView.setOnRefreshListener(() -> loadData());
    }

    private void loadData() {
        mPresenter.getAllList();
    }
    /**
     * 是否删除的弹框
     */
    private void showTipsPop() {
        if (pop==null){
            pop = new TipsPop(this);
            pop.setTips(R.string.toast_delete_message);
        }
        pop.showAtLocation(mListMessage, Gravity.CENTER, 0, 0);
        pop.setOnHolderClick(view -> {
            switch (view.getId()){
                case R.id.btn_ok:
                    mPresenter.deleteMessage(mCurrentBean);
                    mAdapter.remove(mCurrentBean);
                    break;
                case R.id.btn_cancel:
                    pop.dismiss();
                    break;
            }
        });
    }
    @Override
    public void replaceList(List<MemberBean> beans) {
        if (isRunning) {
            this.beans = beans;
            if (mListMessage != null) {
                mListMessage.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                hideEmptyView();
            } else {
                showEmptyView(getResources().getString(R.string.tip_no_data));
            }
        }
    }

    @Override
    public void addMessage(MemberBean bean) {
        if (isRunning) {
            if (mListMessage != null) {
                mListMessage.refreshFinish();
            }
            if (bean != null &beans!=null) {
                isSameMember = false;
                for (MemberBean member : beans) {//查找列表数据，看看有没有相同的。
                    if (member.getId() == bean.getId()) {
                        isSameMember = true;
                    }
                }
            } else {
                return;
            }
            if (!isSameMember) {
                beans.add(bean);
                if (mAdapter != null) {
                    mAdapter.addListBeanAtStart(bean);
                }
            }
            hideEmptyView();
        }
    }

    @Override
    public void showEmptyView(String tip) {
        if (mListMessage != null) {
            if (!NetUtils.isConnected(this)){
                mEmptyView.noNetWork();
            }
            mListMessage.showEmptyView();
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListMessage != null) {
            mListMessage.hideEmptyView();
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (mListMessage != null) {
                mListMessage.refreshFinish();
            }
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
        BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.check_no_read_chat).build());
        mPresenter.onDestroy();
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, MessageListActivity.class));
    }
}
