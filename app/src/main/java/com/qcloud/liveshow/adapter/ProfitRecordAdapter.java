package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.ProfitRecordBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：收益记录
 * Author: Kuzan
 * Date: 2017/8/18 16:26.
 */
public class ProfitRecordAdapter extends CommonRecyclerAdapter<ProfitRecordBean> {
    private String brandCodeTag;

    public ProfitRecordAdapter(Context context) {
        super(context);
        brandCodeTag = context.getResources().getString(R.string.tag_brand_code);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_profit_record;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final ProfitRecordBean bean = mList.get(position);

        holder.setText(R.id.tv_title, bean.getType());
        holder.setText(R.id.tv_time, bean.getTime());

        TextView tvProfit = holder.get(R.id.tv_profit);
        tvProfit.setText(bean.getMoney());
        if (bean.isBrandCodeEmpty()) {
            tvProfit.setTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
        } else {
            tvProfit.setTextColor(ContextCompat.getColor(mContext, R.color.colorTitle));
            holder.setText(R.id.tv_brand_code, String.format(brandCodeTag, bean.getBrandCode()));
        }
    }
}
