package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.NettyMemberBean;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：弹窗消息列表
 * Author: Kuzan
 * Date: 2017/8/29 15:46.
 */
public class PopMessageAdapter extends CommonRecyclerAdapter<NettyMemberBean> {
    public PopMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_pop_message;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final NettyMemberBean bean = mList.get(position);
        UserHeadImageView userView = holder.get(R.id.layout_user);
        userView.loadImage(bean.getHead_img(), bean.getIcon(), 60);

        holder.setText(R.id.tv_name, bean.getNick_name());
        holder.setImageResource(R.id.img_sex, bean.getSexIcon());
        holder.setText(R.id.tv_message, bean.getSignature());
        holder.setText(R.id.tv_time, bean.getLast_send_message_datetime());
    }
}
