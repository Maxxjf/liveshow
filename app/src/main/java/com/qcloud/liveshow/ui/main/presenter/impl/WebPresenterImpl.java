package com.qcloud.liveshow.ui.main.presenter.impl;

import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.enums.ClauseRuleEnum;
import com.qcloud.liveshow.ui.main.presenter.IWebPresenter;
import com.qcloud.liveshow.ui.main.view.IWebView;
import com.qcloud.qclib.FrameConfig;
import com.qcloud.qclib.base.BasePresenter;

/**
 * 类说明：网页有关数据处理
 * Author: Kuzan
 * Date: 2017/8/8 18:50.
 */
public class WebPresenterImpl extends BasePresenter<IWebView> implements IWebPresenter {

    public WebPresenterImpl() {

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
