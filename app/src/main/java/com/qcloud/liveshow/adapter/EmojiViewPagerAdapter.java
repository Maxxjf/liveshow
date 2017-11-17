package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.enums.EmojiClassifyEnum;
import com.qcloud.liveshow.ui.home.widget.EmojiFragment;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：表情切换
 * Author: Kuzan
 * Date: 2017/11/16 9:25.
 */
public class EmojiViewPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private LayoutInflater inflate;
    private List<EmojiClassifyEnum> mList;

    public EmojiViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        inflate = LayoutInflater.from(context);
        mList = new ArrayList<>();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        final EmojiClassifyEnum bean = mList.get(position);
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.item_of_emoji_pager, container, false);
        }
        ImageView imageView = (ImageView) convertView;
        imageView.setImageResource(bean.getRes());

        return convertView;
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    public List<EmojiClassifyEnum> getList() {
        return mList == null? new ArrayList<>() : mList;
    }

    public void replaceList(List<EmojiClassifyEnum> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return EmojiFragment.newInstance(position);
    }
}
