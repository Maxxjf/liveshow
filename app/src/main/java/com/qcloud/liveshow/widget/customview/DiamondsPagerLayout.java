package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.PagerLayoutAdapter;
import com.qcloud.liveshow.adapter.RoomDiamondsAdapter;
import com.qcloud.liveshow.beans.PagerItemBean;
import com.qcloud.qclib.base.BaseLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;

/**
 * 类说明：钻石币分页布局(类似轮播图的分页，内嵌RecyclerView)
 * Author: Kuzan
 * Date: 2017/8/26 15:55.
 */
public class DiamondsPagerLayout extends BaseLinearLayout implements ViewPager.OnPageChangeListener {
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.layout_indicator)
    LinearLayout mLayoutIndicator;

    @BindDimen(R.dimen.margin_1)
    int dp5;

    //每行个数
    private int mCountRow = 3;
    //每页个数
    private int mCountPager = 6;

    private RoomDiamondsAdapter mAdapter;

    private OnItemClickListener mListener;

    /**装载指示器*/
    private List<ImageView> listIndicator = new ArrayList<>();
    /**用来保存viewpager的item*/
    private List<RecyclerView> mPagers = new ArrayList<>();
    private ArrayList<PagerItemBean> mData;
    private ArrayList<ArrayList<PagerItemBean>> mPagerData = new ArrayList<>();

    public DiamondsPagerLayout(Context context) {
        this(context, null);
    }

    public DiamondsPagerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiamondsPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_diamonds_pager;
    }

    @Override
    protected void initViewAndData() {

    }

    private void resetViewPagerSize(int size) {
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        lp.height = size;
        mViewPager.setLayoutParams(lp);
    }

    public void setData(ArrayList data) {
        this.mData = packData(data);
        /**先准备数据、先拆分数据源*/
        initItemData();
        /**实例化控件*/
        initPager();
        /**实例化指示器*/
        initIndicator();
    }

    public ArrayList<PagerItemBean> packData(ArrayList<Object> data) {
        ArrayList<PagerItemBean> list = new ArrayList<>();
        if (data == null) {
            return list;
        }

        int size = data.size();
        for (int i = 0; i < size; i++) {
            PagerItemBean bean = new PagerItemBean();
            bean.setIndex(i + 1);
            bean.setO(data.get(i));
            bean.setSelect(false);
            list.add(bean);
        }
        return list;
    }

    /**
     * 拆分数据
     */
    private void initItemData() {
        if (mData == null) return;
        mPagerData.clear();

        int size = mData.size();

        int i = 0;
        do {
            ArrayList<PagerItemBean> itemData = new ArrayList<>();
            int maxIndex = mCountPager * (i + 1);
            for (int index = i * mCountPager; index < maxIndex && index < size; index++) {
                itemData.add(mData.get(index));
            }
            mPagerData.add(itemData);
            i++;
        } while (i * mCountPager < size);
    }

    /**
     * 初始化Viewpager 和 item
     */
    private void initPager() {
        mPagers.clear();

        for (ArrayList<PagerItemBean> data : mPagerData) {
            RecyclerView pager = new RecyclerView(mContext);
            pager.setLayoutManager(new GridLayoutManager(mContext, mCountRow));
            mAdapter = new RoomDiamondsAdapter(mContext);
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mListener != null) {
                        resetData();
                        PagerItemBean bean = mAdapter.getList().get(i);
                        bean.setSelect(true);
                        resetPager();
                        mListener.onItemClick(bean);
                    }
                }
            });
            mAdapter.replaceList(data);

            pager.setAdapter(mAdapter);
            mPagers.add(pager);
        }

        PagerLayoutAdapter pagerAdapter = new PagerLayoutAdapter(mPagers);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    private void resetData() {
        if (mData != null) {
            for (PagerItemBean bean : mData) {
                bean.setSelect(false);
            }
        }
    }

    private void resetPager() {
        for (RecyclerView v : mPagers) {
            v.getAdapter().notifyDataSetChanged();
        }
    }

    private void initIndicator() {
        if (mPagers.size() <= 1) {
            mLayoutIndicator.setVisibility(GONE);
            return;
        }

        mLayoutIndicator.setVisibility(VISIBLE);
        mLayoutIndicator.removeAllViews();
        listIndicator.clear();
        int size = mPagers.size();
        for (int i = 0; i < size; i++) {
            ImageView view = new ImageView(mContext);
            view.setBackgroundResource(R.drawable.banner_point_unselect);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0) lp.setMargins(dp5, 0, 0, 0);
            view.setLayoutParams(lp);

            mLayoutIndicator.addView(view);
            listIndicator.add(view);
        }

        listIndicator.get(0).setBackgroundResource(R.drawable.banner_point_select);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        if (listIndicator == null) {
            return;
        }

        for (int i = 0; i < listIndicator.size(); i++) {
            listIndicator.get(i).setBackgroundResource(R.drawable.banner_point_unselect);
        }
        listIndicator.get(position).setBackgroundResource(R.drawable.banner_point_select);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }

    public interface OnItemClickListener {
        void onItemClick(Object o);
    }
}
