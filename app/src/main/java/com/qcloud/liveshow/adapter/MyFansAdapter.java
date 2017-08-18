package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：我的关注/我的粉丝
 * Author: Kuzan
 * Date: 2017/8/18 12:03.
 */
public class MyFansAdapter extends CommonRecyclerAdapter<String> {
    String followStr;
    String unFollowStr;

    public MyFansAdapter(Context context) {
        super(context);
        followStr = context.getResources().getString(R.string.tag_follow);
        unFollowStr = context.getResources().getString(R.string.tag_un_follow);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_my_fans;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;

    }
}
