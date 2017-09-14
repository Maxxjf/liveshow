package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：我的礼物
 * Author: Kuzan
 * Date: 2017/8/31 11:38.
 */
public class GiftMemberAdapter extends CommonRecyclerAdapter<MemberBean> {
    private String moneyStr;

    public GiftMemberAdapter(Context context) {
        super(context);
        moneyStr = context.getResources().getString(R.string.money_str);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_my_gifts;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final MemberBean bean = mList.get(position);

        UserHeadImageView layoutUser = holder.get(R.id.layout_user);
        if (bean.isAnchor()) {
            layoutUser.loadImage(bean.getHeadImg(), bean.getAnchorGradeIcon(), 100);
        } else {
            layoutUser.loadImage(bean.getHeadImg(), bean.getMemberGradeIcon(), 100);
        }

        holder.setText(R.id.tv_fans_name, bean.getNickName());
        holder.setImageResource(R.id.img_fans_sex, bean.getSex() == 0 ? R.drawable.icon_man : R.drawable.icon_lady);
        holder.setText(R.id.tv_signature, bean.getSignature());
        holder.setText(R.id.tv_profit, String.format(moneyStr, bean.getMemberSum()));
    }
}
