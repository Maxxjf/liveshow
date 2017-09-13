package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.liveshow.utils.BasicsUtil;
import com.qcloud.liveshow.widget.customview.DiamondsPagerLayout;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;

/**
 * 类说明：购买钻石币弹窗
 * Author: Kuzan
 * Date: 2017/8/26 17:01.
 */
public class BuyDiamondsPop extends BasePopupWindow {
    @Bind(R.id.page_diamonds)
    DiamondsPagerLayout mPageDiamonds;

    private DiamondsBean currBean;

    public BuyDiamondsPop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_buy_diamonds;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void initAfterViews() {

        mPageDiamonds.setCountNum(3, 2);

        mPageDiamonds.setOnItemClickListener(new DiamondsPagerLayout.OnItemClickListener() {
            @Override
            public void onItemClick(Object o) {
                currBean = (DiamondsBean) o;
                if (currBean != null) {
                    ToastUtils.ToastMessage(mContext, currBean.getName());
                }
            }
        });

        loadData();
    }

    @Override
    protected void init() {
        super.init();
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    private void loadData() {
        if (BasicsUtil.mDiamondsList != null && BasicsUtil.mDiamondsList.size() > 0) {
            if (mPageDiamonds != null) {
                mPageDiamonds.setData(BasicsUtil.mDiamondsList);
            }
        } else {
            new ProfitModelImpl().getDiamondsList(new DataCallback<ReturnDataBean<DiamondsBean>>() {
                @Override
                public void onSuccess(ReturnDataBean<DiamondsBean> bean) {
                    if (bean != null && bean.getList() != null) {
                        BasicsUtil.mDiamondsList = bean.getList();
                        if (mPageDiamonds != null) {
                            mPageDiamonds.setData(bean.getList());
                        }
                    }
                }

                @Override
                public void onError(int status, String errMsg) {
                    ToastUtils.ToastMessage(mContext, errMsg+"");
                }
            });
        }
    }
}
