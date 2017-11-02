package com.qcloud.liveshow.netty.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * 类说明：
 * Author: Kuzan
 * Date: 2017/11/1 11:45.
 */
public class NettyGson {
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.PROTECTED) //@protected 修饰的过滤，比如自动增长的id
                .create();
    }

    public static Gson newInstances() {
        return gson;
    }
}
