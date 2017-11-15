package com.qcloud.liveshow.ui.home.presenter.impl;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyChatListBean;
import com.qcloud.liveshow.model.IIMModel;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.ui.home.presenter.IMessageListPresenter;
import com.qcloud.liveshow.ui.home.view.IMessageListView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.rxbus.Bus;
import com.qcloud.qclib.rxbus.BusProvider;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 类说明：消息列表
 * Author: Kuzan
 * Date: 2017/8/30 11:21.
 */
public class MessageListPresenterImpl extends BasePresenter<IMessageListView> implements IMessageListPresenter {

    private IIMModel mModel;
    private RealmHelper myRealmHelper;
    private Bus mEventBus = BusProvider.getInstance();
    public MessageListPresenterImpl() {
        mModel = new IMModelImpl();
        myRealmHelper = new RealmHelper<MemberBean>();
        mEventBus.register(this);
        initRxBusEvent();
    }

    private void initRxBusEvent() {
        mEventBus.registerSubscriber(this, mEventBus.obtainSubscriber(RxBusEvent.class, new Consumer<RxBusEvent>() {
            @Override
            public void accept(@NonNull RxBusEvent rxBusEvent) throws Exception {
                if (mView != null) {
                    switch (rxBusEvent.getType()) {
                        case R.id.netty_get_chat_list_success:
                            NettyChatListBean bean = (NettyChatListBean) rxBusEvent.getObj();
                            List<MemberBean> memberBeans = bean.getList();
                            for (int i=0;i<memberBeans.size();i++){
                                myRealmHelper.addOrUpdateBean(memberBeans.get(i));
                            }
                            if (bean != null && bean.getList() != null) {
                                mView.replaceList(bean.getList());
                            } else {
                                mView.showEmptyView("暂无数据");
                            }
                            break;
                        case R.id.netty_get_chat_list_failure:
                            mView.showEmptyView((String) rxBusEvent.getObj());
                            break;
                    }
                }
            }
        }));
    }

    public void getAllList(){
        List<MemberBean> memberBeans=myRealmHelper.queryBeans(MemberBean.class);
        if (memberBeans != null && memberBeans.size() != 0) {
            mView.replaceList(memberBeans);
        } else {
            mView.showEmptyView("暂无数据");
        }
    }

    /**
     * 获取会话列表
     *
     * @time 2017/11/2 11:01
     */
    @Override
    public void getChatList() {
        mModel.getChatList();
    }

    public void onDestroy() {
        if (mEventBus != null) {
            mEventBus.unregister(this);
            mEventBus = null;
        }
    }
}
