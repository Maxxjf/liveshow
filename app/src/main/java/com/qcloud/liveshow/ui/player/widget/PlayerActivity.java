package com.qcloud.liveshow.ui.player.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.listener.OnPlayerBackListener;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.player.presenter.impl.PlayerPresenterImpl;
import com.qcloud.liveshow.ui.player.view.IPlayerView;
import com.qcloud.qclib.utils.SystemBarUtil;

/**
 * 类说明：播放器
 * Author: Kuzan
 * Date: 2017/8/22 10:55.
 */
public class PlayerActivity extends BaseActivity<IPlayerView, PlayerPresenterImpl> implements IPlayerView {

    private PlayerView mPlayer;
    private String mUrl;
    private String mTitle;

    @Override
    protected int initLayout() {
        return R.layout.simple_player_view_player;
    }

    @Override
    protected PlayerPresenterImpl initPresenter() {
        return new PlayerPresenterImpl();
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        SystemBarUtil.transparencyNavBar(this);
        mUrl = getIntent().getStringExtra("URL");
        mTitle = getIntent().getStringExtra("TITLE");

        initPlayer();
    }

    private void initPlayer() {
        mPlayer = new PlayerView(this)
                .setTitle(mTitle)
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(false)
                .hideMenu(true)
                .hideSteam(true)
                .setForbidDoulbeUp(true)
                .hideCenterPlayer(true)
                .hideControlPanl(true)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                .placeholder(R.color.colorText)
                                .error(R.color.white)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(mUrl)
                .setPlayerBackListener(new OnPlayerBackListener() {
                    @Override
                    public void onPlayerBack() {
                        //这里可以简单播放器点击返回键
                        finish();
                    }
                })
                .startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer != null) {
            mPlayer.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mPlayer != null) {
            mPlayer.onConfigurationChanged(newConfig);
        }
    }

    public static void openActivity(Context context, String url, String title) {
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra("URL", url);
        intent.putExtra("TITLE", title);
        context.startActivity(intent);
    }
}
