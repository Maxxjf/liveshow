package com.qcloud.liveshow.ui.player.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.listener.OnPlayerBackListener;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.RoomAdapter;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.ui.player.presenter.impl.RoomPresenterImpl;
import com.qcloud.liveshow.ui.player.view.IRoomView;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.VerticalViewPager;

import butterknife.Bind;
import timber.log.Timber;

/**
 * 类说明：播放器
 * Author: Kuzan
 * Date: 2017/8/22 10:55.
 */
public class RoomActivity extends BaseActivity<IRoomView, RoomPresenterImpl> implements IRoomView {

    @Bind(R.id.view_pager)
    VerticalViewPager mViewPager;

    private PlayerView mPlayer;
    private String mUrl;
    private String mTitle;

    private RelativeLayout mRoomContainer;
    private FrameLayout mFragmentContainer;
    private RoomAdapter mRoomAdapter;
    private int mCurrentItem;
    private int mRoomId = -1;
    private FragmentManager mFragmentManager;
    private RoomFragment mRoomFragment = RoomFragment.newInstance();
    private boolean mInit = false;

    @Override
    protected int initLayout() {
        return R.layout.activity_room;
    }

    @Override
    protected RoomPresenterImpl initPresenter() {
        return new RoomPresenterImpl();
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

        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_room_container, null);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        mFragmentManager = getSupportFragmentManager();
        mRoomAdapter = new RoomAdapter();
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCurrentItem = position;
            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                ViewGroup viewGroup = (ViewGroup) page;
                Timber.e("page.id == " + page.getId() + ", position == " + position);

                if ((position < 0 && viewGroup.getId() != mCurrentItem)) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }
                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                if (viewGroup.getId() == mCurrentItem && position == 0 && mCurrentItem != mRoomId) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup, mCurrentItem);
                }
            }
        });
        mViewPager.setAdapter(mRoomAdapter);

        initPlayer();
    }

    private void initPlayer() {
        mPlayer = new PlayerView(this, mRoomContainer)
                .setTitle(mTitle)
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(true)
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

    private void loadVideoAndChatRoom(ViewGroup viewGroup, int currentItem) {
//        mSubscription = AppObservable.bindActivity(this, ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
        //聊天室的fragment只加载一次，以后复用
        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
            mInit = true;
        }
        if (mPlayer != null) {
            mPlayer.stopPlay();
        }
        //mUrl = "http://pull2.inke.cn/live/1503399935072960.flv?ikHost=ws&ikOp=1&codecInfo=8192";
        initPlayer();
        viewGroup.addView(mRoomContainer);
        mRoomId = currentItem;
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
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra("URL", url);
        intent.putExtra("TITLE", title);
        context.startActivity(intent);
    }
}
