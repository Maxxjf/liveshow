package com.qcloud.liveshow.realm;

import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.beans.RealmTestBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * 类说明：realm数据处理
 * Author: Kuzan
 * Date: 2017/10/30 17:23.
 */
public class RealmHelper {
    public static final String DB_NAME = "LiveShow.realm";
    private Realm mRealm;
    private RealmAsyncTask mTask;
    private RealmAsyncTask mDelTask;

    public RealmHelper() {

        mRealm = Realm.getDefaultInstance();
    }

    /**
     * 测试用
     *      新增与更新
     * */
    public void addOrUpdateBean(final RealmTestBean bean) {
        mTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bean);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.e("添加或更新成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.e("添加或更新失败");
            }
        });
    }

    /**
     * 测试用
     *      删除
     * */
    public void delBeanById(final long id) {
        if (id < 0) {
            return;
        }
        RealmTestBean bean = mRealm.where(RealmTestBean.class).equalTo("id", id).findFirst();

        if (bean != null) {
            mRealm.beginTransaction();
            bean.deleteFromRealm();
            mRealm.commitTransaction();
        }
    }

    /**
     * 测试用
     *      查找
     * */
    public RealmTestBean queryBeanById(final long id) {
        return mRealm.where(RealmTestBean.class).equalTo("id", id).findFirst();
    }

    /**
     * 测试用
     *      查找所有
     * */
    public List<RealmTestBean> queryBeans() {
        RealmResults<RealmTestBean> list = mRealm.where(RealmTestBean.class).findAll();

        // 增序排列
        list = list.sort("id");

        return mRealm.copyFromRealm(list);
    }
    /**
     *
     *      新增与更新私聊消息
     * */
    public void addOrUpdateNettyReceivePrivateBean(final NettyReceivePrivateBean bean) {
        mTask = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(bean);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.e("添加或更新成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.e("添加或更新失败");
            }
        });
    }

    /**
     * 删除私聊消息
     * */
    public void delNettyReceivePrivateBeanById(final long from_user_id) {
        if (from_user_id < 0) {
            return;
        }
        NettyReceivePrivateBean bean = mRealm.where(NettyReceivePrivateBean.class).
                equalTo("from_user_id", from_user_id).findFirst();

        if (bean != null) {
            mRealm.beginTransaction();
            bean.deleteFromRealm();
            mRealm.commitTransaction();
        }
    }

    /**
     *  根据id查找私聊消息
     *
     * @param from_user_id
     * */
    public List<NettyReceivePrivateBean> queryNettyReceivePrivateBeanById(final String from_user_id) {
      RealmResults<NettyReceivePrivateBean> list=  mRealm.where(NettyReceivePrivateBean.class).equalTo("from_user_id", from_user_id).findAll();
        return mRealm.copyFromRealm(list);
    }

    /**
     *
     *      查找所有私聊消息
     * */
    public List<NettyReceivePrivateBean> queryNettyReceivePrivateBean() {
        RealmResults<NettyReceivePrivateBean> list = mRealm.where(NettyReceivePrivateBean.class).findAll();

        // 增序排列
        list = list.sort("from_user_id");

        return mRealm.copyFromRealm(list);
    }
}
