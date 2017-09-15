package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.LevelBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.widget.customview.RatioImageView;

/**
 * 类说明：等级名称
 * Author: Kuzan
 * Date: 2017/9/4 15:33.
 */
public class LevelNameAdapter extends CommonRecyclerAdapter<LevelBean> {
    public LevelNameAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_level_name;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final LevelBean bean = mList.get(position);

        RatioImageView iconView = holder.get(R.id.img_level);
        GlideUtil.loadCircleImage(mContext, iconView, bean.getIcon(), R.drawable.icon_anchor_level_1, 0, 0, true, false);

        holder.setText(R.id.tv_level_name, bean.getName());
    }
}
