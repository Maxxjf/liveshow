package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：我的关注/我的粉丝
 * Author: Kuzan
 * Date: 2017/8/18 12:03.
 */
public class MyFansAdapter extends CommonRecyclerAdapter<MemberBean> {
    String followStr;
    String unFollowStr;

    public MyFansAdapter(Context context, int type) {
        super(context);
        if (type == StartFansEnum.Blacklist.getKey()) {
            followStr = context.getResources().getString(R.string.tag_add_blacklist);
            unFollowStr = context.getResources().getString(R.string.tag_remove_blacklist);
        } else {
            followStr = context.getResources().getString(R.string.tag_follow);
            unFollowStr = context.getResources().getString(R.string.tag_un_follow);
        }
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_my_fans;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        final MemberBean bean = mList.get(position);

        UserHeadImageView layoutUser = holder.get(R.id.layout_user);
        if (bean.isAnchor()) {
            layoutUser.loadImage(bean.getHeadImg(), bean.getAnchorGradeIcon(), 100);
        } else {
            layoutUser.loadImage(bean.getHeadImg(), bean.getMemberGradeIcon(), 100);
        }

        holder.setText(R.id.tv_user_name, bean.getNickName());
        holder.setImageResource(R.id.img_user_sex, bean.getSex() == 0 ? R.drawable.icon_man : R.drawable.icon_lady);
        holder.setText(R.id.tv_signature, bean.getSignature());

        final TextView btnFollow = holder.get(R.id.btn_follow);

        if (bean.isAttention()) {
            btnFollow.setText(unFollowStr);
            btnFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorText));
            btnFollow.setBackgroundResource(R.drawable.frame_gray_circular);
        } else {
            btnFollow.setText(followStr);
            btnFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorStart));
            btnFollow.setBackgroundResource(R.drawable.frame_orange_circular);
        }

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHolderClick != null) {
                    bean.refreshAttention();
                    mHolderClick.onViewClick(btnFollow, bean, position);
                }
            }
        });
    }

    public void refreshFollow(int position, MemberBean bean) {
        if (position < 0 || position > getItemCount() || bean == null) {
            return;
        }
        replaceItem(bean, position);
    }
}
