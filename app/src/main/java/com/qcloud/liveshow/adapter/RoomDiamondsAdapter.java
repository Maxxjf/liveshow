package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.PagerItemBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

import timber.log.Timber;

/**
 * 类说明：房间钻石币
 * Author: Kuzan
 * Date: 2017/8/26 16:37.
 */
public class RoomDiamondsAdapter extends CommonRecyclerAdapter<PagerItemBean> {
    public RoomDiamondsAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_room_diamonds;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final PagerItemBean bean = mList.get(position);

        Timber.e("=================>>>>" + position);

        LinearLayout layoutItem = holder.get(R.id.layout_item);

        if (bean.isSelect()) {
            layoutItem.setBackgroundResource(R.drawable.icon_diamonds_select);
        } else {
            layoutItem.setBackgroundResource(R.drawable.icon_diamonds_normal);
        }
    }
}
