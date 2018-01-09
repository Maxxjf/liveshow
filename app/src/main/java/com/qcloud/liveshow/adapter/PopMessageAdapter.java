package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.utils.DateUtils;

/**
 * 类说明：弹窗消息列表
 * Author: Kuzan
 * Date: 2017/8/29 15:46.
 */
public class PopMessageAdapter extends CommonRecyclerAdapter<MemberBean> {
    public PopMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_pop_message;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final MemberBean bean = mList.get(position);
        UserHeadImageView userView = holder.get(R.id.layout_user);
        userView.loadImage(bean.getHeadImg(), bean.getIcon(), 60);

        holder.setText(R.id.tv_name, bean.getNickName());
        holder.setImageResource(R.id.img_sex, bean.getSexIcon());
        holder.setText(R.id.tv_message, bean.getSignature());
//        Timber.e("--------->>>>>>>>>>>:"+bean);
        if (bean.getLast_send_message_datetime()!=null){
            holder.setText(R.id.tv_time, DateUtils.longToString(Long.parseLong(bean.getLast_send_message_datetime()), DateUtils.MMddHHmmss));
        }
    }
}
