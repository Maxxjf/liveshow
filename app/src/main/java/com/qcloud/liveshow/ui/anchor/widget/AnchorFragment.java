package com.qcloud.liveshow.ui.anchor.widget;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.sahasbhop.apngview.ApngDrawable;
import com.github.sahasbhop.apngview.ApngImageLoader;
import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.RoomFansAdapter;
import com.qcloud.liveshow.adapter.RoomMessageAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyForbiddenBean;
import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.beans.UserBean;
import com.qcloud.liveshow.beans.UserStatusBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.enums.GiftTypeEnum;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.anchor.presenter.impl.AnchorControlPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IAnchorControlView;
import com.qcloud.liveshow.utils.ShareUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.liveshow.widget.dialog.InputMessageDialog;
import com.qcloud.liveshow.widget.giftlayout.GiftControl;
import com.qcloud.liveshow.widget.giftlayout.widget.CustormAnim;
import com.qcloud.liveshow.widget.pop.FansInfoPop;
import com.qcloud.liveshow.widget.pop.FansManagerPop;
import com.qcloud.liveshow.widget.pop.FansMessagePop;
import com.qcloud.liveshow.widget.pop.GuarderPop;
import com.qcloud.liveshow.widget.pop.MessageListPop;
import com.qcloud.liveshow.widget.pop.SharePop;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.toast.SnackbarUtils;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.MarqueeView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 类说明：直播间控制页面
 * Author: Kuzan
 * Date: 2017/9/2 15:50.
 */
public class AnchorFragment extends BaseFragment<IAnchorControlView, AnchorControlPresenterImpl> implements IAnchorControlView {

    @Bind(R.id.layout_user)
    UserHeadImageView mLayoutUser;
    @Bind(R.id.chronometer)
    Chronometer mChronometer;
    @Bind(R.id.tv_watch_num)
    TextView mTvWatchNum;
    @Bind(R.id.list_fans)
    RecyclerView mListFans;
    @Bind(R.id.layout_top)
    LinearLayout mLayoutTop;
    @Bind(R.id.tv_id)
    TextView mTvId;
    @Bind(R.id.layout_id)
    LinearLayout mLayoutId;
    @Bind(R.id.btn_notice)
    ImageView mBtnNotice;
    @Bind(R.id.layout_notice_bg)
    LinearLayout mLayoutNoticeBg;
    @Bind(R.id.tv_notice)
    MarqueeView mTvNotice;
    @Bind(R.id.list_message)
    RecyclerView mListMessage;
    @Bind(R.id.layout_notice)
    LinearLayout mLayoutNotice;
    @Bind(R.id.btn_send_message)
    ImageView mBtnSendMessage;
    @Bind(R.id.btn_share)
    ImageView mBtnShare;
    @Bind(R.id.btn_switch_camera)
    ImageView mBtnSwitchCamera;
    @Bind(R.id.btn_flash)
    ImageView mBtnFlash;
    @Bind(R.id.btn_receive_message)
    ImageView mBtnReceiveMessage;
    @Bind(R.id.layout_bottom)
    RelativeLayout mLayoutBottom;
    @Bind(R.id.btn_exit)
    ImageView mBtnExit;
    @Bind(R.id.layout_root)
    RelativeLayout layoutRoot;
//    @Bind(R.id.gift)
//    CustomGiftView mGiftShow;

    private UserBean mUserBean;

    private RoomFansAdapter mFansAdapter;
    private RoomMessageAdapter mMessageAdapter;

    /**
     * fragment点击事件回调
     */
    private OnFragmentClickListener mListener;

    /**
     * 输入消息弹窗
     */
    private InputMessageDialog mInputDialog;
    /**
     * 粉丝信息弹窗
     */
    private FansInfoPop mFansPop;
    /**
     * 消息列表弹窗
     */
    private MessageListPop mMessagePop;
    /**
     * 分享弹窗
     */
    private SharePop mSharePop;
    /**
     * 粉丝管理弹窗
     */
    private FansManagerPop mManagerPop;
    /**
     * 守护者弹窗
     */
    private GuarderPop mGuarderPop;
    /**
     * 粉丝消息弹窗
     */
    private FansMessagePop mFansMessagePop;

    private MemberBean mMemberBean;
    private ShareUtil shareUtil;

    @Bind(R.id.layout_small_gift)
    LinearLayout giftParent;
    @Bind(R.id.layout_big_gift)
    LinearLayout BigGiftParent;

