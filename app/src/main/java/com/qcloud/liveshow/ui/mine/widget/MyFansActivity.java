package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.MyFansAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.mine.presenter.impl.MyFansPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMyFansView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：我的关注列表
 * Author: Kuzan
 * Date: 2017/8/18 11:18.
 */
public class MyFansActivity extends SwipeBaseActivity<IMyFansView, MyFansPresenterImpl> implements IMyFansView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_my_fans)
    PullRefreshRecyclerView mListMyFans;

    private MyFansAdapter mAdapter;

    private int type = StartFansEnum.MyFollow.getKey();

    @Override
    protected int initLayout() {
        return R.layout.activity_my_fans;
    }

    @Override
    protected MyFansPresenterImpl initPresenter() {
        return new MyFansPresenterImpl();
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    @Override
    protected void initViewAndData() {
        //SystemBarUtil.remeasureTitleBar(this, mTitleBar, ContextCompat.getColor(this, R.color.white));

        type = getIntent().getIntExtra("TYPE", StartFansEnum.MyFollow.getKey());
        mTitleBar.setTitle(StartFansEnum.valueOf(type).getValue());

        initListLayout();
    }

    private void initListLayout() {
        PullRefreshUtil.setRefresh(mListMyFans, true, true);
        mAdapter = new MyFansAdapter(this);

        mListMyFans.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListMyFans.setAdapter(mAdapter);
        mListMyFans.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                mListMyFans.refreshFinish();
            }
        });
        mListMyFans.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mListMyFans.refreshFinish();
            }
        });
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

    public static void openActivity(Context context, int type) {
        Intent intent = new Intent(context, MyFansActivity.class);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
    }
}
