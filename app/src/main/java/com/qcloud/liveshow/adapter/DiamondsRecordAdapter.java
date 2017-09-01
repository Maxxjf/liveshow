package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：钻石币交易记录
 * Author: Kuzan
 * Date: 2017/9/1 16:32.
 */
public class DiamondsRecordAdapter extends CommonRecyclerAdapter<String> {
    public DiamondsRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_profit_record;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;

    }
}
