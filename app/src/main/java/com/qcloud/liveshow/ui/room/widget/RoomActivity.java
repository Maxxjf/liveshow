package com.qcloud.liveshow.ui.room.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
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
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.qclib.image.GlideUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.VerticalViewPager;
import com.umeng.socialize.UMShareAPI;

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
    /**
     * 是否初始化了Room
     */
    private boolean mInit = false;

    /**
     * 播放器
     */
    private PlayerView mPlayer;

    private String currUrl;
    private String currImage;
    private List<RoomBean> mList;
    private RoomBean mCurrBean;
    private TipsPop tipsPop;
    private TipsPop netWorkTipsPop;

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
     */
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
                .setChargeTie(true, 3)  // 播放时长，6秒后收费
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        ivThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        GlideUtil.loadImage(mContext, ivThumbnail, currImage, R.drawable.bitmap_user, true, false);
                    }
                })
                .setPlaySource("高清",currUrl)
//                .setPlaySource("高清","http://10.10.22.123:80/room/123/123.flv")
                .startPlay();
            Timber.e("播放url："+currUrl);
//        int screenbrightness=0;
//        try {
//             screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
//        } catch (Settings.SettingNotFoundException e) {
//            e.printStackTrace();
//        }
        mPlayer.setBrightness(50);
        mPlayer.setOnLoadingErrorLinstener(new PlayerView.LoadingErrorListener() {
            @Override
            public void showStatus(String errInfo) {
                if (tipsPop == null) {
                    initTipsPop();
                }
                tipsPop.setTips(errInfo+","+getResources().getString(R.string.tip_paly_again));
                tipsPop.showAtLocation(mViewPager, Gravity.CENTER, 0, 0);
            }

            @Override
            public void use4GNetwork() {
                if (netWorkTipsPop==null){
                    initNetWorkTipsPop();
                }
                netWorkTipsPop.setTips(getResources().getString(R.string.tip_high_flow));
                netWorkTipsPop.showAtLocation(mViewPager, Gravity.CENTER, 0, 0);
            }
        });
        mPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                switch (i) {
                    case PlayStateParams.STATE_COMPLETED:
                        // 播放完成
                        Timber.e("PlayStateParams.STATE_COMPLETED");
                        ToastUtils.ToastMessage(RoomActivity.this, "播放完成");
                        RoomFinishActivity.openActivity(RoomActivity.this);
                        finish();
                        break;
                    case PlayStateParams.STATE_ERROR://错误
                        Timber.e("PlayStateParams.STATE_ERROR");
                        mFragmentContainer.setVisibility(View.GONE);
                        break;
                    case PlayStateParams.STATE_IDLE://空闲状态
                        Timber.e("PlayStateParams.STATE_IDLE");
//                        mFragmentContainer.setVisibility(View.GONE);
                        break;
                    case PlayStateParams.STATE_PAUSED://暂停
                        Timber.e("PlayStateParams.STATE_PAUSED");
                        mFragmentContainer.setVisibility(View.GONE);
                        break;
                    case PlayStateParams.STATE_PLAYING://正在播放
                        Timber.e("PlayStateParams.STATE_PLAYING");
//                        mFragmentContainer.setVisibility(View.GONE);
                        break;
                    case PlayStateParams.STATE_PREPARED://准备好的
                        Timber.e("PlayStateParams.STATE_PREPARED");
//                        mFragmentContainer.setVisibility(View.GONE);
                        break;
                    case PlayStateParams.STATE_PREPARING://准备中
                        Timber.e("PlayStateParams.STATE_PREPARING");
//                        mFragmentContainer.setVisibility(View.GONE);
                        break;

                }
                return true;
            }
        });
    }

    private void initTipsPop() {
        tipsPop = new TipsPop(this);
        tipsPop.setCancelBtn(R.string.out);
        tipsPop.setOnHolderClick(view -> {
            switch (view.getId()) {
                case R.id.btn_ok:
                    mPlayer.startPlay();
                    break;
                case R.id.btn_cancel:
                    finish();
            }
        });
    }

    private void initNetWorkTipsPop() {
        netWorkTipsPop = new TipsPop(this);
        tipsPop.setCancelBtn(R.string.out);
        netWorkTipsPop.setOnHolderClick(view -> {
            switch (view.getId()) {
                case R.id.btn_ok:
                    mPlayer.setNetWorkTypeTie(false);
                    mPlayer.startPlay();
                    break;
                case R.id.btn_cancel:
                    finish();
            }
        });
    }

    /**
     * 初始化直播间
     */
    private void initViewPager() {
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
     */
    private void loadVideoAndChatRoom(ViewGroup viewGroup, int currentItem) {
        // 聊天室的fragment只加载一次，以后复用
        if (!mInit) {
            mFragmentManager.beginTransaction().add(mFragmentContainer.getId(), mRoomFragment).commitAllowingStateLoss();
            mInit = true;
        }
        if (mList != null) {
            mCurrBean = mList.get(currentItem);
            if (mCurrBean != null && mCurrBean.getMember() != null) {
                currUrl = initStreamUrl(mCurrBean.getMember().getIdAccount(), mCurrBean.getRoomIdStr());
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

    private String initStreamUrl(String id, String roomId) {
        StringBuffer url = new StringBuffer();
        url.append(UrlConstants.STREAM_IN_URL);
        url.append(id + "/" + roomId + ".flv");
//        url.append(id + "/" + roomId );
        Timber.e("url:"+url.toString());
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
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//        startLoadingDialog();
        if (mRoomFragment != null) {
            mRoomFragment.onActivityResult(requestCode, resultCode, data);
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
