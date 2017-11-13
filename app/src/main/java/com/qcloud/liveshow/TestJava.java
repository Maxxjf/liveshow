package com.qcloud.liveshow;

import com.qcloud.qclib.utils.DateUtils;

/**
 * 类说明：${必须填}
 * Author: iceberg
 * Date: 2017/11/10.
 */
public class TestJava {
    public static void main(String args[]){
        Long time=1510276346037l;
       String s= DateUtils.translateDate(time);
        System.out.println(s);

    }
}
