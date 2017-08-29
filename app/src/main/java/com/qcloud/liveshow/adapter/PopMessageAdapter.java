package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：弹窗消息列表
 * Author: Kuzan
 * Date: 2017/8/29 15:46.
 */
public class PopMessageAdapter extends CommonRecyclerAdapter<String> {
    public PopMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_pop_message;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        UserHeadImageView userHead = holder.get(R.id.layout_user);

        userHead.loadImage("", R.drawable.icon_anchor_level_1, 60);
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
