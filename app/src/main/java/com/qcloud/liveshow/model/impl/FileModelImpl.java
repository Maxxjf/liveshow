package com.qcloud.liveshow.model.impl;

import com.lzy.okgo.model.HttpParams;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.model.IFileModel;
import com.qcloud.qclib.callback.LoadFileCallback;
import com.qcloud.qclib.network.OkGoRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明：文件有关处理
 * Author: Kuzan
 * Date: 2017/9/7 16:02.
 */
public class FileModelImpl implements IFileModel {

    /**请求参数*/
    private HttpParams mParams;

    public FileModelImpl() {
        mParams = OkGoRequest.getAppParams();
        OkGoRequest.createService();
    }

    /**
     * 上传文件
     *
     * @time 2017/9/7 16:02
     */
    @Override
    public void uploadFile(List<String> paths, LoadFileCallback callback) {
        mParams = OkGoRequest.getAppParams();
        List<File> files = new ArrayList<>();
        if (paths != null && paths.size() > 0) {
            for (String path: paths) {
                files.add(new File(path));
            }

            OkGoRequest.uploadRequest("uploadFile", UrlConstants.UPLOAD_FILE, mParams, files, callback);
        }
    }
}
