package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.AnchorBean;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.beans.ReturnGiftBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.utils.BasicsUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.customview.DiamondsPagerLayout;
import com.qcloud.liveshow.widget.customview.GiftPagerLayout;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;

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

    private GiftBean currBean;
    private String roomId = "";
    private String id = "";
    private long virtualCoin;
    private NoMoneyListener noMoneyListener;

    public SendGiftPop(Context context, RoomBean roomBean, AnchorBean anchorBean) {
        super(context);
        if (roomBean != null && anchorBean != null) {
            this.roomId = roomBean.getRoomIdStr();
            this.id = anchorBean.getIdStr();
            UserInfoUtil.loadUserInfo();
            setVirtualCoin(UserInfoUtil.mUser.getVirtualCoin());
        }
        mBtnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noMoneyListener.goToPay();
            }
        });

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

        mPageGift.setCountNum(4, 2);

        mPageGift.setOnItemClickListener(new DiamondsPagerLayout.OnItemClickListener() {
            @Override
            public void onItemClick(Object o) {
                currBean = (GiftBean) o;
            }
        });

        loadData();
    }

    @OnClick(R.id.btn_buy)
    void onClickSend() {
        if (currBean != null) {
            try {
                //Android端控制余额不足，其实服务端也做了处理，并受到IM type为16的通知，此判断可以去掉
//                long giftPrice = (long) currBean.getVirtualCoin();
//                if (virtualCoin - giftPrice < 0) {
//                    noMoneyListener.hasNoMoney();
//                    return;
//                }
                new ProfitModelImpl().sendGift(currBean.getIdStr(), id, roomId, new DataCallback<ReturnGiftBean>() {
                    @Override
                    public void onSuccess(ReturnGiftBean bean) {
                        if (bean.isSuccess()) {
//                            ToastUtils.ToastMessage(mContext, mContext.getResources().getString(R.string.toast_send_gift_success));
//                            virtualCoin -= giftPrice;
                            setVirtualCoin(bean.getVirtualCoin());
                        } else {
                            noMoneyListener.hasNoMoney();
                            ToastUtils.ToastMessage(mContext, mContext.getResources().getString(R.string.toast_send_gift_fail));
                        }
                    }

                    @Override
                    public void onError(int status, String errMsg) {
                        ToastUtils.ToastMessage(mContext, errMsg);
                    }
                });
                if (currBean.getType() == 1) {
                    this.dismiss();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setVirtualCoin(long virtualCoin) {
        this.virtualCoin = virtualCoin;
        mTvDiamonds.setText(virtualCoin + "钻石币");
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    private void loadData() {
        if (BasicsUtil.mGiftList != null && BasicsUtil.mGiftList.size() > 0) {
            if (mPageGift != null) {
                mPageGift.setData(BasicsUtil.mGiftList);
            }
        } else {
            new ProfitModelImpl().getGiftList(new DataCallback<ReturnDataBean<GiftBean>>() {
                @Override
                public void onSuccess(ReturnDataBean<GiftBean> bean) {
                    if (bean != null && bean.getList() != null) {
                        BasicsUtil.mGiftList = bean.getList();
                        if (mPageGift != null) {
                            mPageGift.setData(bean.getList());
                        }
                    }
                }

                @Override
                public void onError(int status, String errMsg) {
                    ToastUtils.ToastMessage(mContext, errMsg + "");
                }
            });
        }
    }

    public void setOnNoMoneyLinstener(NoMoneyListener noMoneyListener) {
        this.noMoneyListener = noMoneyListener;
    }

    public interface NoMoneyListener {
        void hasNoMoney();

        void goToPay();
    }
}
