package com.qcloud.liveshow.netty.handler;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.NettyBaseResponse;
import com.qcloud.liveshow.enums.NettyResponseCode;
import com.qcloud.liveshow.netty.callback.ResponseListener;
import com.qcloud.qclib.beans.RxBusEvent;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.rxbus.BusProvider;

import java.lang.reflect.Type;

import io.netty.channel.ChannelHandlerContext;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 类说明：数据响应处理
 * Author: Kuzan
 * Date: 2017/11/1 13:42.
 */
public class ResponseHandler<T> implements ResponseListener<JsonElement> {
    private static Disposable mDisposable;
    private DataCallback<T> mCallback;
    private Type mType;

    public ResponseHandler(DataCallback<T> callback, Type type) {
        this.mCallback = callback;
        this.mType = type;
    }

    @Override
    public boolean channelRead(ChannelHandlerContext ctx, JsonElement msgConfig) throws Exception {
        Timber.i("Read Message: " + msgConfig);
        if (msgConfig != null && msgConfig.isJsonObject()) {
            dispose(msgConfig, mCallback);
            return true;
        }
        return false;
    }

    /**
     * 数据处理
     * */
    private <T> void dispose(@NonNull JsonElement jsonStr, @NonNull final DataCallback<T> callback) {

        Observable.just(jsonStr).map(new Function<JsonElement, NettyBaseResponse<T>>() {
            @Override
            public NettyBaseResponse<T> apply(@NonNull JsonElement jsonStr) throws Exception {
                if (mType == null) {
                    mType = new TypeToken<NettyBaseResponse<T>>() {}.getType();
                }

                NettyBaseResponse<T> data = new Gson().fromJson(jsonStr, mType);
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
                            case 0:
                                Timber.e("" + bean);
                                callback.onSuccess(bean.getData());
                                break;
                            case 2:
                                BusProvider.getInstance().post(RxBusEvent.newBuilder(R.id.netty_auth_fail)
                                        .setObj(NettyResponseCode.AUTH_FAIL.getValue()).build());
                                callback.onError(NettyResponseCode.AUTH_FAIL.getKey(), NettyResponseCode.AUTH_FAIL.getValue());
                                break;
                            case 1:
                            case 3:
                            case 4:
                                callback.onError(bean.getCode(), NettyResponseCode.valueOf(bean.getCode()).getValue());
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
