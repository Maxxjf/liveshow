package com.qcloud.liveshow.realm;

import android.annotation.SuppressLint;

import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.utils.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import timber.log.Timber;

/**
 * 类说明：realm数据处理
 * Author: Kuzan
 * Date: 2017/10/30 17:23.
 */
@SuppressLint("StaticFieldLeak")
public class RealmHelper {
    private static final String DB_NAME = "LiveShow_%s.realm";
    private static RealmHelper mHelper;
    private static Realm mRealm;     // mRealm可能为空，注意判断

    /**
     * 使用单例模式
     * */
    public static synchronized RealmHelper getInstance() {
        if (mHelper == null) {
            mHelper = new RealmHelper();
        }
        return mHelper;
    }

    public RealmHelper() {
        initRealm();
    }

    /**
     * 初始化Realm
     * */
    public void initRealm() {
        String userId = "";
        if (UserInfoUtil.mUser != null) {
            userId = UserInfoUtil.mUser.getIdStr();
        }
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(String.format(DB_NAME, userId))
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(AppConstants.REALM_VERSION)
                .build();

        try {
            mRealm = Realm.getInstance(configuration);
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(e.toString());
        }
    }

    /**
     * 新增与更新
     *
     * @param bean 继承RealmObject的实体类
     * */
    public <T extends RealmObject> void addOrUpdateBean(final T bean) {
        if (mRealm != null) {
            mRealm.executeTransactionAsync(
                    realm -> realm.copyToRealmOrUpdate(bean),
                    () -> Timber.e("添加或更新成功"),
                    error -> Timber.e("添加或更新失败"));
        } else {
            Timber.e("realm is null");
        }
    }

    /**
     * 删除对象(表)
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     * */
    public <T extends RealmObject> void delBeanById(Class<T> clazz, String fieldName, final long id) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return;
        }

        T bean = mRealm.where(clazz).equalTo(fieldName, id).findFirst();

        if (bean != null) {
            mRealm.beginTransaction();
            bean.deleteFromRealm();
            mRealm.commitTransaction();
        }
    }

    /**
     * 查找所有
     *
     * @param clazz 继承RealmObject的实体类
     * */
    public <T extends RealmObject> List<T> queryBeans(Class<T> clazz) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return new ArrayList<>();
        }
        RealmResults<T> list = mRealm.where(clazz).findAll();

        return mRealm.copyFromRealm(list);
    }

    /**
     * 查找
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *
     * */
    public <T extends RealmObject> T queryBeanById(Class<T> clazz, String fieldName, final long id) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return null;
        }
        return mRealm.where(clazz).equalTo(fieldName, id).findFirst();
    }

    /**
     * 查找
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *
     * */
    public <T extends RealmObject> T queryBeanById(Class<T> clazz, String fieldName, final String id) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return null;
        }
        return mRealm.where(clazz).equalTo(fieldName, id).findFirst();
    }

    /**
     * 查找
     *
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     * */
    public <T extends RealmObject> T queryBeanByValue(Class<T> clazz, String fieldName, final String value) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return null;
        }
        return mRealm.where(clazz).equalTo(fieldName, value).findFirst();
    }


    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     *
     * */
    public <T extends RealmObject> List<T> queryListById(Class<T> clazz, String fieldName, long id) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return new ArrayList<>();
        }
        RealmResults<T> list=  mRealm.where(clazz).equalTo(fieldName, id).findAll();
        return mRealm.copyFromRealm(list);
    }

    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param id 数据库对应的值
     * @param isAscending 是否升序，true升序，false降序@see Sort.ASCENDING
     *
     * */
    public <T extends RealmObject> List<T> queryListByIdSort(Class<T> clazz, String fieldName, long id, boolean isAscending) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return new ArrayList<>();
        }
        Sort sort = isAscending? Sort.ASCENDING : Sort.DESCENDING;
        RealmResults<T> list=  mRealm.where(clazz).equalTo(fieldName, id).findAllSorted(fieldName, sort);
        return mRealm.copyFromRealm(list);
    }

    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     *
     * */
    public <T extends RealmObject> List<T> queryListByValue(Class<T> clazz, String fieldName, String value) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return new ArrayList<>();
        }
        RealmResults<T> list=  mRealm.where(clazz).equalTo(fieldName, value).findAll();
        return mRealm.copyFromRealm(list);
    }

    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     *
     * */
    public <T extends RealmObject> List<T> queryListByValue(Class<T> clazz, String fieldName, boolean value) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return new ArrayList<>();
        }
        RealmResults<T> list=  mRealm.where(clazz).equalTo(fieldName, value).findAll();
        return mRealm.copyFromRealm(list);
    }

    /**
     * 查找所有列表
     * @param clazz 继承RealmObject的实体类
     * @param fieldName 数据库对应的字段
     * @param value 数据库对应的值
     *
     * */
    public <T extends RealmObject> List<T> queryListByValue(Class<T> clazz, String fieldName, String value, String sortFieldName) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return new ArrayList<>();
        }
        RealmResults<T> list = mRealm.where(clazz).equalTo(fieldName, value).findAllSorted(sortFieldName);
        return mRealm.copyFromRealm(list);
    }

    /**
     * 更新成员对象
     *  查询出来的对象不可以临时改变其数据, 否则会报错:
     *  java.lang.IllegalStateException: Changing Realm data can only be done from inside a transaction.
     *  只能在transaction内执行操作
     *
     * @param id 成员id
     * */
    public void updateMember(long id) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return;
        }

        MemberBean bean = mRealm.where(MemberBean.class).equalTo("id", id).findFirst();
        if (bean != null) {
            mRealm.beginTransaction();
            bean.setRead(false);
            mRealm.commitTransaction();
        }
    }


    /**
     *
     * @param chatId
     * */
    public void updateMessageStatus(String chatId,int messageStatus) {
        if (mRealm == null) {
            Timber.e("realm is null");
            return;
        }

        NettyReceivePrivateBean bean = mRealm.where(NettyReceivePrivateBean.class).equalTo("chat_id", chatId).findFirst();
        if (bean != null) {
            mRealm.beginTransaction();
            bean.setSendStatus(messageStatus);
            mRealm.commitTransaction();
        }
    }


    /**
     * 关闭Realm
     * */
    public  void closeRealm() {
        if (mRealm != null){
            mRealm.close();
            mRealm = null;
        }
        mHelper = null;
    }
}
