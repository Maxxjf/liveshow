package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.AnchorBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：搜索主播
 * Author: Kuzan
 * Date: 2017/8/30 11:02.
 */
public class SearchAnchorAdapter extends CommonRecyclerAdapter<RoomBean> {
    public SearchAnchorAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_search_anchor;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final RoomBean bean = mList.get(position);
        final AnchorBean anchorBean = bean.getAnchor();

        UserHeadImageView userView = holder.get(R.id.layout_user);
        if (anchorBean != null) {
            userView.loadImage(anchorBean.getHeadImg(), anchorBean.getIcon(), 80);

            holder.setText(R.id.tv_name, anchorBean.getNickName());
            holder.setImageResource(R.id.img_sex, anchorBean.getSexIcon());
            holder.setText(R.id.tv_signature, anchorBean.getSignature());
        }
    }
}
