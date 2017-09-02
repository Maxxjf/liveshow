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
import com.qcloud.liveshow.beans.LevelViewPageBean;
import com.qcloud.liveshow.ui.mine.widget.AnchorLevelFragment;
import com.qcloud.liveshow.ui.mine.widget.UserLevelFragment;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：等级分类(用户等级、主播等级)
 * Author: Kuzan
 * Date: 2017/9/2 17:49.
 */
public class LevelViewPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private LayoutInflater inflate;
    private List<LevelViewPageBean> mList;
    private UserLevelFragment mUserFragment;
    private AnchorLevelFragment mAnchorFragment;

    public LevelViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        inflate = LayoutInflater.from(context);
        mList = new ArrayList<>();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        final LevelViewPageBean bean = mList.get(position);
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

    public List<LevelViewPageBean> getList() {
        return mList == null? new ArrayList<LevelViewPageBean>() : mList;
    }

    public void replaceList(List<LevelViewPageBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        BaseFragment fragment;
        switch (position) {
            case 0: // 用户等级
                if (mUserFragment == null) {
                    mUserFragment = new UserLevelFragment();
                }
                fragment = mUserFragment;
                break;
            case 1: // 最新
                if (mAnchorFragment == null) {
                    mAnchorFragment = new AnchorLevelFragment();
                }
                fragment = mAnchorFragment;
                break;
            default:
                if (mUserFragment == null) {
                    mUserFragment = new UserLevelFragment();
                }
                fragment = mUserFragment;
                break;
        }
        return fragment;
    }
}
