package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.AnchorBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

/**
 * 类说明：最新
 * Author: Kuzan
 * Date: 2017/8/14 16:06.
 */
public class NewestAdapter extends CommonRecyclerAdapter<RoomBean> {

    public NewestAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_newest;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final RoomBean bean = mList.get(position);
        final AnchorBean anchorBean = bean.getAnchor();
        RatioImageView imgCover = holder.get(R.id.img_cover);

        GlideUtil.loadImage(mContext, imgCover, bean.getCover(), R.drawable.bitmap_user, 0, 0, true, false);
        if (anchorBean != null) {
            holder.setText(R.id.tv_user_name, anchorBean.getNickName());
        }
    }
}
