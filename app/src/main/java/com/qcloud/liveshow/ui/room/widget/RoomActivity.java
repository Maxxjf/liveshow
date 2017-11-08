package com.qcloud.liveshow.ui.room.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.RoomAdapter;
import com.qcloud.liveshow.base.BaseActivity;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.ui.room.presenter.impl.RoomPresenterImpl;
import com.qcloud.liveshow.ui.room.view.IRoomView;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.widget.customview.VerticalViewPager;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import timber.log.Timber;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * 类说明：直播间
 * Author: Kuzan
 * Date: 2017/8/22 10:55.
 */
public class RoomActivity extends BaseActivity<IRoomView, RoomPresenterImpl> implements IRoomView {

    @Bind(R.id.view_pager)
    VerticalViewPager mViewPager;

    private RelativeLayout mRoomContainer;
    private FrameLayout mFragmentContainer;
    private RoomAdapter mRoomAdapter;
    private int mCurrentItem;
    private int mRoomId = -1;
    private FragmentManager mFragmentManager;
    private RoomFragment mRoomFragment;
    /**是否初始化了Room*/
    private boolean mInit = false;

    /**播放器*/
    private PlayerView mPlayer;

    private String currUrl;
    private String currImage;
    private List<RoomBean> mList;
    private RoomBean mCurrBean;

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
        //SystemBarUtil.hideNavBar(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mCurrentItem = getIntent().getIntExtra("POSITION", 0);
        mList = (List<RoomBean>) getIntent().getSerializableExtra("LIST");
        if (mList != null && mCurrentItem < mList.size()) {
            mCurrBean = mList.get(mCurrentItem);
        }

        mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_room_container, null);
        mFragmentContainer = (FrameLayout) mRoomContainer.findViewById(R.id.fragment_container);
        mRoomFragment = RoomFragment.newInstance();
        mFragmentManager = getSupportFragmentManager();

        initViewPager();
    }

    /**
     * 初始化播放器
     * */
    private void initPlayer() {
        Timber.e("initPlayer");
        mPlayer = new PlayerView(this, mRoomContainer)
                .setTitle("直播")
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
                        GlideUtil.loadImage(mContext, ivThumbnail, currImage, R.drawable.bitmap_user, true, false);
                    }
                })
                .setPlaySource(currUrl)
                .startPlay();
        mPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                if (i == PlayStateParams.STATE_COMPLETED) {
                    // 播放完成
                    RoomFinishActivity.openActivity(RoomActivity.this);
                }
                return true;
            }
        });
    }

    /**
     * 初始化直播间
     * */
    private void initViewPager() {
        Timber.e("mCurrentItem = %d", mCurrentItem);

        mRoomAdapter = new RoomAdapter(this, mList);
        mViewPager.setCurrentItem(mCurrentItem);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Timber.e("position = %d, positionOffset = %2.1f, positionOffsetPixels = %d", position, positionOffset, positionOffsetPixels);
                mCurrentItem = position;
            }
        });

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                Timber.e("page.id = %d, position = %2.1f", page.getId(), position);
                ViewGroup viewGroup = (ViewGroup) page;

                if (position < 0 && viewGroup.getId() != mCurrentItem) {
                    View roomContainer = viewGroup.findViewById(R.id.room_container);
                    if (roomContainer != null && roomContainer.getParent() != null && roomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (roomContainer.getParent())).removeView(roomContainer);
                    }
                }
                // 满足此种条件，表明需要加载直播视频，以及聊天室了
                /*viewGroup.getId() == mCurrentItem && */
                if (viewGroup.getId() == mCurrentItem && position == 0 && mCurrentItem != mRoomId) {
                    if (mRoomContainer.getParent() != null && mRoomContainer.getParent() instanceof ViewGroup) {
                        ((ViewGroup) (mRoomContainer.getParent())).removeView(mRoomContainer);
                    }
                    loadVideoAndChatRoom(viewGroup, mCurrentItem);
                }
            }
        });
        mViewPager.setAdapter(mRoomAdapter);
        mViewPager.setCurrentItem(mCurrentItem);
    }

    /**
     * 加载直播视频
     * */
    private void loadVideoAndChatRoom(ViewGroup viewGroup, int currentItem) {
        Timber.e("currentItem = %d", currentItem);
        // 聊天室的fragment只加载一次，以后复用
        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
            mInit = true;
        }
        if (mList != null) {
            mCurrBean = mList.get(currentItem);
            if (mCurrBean != null && mCurrBean.getMember() != null) {
                currUrl = initStreamUrl(mCurrBean.getMember().getIdAccount());
            }
            currImage = mList.get(currentItem).getCover();
        }
        if (mRoomFragment != null) {
            mRoomFragment.refreshRoom(mCurrBean);
            mRoomFragment.beginLoad();
        }
        if (mPlayer != null) {
            mPlayer.stopPlay();
        }

        initPlayer();
        viewGroup.addView(mRoomContainer);
        mRoomId = currentItem;
    }

    private String initStreamUrl(String id) {
        StringBuffer url = new StringBuffer();
        url.append(UrlConstants.STREAM_URL);
        url.append(id);

        return url.toString();
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
        if (mCurrBean != null) {
            mPresenter.outGroup(mCurrBean.getRoomIdStr());
        }
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

    public static void openActivity(Context context, int position, List<RoomBean> list) {
        Intent intent = new Intent(context, RoomActivity.class);
        intent.putExtra("POSITION", position);
        intent.putExtra("LIST", (Serializable) list);
        context.startActivity(intent);
    }
}
