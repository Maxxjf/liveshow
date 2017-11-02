package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.beans.NettyChatListBean;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.ui.home.presenter.IMessageListPresenter;
import com.qcloud.liveshow.ui.home.view.IMessageListView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：消息列表
 * Author: Kuzan
 * Date: 2017/8/30 11:21.
 */
public class MessageListPresenterImpl extends BasePresenter<IMessageListView> implements IMessageListPresenter {

    private IIMModel mModel;
    private RealmHelper mHelper;

    public MessageListPresenterImpl() {
        mModel = new IMModelImpl();
        mHelper = new RealmHelper();
    }



    /**
     * 获取会话列表
     *
     * @time 2017/11/2 11:01
     */
    @Override
    public void getChatList() {
        mModel.getChatList(new DataCallback<NettyChatListBean>() {
            @Override
            public void onSuccess(NettyChatListBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null && bean.getList() != null) {
                    mView.replaceList(bean.getList());
                } else {
                    mView.loadErr(true, "暂无会话列表");
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
