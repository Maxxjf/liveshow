package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
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

    }

    @Override
    public int getItemCount() {
        return 50;

    }
}
