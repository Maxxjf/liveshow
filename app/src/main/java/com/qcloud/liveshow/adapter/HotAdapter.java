package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 17:52.
 */
public class HotAdapter extends CommonRecyclerAdapter<RoomBean> {
    private String watchNumStr;

    public HotAdapter(Context context) {
        super(context);
        watchNumStr = context.getResources().getString(R.string.tag_watch_num);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_hot;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final RoomBean bean = mList.get(position);

        UserHeadImageView userView = holder.get(R.id.layout_user);
        userView.loadImage(bean.getHeadImg(), bean.getIcon(), 80);

        holder.setText(R.id.tv_user_name, bean.getNickName());
        holder.setImageResource(R.id.img_user_sex, bean.getSex() == 0 ? R.drawable.icon_man : R.drawable.icon_lady);
        holder.setText(R.id.tv_room, bean.getType());
        holder.setText(R.id.tv_fans, String.format(watchNumStr, bean.getWatchNum()));

        RatioImageView imgUser = holder.get(R.id.img_user);

        GlideUtil.loadImage(mContext, imgUser, bean.getCover(), R.drawable.bitmap_user, true, false);
    }
}
