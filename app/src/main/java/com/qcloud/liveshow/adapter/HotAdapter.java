package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：热门
 * Author: Kuzan
 * Date: 2017/8/11 17:52.
 */
public class HotAdapter extends CommonRecyclerAdapter<String> {
    public HotAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_hot;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        UserHeadImageView userView = holder.get(R.id.layout_user);
        userView.loadImage("http://img0.imgtn.bdimg.com/it/u=2880194567,3262606007&fm=26&gp=0.jpg", R.drawable.icon_anchor_level_1, 150);
    }

    @Override
    public int getItemCount() {
        return 30;

    }
}
