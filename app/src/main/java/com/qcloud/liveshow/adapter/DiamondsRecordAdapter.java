package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.DiamondsRecordBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：钻石币交易记录
 * Author: Kuzan
 * Date: 2017/9/1 16:32.
 */
public class DiamondsRecordAdapter extends CommonRecyclerAdapter<DiamondsRecordBean> {
    public DiamondsRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_profit_record;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final DiamondsRecordBean bean = mList.get(position);
        holder.setText(R.id.tv_title,bean.getTypeName());
        holder.setText(R.id.tv_time,bean.getTime());
        holder.setText(R.id.tv_profit,bean.getVirtualCoin());
        holder.setText(R.id.tv_brand_code,String.format(mContext.getResources().getString(R.string.tag_brand_code),String.valueOf(bean.getBalance())));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}
