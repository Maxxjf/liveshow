package com.qcloud.liveshow.enums;

/**
 * 类说明：Netty响应类型
 * Author: Kuzan
 * Date: 2017/11/1 15:30.
 */
public enum NettyActionType {
    AUTH                    (0, "鉴权"),
    GROUP_CHAT              (1, "群聊"),
    PRIVATE_CHAT            (2, "私聊"),
    GIVING_GIFTS            (3, "送礼物"),
    PULL_THE_BLACK          (4, "拉黑"),
    NO_SPEAKING             (5, "禁止发言"),
    UN_NO_SPEAKING          (6, "解除禁止发言"),
    KICK_OUT_ROOM           (7, "踢出房间"),
    GUARD                   (8, "设置守护"),
    UN_GUARD                (9, "解除守护"),
    IN_ROOM                 (10, "进入房间"),
    OUT_ROOM                (11, "退出房间"),
    GET_NOT_READ_MESSAGE    (102, "获取未发送私聊信息"),
    GET_PRIVATE_CHAT_LIST   (104, "获取私聊会话列表"),
    USER_REMOVE_GROUP_CHAT  (203, "有用户从直播室群聊退出"),
    USER_ADD_GROUP_CHAT     (204, "有用户加入直播室群聊");


    private int key;
    private String value;

    NettyActionType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static NettyActionType valueOf(int key) {
        switch (key) {
            case 0:
                return AUTH;
            case 1:
                return GROUP_CHAT;
            case 2:
                return PRIVATE_CHAT;
            case 3:
                return GIVING_GIFTS;
            case 4:
                return PULL_THE_BLACK;
            case 5:
                return NO_SPEAKING;
            case 6:
                return UN_NO_SPEAKING;
            case 7:
                return KICK_OUT_ROOM;
            case 8:
                return GUARD;
            case 9:
                return UN_GUARD;
            case 10:
                return IN_ROOM;
            case 11:
                return OUT_ROOM;
            case 102:
                return GET_NOT_READ_MESSAGE;
            case 104:
                return GET_PRIVATE_CHAT_LIST;
            case 203:
                return USER_REMOVE_GROUP_CHAT;
            case 204:
                return USER_ADD_GROUP_CHAT;
            default:
                return AUTH;
        }
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
