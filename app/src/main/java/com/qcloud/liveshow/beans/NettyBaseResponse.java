package com.qcloud.liveshow.beans;

/**
 * 类说明：NettyBaseResponse
 * Author: Kuzan
 * Date: 2017/11/1 15:46.
 */
public class NettyBaseResponse<T> {
    int action_type;        // 参见NettyActionType
    int code;               // 参见NettyResponseCode
    String uuid;            // 客户端每次数据请求附带uuid唯一标识码，服务端响应回传同样 uuid，用来区分每次的请求
    T data;                 // 发送数据的内容，必须为 key-value 类型

    public int getAction_type() {
        return action_type;
    }

    public void setAction_type(int action_type) {
        this.action_type = action_type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NettyBaseResponse{" +
                "action_type=" + action_type +
                ", code=" + code +
                ", uuid='" + uuid + '\'' +
                ", data=" + data +
                '}';
    }
}
