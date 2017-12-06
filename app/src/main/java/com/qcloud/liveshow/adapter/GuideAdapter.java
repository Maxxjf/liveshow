package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 类说明：启动轮播图的adapter
 * Author: iceberg
 * Date: 2017/11/23.
 */
public class GuideAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
    private Context mContext;
    private List<Integer> mList = new ArrayList<>();

    public GuideAdapter(Context context, List<Integer> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_guide, container, false);
        }
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = new View(mContext);
            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        convertView.setBackgroundResource(mList.get(position));
        return convertView;
    }

    @Override
    public int getItemPosition(Object object) {
        //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
        // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }
}
