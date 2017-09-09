package com.qcloud.liveshow.net;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.RetBean;
import com.qcloud.qclib.network.RxRequest;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/7/31 16:00.
 */
public class ITestApi {

    public static Observable<RetBean> getData(HttpParams mParams) {
        Type type = new TypeToken<RetBean>(){}.getType();

        return RxRequest.request(HttpMethod.GET, "getData", "http://116.211.167.106/api/live/aggregation", type, mParams);
    }
}
