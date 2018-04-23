package com.qcloud.liveshow.ui.anchor.presenter.impl;

import com.lzy.okgo.model.Progress;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.beans.LiveInfoBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.beans.SubmitStartLiveBean;
import com.qcloud.liveshow.model.IAnchorModel;
import com.qcloud.liveshow.model.IFileModel;
import com.qcloud.liveshow.model.impl.AnchorModelImpl;
import com.qcloud.liveshow.model.impl.FileModelImpl;
import com.qcloud.liveshow.ui.anchor.presenter.IPreAnchorPresenter;
import com.qcloud.liveshow.ui.anchor.view.IPreAnchorView;
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
 * 类说明：主播前的准备
 * Author: Kuzan
 * Date: 2017/9/2 11:18.
 */
public class PreAnchorPresenterImpl extends BasePresenter<IPreAnchorView> implements IPreAnchorPresenter {

    private IAnchorModel mModel;
    private IFileModel mFileModel;

    public PreAnchorPresenterImpl() {
        mModel = new AnchorModelImpl();
        mFileModel = new FileModelImpl();
    }

    @Override
    public void onBtnClick(int viewId) {
        if (mView==null){
            return;
        }
        switch (viewId) {
            case R.id.btn_exit:
                mView.onExitClick();
                break;
            case R.id.btn_switch_camera:
                mView.onSwitchCameraClick();
                break;
            case R.id.btn_begin:
                mView.onBeginAnchorClick();
                break;
            case R.id.layout_change_cover:
                mView.onChangeCoverClick();
                break;
            case R.id.img_title_clear:
                mView.onClearTitleClick();
                break;
            case R.id.img_notice_clear:
                mView.onClearNoticeClick();
                break;
            case R.id.tv_toll_standard:
                mView.onSelectDiamondsClick();
                break;
        }
    }

    @Override
    public void uploadCoverImg(String path) {
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

    @Override
    public void createLive(SubmitStartLiveBean bean) {
        mModel.createLive(bean, new DataCallback<RoomBean>() {
            @Override
            public void onSuccess(RoomBean returnEmptyBean) {
                if (mView != null) {
                    mView.createLiveSuccess(returnEmptyBean);
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

    @Override
    public void getLiveinfo() {
       mModel.getLiveinfo(new DataCallback<LiveInfoBean>() {
           @Override
           public void onSuccess(LiveInfoBean bean) {
               if (mView!=null){
                   mView.getLiveInfoSuccess(bean);
               }
           }

           @Override
           public void onError(int status, String errMsg) {
               if (mView!=null){
                   mView.getLiveInfoError(errMsg);
               }
           }
       });
    }
}
