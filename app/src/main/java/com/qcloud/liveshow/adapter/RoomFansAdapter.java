package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.view.View;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.base.BaseLinearLayout;

/**
 * 类说明：直播房间的粉丝
 * Author: Kuzan
 * Date: 2017/8/24 11:19.
 */
public class RoomFansAdapter extends CommonRecyclerAdapter<MemberBean> {
    public RoomFansAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_room_fans;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        final MemberBean bean = mList.get(position);
        UserHeadImageView userHeader = holder.get(R.id.layout_user);
        userHeader.loadImage(bean.getHeadImg(), bean.getIcon(), 80);

        userHeader.setOnViewClickListener(new BaseLinearLayout.OnViewClickListener() {
            @Override
            public void onViewClick(View view) {
                if (mHolderClick != null) {
                    mHolderClick.onViewClick(view, bean, position);
                }
            }
        });
    }
}
