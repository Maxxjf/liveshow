package com.qcloud.liveshow.widget.customview;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.qcloud.liveshow.adapter.MyGiftAdapter;
import com.qcloud.liveshow.adapter.PagerLayoutAdapter;
import com.qcloud.liveshow.beans.PagerItemBean;

import java.util.ArrayList;

/**
 * 类说明：我的礼物布局
 * Author: Kuzan
 * Date: 2017/9/14 14:30.
 */
public class MyGiftPagerLayout extends BasePagerLayout {
    private MyGiftAdapter mAdapter;

    public MyGiftPagerLayout(Context context) {
        this(context, null);
    }

    public MyGiftPagerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGiftPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initPager() {
        mPagers.clear();

        for (ArrayList<PagerItemBean> data : mPagerData) {
            RecyclerView pager = new RecyclerView(mContext);
            pager.setLayoutManager(new GridLayoutManager(mContext, mCountRow));
            mAdapter = new MyGiftAdapter(mContext);
            mAdapter.replaceList(data);

            pager.setAdapter(mAdapter);
            mPagers.add(pager);
        }

        PagerLayoutAdapter pagerAdapter = new PagerLayoutAdapter(mPagers);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }
}
