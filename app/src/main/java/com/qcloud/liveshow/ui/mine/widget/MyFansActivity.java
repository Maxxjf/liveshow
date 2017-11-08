package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.MyFansAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.home.widget.FansMessageActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.MyFansPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMyFansView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.customview.NoFollowView;
import com.qcloud.liveshow.widget.pop.FansInfoPop;
import com.qcloud.liveshow.widget.pop.FansManagerPop;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;

import java.util.List;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：我的关注/粉丝/黑名单列表
 * Author: Kuzan
 * Date: 2017/8/18 11:18.
 */
public class MyFansActivity extends SwipeBaseActivity<IMyFansView, MyFansPresenterImpl> implements IMyFansView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_my_fans)
    PullRefreshRecyclerView mListMyFans;

    private MyFansAdapter mAdapter;

    private NoFollowView mEmptyView;

    private int type = StartFansEnum.MyFollow.getKey();

    private int pageNum = 1;
    private MemberBean currBean;
    private int currPosition = -1;

    /**
     * 粉丝信息弹窗
     */
    private FansInfoPop mFansPop;
    /**粉丝管理弹窗*/
    private FansManagerPop mManagerPop;

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
        type = getIntent().getIntExtra("TYPE", StartFansEnum.MyFollow.getKey());
        mTitleBar.setTitle(StartFansEnum.valueOf(type).getValue());

        initListLayout();

        loadData();
        startLoadingDialog();
    }

    private void loadData() {
        mPresenter.getMyAttention(type, pageNum, AppConstants.PAGE_SIZE);
    }

    private void initListLayout() {
        PullRefreshUtil.setRefresh(mListMyFans, true, true);
        mListMyFans.setOnPullDownRefreshListener(new PullRefreshView.OnPullDownRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                mListMyFans.isMore(true);
                loadData();
            }
        });
        mListMyFans.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum++;
                loadData();
            }
        });

        mListMyFans.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new MyFansAdapter(this, type);
        mListMyFans.setAdapter(mAdapter);
        mAdapter.setOnHolderClick(new CommonRecyclerAdapter.ViewHolderClick<MemberBean>() {
            @Override
            public void onViewClick(View view, MemberBean memberBean, int position) {
                currBean = memberBean;
                currPosition = position;
                mPresenter.submitAttention(type, memberBean.getId(), memberBean.isAttention());
            }
        });
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currBean = mAdapter.getList().get(position);
                currPosition = position;
                Timber.d("currBean:"+currBean);
                FansInfoActivity.openActivity(mContext,currBean);
//                if (mFansPop == null) {
//                    initFansPop();
//                }
//                mFansPop.setBean(currBean);
//                mFansPop.showAtLocation(mListMyFans, Gravity.BOTTOM, 0, 0);
            }
        });
        mEmptyView = new NoFollowView(this);
        mListMyFans.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void replaceList(List<MemberBean> beans, boolean isNext) {
        if (isRunning) {
            stopLoadingDialog();
            if (mListMyFans != null) {
                mListMyFans.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
                if (mListMyFans != null) {
                    mListMyFans.isMore(isNext);
                }
                hideEmptyView();
            } else {
                showEmptyView();
            }
        }
    }

    /**
     * 初始化粉丝信息弹窗
     */
    private void initFansPop() {
        mFansPop = new FansInfoPop(mContext);
        mFansPop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_manager:
                        if (mManagerPop == null) {
                            initFansManagerPop();
                        }
                        mManagerPop.showAtLocation(mListMyFans, Gravity.BOTTOM, 0, 0);
                        break;
                    case R.id.btn_letter:
                        FansMessageActivity.openActivity(mContext);
                        break;
                    case R.id.btn_follow:
                        currBean.refreshAttention();
                        mPresenter.submitAttention(type, currBean.getId(), currBean.isAttention());
                        break;
                }
            }
        });
        mFansPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }
    /**
     * 初始化消息列表弹窗
     * */
    private void initFansManagerPop() {
        mManagerPop = new FansManagerPop(mContext);
        mManagerPop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_set_guarder:

                        break;
                    case R.id.btn_my_guarder_list:

                        break;
                    case R.id.btn_gag:

                        break;
                    case R.id.btn_add_blacklist:
                        mPresenter.submitAttention(StartFansEnum.Blacklist.getKey(),currBean.getId(),true);
                        break;
                }
            }
        });
        mManagerPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }
    @Override
    public void addListAtEnd(List<MemberBean> beans, boolean isNext) {
        if (isRunning) {
            if (mListMyFans != null) {
                mListMyFans.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.addListAtEnd(beans);
                }
                if (mListMyFans != null) {
                    mListMyFans.isMore(isNext);
                }
            } else {
                ToastUtils.ToastMessage(this, R.string.toast_no_more_data);
                if (mListMyFans != null) {
                    mListMyFans.isMore(false);
                }
            }
        }
    }

    @Override
    public void isAttentionSuccess() {
        if (isRunning) {
            UserInfoUtil.loadUserInfo();
            if (mAdapter != null) {
                mAdapter.refreshFollow(currPosition, currBean);
            }
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListMyFans != null) {
            mListMyFans.hideEmptyView();
        }
    }

    @Override
    public void showEmptyView() {
        if (mListMyFans != null) {
            mListMyFans.showEmptyView();
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            stopLoadingDialog();
            if (mListMyFans != null) {
                mListMyFans.refreshFinish();
            }
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
