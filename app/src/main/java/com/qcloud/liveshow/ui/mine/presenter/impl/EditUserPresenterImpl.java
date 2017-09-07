package com.qcloud.liveshow.ui.mine.presenter.impl;

import com.lzy.okgo.model.Progress;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.ReturnEmptyBean;
import com.qcloud.liveshow.model.IFileModel;
import com.qcloud.liveshow.model.IUserModel;
import com.qcloud.liveshow.model.impl.FileModelImpl;
import com.qcloud.liveshow.model.impl.UserModelImpl;
import com.qcloud.liveshow.ui.mine.presenter.IEditUserPresenter;
import com.qcloud.liveshow.ui.mine.view.IEditUserView;
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
 * 类说明：编辑用户信息
 * Author: Kuzan
 * Date: 2017/8/17 19:02.
 */
public class EditUserPresenterImpl extends BasePresenter<IEditUserView> implements IEditUserPresenter {

    private IUserModel mModel;
    private IFileModel mFileModel;

    public EditUserPresenterImpl() {
        mModel = new UserModelImpl();
        mFileModel = new FileModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {
        switch (viewId) {
            case R.id.img_user_head:
                mView.onUserHeadClick();
                break;
        }
    }

    @Override
    public void uploadHeadImg(String path) {
        List<String> paths = new ArrayList<>();
        if (StringUtils.isNotEmptyString(path)) {
            paths.add(path);
        }
        mFileModel.uploadFile(paths, new UploadCallback() {
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

    /**
     * 编辑用户
     * */
    @Override
    public void edit(String headImg, String nickName, int sex, String signature) {
        mModel.edit(headImg, nickName, sex, signature, new DataCallback<ReturnEmptyBean>() {
            @Override
            public void onSuccess(ReturnEmptyBean returnEmptyBean) {
                if (mView != null) {
                    mView.editSuccess();
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
