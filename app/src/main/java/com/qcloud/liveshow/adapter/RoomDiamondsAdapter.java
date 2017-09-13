package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.PagerItemBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：房间钻石币
 * Author: Kuzan
 * Date: 2017/8/26 16:37.
 */
public class RoomDiamondsAdapter extends CommonRecyclerAdapter<PagerItemBean> {
    private String moneyStr;

    public RoomDiamondsAdapter(Context context) {
        super(context);
        moneyStr = context.getResources().getString(R.string.money_str);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_room_diamonds;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final PagerItemBean bean = mList.get(position);
        final DiamondsBean diamondsBean = (DiamondsBean) bean.getO();
        holder.getConvertView().setTag(R.id.item_diamonds_tag, bean);

        if (diamondsBean != null) {
            holder.setText(R.id.tv_name, diamondsBean.getName());
            holder.setText(R.id.tv_price, String.format(moneyStr, diamondsBean.getMoney()));
        }

        LinearLayout layoutItem = holder.get(R.id.layout_item);
        if (bean.isSelect()) {
            layoutItem.setBackgroundResource(R.drawable.icon_diamonds_select);
        } else {
            layoutItem.setBackgroundResource(R.drawable.icon_diamonds_normal);
        }
    }
}
