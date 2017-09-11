package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：守护者列表
 * Author: Kuzan
 * Date: 2017/9/11 14:55.
 */
public class GuarderAdapter extends CommonRecyclerAdapter<String> {
    public GuarderAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_my_guarder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
