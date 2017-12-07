package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.beans.AnchorGradeBean;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.MemberGradeBean;
import com.qcloud.liveshow.beans.MyGiftsBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.net.IMineApi;
import com.qcloud.qclib.beans.ReturnDataBean;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.network.BaseApi;
import com.qcloud.qclib.network.OkGoRequest;

/**
 * 类说明：我的有关
 * Author: Kuzan
 * Date: 2017/9/12 15:42.
 */
public class MineModelImpl implements IMineModel {
    /**请求参数*/
    private HttpParams mParams;

    public MineModelImpl() {
        mParams = OkGoRequest.getAppParams();
        OkGoRequest.createService();
    }

    /**
     * 获取我的礼物列表
     *
     * @param pageNum
     * @param pageSize
     * @param callback
     *
     * @time 2017/9/12 15:19
     */
    @Override
    public void getGiftPage(int pageNum, int pageSize, DataCallback<MyGiftsBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        BaseApi.dispose(IMineApi.getGiftPage(mParams), callback);
    }

    /**
     * 获取我的关注/我的粉丝/我的黑名单
     *
     * @param type 1我的关注 2我的粉丝 3我的黑名单
     * @param pageNum
     * @param pageSize
     * @param callback
     *
     * @time 2017/9/12 16:43
     */
    @Override
    public void getAttentionPage(int type, int pageNum, int pageSize, DataCallback<ReturnDataBean<MemberBean>> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("pageNum", pageNum);
        mParams.put("pageSize", pageSize);

        if (type == StartFansEnum.MyFans.getKey()) {
            BaseApi.dispose(IMineApi.getFansPage(mParams), callback);
        } else if (type == StartFansEnum.Blacklist.getKey()) {
            BaseApi.dispose(IMineApi.getBlacklistPage(mParams), callback);
        } else {
            BaseApi.dispose(IMineApi.getAttentionPage(mParams), callback);
        }
    }

    /**
     * 关注/取消关注 加入/移出黑名单
     *
     * @param type          1我的关注 2我的粉丝 3我的黑名单
     * @param id            粉丝id
     * @param isAttention   true 关注/加入黑名单 false 取消关注/移出黑名单
     * @param callback
     *
     * @time 2017/9/12 16:45
     */
    @Override
    public void submitAttention(int type, long id, boolean isAttention, DataCallback<ReturnEmptyBean> callback) {
        mParams = OkGoRequest.getAppParams();
        mParams.put("id", id);

        if (type == StartFansEnum.Blacklist.getKey()) {
            mParams.put("isBlack", isAttention);
            BaseApi.dispose(IMineApi.inOutBlacklist(mParams), callback);
        } else {
            mParams.put("isAttention", isAttention);
            BaseApi.dispose(IMineApi.attention(mParams), callback);
        }
    }

    /**
     * 会员等级
     *
     * @time 2017/9/15 10:07
     */
    @Override
    public void getMemberGrade(DataCallback<MemberGradeBean> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IMineApi.getMemberGrade(mParams), callback);
    }

    /**
     * 主播等级
     *
     * @time 2017/9/15 10:07
     */
    @Override
    public void getAnchorGrade(DataCallback<AnchorGradeBean> callback) {
        mParams = OkGoRequest.getAppParams();

        BaseApi.dispose(IMineApi.getAnchorGrade(mParams), callback);
    }

    @Override
    public void forgetPasswordCode(String loginAccount,String email, DataCallback<GetCodeResBean> callback) {
        mParams=OkGoRequest.getAppParams();
        mParams.put("loginAccount",loginAccount);
        mParams.put("email",email);
        BaseApi.dispose(IMineApi.forgetPasswordCode(mParams),callback);
    }

    @Override
    public void forgetPassword(String loginAccount,String email, String code, String newPassword, DataCallback<ReturnEmptyBean> callback) {
        mParams=OkGoRequest.getAppParams();
        mParams.put("loginAccount",loginAccount);
        mParams.put("email",email);
        mParams.put("code",code);
        mParams.put("newPassword",newPassword);
        BaseApi.dispose(IMineApi.forgetPassWord(mParams),callback);
    }
}
