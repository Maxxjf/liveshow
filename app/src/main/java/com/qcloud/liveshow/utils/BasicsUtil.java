package com.qcloud.liveshow.utils;

import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.GiftBean;
import com.qcloud.liveshow.model.impl.ProfitModelImpl;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

import java.util.List;

import timber.log.Timber;

/**
 * 类说明：基础数据
 * Author: Kuzan
 * Date: 2017/9/13 16:32.
 */
public class BasicsUtil {
    /**钻石币充值套餐列表*/
    public static List<DiamondsBean> mDiamondsList;
    /**礼物列表*/
    public static List<GiftBean> mGiftList;

    /**
     * 获取钻石币充值套餐
     * */
    public static void loadDiamonds() {
        new ProfitModelImpl().getDiamondsList(new DataCallback<ReturnDataBean<DiamondsBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<DiamondsBean> bean) {
                if (bean != null && bean.getList() != null) {
                    mDiamondsList = bean.getList();
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }

    /**
     * 获取礼物充值
     * */
    public static void loadGift() {
        new ProfitModelImpl().getGiftList(new DataCallback<ReturnDataBean<GiftBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<GiftBean> bean) {
                if (bean != null && bean.getList() != null) {
                    mGiftList = bean.getList();
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                Timber.e(errMsg);
            }
        });
    }
}