    @Bind(R.id.img_gift_gif)
    ImageView imgGiftGif;
    private GiftControl smallGiftControl;
    private GiftControl bigGigtControl;
    private Disposable disposable;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor;
    }

    @Override
    protected AnchorControlPresenterImpl initPresenter() {
        return new AnchorControlPresenterImpl();
    }

    @Override
    public void loadError(String errorMsg) {
        SnackbarUtils.showShortSnackbar(layoutRoot, errorMsg.trim(), getResources().getColor(R.color.colorGrayDark), getResources().getColor(R.color.colorOrange));
    }

    @Override
    protected void initViewAndData() {
        initFansLayout();
        initMessageLayout();
        initMessagePop();
        initGiftParent();
    }

    private void initGiftParent() {
        smallGiftControl = new GiftControl(getActivity());
        smallGiftControl.setGiftLayout(giftParent, 2)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画
        smallGiftControl.setDisplayMode(GiftControl.FROM_TOP_TO_BOTTOM);

        bigGigtControl = new GiftControl(getActivity());
        bigGigtControl.setGiftLayout(BigGiftParent, 1)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());
        bigGigtControl.setDisplayMode(GiftControl.FROM_TOP_TO_BOTTOM);

    }


    @Override
    protected void beginLoad() {
        refreshAndLoadData();
    }

    /**
     * 刷新和重新加载数据
     */
    private void refreshAndLoadData() {
        Timber.e("refreshAndLoadData()");

        refreshUserInfo();

        if (mTvWatchNum != null) {
            mTvWatchNum.setText(String.valueOf(mFansAdapter.getItemCount()));
        }
        if (mTvNotice != null) {
            mTvNotice.stopScroll();
            mTvNotice.setText(((AnchorActivity) getActivity()).getNotice());
            mPresenter.sendGroupNotice(((AnchorActivity) getActivity()).getRoom().getRoomIdStr(),((AnchorActivity) getActivity()).getNotice());
            resetNoticeWith();
        }
        intoRoom();
    }

    /**
     * 进入群聊，返回用户列表
     */
    private void intoRoom() {
        if (isInFragment) {
            String roomId = ((AnchorActivity) getActivity()).getRoom().getRoomIdStr();
            mPresenter.joinGroup(roomId);
        }
    }

    /**
     * 刷新主播员信息
     */
    private void refreshUserInfo() {
        mUserBean = UserInfoUtil.mUser;
        if (mUserBean == null) {
            return;
        }
        if (mLayoutUser != null) {
            mLayoutUser.loadImage(mUserBean.getHeadImg(), mUserBean.getIcon(), 80);
        }
        if (mTvId != null) {
            mTvId.setText(mUserBean.getIdAccount());
        }
    }

    /**
     * 设置公告背景宽度
     */
    private void resetNoticeWith() {
        if (mTvNotice != null) {
            mTvNotice.post(() -> {
                int textWidth = mTvNotice.getTextWidth() + 30;
                int viewWidth = mTvNotice.getViewWidth();
                Timber.e("textWidth = %d, viewWidth = %d", textWidth, viewWidth);
                ViewGroup.LayoutParams lp = mLayoutNoticeBg.getLayoutParams();
                if (textWidth > viewWidth) {
                    lp.width = viewWidth;
                } else {
                    lp.width = textWidth;
                }
                mLayoutNoticeBg.setLayoutParams(lp);
            });
        }
    }

    /**
     * 初始化输入消息弹窗
     */
    private void initInputDialog() {
        mInputDialog = new InputMessageDialog(getActivity());
        mInputDialog.setOnMessageSendListener((message, isNotice) -> {

            String roomId = ((AnchorActivity) getActivity()).getRoom().getRoomIdStr();
            if (roomId != null && !"".equals(roomId)) {
                if (!isNotice) {  //发送群聊
                    mPresenter.sendGroupMessage(roomId, message, mMessageAdapter.getItemCount());
                } else { //修改公告
                    mPresenter.sendGroupNotice(roomId, message);
                }
            }


        });
    }

    @Override
    public void addMessage(MemberBean bean) {
        if (isInFragment) {
            if (isInFragment) {
                Timber.e("char:" + "bean:" + bean);
                if (bean != null && mMessagePop != null) {
                    mMessagePop.add(bean);
                }
            }
        }
    }

    @Override
    public void showGift(NettyGiftBean gift) {
        if (isInFragment) {
            if (gift.getGift().getType() == GiftTypeEnum.BigGift.getKey()) {//大礼物
                bigGigtControl.loadGift(gift);
                imgGiftGif.setVisibility(View.VISIBLE);
                if (StringUtils.isNotEmptyString(gift.getGift().getGiftKey())) {
                    startThread();
//                    Glide.with(mContext).load(gift.getGift().getGiftKey()).into(imgGiftGif);
                    ApngImageLoader.getInstance()
                            .displayApng(gift.getGift().getGiftKey(), imgGiftGif,
                                    new ApngImageLoader.ApngConfig(8, false));
//                    imgGiftGif.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            ApngDrawable apngDrawable = ApngDrawable.getFromView(v);
//                            if (apngDrawable == null) return;
//
//                            if (apngDrawable.isRunning()) {
//                                apngDrawable.stop(); // Stop animation
//                            } else {
//                                apngDrawable.setNumPlays(3); // Fix number of repetition
//                                apngDrawable.start(); // Start animation
//                            }
//                        }
//                    });
//                    imgGiftGif.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//                        @Override
//                        public void onSystemUiVisibilityChange(int i) {
//
//                        }
//                    });
                }
            } else {//小礼物
                smallGiftControl.loadGift(gift);
            }
        }
    }

    public void startThread() {
        disposable = Observable.interval(1, TimeUnit.SECONDS).take(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        ApngDrawable apngDrawable = ApngDrawable.getFromView(imgGiftGif);
                        if (apngDrawable == null) return;
                        if (apngDrawable.isRunning()) {
                            imgGiftGif.setVisibility(View.VISIBLE);
                        } else {
                            imgGiftGif.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 初始化粉丝信息弹窗
     */
    private void initFansPop() {
        mFansPop = new FansInfoPop(mContext);
        mFansPop.setOnHolderClick(view -> {
            if (view.getId() == R.id.btn_manager) {
                if (mManagerPop == null) {
                    initFansManagerPop();
                }
                mManagerPop.setGuarderText(mMemberBean.isGuard());
                mManagerPop.setBlackText(mMemberBean.isBlack());
                mManagerPop.setGagText(mMemberBean.isForbidden());
                mManagerPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
            } else if (view.getId() == R.id.btn_letter) {
                if (mFansMessagePop == null) {
                    initFansMessagePop();
                }
                mFansMessagePop.refreshMemberInfo(mFansPop.getCurrMember());
                mFansMessagePop.showAtLocation(mBtnReceiveMessage, Gravity.BOTTOM, 0, 0);
            } else if (view.getId() == R.id.btn_follow) {
                mPresenter.submitAttention(StartFansEnum.MyFans.getKey(), mMemberBean.getId(), !mMemberBean.isAttention());
            } else {
                mFansPop.dismiss();
            }
        });
    }

    /**
     * 粉丝消息弹窗
     */
    private void initFansMessagePop() {
        mFansMessagePop = new FansMessagePop(getActivity());
    }

    /**
     * 更新私聊信息发送状态
     */
    @Override
    public void upDateMessageSendStatus(String chatId, int charStatus) {
        mFansMessagePop.upDateApater(chatId, charStatus);
    }

    /**
     * 初始化消息列表弹窗
     */
    private void initMessagePop() {
        mMessagePop = new MessageListPop(mContext);
        mMessagePop.setOnPopItemClick((position, member) -> {
            if (mFansMessagePop == null) {
                initFansMessagePop();
            }
            mFansMessagePop.refreshMemberInfo(member);
            mFansMessagePop.showAtLocation(mBtnReceiveMessage, Gravity.BOTTOM, 0, 0);
        });
    }

    /**
     * 初始化消息列表弹窗
     */
    private void initSharePop() {
        RoomBean room = ((AnchorActivity) getActivity()).getRoom();
        if (room != null && room.getRoomIdStr() != null && StringUtils.isNotEmptyString(room.getRoomIdStr())) {
            mSharePop = new SharePop(mContext);
            shareUtil = new ShareUtil(getActivity());
            mSharePop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
                @Override
                public void onViewClick(View view) {
                    switch (view.getId()) {
                        case R.id.btn_share_wechat:
                            shareUtil.shareWeb(SHARE_MEDIA.WEIXIN, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mUserBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    room.getCover(),
                                    room.getTitle(), getResources().getString(R.string.tip_room_descrption));
                            break;
                        case R.id.btn_share_wechat_circle:
                            shareUtil.shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mUserBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    room.getCover(),
                                    room.getTitle(), getResources().getString(R.string.tip_room_descrption));
                            break;
                        case R.id.btn_facebook:
                            shareUtil.shareWeb(SHARE_MEDIA.FACEBOOK, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mUserBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    room.getCover(),
                                    room.getTitle(), getResources().getString(R.string.tip_room_descrption));
                            break;
                    }
                }
            });
        }
    }

    /**
     * 初始化管理弹窗
     */
    private void initFansManagerPop() {
        mManagerPop = new FansManagerPop(mContext);

        mManagerPop.setOnHolderClick(view -> {
            switch (view.getId()) {
                case R.id.btn_set_guarder:
                    mPresenter.inOutGuard(mMemberBean.getId(), !mMemberBean.isGuard());
                    break;
                case R.id.btn_my_guarder_list:
                    mPresenter.getGuardList();
                    if (mGuarderPop == null) {
                        initGuarderPop();
                    }
                    mGuarderPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.btn_gag:
                    mPresenter.shutUp(((AnchorActivity) getActivity()).getRoom().getRoomIdStr(), mMemberBean.getIdStr(), !mMemberBean.isForbidden());
                    break;
                case R.id.btn_add_blacklist:
                    mPresenter.submitAttention(StartFansEnum.Blacklist.getKey(), mMemberBean.getId(), !mMemberBean.isBlack());
                    break;
            }
        });
    }


    /**
     * 初始化粉守护者弹窗
     */
    private void initGuarderPop() {
        mGuarderPop = new GuarderPop(mContext);
    }

    /**
     * 粉丝列表
     */
    private void initFansLayout() {
        mFansAdapter = new RoomFansAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mListFans.setLayoutManager(manager);
        mListFans.setAdapter(mFansAdapter);
        mFansAdapter.setOnHolderClick((view, bean, position) -> {
            mMemberBean = bean;
            RoomBean roomBean = ((AnchorActivity) getActivity()).getRoom();
            if (roomBean != null) {
                mPresenter.getUserIsAttention(mMemberBean.getIdStr(), (roomBean.getRoomIdStr()));
            } else {
                Timber.e("roomBean为Null");
            }


        });
    }

    /**
     * 初始化Member的是否关注，是否被拉黑，是否被禁言。是否设置为守护
     *
     * @param userStatusBean
     */
    @Override
    public void getUserIsAttention(UserStatusBean userStatusBean) {
        if (mMemberBean != null) {
            mMemberBean.setAttention(userStatusBean.isAttention());
            mMemberBean.setBlack(userStatusBean.isBlack());
            mMemberBean.setForbidden(userStatusBean.isForbidden());
            mMemberBean.setGuard(userStatusBean.isGuard());
        }
        if (mFansPop == null) {
            initFansPop();
        }
        mFansPop.refreshData(mMemberBean);
        mFansPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 消息列表
     */
    private void initMessageLayout() {
        mMessageAdapter = new RoomMessageAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mListMessage.setLayoutManager(manager);
        mListMessage.setAdapter(mMessageAdapter);
    }

    /**
     * 更新群聊信息发送的状态
     */
    @Override
    public void upDateGroupMessageStatus(int position, int charStatus) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessageAdapter.upDateMessageStatus(position, charStatus);
            }
        });

    }


    /**
     * 开始计时
     */
    public void startChronometer() {
        if (mChronometer != null) {
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
        }
    }

    /**
     * 停止计时
     */
    public void stopChronometer() {
        if (mChronometer != null) {
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.stop();
        }
    }

    @OnClick({R.id.btn_notice, R.id.btn_send_message, R.id.btn_receive_message, R.id.btn_flash,
            R.id.btn_switch_camera, R.id.btn_share, R.id.btn_exit})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onNoticeClick() {
        if (mTvNotice != null) {
            if (mTvNotice.getVisibility() == View.VISIBLE) {
                mTvNotice.setVisibility(View.GONE);
                mLayoutNoticeBg.setVisibility(View.GONE);
            } else {
                mTvNotice.setVisibility(View.VISIBLE);
                mLayoutNoticeBg.setVisibility(View.VISIBLE);
                mTvNotice.reset();
            }
        }
    }

    @Override
    public void onSendMessageClick() {
        if (mInputDialog == null) {
            initInputDialog();
        }
        mInputDialog.show();
    }

    @Override
    public void onReceiveMessageClick() {
        mMessagePop.initData();
        if (mMessagePop == null) {
            initMessagePop();
        }
        mMessagePop.showAtLocation(mBtnReceiveMessage, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onFlashClick() {
        if (mListener != null) {
            mListener.onBtnClick(mBtnFlash);
        }
    }

    @Override
    public void onSwitchCameraClick() {
        if (mListener != null) {
            mListener.onBtnClick(mBtnSwitchCamera);
        }
    }

    @Override
    public void onShareClick() {
        if (mSharePop == null) {
            initSharePop();
        }
        mSharePop.showAtLocation(mBtnShare, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onExitClick() {
        if (mListener != null) {
            mListener.onBtnClick(mBtnExit);
        }
    }

    @Override
    public void replaceGuardList(List<MemberBean> been) {
        if (isInFragment) {
            stopLoadingDialog();
            if (mGuarderPop == null) {
                initGuarderPop();
            }
            mGuarderPop.replaceList(been);
            mGuarderPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
        }
    }

    /**
     * 看直播成员加入
     */
    @Override
    public void addMember(NettyRoomMemberBean bean) {
        if (isInFragment) {
            if (bean != null && bean.getUser() != null && mFansAdapter != null) {
                mFansAdapter.addListBeanAtStart(bean.getUser());
                mListFans.scrollToPosition(0);
                mTvWatchNum.setText(String.valueOf(mFansAdapter.getItemCount()));
            }
        }
    }


    /**
     * 群聊消息
     */
    @Override
    public void addGroupChat(NettyReceiveGroupBean bean) {
        if (isInFragment) {
            if (bean != null && mMessageAdapter != null) {
                mMessageAdapter.addListBeanAtEnd(bean);
                mListMessage.scrollToPosition(mMessageAdapter.getItemCount() - 1);
            }
        }
    }

    /**
     * 修改公告
     *
     * @param bean NettyLiveNoticeBean通知的实体类
     */
    @Override
    public void refreshNotice(NettyLiveNoticeBean bean) {
        if (isInFragment) {
            if (bean != null && bean.getContent() != null) {
                if (mTvNotice != null) {
                    mTvNotice.stopScroll();
                    mTvNotice.setText(bean.getContent().getText());
                    resetNoticeWith();
                }
            }
        }
    }

    /**
     * 用户退出群聊
     */
    @Override
    public void userOutGroup(NettyNoticeBean bean) {
        if (isInFragment) {
            if (bean != null && bean.getUser() != null && mFansAdapter != null) {
                mFansAdapter.removeBeanByUserId(bean.getUser().getIdStr());
            }
        }
    }

    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isInFragment) {
            if (isShow) {
                ToastUtils.ToastMessage(getActivity(), errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    @Override
    public void backListSuccess() {
        mMemberBean.refreshBlack();
        ToastUtils.ToastMessage(mContext, getString(R.string.toast_edit_success));
    }

    @Override
    public void inOutGuardSuccess() {
        mMemberBean.refreshGuard();
        ToastUtils.ToastMessage(mContext, getString(R.string.toast_edit_success));
    }

    @Override
    public void refreshForbidden(NettyForbiddenBean obj) {
        mMemberBean.refreshBlack();
        ToastUtils.ToastMessage(mContext, getString(R.string.toast_edit_success));
    }

    @Override
    public void onFollowRes(boolean isSuccess) {
        if (isInFragment) {
            if (isSuccess) {
                ToastUtils.ToastMessage(getActivity(), R.string.toast_edit_success);
            } else {
                ToastUtils.ToastMessage(getActivity(), R.string.toast_follow_failure);
            }
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mTvNotice != null) {
            mTvNotice.stopScroll();
        }
        if (mInputDialog != null && mInputDialog.isShowing()) {
            mInputDialog.dismiss();
        }
        if (mMessagePop != null && mMessagePop.isShowing()) {
            mMessagePop.dismiss();
        }
        if (mSharePop != null && mSharePop.isShowing()) {
            mSharePop.dismiss();
        }
        if (mChronometer != null) {
            mChronometer.stop();
        }
    }

    /**
     * 点击事件回调
     */
    public void setOnFragmentClickListener(OnFragmentClickListener listener) {
        this.mListener = listener;
    }

    public static AnchorFragment newInstance() {
        AnchorFragment fragment = new AnchorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
