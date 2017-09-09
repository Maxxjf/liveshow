package com.qcloud.liveshow.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.widget.customview.SpreadLayout;
import com.qcloud.qclib.adapter.recyclerview.BaseViewHolder;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;

/**
 * 类说明：常见问题
 * Author: Kuzan
 * Date: 2017/9/9 18:03.
 */
public class ProblemAdapter extends CommonRecyclerAdapter<String> {
    private BitmapDrawable mOpen;
    private BitmapDrawable mClose;

    public ProblemAdapter(Context context) {
        super(context);
        initDrawable();
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_of_problem;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final TextView tvTitle = holder.get(R.id.tv_title);
        final SpreadLayout slContent = holder.get(R.id.sl_content);

//        if (bean.isOpen) {
//            spread(tvTitle, slContent);
//            tvTitle.setCompoundDrawables(null, null, mOpen, null);
//        }
//
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spread(tvTitle, slContent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private void initDrawable() {
        Resources resources = mContext.getResources();

        Bitmap open = BitmapFactory.decodeResource(resources, R.drawable.icon_gray_up_arrow);
        mOpen = new BitmapDrawable(resources, open);
        mOpen.setBounds(0, 0, open.getWidth(), open.getHeight());

        Bitmap close = BitmapFactory.decodeResource(resources, R.drawable.icon_gray_down_arrow);
        mClose = new BitmapDrawable(resources, close);
        mClose.setBounds(0, 0, close.getWidth(), close.getHeight());
    }

    private void spread(TextView view, SpreadLayout spreadLayout) {
        boolean isSpread = !spreadLayout.isSpread();
        if (isSpread) {
            view.setCompoundDrawables(null, null, mOpen, null);
        } else {
            view.setCompoundDrawables(null, null, mClose, null);
        }
        spreadLayout.setSpread(isSpread, true);
    }
}
