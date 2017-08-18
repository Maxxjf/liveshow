package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：收益记录
 * Author: Kuzan
 * Date: 2017/8/18 16:26.
 */
public class ProfitRecordAdapter extends CommonRecyclerAdapter<String> {
    public ProfitRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_profit_record;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }
}
