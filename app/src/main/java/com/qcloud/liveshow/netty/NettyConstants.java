package com.qcloud.liveshow.netty;

/**
 * 类说明：常量
 * Author: Kuzan
 * Date: 2017/11/1 12:08.
 */
public class NettyConstants {
    public static class TYPE {
        public static class REQUEST {
            public static final int PING = 0;
            public static final int LOGIN = 4;
            public static final int REQUEST = 5;
        }

        public static class RESPONSE {
            public static final int ANSWER = 2;
            public static final int RESPONSE = 5;
            public static final int LOGOUT = -4;
        }
    }

    public static class PARAMS {
        public static class RESPONSE {
            public static final String TYPE = "action_type";
            public static final String CODE = "code";
            public static final String REPLY = "reply";
            public static final String ID = "uuid";
        }
    }

    public static class CODE {
        public static class RESPONSE {
            public static final int SUCCESS = 200;
            public static final int FAIL = 500;
        }
    }
}
