package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.ProblemAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.ProblemBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.mine.presenter.impl.ProblemPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IProblemView;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：常见问题
 * Author: Kuzan
 * Date: 2017/9/9 17:59.
 */
public class ProblemActivity extends SwipeBaseActivity<IProblemView, ProblemPresenterImpl> implements IProblemView {

    @Bind(R.id.list_problem)
    PullRefreshRecyclerView mListProblem;

    private ProblemAdapter mAdapter;

    private int pageNum = 1;

    @Override
    protected int initLayout() {
        return R.layout.activity_problem_list;
    }

    @Override
    protected ProblemPresenterImpl initPresenter() {
        return new ProblemPresenterImpl();
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
        initRefreshView();

        startLoadingDialog();
        loadData();
    }

    private void loadData() {
        mPresenter.loadData(pageNum, AppConstants.PAGE_SIZE);
    }

    private void initRefreshView() {
        PullRefreshUtil.setRefresh(mListProblem, true, true);

        mListProblem.setLayoutManager(new LinearLayoutManager(this));
        mListProblem.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                mListProblem.isMore(true);
                loadData();
            }
        });
        mListProblem.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum++;
                loadData();
            }
        });

        mAdapter = new ProblemAdapter(this);
        mListProblem.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProblemBean bean = mAdapter.getList().get(i);
                PrombleDetailActivity.openActivity(mContext, bean.getQuestion(), bean.getAnswer());
            }
        });
    }

    @Override
    public void replaceList(List<ProblemBean> beans, boolean isNext) {
        if (isRunning) {
            stopLoadingDialog();
            if (mListProblem != null) {
                mListProblem.refreshFinish();
            }
            if (beans != null) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                if (mListProblem != null) {
                    mListProblem.isMore(isNext);
                }
            } else {
                if (mListProblem != null) {
                    mListProblem.isMore(false);
                }
            }
        }
    }

    @Override
    public void addListAtEnd(List<ProblemBean> beans, boolean isNext) {
        if (isRunning) {
            stopLoadingDialog();
            if (mListProblem != null) {
                mListProblem.refreshFinish();
            }
            if (beans != null) {
                if (mAdapter != null) {
                    mAdapter.addListAtEnd(beans);
                }
                if (mListProblem != null) {
                    mListProblem.isMore(isNext);
                }
            } else {
                if (mListProblem != null) {
                    mListProblem.isMore(false);
                }
            }
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            stopLoadingDialog();
            if (mListProblem != null) {
                mListProblem.refreshFinish();
            }
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, ProblemActivity.class));
    }
}
