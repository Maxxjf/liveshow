package com.qcloud.liveshow;

import android.Manifest;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.capture.camera.CameraTouchHelper;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.qcloud.liveshow.constant.CameraConstants;
import com.qcloud.liveshow.enums.BeautyUiEnum;
import com.qcloud.liveshow.widget.cameraview.CameraHintView;
import com.qcloud.qclib.permission.PermissionsManager;
import com.qcloud.qclib.toast.ToastUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class TestActivity extends AppCompatActivity {
    // 权限申请
    private String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.RECORD_AUDIO};
    CameraHintView mCameraHint;

    GLSurfaceView mCameraPreview;
    private KSYStreamer mStreamer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mCameraPreview= (GLSurfaceView) findViewById(R.id.camera_preview);
        mCameraHint= (CameraHintView) findViewById(R.id.camera_hint);
        requestPermission();
        initCamera();

    }
    /**
     * TODO 分三步逻辑走：
     * 1.有很多人理解不了A这句话，其实可以翻译成：“反正我不告诉你，你也不会知道答案”
     * 分析A说的话，因为5月19,6月18是特征数，A知道不可能是18,19号。所以也不可能是相应的5,6月份
     * 2.B听后排除了5,6月份，马上可以离开了，说明是7,8月份的唯一日期数，不可能是14号，所以剩下7.16  8.15  8.17
     * 3.A看到B离开才能知道答案，因为7月有一个选择，8月2个选择。 所以A才能知道最后答案 7.16
     *  综上所述，答案是7.16。如果看不懂文字，可以用笔和纸去写日期，细心琢磨排除选项。
     *
     * */
    private void requestPermission() {
        final PermissionsManager manager = new PermissionsManager(this);
        manager.setLogging(true);
        manager.request(PERMISSIONS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {

                    }
                });
    }
    /**
     * 初始化摄像头
     */
    private void initCamera() {
//        mMainHandler = new Handler();

        mStreamer = new KSYStreamer(this);

        /**设置推流URL*/
//        mStreamer.setUrl(mUrl);
        mStreamer.setDisplayPreview(mCameraPreview);
        /**禁用后台重复上次帧*/
        mStreamer.setEnableRepeatLastFrame(false);
        /**启动自动重连*/
        mStreamer.setEnableAutoRestart(true, 3000); // enable auto restart
        /**设置前置摄像头*/
        mStreamer.setCameraFacing(CameraCapture.FACING_FRONT);
        /**设置前置镜像*/
        mStreamer.setFrontCameraMirror(true);
        /**设置是否静音*/
        mStreamer.setMuteAudio(false);
        /**设置耳返*/
        mStreamer.setEnableAudioPreview(false);
        /**设置美颜*/
        mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(), BeautyUiEnum.PRO4.getKey());
        mStreamer.getImgTexFilterMgt().setOnErrorListener(new ImgTexFilterBase.OnErrorListener() {
            @Override
            public void onError(ImgTexFilterBase filter, int errno) {
                ToastUtils.ToastMessage(TestActivity.this, R.string.toast_does_not_support_beauty);
                mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(),
                        ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE);
            }
        });
//        List<ImgFilterBase> filters = mStreamer.getImgTexFilterMgt().getFilter();
//        if (filters != null && !filters.isEmpty()) {
//            final ImgFilterBase filter = filters.get(0);
//            filter.setGrindRatio(0.5f);     // 磨皮0~1.0f
//            filter.setWhitenRatio(0.5f);    // 美白0~1.0f
//            filter.setRuddyRatio(0.0f);     // 红润0~1.0f
//        }
        /**设置采集帧率为24*/
        mStreamer.setPreviewFps(CameraConstants.FRAME_RATE);
        mStreamer.setTargetFps(CameraConstants.FRAME_RATE);
        /**设置视频码率(Max)为800*/
        mStreamer.setVideoKBitrate(CameraConstants.VIDEO_BITRATE * 3 / 4, CameraConstants.VIDEO_BITRATE, CameraConstants.VIDEO_BITRATE / 4);
        /**设置音频码率(Max)为48*/
        mStreamer.setAudioKBitrate(CameraConstants.AUDIO_BITRATE);
        /**设置采集分辨率*/
        mStreamer.setCameraCaptureResolution(CameraConstants.CAPTURE_RESOLUTION);
        /**设置预览分辨率*/
        mStreamer.setPreviewResolution(CameraConstants.PREVIEW_RESOLUTION);
        /**设置推流分辨率*/
        mStreamer.setTargetResolution(CameraConstants.VIDEO_RESOLUTION);
        /**设置屏幕状态*/
        mStreamer.setRotateDegrees(0);
        /**设置编码类型*/
        mStreamer.setVideoCodecId(CameraConstants.ENCODE_TYPE);
        /**设置编码方式*/
        mStreamer.setEncodeMethod(CameraConstants.ENCODE_METHOD);
        /**设置编码模式(硬编要设为0)*/
        mStreamer.setVideoEncodeScene(CameraConstants.ENCODE_SCENE);
        /**设置编码性能*/
        mStreamer.setVideoEncodeProfile(CameraConstants.ENCODE_PROFILE);
        /**设置单双声道*/
        mStreamer.setAudioChannels(2);

//        /**信息打印*/
//        mStreamer.setOnInfoListener(mOnInfoListener);
//        /**错误信息*/
//        mStreamer.setOnErrorListener(mOnErrorListener);
//        /**日志打印*/
//        mStreamer.setOnLogEventListener(mOnLogEventListener);

        /**触摸对焦和变焦支持*/
        CameraTouchHelper cameraTouchHelper = new CameraTouchHelper();
        cameraTouchHelper.setCameraCapture(mStreamer.getCameraCapture());
        mCameraPreview.setOnTouchListener(cameraTouchHelper);
        /**设置相机提示视图显示焦点矩形和缩放比例*/
        cameraTouchHelper.setCameraHintView(mCameraHint);

        startCameraPreview();
    }
    /**
     * 开始摄像头工作
     */
    private void startCameraPreview() {
        mStreamer.startCameraPreview();
//            startStream();
        mStreamer.startStream();
    }
}
