package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：钻石币充值套餐
 * Author: Kuzan
 * Date: 2017/9/1 16:01.
 */
public class DiamondsAdapter extends CommonRecyclerAdapter<DiamondsBean> {
    private String moneyStr;

    public DiamondsAdapter(Context context) {
        super(context);
        moneyStr = context.getResources().getString(R.string.money_str);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_diamonds;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final DiamondsBean bean = mList.get(position);
        LinearLayout holderView = holder.get(R.id.layout_item);
        holder.setText(R.id.tv_name, String.format(mContext.getResources().getString(R.string.tag_diamonds_price),bean.getVirtualCoin()));
        holder.setText(R.id.tv_price, String.format(moneyStr, bean.getMoney()));

        if (bean.isSelect()) {
            holderView.setBackgroundResource(R.drawable.icon_big_diamonds_select);
        } else {
            holderView.setBackgroundResource(R.drawable.icon_big_diamonds_normal);
        }
    }

    public DiamondsBean refreshSelect(int position) {
        if (position < 0 || position > getItemCount()) {
            return null;
        }
        if (!mList.get(position).isSelect()) {
            int lastPosition = getSelectItem();
            if (lastPosition >= 0) {
                mList.get(lastPosition).setSelect(false);
                replaceItem(mList.get(lastPosition), lastPosition);
            }
            mList.get(position).setSelect(true);
            replaceItem(mList.get(position), position);
        } else {
            mList.get(position).setSelect(false);
            replaceItem(mList.get(position), position);
        }
        return mList.get(position);
    }

    private int getSelectItem() {
        int position = -1;
        for (int i=0; i<getItemCount(); i++) {
            if (mList.get(i).isSelect()) {
                position = i;
                break;
            }
        }
        return position;
    }
}
