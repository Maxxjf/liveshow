package com.qcloud.liveshow.constant;

import com.qcloud.qclib.FrameConfig;

/**
 * 类说明：接口Url常量
 * Author: Kuzan
 * Date: 2017/8/1 19:10.
 */
public interface UrlConstants {
    /**app请求前缀*/
    String URL_PREFIX = "fep/";
    /**带app的url前缀*/
    String URL_PREFIX_APP = URL_PREFIX + "app/";

    /**
     * 直播url
     * rtmp://10.10.22.123:1935/room/
     * */
    String STREAM_URL = "rtmp://"+AppConstants.IO_HOST+":"+AppConstants.IO_PORT+"/room/";
//    String STREAM_URL = "rtmp://10.10.22.120:1935/room/";

    /**
     * 用户有关
     * */
    /**获取验证码*/
    String GET_CODE = URL_PREFIX_APP + "login/sendCode";
    /**普通登录*/
    String LOGIN_NORMAL = URL_PREFIX_APP + "login/normalLogin";
    /**第三方登录*/
    String LOGIN_OTHER = URL_PREFIX_APP + "login/otherLogin";
    /**获取用户信息*/
    String GET_USER_INFO = URL_PREFIX_APP + "member/personalcenter";
    /**退出登录*/
    String LOGOUT = URL_PREFIX_APP + "member/logout";
    /**编辑用户*/
    String EDIT_USER = URL_PREFIX_APP + "member/edit";
    /**条款协议*/
    String CLAUSE_RULE = URL_PREFIX_APP + "basics/clauseRule";
    /**关于我们*/
    String ABOUT_US = URL_PREFIX_APP + "basics/aboutUs";
    /**获取常见问题*/
    String GET_PROBLEM_LIST = URL_PREFIX_APP + "basics/questionList";
    /**获取官方联系方式*/
    String GET_CONTACT_WAY = URL_PREFIX_APP + "member/contact";
    /**忘记密码*/
    String FORGET_PASSWORD = URL_PREFIX_APP + "login/forgetPassword";
    /**忘记密码验证码*/
    String FORGET_PASSWORD_CODE = URL_PREFIX_APP + "login/forgetPasswordSendCode";

    /**
     * 直播间有关
     * */
    /**获取热门直播间*/
    String GET_HOT_LIST = URL_PREFIX_APP + "index/hotList";
    /**获取最新直播间*/
    String GET_NEWEST_LIST = URL_PREFIX_APP + "index/newList";
    /**获取关注直播间*/
    String GET_FOLLOW_LIST = URL_PREFIX_APP + "index/attentionList";
    /**获取搜索主播间*/
    String GET_SEARCH_LIST = URL_PREFIX_APP + "index/search";
    /**获取主播间信息（分享专用）*/
    String GET_ROOM_INFO = URL_PREFIX_APP + "index/shareGetRoom";

    /**
     * 直播有关
     * */
    /**申请主播状态*/
    String GET_APPLY_STATUS = URL_PREFIX_APP + "anchor/applyStatus";
    /**获取验证码*/
    String GET_APPLY_CODE = URL_PREFIX_APP + "anchor/sendCode";
    /**提交申请*/
    String SUBMIT_APPLY = URL_PREFIX_APP + "anchor/apply";
    /**创建直播间*/
    String CREATE_LIVE = URL_PREFIX_APP + "room/create";
    /**获取我的守护列表*/
    String GET_GUARD_LIST = URL_PREFIX_APP + "room/guardList";
    /**添加守护/取消守护*/
    String IN_OUT_GRARD = URL_PREFIX_APP + "room/inOutGuard";
    /**结束直播*/
    String FINISH_LIVE = URL_PREFIX_APP + "room/finish";
    /**开始直播前获取直播消息*/
    String GET_LIVEINFO = URL_PREFIX_APP + "room/beginCreate";
    /**判断该用户身份(主播,守护,观众)*/
    String GET_USER_STATUS = URL_PREFIX_APP + "room/estimateIdentity";
    /**发礼物*/
    String SEND_GIFT = URL_PREFIX_APP + "room/sendGift";


    /**
     * 我的有关
     * */
    /**我的礼物列表*/
    String GET_GIFT_PAGE = URL_PREFIX_APP + "mine/giftPage";
    /**我的关注*/
    String GET_MY_FOLLOW = URL_PREFIX_APP + "mine/attentionPage";
    /**我的粉丝*/
    String GET_MY_FANS = URL_PREFIX_APP + "mine/fansPage";
    /**我的黑名单*/
    String GET_MY_BLACKLIST = URL_PREFIX_APP + "mine/blackPage";
    /**关注/取消关注*/
    String ATTENTION = URL_PREFIX_APP + "mine/attention";
    /**移出黑名单*/
    String IN_OUT_BLACKLIST = URL_PREFIX_APP + "mine/inOutBlacklist";
    /**会员等级*/
    String GET_MEMBER_GRADE = URL_PREFIX_APP + "mine/memberGrade";
    /**主播等级*/
    String GET_ANCHOR_GRADE = URL_PREFIX_APP + "mine/anchorGrade";

    /**
     * 收益有关
     * */
    /**获取钻石币充值套餐列表*/
    String GET_PAY_MEAL_LIST = URL_PREFIX_APP + "basics/payMealList";
    /**获取礼物列表*/
    String GET_GIFT_LIST = URL_PREFIX_APP + "basics/giftList";
    /**是否已设置提现密码*/
    String IS_SET_PASSWORD = URL_PREFIX_APP + "member/isSetWithdrawPassword";
    /**设置提现密码获取验证码*/
    String GET_CODE_BY_SET_PASSWORD = URL_PREFIX_APP + "member/settingSendCode";
    /**设置提现密码*/
    String SET_WITHDRAW_CASH_PASSWORD = URL_PREFIX_APP + "member/settingWithdrawPassword";
    /**重置提现密码获取验证码*/
    String GET_CODE_BY_RESET_PASSWORD = URL_PREFIX_APP + "member/resetSendCode";
    /**重置提现密码*/
    String RESET_WITHDRAW_CASH_PASSWORD = URL_PREFIX_APP + "member/resetWithdrawPassword";
    /**获取我的收益*/
    String GET_MY_PROFIT = URL_PREFIX_APP + "mine/myEarnings";
    /**获取我的收益记录*/
    String GET_PROFTI_RECORD = URL_PREFIX_APP + "mine/earningsDetail";
    /**绑定分佣关系*/
    String SUBMIT_BINDING = URL_PREFIX_APP + "index/bindingGeneralizeRelation";
    /**提现*/
    String WITHDRAW_CARD = URL_PREFIX_APP + "member/withdraw2card";



    /**
     * 文件有关
     * */
    /**上传图片*/
    String UPLOAD_FILE = URL_PREFIX_APP + "file/add";
    /**下载文件*/
    String DOWNLOAD_FILE ="";

    /**
     * 分享页面url
     */
//    String SHARP_LIVR_URL= FrameConfig.server+"fep/app/share/shareLive";
    String SHARP_LIVR_URL = FrameConfig.server+"fep/app/share/shareLive";
    String SHARP_Generalize_URL= FrameConfig.server+"fep/app/share/shareGeneralize";
}
