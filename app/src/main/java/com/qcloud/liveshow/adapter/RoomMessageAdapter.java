package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.image.GlideUtil;

/**
 * 类说明：房间聊天消息
 * Author: Kuzan
 * Date: 2017/8/24 11:36.
 */
public class RoomMessageAdapter extends CommonRecyclerAdapter<NettyReceiveGroupBean> {

    public RoomMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_room_message;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final NettyReceiveGroupBean bean = mList.get(position);
        final MemberBean memberBean = bean.getUser();
        final NettyContentBean contentBean = bean.getContent();

        if (memberBean != null) {
            ImageView imgLevel = holder.get(R.id.img_anchor_level);
            GlideUtil.loadImage(mContext, imgLevel, memberBean.getIcon(), R.drawable.icon_member_level_1, true, false);

            ImageView imgIsFans = holder.get(R.id.img_is_fans);
            imgIsFans.setVisibility(memberBean.isAttention()? View.VISIBLE : View.GONE);

            holder.setText(R.id.tv_nickname, memberBean.getNickName() + ":");
        }

        if (contentBean != null) {
            TextView tvMessage = holder.get(R.id.tv_message);
            tvMessage.setText(contentBean.getText());
        }
    }
}
