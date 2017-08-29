package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.PagerItemBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：房间礼物列表
 * Author: Kuzan
 * Date: 2017/8/29 11:43.
 */
public class RoomGiftAdapter extends CommonRecyclerAdapter<PagerItemBean> {
    public RoomGiftAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_room_gift;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final PagerItemBean bean = mList.get(position);
        holder.getConvertView().setTag(R.id.item_diamonds_tag, bean);

        LinearLayout layoutItem = holder.get(R.id.layout_item);

        if (bean.isSelect()) {
            layoutItem.setBackgroundResource(R.drawable.frame_orange_radius);
        } else {
            layoutItem.setBackgroundResource(0);
        }
    }
}
