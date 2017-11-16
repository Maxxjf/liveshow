package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.HomeViewPageBean;
import com.qcloud.liveshow.ui.home.widget.FollowFragment;
import com.qcloud.liveshow.ui.home.widget.HotFragment;
import com.qcloud.liveshow.ui.home.widget.NewestFragment;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：直播分类(热门、最新、关注)
 * Author: Kuzan
 * Date: 2017/8/11 10:58.
 */
public class HomeViewPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private LayoutInflater inflate;
    private List<HomeViewPageBean> mList;
    private HotFragment mHotFragment;
    private NewestFragment mNewestFragment;
    private FollowFragment mFollowFragment;

    public HomeViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        inflate = LayoutInflater.from(context);
        mList = new ArrayList<>();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        final HomeViewPageBean bean = mList.get(position);
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.item_of_view_pager, container, false);
        }
        TextView textView = (TextView) convertView;

        textView.setText(bean.getEnum().getValue());

        return convertView;
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    public List<HomeViewPageBean> getList() {
        return mList == null? new ArrayList<>() : mList;
    }

    public void replaceList(List<HomeViewPageBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        BaseFragment fragment;
        switch (position) {
            case 0: // 热门
                if (mHotFragment == null) {
                    mHotFragment = new HotFragment();
                }
                fragment = mHotFragment;
                break;
            case 1: // 最新
                if (mNewestFragment == null) {
                    mNewestFragment = new NewestFragment();
                }
                fragment = mNewestFragment;
                break;
            case 2: // 关注
                if (mFollowFragment == null) {
                    mFollowFragment = new FollowFragment();
                }
                fragment = mFollowFragment;
                break;
            default:
                if (mHotFragment == null) {
                    mHotFragment = new HotFragment();
                }
                fragment = mHotFragment;
                break;
        }
        return fragment;
    }
}
