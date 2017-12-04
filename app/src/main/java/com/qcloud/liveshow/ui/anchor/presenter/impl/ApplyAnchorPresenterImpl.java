package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.lzy.okgo.model.Progress;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.GetCodeResBean;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.beans.SubmitApplyBean;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.model.impl.FileModelImpl;
import com.qcloud.liveshow.ui.anchor.presenter.IApplyAnchorPresenter;
import com.qcloud.liveshow.ui.anchor.view.IApplyAnchorView;
import com.qcloud.qclib.base.BasePresenter;
import com.qcloud.qclib.beans.UploadFileBean;
import com.qcloud.qclib.callback.DataCallback;
import com.qcloud.qclib.callback.UploadCallback;
import com.qcloud.qclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;

/**
 * 类说明：申请主播
 * Author: Kuzan
 * Date: 2017/8/30 16:15.
 */
public class ApplyAnchorPresenterImpl extends BasePresenter<IApplyAnchorView> implements IApplyAnchorPresenter {
    private IAnchorModel mModel;

    public ApplyAnchorPresenterImpl() {
        mModel = new AnchorModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.btn_get_code:
                mView.onGetCodeClick();
                break;
            case R.id.img_header:
                mView.onUploadHeaderClick();
                break;
            case R.id.btn_clause:
                mView.onClauseClick();
                break;
        }
    }

    @Override
    public void getCode(String email) {
        mModel.getCode(email, new DataCallback<GetCodeResBean>() {
            @Override
            public void onSuccess(GetCodeResBean bean) {
                if (mView != null && bean != null) {
                    mView.getCodeSuccess(bean.getCode());
                }
            }

            @Override
            public void onError(int status, String errMsg) {
                if (mView != null) {
                    mView.getCodeFailure(errMsg);
                }
            }
        });
    }

    @Override
    public void uploadHeadImg(String path) {
        List<String> paths = new ArrayList<>();
        if (StringUtils.isNotEmptyString(path)) {
            paths.add(path);
        }
        new FileModelImpl().uploadFile(paths, new UploadCallback() {
            @Override
            public void onAccept(String acceptStr) {
                Timber.e(acceptStr);
            }

            @Override
            public void onProgress(@NonNull Progress progress) {
                // 已上传的文件大小
                int pro = (int) (progress.fraction * 10000);
                Timber.e("progress = %d", pro);
            }

            @Override
            public void onError(String errMsg) {
                if (mView != null) {
                    mView.loadErr(true, errMsg);
                }
            }

            @Override
            public void onComplete(String completeMsg) {
                Timber.e(completeMsg);
            }

            @Override
            public void onSuccess(UploadFileBean bean) {
                if (mView != null) {
                    mView.uploadSuccess(bean);
                }
            }
        });
    }

    @Override
    public void submitApply(SubmitApplyBean bean) {
        mModel.submitApply(bean, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                if (mView != null) {
                    mView.submitSuccess();
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
}
