package com.qcloud.liveshow.netty.handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.qcloud.liveshow.beans.NettyBaseResponse;
import com.qcloud.liveshow.enums.NettyResponseCode;
import com.qcloud.qclib.callback.NettyDataCallback;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 类说明：数据处理
 * Author: Kuzan
 * Date: 2017/11/3 17:44.
 */
public class NettyDispose {
    private static Disposable mDisposable;

    /**
     * 数据处理
     * */
    public static <T> void dispose(@NonNull JsonElement jsonStr, final Type type, @NonNull final NettyDataCallback<T> callback) {
        Observable.just(jsonStr).map(new Function<JsonElement, NettyBaseResponse<T>>() {
            @Override
            public NettyBaseResponse<T> apply(@NonNull JsonElement jsonStr) throws Exception {
                NettyBaseResponse<T> data = new Gson().fromJson(jsonStr, type);
                return data;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NettyBaseResponse<T>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Timber.e("onSubscribe");
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull NettyBaseResponse<T> bean) {
                        Timber.e("onNext");
                        switch (bean.getCode()) {
                            case 0://成功
                                Timber.e("" + bean);
                                callback.onSuccess(bean.getData(), bean.getUuid());
                                break;
                            case 1://失败
                            case 2://鉴权失败
                                Timber.e("鉴权失败");
                                callback.onError(NettyResponseCode.AUTH_FAIL.getKey(), NettyResponseCode.AUTH_FAIL.getValue());
                                break;
                            case 3://数据格式错误
                            case 4://缺少必要参数
                                callback.onError(bean.getCode(), NettyResponseCode.valueOf(bean.getCode()).getValue());
                                break;
                            case 5://已经被禁言
                            case 6://已经被拉黑
                                callback.onError(bean.getCode(),bean.getUuid());
                                break;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e("onError");
                        callback.onError(1, "数据处理失败");
                    }

                    @Override
                    public void onComplete() {
                        Timber.e("onComplete");
                    }
                });
    }

    public static void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
