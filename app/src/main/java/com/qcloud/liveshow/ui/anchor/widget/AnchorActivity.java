package com.qcloud.liveshow.ui.anchor.widget;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.faucamp.simplertmp.RtmpHandler;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.anchor.presenter.impl.AnchorPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IAnchorView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.SystemBarUtil;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.IOException;
import java.net.SocketException;

import timber.log.Timber;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/9/1 17:21.
 */
public class AnchorActivity extends BaseActivity<IAnchorView, AnchorPresenterImpl> implements IAnchorView,
        RtmpHandler.RtmpListener, SrsRecordHandler.SrsRecordListener, SrsEncodeHandler.SrsEncodeListener{

    private SrsPublisher mPublisher;

    /**直播前准备页面*/
    private PreAnchorFragment mPreFragment;
    /**直播控制页面*/
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
        SystemBarUtil.hideNavBar(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        switchPreFragment();

        initPublisher();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPublisher.resumeRecord();
    }

    private void initPublisher() {
        mPublisher = new SrsPublisher((SrsCameraView) findViewById(R.id.camera_view));
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        mPublisher.setPreviewResolution(640, 360);
        mPublisher.setOutputResolution(360, 640);
        mPublisher.setVideoHDMode();
        mPublisher.startCamera();
    }

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

    private void initControlFragment() {
        mControlFragment = AnchorFragment.newInstance();
        mControlFragment.setOnFragmentClickListener(new OnFragmentClickListener() {
            @Override
            public void onBtnClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_switch_camera:
                        onSwitchCameraClick();
                        break;
                }
            }
        });
    }

    private void switchPreFragment() {
        if (mPreFragment == null) {
            initPreFragment();
        }
        replaceFragment(mPreFragment, R.id.fragment_container, false);
    }

    @Override
    public void onSwitchCameraClick() {
        if (mPublisher != null) {
            mPublisher.switchCameraFace((mPublisher.getCamraId() + 1) % Camera.getNumberOfCameras());
        }
    }

    @Override
    public void onBeginAnchorClick() {
        if (mControlFragment == null) {
            initControlFragment();
        }
        replaceFragment(mControlFragment, R.id.fragment_container, false);
    }

    /**
     * Implementation of SrsRtmpListener.
     * */
    @Override
    public void onRtmpConnecting(String msg) {
        ToastUtils.ToastMessage(this, msg);
    }

    @Override
    public void onRtmpConnected(String msg) {
        ToastUtils.ToastMessage(this, msg);
    }

    @Override
    public void onRtmpVideoStreaming() {
    }

    @Override
    public void onRtmpAudioStreaming() {
    }

    @Override
    public void onRtmpStopped() {
        ToastUtils.ToastMessage(this, "Stopped");
    }

    @Override
    public void onRtmpDisconnected() {
        ToastUtils.ToastMessage(this, "Disconnected");
    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {
        Timber.i("Output Fps: %f", fps);
    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {
        int rate = (int) bitrate;
        if (rate / 1000 > 0) {
            Timber.i("Video bitrate: %f kbps", bitrate / 1000);
        } else {
            Timber.i("Video bitrate: %d bps", rate);
        }
    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {
        int rate = (int) bitrate;
        if (rate / 1000 > 0) {
            Timber.i("Audio bitrate: %f kbps", bitrate / 1000);
        } else {
            Timber.i("Audio bitrate: %d bps", rate);
        }
    }

    @Override
    public void onRtmpSocketException(SocketException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIOException(IOException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {
        handleException(e);
    }

    /**
     * Implementation of SrsRecordHandler.
     * */
    @Override
    public void onRecordPause() {
        ToastUtils.ToastMessage(this, "Record paused");
        Toast.makeText(getApplicationContext(), "Record paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordResume() {
        ToastUtils.ToastMessage(this, "Record resumed");
    }

    @Override
    public void onRecordStarted(String msg) {
        ToastUtils.ToastMessage(this, "Recording file: " + msg);
    }

    @Override
    public void onRecordFinished(String msg) {
        ToastUtils.ToastMessage(this, "MP4 file saved: " + msg);
    }

    @Override
    public void onRecordIOException(IOException e) {
        handleException(e);
    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    /**
     * Implementation of SrsEncodeHandler.
     * */
    @Override
    public void onNetworkWeak() {
        ToastUtils.ToastMessage(this, "Network weak");
    }

    @Override
    public void onNetworkResume() {
        ToastUtils.ToastMessage(this, "Network resume");
    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    private void handleException(Exception e) {
        try {
            ToastUtils.ToastMessage(this, e.getMessage());
            mPublisher.stopPublish();
            mPublisher.stopRecord();
        } catch (Exception e1) {
            Timber.e(e1.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPublisher.pauseRecord();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPublisher.stopPublish();
        mPublisher.stopRecord();
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
}
