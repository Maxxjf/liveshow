package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.PagerItemBean;
import com.qcloud.liveshow.widget.customview.DiamondsPagerLayout;
import com.qcloud.liveshow.widget.customview.GiftPagerLayout;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.toast.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 类说明：发送礼物弹窗
 * Author: Kuzan
 * Date: 2017/8/29 11:57.
 */
public class SendGiftPop extends BasePopupWindow {
    @Bind(R.id.page_gift)
    GiftPagerLayout mPageGift;
    @Bind(R.id.tv_diamonds)
    TextView mTvDiamonds;
    @Bind(R.id.btn_recharge)
    TextView mBtnRecharge;
    @Bind(R.id.btn_buy)
    TextView mBtnBuy;

    public SendGiftPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_send_gift;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void init() {
        super.init();
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initAfterViews() {
        //测试的假数据
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");

        mPageGift.setCountNum(4, 2);
        mPageGift.setData(list);

        mPageGift.setOnItemClickListener(new DiamondsPagerLayout.OnItemClickListener() {
            @Override
            public void onItemClick(Object o) {
                ToastUtils.ToastMessage(mContext, ((PagerItemBean) o).getIndex() + " ");
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }
}
