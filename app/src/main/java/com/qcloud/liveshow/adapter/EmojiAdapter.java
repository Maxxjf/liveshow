package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：表情符号
 * Author: Kuzan
 * Date: 2017/11/16 18:50.
 */
public class EmojiAdapter extends CommonRecyclerAdapter<String> {
    public EmojiAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_emoji;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final String emoji = mList.get(position);
        holder.setText(R.id.tv_name, emoji);
    }
}
