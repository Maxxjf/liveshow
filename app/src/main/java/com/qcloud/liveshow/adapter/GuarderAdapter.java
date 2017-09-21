package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：守护者列表
 * Author: Kuzan
 * Date: 2017/9/11 14:55.
 */
public class GuarderAdapter extends CommonRecyclerAdapter<MemberBean> {
    String guardStr;
    String unGuardStr;

    public GuarderAdapter(Context context) {
        super(context);
        guardStr = context.getResources().getString(R.string.tag_add_guard);
        unGuardStr = context.getResources().getString(R.string.tag_remove_guard);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_my_guarder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        final MemberBean bean = mList.get(position);

        UserHeadImageView layoutUser = holder.get(R.id.layout_user);
        layoutUser.loadImage(bean.getHeadImg(), bean.getIcon(), 60);

        holder.setText(R.id.tv_name, bean.getNickName());
        holder.setImageResource(R.id.img_sex, bean.getSexIcon());
        holder.setText(R.id.tv_signature, bean.getSignature());

        final TextView btnFollow = holder.get(R.id.btn_follow);

        if (bean.isAttention()) {
            btnFollow.setText(unGuardStr);
            btnFollow.setTextColor(ContextCompat.getColor(mContext, R.color.colorText));
            btnFollow.setBackgroundResource(R.drawable.frame_gray_circular);
        } else {
            btnFollow.setText(guardStr);
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
}
