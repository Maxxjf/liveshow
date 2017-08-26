package com.qcloud.liveshow.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 类说明：分页布局
 * Author: Kuzan
 * Date: 2017/8/26 16:53.
 */
public class PagerLayoutAdapter extends PagerAdapter {
    private List<RecyclerView> listMenuView ;

    public PagerLayoutAdapter(List<RecyclerView> listMenuView) {
        this.listMenuView = listMenuView;
    }

    @Override
    public int getCount() {
        return listMenuView == null ? 0 : listMenuView.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listMenuView.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(listMenuView.get(position));
        return listMenuView.get(position);
    }
}
