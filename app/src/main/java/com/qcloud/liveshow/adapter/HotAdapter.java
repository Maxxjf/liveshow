package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.AnchorBean;
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
        final AnchorBean anchorBean = bean.getMember();

        UserHeadImageView userView = holder.get(R.id.layout_user);
        if (anchorBean != null) {
            userView.loadImage(anchorBean.getHeadImg(), anchorBean.getIcon(), 80);

            holder.setText(R.id.tv_user_name, anchorBean.getNickName());
            holder.setImageResource(R.id.img_user_sex, anchorBean.getSexIcon());
        }
        holder.setText(R.id.tv_room_type, bean.getType());
        holder.setText(R.id.tv_watch_num, String.format(watchNumStr, bean.getWatchNum()));
        holder.setText(R.id.tv_title, bean.getTitle());

        RatioImageView imgCover = holder.get(R.id.img_cover);
        GlideUtil.loadImage(mContext, imgCover, bean.getCover(), R.drawable.bitmap_user, true, false);
    }
}
