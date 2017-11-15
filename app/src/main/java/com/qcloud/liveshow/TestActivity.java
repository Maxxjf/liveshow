package com.qcloud.liveshow;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ksyun.media.streamer.capture.CameraCapture;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterBase;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.qcloud.liveshow.adapter.TextWatcherAdapter;
import com.qcloud.liveshow.enums.BeautyUiEnum;
import com.qcloud.qclib.permission.PermissionsManager;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;


public class TestActivity extends AppCompatActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener {
    EmojiconEditText mEditEmojicon;
    EmojiconTextView mTxtEmojicon;
    CheckBox mCheckBox;

    GLSurfaceView mCameraPreview;
    private KSYStreamer mStreamer;

    TextView mTvText;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditEmojicon = (EmojiconEditText) findViewById(R.id.editEmojicon);
        mTxtEmojicon = (EmojiconTextView) findViewById(R.id.txtEmojicon);
        mEditEmojicon.addTextChangedListener(new TextWatcherAdapter() {
>>>>>>> f06d9038477a513a5bd4a20d612d696ab8c9dc87
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTxtEmojicon.setText(s);
            }
        });
        mCheckBox = (CheckBox) findViewById(R.id.use_system_default);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mEditEmojicon.setUseSystemDefault(b);
                mTxtEmojicon.setUseSystemDefault(b);
                setEmojiconFragment(b);
            }
        });

        setEmojiconFragment(false);
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEditEmojicon, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEditEmojicon);
    }

}