package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.qclib.image.GlideUtil;

import java.util.List;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/22 18:49.
 */
public class RoomAdapter extends PagerAdapter {

    private Context mContext;
    private List<RoomBean> mList;

    public RoomAdapter(Context context, List<RoomBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null?0:mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_of_view_room, null);
        view.setId(position);
        final RoomBean bean = mList.get(position);
        ImageView imgAnchor = (ImageView) view.findViewById(R.id.img_anchor);
        GlideUtil.loadImage(mContext, imgAnchor, bean.getCover()+"?x-oss-process=image/resize,m_fixed,h_320,w_180",
                R.drawable.bitmap_user, true);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(container.findViewById(position));
    }
}
