package com.qcloud.liveshow.widget.customview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.PagerLayoutAdapter;
import com.qcloud.liveshow.adapter.RoomGiftAdapter;
import com.qcloud.liveshow.beans.PagerItemBean;

import java.util.ArrayList;

/**
 * 类说明：发送礼物布局
 * Author: Kuzan
 * Date: 2017/8/29 11:42.
 */
public class GiftPagerLayout extends BasePagerLayout {
    private RoomGiftAdapter mAdapter;

    public GiftPagerLayout(Context context) {
        this(context, null);
    }

    public GiftPagerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initPager() {
        mPagers.clear();

        for (ArrayList<PagerItemBean> data : mPagerData) {
            RecyclerView pager = new RecyclerView(mContext);
            pager.setLayoutManager(new GridLayoutManager(mContext, mCountRow));
            mAdapter = new RoomGiftAdapter(mContext);
            mAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (mListener != null) {
                        resetData();
                        PagerItemBean bean = (PagerItemBean) view.getTag(R.id.item_diamonds_tag);
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
}
