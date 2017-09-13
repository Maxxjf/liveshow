package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.PagerItemBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

/**
 * 类说明：房间礼物列表
 * Author: Kuzan
 * Date: 2017/8/29 11:43.
 */
public class RoomGiftAdapter extends CommonRecyclerAdapter<PagerItemBean> {
    private String tagDiamonds;

    public RoomGiftAdapter(Context context) {
        super(context);
        tagDiamonds = context.getResources().getString(R.string.tag_diamonds_price);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_room_gift;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final PagerItemBean bean = mList.get(position);
        holder.getConvertView().setTag(R.id.item_diamonds_tag, bean);

        GiftBean giftBean = (GiftBean) bean.getO();
        if (giftBean != null) {
            RatioImageView imgGift = holder.get(R.id.img_gift);
            GlideUtil.loadImage(mContext, imgGift, giftBean.getImage(), R.drawable.icon_default_gift, 0, 0, true, false);
            holder.setText(R.id.tv_name, giftBean.getName());
            holder.setText(R.id.tv_price, String.format(tagDiamonds, giftBean.getVirtualCoin()));
        }

        LinearLayout layoutItem = holder.get(R.id.layout_item);
        if (bean.isSelect()) {
            layoutItem.setBackgroundResource(R.drawable.frame_orange_radius);
        } else {
            layoutItem.setBackgroundResource(0);
        }
    }
}
