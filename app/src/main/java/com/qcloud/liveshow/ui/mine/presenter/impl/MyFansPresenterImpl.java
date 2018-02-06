package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.mine.presenter.IMyFansPresenter;
import com.qcloud.liveshow.ui.mine.view.IMyFansView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：我的关注列表
 * Author: Kuzan
 * Date: 2017/8/18 11:15.
 */
public class MyFansPresenterImpl extends BasePresenter<IMyFansView> implements IMyFansPresenter {

    private IMineModel mModel;

    public MyFansPresenterImpl() {
        mModel = new MineModelImpl();
    }

    /**
     * 加载我的关注/我的粉丝/我的黑名单
     *
     * @param type 1我的关注 2我的粉丝 3我的黑名单
     *
     * @time 2017/9/14 10:18
     */
    @Override
    public void getMyAttention(int type, int pageNum, int PageSize) {
        mModel.getAttentionPage(type, pageNum, PageSize, new DataCallback<ReturnDataBean<MemberBean>>() {
            @Override
            public void onSuccess(ReturnDataBean<MemberBean> bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    if (bean.getPageNum() == 1) {
                        mView.replaceList(bean.getList(), bean.isNext());
                    } else {
                        mView.addListAtEnd(bean.getList(), bean.isNext());
                    }
                } else {
                    mView.loadErr(true, "暂无数据");
                    mView.showEmptyView();
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.showEmptyView();
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }

    /**
     * 关注/取消息关注 拉黑/移出拉黑
     *
     * @param type 1我的关注 2我的粉丝 3我的黑名单
     * */
    @Override
    public void submitAttention(int type, long id, boolean isAttention) {
        mModel.submitAttention(type, id, isAttention, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                UserInfoUtil.loadUserInfo();
                if (mView != null) {
                    mView.isAttentionSuccess();
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }
}
