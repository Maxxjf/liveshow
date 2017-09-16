package com.qcloud.liveshow.adapter;

import android.content.Context;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.ProblemBean;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：常见问题
 * Author: Kuzan
 * Date: 2017/9/9 18:03.
 */
public class ProblemAdapter extends CommonRecyclerAdapter<ProblemBean> {

    public ProblemAdapter(Context context) {
        super(context);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_problem;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final ProblemBean bean = mList.get(position);
        holder.setText(R.id.tv_title, bean.getQuestion());
    }
}
