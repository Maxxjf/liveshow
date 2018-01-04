package com.qcloud.liveshow.ui.anchor.widget;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;

import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.capture.ViewCapture;
import com.ksyun.media.streamer.capture.camera.CameraTouchHelper;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.logstats.StatsLogReport;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.CameraConstants;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.enums.BeautyUiEnum;
import com.qcloud.liveshow.ui.anchor.presenter.impl.AnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IAnchorView;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.cameraview.CameraHintView;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.toast.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/9/1 17:21.
 */
public class AnchorActivity extends BaseActivity<IAnchorView, AnchorPresenterImpl> implements IAnchorView, SurfaceHolder.Callback {

    @Bind(R.id.camera_preview)
    GLSurfaceView mCameraPreview;
    @Bind(R.id.camera_hint)
    CameraHintView mCameraHint;

    private ViewCapture mPaintViewCapture;
    private KSYStreamer mStreamer;
    private Handler mMainHandler;

    /**
     * 是否自动推流
     */
    private boolean mAutoStart;
    private boolean mRecording = false;
    private boolean mIsFileRecording = false;
    private boolean mIsFlashOpened = false;
    private String mUrl = UrlConstants.STREAM_URL;
//    private String mDebugInfo = "";
//    private String mBgmPath = "/sdcard/test.mp3";
//    private String mLogoPath = "file:///sdcard/test.png";
//    private String mAnimatedLogoPath = "assets://ksyun.webp";
//    private String mBgImagePath = "assets://bg.jpg";
//    private String mRecordUrl = "/sdcard/rec_test.mp4";

    boolean isLiveStart = false;//直播已经开始
    RoomBean room;//直播室的id
    String notice = "";//直播公告
    /**
     * 是否支持硬编
     */
    private boolean mHWEncoderUnsupported;
    /**
     * 是否支持软编
     */
    private boolean mSWEncoderUnsupported;

    /**
     * 直播前准备页面
     */
    private PreAnchorFragment mPreFragment;
    /**
     * 直播控制页面
     */
    private AnchorFragment mControlFragment;

    @Override
    protected int initLayout() {
        return R.layout.activity_anchor;
    }

    @Override
    protected AnchorPresenterImpl initPresenter() {
        return new AnchorPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        //SystemBarUtil.hideNavBar(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mPresenter.finishLive();//一进来结束直播
        initStreamUrl();

        switchPreFragment();

        mMainHandler = new Handler();

        mStreamer = new KSYStreamer(this);
        initCamera();
//        startStream();  开始直播，用于直接测试推拉流
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraPreview.setVisibility(View.VISIBLE);
        mStreamer.setDisplayPreview(mCameraPreview);
        mStreamer.onResume();
        mCameraHint.hideAll();
    }

