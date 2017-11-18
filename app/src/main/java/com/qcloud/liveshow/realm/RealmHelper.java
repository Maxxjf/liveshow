package com.qcloud.liveshow.realm;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmObject;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * 类说明：realm数据处理
 * Author: Kuzan
 * Date: 2017/10/30 17:23.
 */
public class RealmHelper<T extends RealmObject> {
    public static final String DB_NAME = "LiveShow.realm";
    public Realm mRealm;
    private RealmAsyncTask mTask;
    private RealmAsyncTask mDelTask;

    public RealmHelper() {
        if (mRealm==null){
            mRealm = Realm.getDefaultInstance();
        }
    }

    /**
     *
     *      新增与更新
     *     @param bean 继承RealmObject的实体类
     * */
    public void addOrUpdateBean(final T bean) {
        mTask = mRealm.executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(bean), () -> Timber.e("添加或更新成功"),
                error -> Timber.e("添加或更新失败"));
    }

    /**
     * @param c 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *      删除
     * */
    public void delBeanById(Class c,String fieldName,final long id) {
        if (id < 0) {
            return;
        }
        T bean = (T) mRealm.where(c).equalTo(fieldName, id).findFirst();

        if (bean != null) {
            mRealm.beginTransaction();
            bean.deleteFromRealm();
            mRealm.commitTransaction();
        }
    }

    /**
     * @param c 继承RealmObject的实体类
     *      查找所有
     * */
    public List<T> queryBeans(Class c) {
        RealmResults<T> list = mRealm.where(c).findAll();

        return mRealm.copyFromRealm(list);
    }

    /**
     * 查找
     * @param c 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *
     * */
    public T queryBeanById(Class c,String fieldName,final String id) {
        return (T) mRealm.where(c).equalTo(fieldName, id).findFirst();
    }

    /**
     * 查找
     * @param c 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *
     * */
    public T queryBeanById(Class c,String fieldName,final long id) {
        return (T) mRealm.where(c).equalTo(fieldName, id).findFirst();
    }

    /**
     * 查找所有列表
     * @param c 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param vaule 数据库对应的值
     *
     * */
    public List<T> queryListById(Class c, String fieldName, String vaule) {
        RealmResults<T> list=  mRealm.where(c).equalTo(fieldName, vaule).findAll();
        return mRealm.copyFromRealm(list);
    }


}
