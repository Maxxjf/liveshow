package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.utils.DateUtils;
import com.qcloud.qclib.widget.customview.RatioImageView;

/**
 * 类说明：粉丝消息列表
 * Author: Kuzan
 * Date: 2017/9/11 11:42.
 */
public class FansMessageAdapter extends CommonRecyclerAdapter<NettyReceivePrivateBean> {
    private MemberBean mMemberBean;
    private UserBean mUserBean;

    public FansMessageAdapter(Context context) {
        super(context);
        mUserBean = UserInfoUtil.mUser;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_fans_message;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final NettyReceivePrivateBean bean = mList.get(position);
        final NettyContentBean contentBean = bean.getContent();
        TextView mTvTime = holder.get(R.id.tv_time);
        long pTime=0;//上一个会话时间
        if (position!=0){
            pTime=mList.get(position-1).getDate_timeLong();
        }
        if (bean.getDate_timeLong()-pTime>2*60*1000||position==0){//第二个会话与上个会话相隔2分钟
            mTvTime.setText(DateUtils.dateToString(DateUtils.parseDate(bean.getDate_time_str(),DateUtils.yyyyMMddHHmmss),DateUtils.MMddHHmmss));
            mTvTime.setVisibility(View.VISIBLE);
        }else {
            mTvTime.setVisibility(View.GONE);
        }
        // 接收消息
        LinearLayout mLayoutReceive = holder.get(R.id.layout_receive);
        RatioImageView mImgFansHead = holder.get(R.id.img_fans_header);
        TextView mTvReceiveMessage = holder.get(R.id.tv_receive_message);

        // 发送消息
        LinearLayout mLayoutSend = holder.get(R.id.layout_send);
        RatioImageView mImgMineHead = holder.get(R.id.img_user_header);
        TextView mTvSendMessage = holder.get(R.id.tv_send_message);

        if (bean.isSend()) {
            // 发送消息
            mLayoutReceive.setVisibility(View.GONE);
            mLayoutSend.setVisibility(View.VISIBLE);

            GlideUtil.loadCircleImage(mContext, mImgMineHead, mUserBean.getHeadImg(), R.drawable.bitmap_user_head, 0, 0, true, false);

            if (contentBean != null) {
                mTvSendMessage.setText(contentBean.getText());
            } else {
                mLayoutSend.setVisibility(View.GONE);
            }
        } else {
            // 接收消息
            mLayoutReceive.setVisibility(View.VISIBLE);
            mLayoutSend.setVisibility(View.GONE);
            GlideUtil.loadCircleImage(mContext, mImgFansHead, mMemberBean.getHeadImg(), R.drawable.bitmap_user_head, 0, 0, true, false);
            if (contentBean != null) {
                mTvReceiveMessage.setText(contentBean.getText());
            } else {
                mLayoutReceive.setVisibility(View.GONE);
            }
        }
    }

    public void refreshMember(MemberBean bean) {
        mMemberBean = bean;
    }
}
