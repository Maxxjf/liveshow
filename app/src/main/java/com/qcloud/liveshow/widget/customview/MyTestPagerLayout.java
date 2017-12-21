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
 * 类说明：${必须填}
 * Author: iceberg
 * Date: 2017/12/11.
 */
public class MyTestPagerLayout extends BasePagerLayout {
    private MyGiftAdapter adapter;

    public MyTestPagerLayout(Context context) {
        super(context);
    }

    public MyTestPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCountNum(5, 3);
    }

    @Override
    protected void initPager() {
        mPagers.clear();
        for (ArrayList<PagerItemBean> data : mPagerData) {
            RecyclerView pager = new RecyclerView(mContext);
            pager.setLayoutManager(new GridLayoutManager(mContext, mCountRow));
            adapter = new MyGiftAdapter(mContext);
            adapter.replaceList(data);
            pager.setAdapter(adapter);
            mPagers.add(pager);

        }
        PagerLayoutAdapter pagerLayoutAdapter = new PagerLayoutAdapter(mPagers);
        mViewPager.setAdapter(pagerLayoutAdapter);
        mViewPager.addOnPageChangeListener(this);
    }
}
