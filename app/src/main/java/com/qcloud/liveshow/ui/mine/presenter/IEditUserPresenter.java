package com.qcloud.liveshow.ui.mine.presenter;

import com.qcloud.qclib.base.BtnClickPresenter;

/**
 * 类说明：编辑用户信息
 * Author: Kuzan
 * Date: 2017/8/17 19:02.
 */
public interface IEditUserPresenter extends BtnClickPresenter {
    /**上传头像*/
    void uploadHeadImg(String path);

    /**编辑用户*/
    void edit(String headImg, String nickName, int sex, String signature);
}
