package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.LevelBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：分佣等级
 * Author: Kuzan
 * Date: 2017/9/4 15:40.
 */
public class SubCommissionAdapter extends CommonRecyclerAdapter<LevelBean> {
    private String tagTopProfit;

    public SubCommissionAdapter(Context context) {
        super(context);
        tagTopProfit = context.getResources().getString(R.string.tag_top_profit);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_sub_commission;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final  LevelBean bean = mList.get(position);
        holder.setText(R.id.tv_name, bean.getName());
        holder.setText(R.id.tv_profit, bean.getProportion() + "%");
        holder.setText(R.id.tv_top_profit, String.format(tagTopProfit, bean.getUpperLimit()));
    }

}
