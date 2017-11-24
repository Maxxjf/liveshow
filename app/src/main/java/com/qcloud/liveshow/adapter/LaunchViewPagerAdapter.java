package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.ui.main.widget.LaunchActivity;
import com.qcloud.qclib.utils.ConstantUtil;
import com.qcloud.qclib.widget.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * 类说明：启动轮播图的adapter
 * Author: iceberg
 * Date: 2017/11/23.
 */
public class LaunchViewPagerAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
    private List<Integer> mList; //轮播图片R.drawable
    private LayoutInflater inflate;
    private Context mContext;

    public LaunchViewPagerAdapter(Context context) {
        inflate = LayoutInflater.from(context);
        this.mContext = context;
        mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.tab_dot, container, false);
        }
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.tab_imageview, container, false);
            viewHolder=new ViewHolder();
            viewHolder.imgLaunch=(ImageView) convertView.findViewById(R.id.img_launch);
            viewHolder.tvLaunch=(TextView) convertView.findViewById(R.id.btn_go);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.imgLaunch.setImageResource(mList.get(position));
        if (position == mList.size() - 1) {
            viewHolder.tvLaunch.setVisibility(View.VISIBLE);
            viewHolder.tvLaunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConstantUtil.writeBoolean("LaunchBefore",true);
                    LaunchActivity.openActivity(mContext);
                }
            });
        }else {
            viewHolder.tvLaunch.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imgLaunch;
        TextView tvLaunch;
    }

    public List<Integer> getList() {
        return mList == null ? new ArrayList<>() : mList;
    }

    public void replaceList(List<Integer> list) {
        this.mList = list;
        notifyDataSetChanged();
    }
}
