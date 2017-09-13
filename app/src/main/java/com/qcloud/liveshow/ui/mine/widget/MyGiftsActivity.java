package com.qcloud.liveshow.ui.mine.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.MyGiftsAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.ui.mine.presenter.impl.MyGiftsPresenterImpl;
import com.qcloud.liveshow.ui.mine.view.IMyGiftsView;
import com.qcloud.liveshow.widget.customview.GiftPagerLayout;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.layoutManager.FullyLinearLayoutManager;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：我的礼物列表
 * Author: Kuzan
 * Date: 2017/8/31 11:17.
 */
public class MyGiftsActivity extends SwipeBaseActivity<IMyGiftsView, MyGiftsPresenterImpl> implements IMyGiftsView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.page_gift)
    GiftPagerLayout mPageGift;
    @Bind(R.id.img_gift_profit_title)
    ImageView mImgGiftProfitTitle;
    @Bind(R.id.tv_gift_profit)
    TextView mTvGiftProfit;
    @Bind(R.id.list_gift)
    RecyclerView mListGift;
    @Bind(R.id.refresh_view)
    PullRefreshView mRefreshView;

    private MyGiftsAdapter mAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_my_gifts;
    }

    @Override
    protected MyGiftsPresenterImpl initPresenter() {
        return new MyGiftsPresenterImpl();
    }

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.black);
    }

    @Override
    protected void initViewAndData() {
        initGiftPager();
        initRefreshLayout();
    }

    private void initGiftPager() {
        //测试的假数据
//        ArrayList<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//        list.add("4");
//        list.add("5");
//        list.add("6");
//        list.add("7");
//        list.add("8");
//        list.add("9");
//        list.add("10");
//        list.add("11");
//        list.add("12");
//        list.add("13");
//        list.add("14");
//        list.add("15");
//        list.add("16");
//        list.add("17");
//        list.add("18");

        mPageGift.setCountNum(4, 2);
        //mPageGift.setData(list);

//        mPageGift.setOnItemClickListener(new DiamondsPagerLayout.OnItemClickListener() {
//            @Override
//            public void onItemClick(Object o) {
//                ToastUtils.ToastMessage(mContext, ((PagerItemBean) o).getIndex() + " ");
//            }
//        });
    }

    private void initRefreshLayout() {
        PullRefreshUtil.setRefresh(mRefreshView, false, false);
        mListGift.setLayoutManager(new FullyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListGift.setNestedScrollingEnabled(false);

        mAdapter = new MyGiftsAdapter(this);
        mListGift.setAdapter(mAdapter);
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
        context.startActivity(new Intent(context, MyGiftsActivity.class));
    }
}