    private void initStreamUrl() {
        if (UserInfoUtil.mUser != null) {
            mUrl += UserInfoUtil.mUser.getIdAccount();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 关闭摄像头
     */
    protected void stopSurface() {
        if (mCameraPreview != null) {
            mCameraPreview.setVisibility(View.GONE);
            mStreamer.stopCameraPreview();
            mStreamer.stopStream();
        }
    }


    /**
     * 打开摄像头
     */
    protected void startSurface() {
        if (mCameraPreview != null) {
            mCameraPreview.setVisibility(View.VISIBLE);
            mStreamer.startCameraPreview();
            mStreamer.startStream();
        }
    }

    /**
     * 初始化摄像头
     */
    private void initCamera() {


        /**设置推流URL*/
        mStreamer.setUrl(mUrl);
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
        mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(), BeautyUiEnum.PRO3.getKey());
        mStreamer.getImgTexFilterMgt().setOnErrorListener(new ImgTexFilterBase.OnErrorListener() {
            @Override
            public void onError(ImgTexFilterBase filter, int errno) {
                ToastUtils.ToastMessage(AnchorActivity.this, R.string.toast_does_not_support_beauty);
                mStreamer.getImgTexFilterMgt().setFilter(mStreamer.getGLRender(),
                        ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE);
            }
        });
//        List<ImgFilterBase> filters = mStreamer.getImgTexFilterMgt().getFilter();
//        if (filters != null && !filters.isEmpty()) {
//            final ImgFilterBase filter = filters.get(0);
//            filter.setGrindRatio(0.5f);     // 磨皮0~1.0f
//            filter.setWhitenRatio(1.0f);    // 美白0~1.0f
//            filter.setRuddyRatio(1.0f);     // 红润0~1.0f
//        }
//
//        mStreamer.getImgTexFilterMgt().setFilter(filters);
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

        /**信息打印*/
        mStreamer.setOnInfoListener(mOnInfoListener);
        /**错误信息*/
        mStreamer.setOnErrorListener(mOnErrorListener);
        /**日志打印*/
        mStreamer.setOnLogEventListener(mOnLogEventListener);

        /**触摸对焦和变焦支持*/
        CameraTouchHelper cameraTouchHelper = new CameraTouchHelper();
        cameraTouchHelper.setCameraCapture(mStreamer.getCameraCapture());
        mCameraPreview.setOnTouchListener(cameraTouchHelper);
        /**设置相机提示视图显示焦点矩形和缩放比例*/
        cameraTouchHelper.setCameraHintView(mCameraHint);

        startCameraPreview();
    }

    /**
     * 得到公告
     */
    public String getNotice() {
        return notice;
    }

    /**
     * 设置公告
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }

    /**
     * 得到房间ID
     */
    public RoomBean getRoom() {
        return room;
    }

    /**
     * 设置房间ID
     */
    public void setRoom(RoomBean room) {
        this.room = room;
    }

    /**
     * 开始摄像头工作
     */
    private void startCameraPreview() {
        mStreamer.startCameraPreview();
        if (mAutoStart) {
            mAutoStart = false;
            startStream();
        }
    }

    /**
     * 开始直播
     */
    protected void startStream() {
        mStreamer.startStream();
        mRecording = true;
    }

    /**
     * 停止直播
     */
    protected void stopStream() {
        mStreamer.stopStream();
        mRecording = false;
        stopChronometer();
    }

    /**
     * 开始计时
     */
    private void startChronometer() {
        if (mControlFragment != null) {
            mControlFragment.startChronometer();
        }
    }

    /**
     * 停止计时
     */
    private void stopChronometer() {
        if (mRecording || mIsFileRecording) {
            return;
        }
        if (mControlFragment != null) {
            mControlFragment.stopChronometer();
        }
    }

    /**
     * 初始化直播前的准备页面
     */
    private void initPreFragment() {
        mPreFragment = PreAnchorFragment.newInstance();
        mPreFragment.setOnFragmentClickListener(new OnFragmentClickListener() {
            @Override
            public void onBtnClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_switch_camera:
                        onSwitchCameraClick();
                        break;
                    case R.id.btn_begin:
                        onBeginAnchorClick();
                        break;
                }
            }
        });
    }

    /**
     * 初始化直播中的控制页面
     */
    private void initControlFragment() {
        mControlFragment = AnchorFragment.newInstance();
        mControlFragment.setOnFragmentClickListener(new OnFragmentClickListener() {
            @Override
            public void onBtnClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_switch_camera:
                        onSwitchCameraClick();
                        break;
                    case R.id.btn_flash:
                        onFlashClick();
                        break;
                    case R.id.btn_exit:
                        finishLive();
                        break;
                }
            }
        });
    }

    /**
     * 选择直播前的准备页面
     */
    private void switchPreFragment() {
        if (mPreFragment == null) {
            initPreFragment();
        }
        replaceFragment(mPreFragment, R.id.fragment_container, false);
    }

    /**
     * 点击切换镜头
     */
    @Override
    public void onSwitchCameraClick() {
        if (isRunning) {
            mStreamer.switchCamera();
            mCameraHint.hideAll();
        }
    }

    /**
     * 点击闪光灯
     */
    @Override
    public void onFlashClick() {
        if (isRunning) {
            if (mIsFlashOpened) {
                mStreamer.toggleTorch(false);
                mIsFlashOpened = false;
            } else {
                mStreamer.toggleTorch(true);
                mIsFlashOpened = true;
            }
        }
    }

    /**
     * 点击开始直播
     */
    @Override
    public void onBeginAnchorClick() {
        if (mControlFragment == null) {
            initControlFragment();
        }
        isLiveStart = true;
        replaceFragment(mControlFragment, R.id.fragment_container, false);
        mPreFragment.detach();//防止2个Fargment复用的情况
        startStream();
    }

    /**
     * 日志打印
     */
    StatsLogReport.OnLogEventListener mOnLogEventListener = new StatsLogReport.OnLogEventListener() {
        @Override
        public void onLogEvent(StringBuilder singleLogContent) {
            Timber.i("***onLogEvent : " + singleLogContent.toString());
        }
    };

    /**
     * 直播中摄像头信息监听
     */
    KSYStreamer.OnInfoListener mOnInfoListener = new KSYStreamer.OnInfoListener() {
        @Override
        public void onInfo(int what, int msg1, int msg2) {
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_INIT_DONE:
                    Timber.d("KSY_STREAMER_CAMERA_INIT_DONE");
                    setCameraAntiBanding50Hz();
                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_STREAM_SUCCESS:
                    Timber.d("KSY_STREAMER_OPEN_STREAM_SUCCESS");
                    startChronometer();
                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_FILE_SUCCESS:
                    Timber.d("KSY_STREAMER_OPEN_FILE_SUCCESS");
                    startChronometer();
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_RECORD_STOPPED:
                    Timber.d("KSY_STREAMER_FILE_RECORD_STOPPED");
                    stopChronometer();
                    break;
                case StreamerConstants.KSY_STREAMER_FRAME_SEND_SLOW:
                    Timber.d("KSY_STREAMER_FRAME_SEND_SLOW " + msg1 + "ms");
                    ToastUtils.ToastMessage(mContext, "Network not good!");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_RAISE:
                    Timber.d("BW raise to " + msg1 / 1000 + "kbps");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_DROP:
                    Timber.d("BW drop to " + msg1 / 1000 + "kpbs");
                    break;
                default:
                    Timber.d("OnInfo: " + what + " msg1: " + msg1 + " msg2: " + msg2);
                    break;
            }
        }
    };

    /**
     * 错误监听
     */
    KSYStreamer.OnErrorListener mOnErrorListener = new KSYStreamer.OnErrorListener() {
        @Override
        public void onError(int what, int msg1, int msg2) {
            switch (what) {
                case StreamerConstants.KSY_STREAMER_ERROR_DNS_PARSE_FAILED:
                    Timber.d("KSY_STREAMER_ERROR_DNS_PARSE_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_CONNECT_FAILED:
                    Timber.d("KSY_STREAMER_ERROR_CONNECT_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_PUBLISH_FAILED:
                    Timber.d("KSY_STREAMER_ERROR_PUBLISH_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_CONNECT_BREAKED:
                    Timber.d("KSY_STREAMER_ERROR_CONNECT_BREAKED");
                    break;
                case StreamerConstants.KSY_STREAMER_ERROR_AV_ASYNC:
                    Timber.d("KSY_STREAMER_ERROR_AV_ASYNC " + msg1 + "ms");
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                    Timber.d("KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED");
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN:
                    Timber.d("KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED:
                    Timber.d("KSY_STREAMER_AUDIO_ENCODER_ERROR_UNSUPPORTED");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_ENCODER_ERROR_UNKNOWN:
                    Timber.d("KSY_STREAMER_AUDIO_ENCODER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED:
                    Timber.d("KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN:
                    Timber.d("KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN:
                    Timber.d("KSY_STREAMER_CAMERA_ERROR_UNKNOWN");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED:
                    Timber.d("KSY_STREAMER_CAMERA_ERROR_START_FAILED");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED:
                    Timber.d("KSY_STREAMER_CAMERA_ERROR_SERVER_DIED");
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_EVICTED:
                    Timber.d("KSY_STREAMER_CAMERA_ERROR_EVICTED");
                    break;
                default:
                    Timber.d("what=" + what + " msg1=" + msg1 + " msg2=" + msg2);
                    break;
            }
            switch (what) {
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_START_FAILED:
                case StreamerConstants.KSY_STREAMER_AUDIO_RECORDER_ERROR_UNKNOWN:
                    break;
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_UNKNOWN:
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_START_FAILED:
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_EVICTED:
                case StreamerConstants.KSY_STREAMER_CAMERA_ERROR_SERVER_DIED:
                    mStreamer.stopCameraPreview();
                    break;
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_CLOSE_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_ERROR_UNKNOWN:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_OPEN_FAILED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_FORMAT_NOT_SUPPORTED:
                case StreamerConstants.KSY_STREAMER_FILE_PUBLISHER_WRITE_FAILED:
                    break;
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN: {
                    handleEncodeError();
                    if (mRecording) {
                        stopStream();
                        mMainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startStream();
                            }
                        }, 3000);
                    }
                }
                break;
                default:
                    if (mStreamer.getEnableAutoRestart()) {
                        mRecording = false;
                        stopChronometer();
                    } else {
                        stopStream();
                        mMainHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startStream();
                            }
                        }, 3000);
                    }
                    break;
            }
        }
    };

    /**
     * 处理与相机相关的操作
     */
    private void setCameraAntiBanding50Hz() {
        Camera.Parameters parameters = mStreamer.getCameraCapture().getCameraParameters();
        if (parameters != null) {
            parameters.setAntibanding(Camera.Parameters.ANTIBANDING_50HZ);
            mStreamer.getCameraCapture().setCameraParameters(parameters);
        }
    }

    /**
     * 处理编码错误
     */
    private void handleEncodeError() {
        if (mStreamer == null) return;
        int encodeMethod = mStreamer.getVideoEncodeMethod();
        if (encodeMethod == StreamerConstants.ENCODE_METHOD_HARDWARE) {
            mHWEncoderUnsupported = true;
            if (mSWEncoderUnsupported) {
                mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT);
                Timber.e("Got HW encoder error, switch to SOFTWARE_COMPAT mode");
            } else {
                mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE);
                Timber.e("Got HW encoder error, switch to SOFTWARE mode");
            }
        } else if (encodeMethod == StreamerConstants.ENCODE_METHOD_SOFTWARE) {
            mSWEncoderUnsupported = true;
            if (mHWEncoderUnsupported) {
                mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT);
                Timber.e("Got SW encoder error, switch to SOFTWARE_COMPAT mode");
            } else {
                mStreamer.setEncodeMethod(StreamerConstants.ENCODE_METHOD_HARDWARE);
                Timber.e("Got SW encoder error, switch to HARDWARE mode");
            }
        }
    }

    /**
     * 结束直播
     */
    private void finishLive() {
        TipsPop pop = new TipsPop(this);
        pop.setTips(R.string.toast_exit_anchor);
        pop.showAtLocation(mCameraPreview, Gravity.CENTER, 0, 0);
        pop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
//                mPresenter.finishLive();
                AnchorFinishActivity.openActivity(AnchorActivity.this);
                finish();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        mStreamer.onPause();
    }

    @Override
    protected void onDestroy() {
        mPresenter.finishLive();
        super.onDestroy();
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
        mStreamer.setOnLogEventListener(null);
        mStreamer.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (mPreFragment != null) {
                mPreFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public static void openActivity(Context context) {
        context.startActivity(new Intent(context, AnchorActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isLiveStart) {
                finishLive();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Timber.e("摄像头创建");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Timber.e("摄像头改变");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Timber.e("摄像头摧毁");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
