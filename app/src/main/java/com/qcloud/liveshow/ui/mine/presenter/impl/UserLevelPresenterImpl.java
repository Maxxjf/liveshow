package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.qcloud.liveshow.beans.MemberGradeBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.enums.ClauseRuleEnum;
import com.qcloud.liveshow.model.IMineModel;
import com.qcloud.liveshow.model.impl.MineModelImpl;
import com.qcloud.liveshow.ui.mine.presenter.IUserLevelPresenter;
import com.qcloud.liveshow.ui.mine.view.IUserLevelView;
import com.qcloud.qclib.FrameConfig;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.callback.DataCallback;

/**
 * 类说明：用户等级
 * Author: Kuzan
 * Date: 2017/9/2 17:55.
 */
public class UserLevelPresenterImpl extends BasePresenter<IUserLevelView> implements IUserLevelPresenter {

    private IMineModel mModel;

    public UserLevelPresenterImpl() {
        mModel = new MineModelImpl();
    }

    @Override
    public void getMemberGrade() {
        mModel.getMemberGrade(new DataCallback<MemberGradeBean>() {
            @Override
            public void onSuccess(MemberGradeBean bean) {
                if (mView == null) {
                    return;
                }
                if (bean != null) {
                    mView.refreshData(bean);
                } else {
                    mView.loadErr(true, "获取会员等级失败");
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.loadErr(true, errMsg);
                }
            }
        });
    }
    /**
     * 获取免责条款
     *
     * @param type 0:关于我们; 1:注册免责; 2:主播免责; 3:提现条款; 4:充值条款; 5:提现规则; 6:会员等级规则
     * */
    @Override
    public void getRuleWebUrl(int type) {
        StringBuffer webUrl = new StringBuffer();
        String baseUrl = FrameConfig.server;
        int language = 0;   // TODO 获取系统语言

        webUrl.append(baseUrl);
        if (type == ClauseRuleEnum.AboutUs.getKey()) {
            webUrl.append(UrlConstants.ABOUT_US);
        } else {
            webUrl.append(UrlConstants.CLAUSE_RULE);
        }
        webUrl.append("?language=");
        webUrl.append(language);
        webUrl.append("&type=");
        webUrl.append(type);

        mView.displayWeb(webUrl.toString());
    }
}
