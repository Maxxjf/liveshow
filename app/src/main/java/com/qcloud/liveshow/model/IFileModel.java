package com.qcloud.liveshow.model;

import com.qcloud.qclib.callback.UploadCallback;

import java.util.List;

/**
 * 类说明：文件有关
 * Author: Kuzan
 * Date: 2017/9/7 15:59.
 */
public interface IFileModel {
    /**上传图片*/
    void uploadFile(List<String> paths, UploadCallback callback);
}
