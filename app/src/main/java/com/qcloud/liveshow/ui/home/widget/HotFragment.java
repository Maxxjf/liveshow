package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.HotAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.LiveShowBean;
import com.qcloud.liveshow.ui.home.presenter.impl.HotPresenterImpl;
import com.qcloud.liveshow.ui.home.view.IHotView;
import com.qcloud.liveshow.ui.player.widget.RoomActivity;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.swiperefresh.CustomSwipeRefreshLayout;
import com.qcloud.qclib.swiperefresh.SwipeRefreshUtil;
import com.qcloud.qclib.utils.ScreenUtils;
import com.qcloud.qclib.widget.customview.banner.CustomBanner;
import com.qcloud.qclib.widget.layoutManager.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 11:33.
 */
public class HotFragment extends BaseFragment<IHotView, HotPresenterImpl> implements IHotView {

    @Bind(R.id.banner)
    CustomBanner mBanner;
    @Bind(R.id.list_hot)
    RecyclerView mListHot;
    @Bind(R.id.refresh_layout)
    CustomSwipeRefreshLayout mRefreshLayout;

    private HotAdapter mAdapter;
    private int pageNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot;
    }

    @Override
    protected HotPresenterImpl initPresenter() {
        return new HotPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        initBanner();

        initRefreshLayout();
    }

    @Override
    protected void beginLoad() {

    }

    private void loadData() {
        mPresenter.loadData();
    }

    private void initBanner() {
        int width = ScreenUtils.getScreenWidth(getActivity());
        ViewGroup.LayoutParams lp = mBanner.getLayoutParams();
        lp.height = width * 320 / 690;
        mBanner.setLayoutParams(lp);

        ArrayList<String> list = new ArrayList<>();
        list.add("http://file.greencd.cn/file/get.do?uid=D09BF2AA53DA4C02B2D00C9B097F4CD6.jpg");
        list.add("http://file.greencd.cn/file/get.do?uid=D09BF2AA53DA4C02B2D00C9B097F4CD6.jpg");
        list.add("http://file.greencd.cn/file/get.do?uid=D09BF2AA53DA4C02B2D00C9B097F4CD6.jpg");
        replaceBanner(list);
    }

    private void initRefreshLayout() {
        FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        mListHot.setLayoutManager(layoutManager);
        mListHot.setNestedScrollingEnabled(false);

        SwipeRefreshUtil.setLoadMore(mRefreshLayout, true);
        mRefreshLayout.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                mRefreshLayout.isMore(true);
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mRefreshLayout.refreshFinish();
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new CustomSwipeRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mRefreshLayout.loadMoreFinish();
                    }
                };
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });

        mAdapter = new HotAdapter(getActivity());
        mListHot.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RoomActivity.openActivity(getActivity(), i, mAdapter.getList());
            }
        });
        loadData();
    }

    @Override
    public void replaceBanner(List<String> list) {
        mBanner.setPages(new CustomBanner.ViewCreator<String>() {
            @Override
            public View createView(Context context, int position) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(0, 0, 0, 0);
                return imageView;
            }

            @Override
            public void UpdateUI(Context context, View view, int position, String entity) {
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(null);
                GlideUtil.loadImage(context, imageView, entity, R.drawable.bg_banner_default, true);
            }
        }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setIndicatorRes(R.drawable.banner_point_select, R.drawable.banner_point_unselect)
                //设置指示器的方向
                .setIndicatorGravity(CustomBanner.IndicatorGravity.CENTER_HORIZONTAL)
                //设置翻页的效果，不需要翻页效果可用不设
                .startTurning(5000);
    }

    @Override
    public void replaceList(List<LiveShowBean> beans) {
        if (isInFragment) {
            if (beans != null && beans.size() > 0) {
                mAdapter.replaceList(beans);
            }
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {

    }
}
