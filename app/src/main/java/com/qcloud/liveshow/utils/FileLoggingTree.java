package com.qcloud.liveshow.utils;

import android.util.Log;

import com.qcloud.qclib.utils.FileUtils;
import com.qcloud.qclib.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import timber.log.Timber;

/**
 * 类说明：保存日志文档
 * Author: Kuzan
 * Date: 2017/8/8 17:22.
 */
public class FileLoggingTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        String cacheDiaPath = FileUtils.getSdCardPath();
        if (StringUtils.isEmptyString(cacheDiaPath)) {
            return;
        }
        File file = new File(cacheDiaPath + "/log.txt");
        Log.v("dyp", "file.path:" + file.getAbsolutePath() + ",message:" + message);
        FileWriter writer;
        BufferedWriter bufferedWriter = null;
        try {
            writer = new FileWriter(file);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(message);
            bufferedWriter.flush();

        } catch (IOException e) {
            Log.v("dyp", "存储文件失败");
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
