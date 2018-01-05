package com.qcloud.liveshow.ui.room.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.qcloud.liveshow.beans.AnchorBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.enums.GiftTypeEnum;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.room.presenter.impl.RoomControlPresenterImpl;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.liveshow.utils.ShareUtil;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.liveshow.widget.dialog.InputMessageDialog;
import com.qcloud.liveshow.widget.giftlayout.GiftControl;
import com.qcloud.liveshow.widget.giftlayout.widget.CustormAnim;
import com.qcloud.liveshow.widget.pop.BuyDiamondsPop;
import com.qcloud.liveshow.widget.pop.FansInfoPop;
import com.qcloud.liveshow.widget.pop.FansManagerPop;
import com.qcloud.liveshow.widget.pop.FansMessagePop;
import com.qcloud.liveshow.widget.pop.GuarderPop;
import com.qcloud.liveshow.widget.pop.MessageListPop;
import com.qcloud.liveshow.widget.pop.SendGiftPop;
import com.qcloud.liveshow.widget.pop.SharePop;
import com.qcloud.liveshow.widget.pop.SystemMessagePop;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.MarqueeView;
import com.qcloud.qclib.widget.customview.clearscreen.ClearScreenHelper;
import com.qcloud.qclib.widget.customview.clearscreen.IClearEvent;
import com.qcloud.qclib.widget.customview.clearscreen.IClearRootView;
import com.qcloud.qclib.widget.customview.clearscreen.view.RelativeClearLayout;
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
 * 类说明：直播间控制面板
 * Author: Kuzan
 * Date: 2017/8/22 18:54.
 */
public class RoomFragment extends BaseFragment<IRoomControlView, RoomControlPresenterImpl> implements IRoomControlView {


    //    @Bind(R.id.layout_gift)
//    LinearLayout layoutGift;
    private IClearRootView mClearRootLayout;
    private ClearScreenHelper mClearScreenHelper;

    private UserHeadImageView mUserHead;
    private TextView mTvName;
    private TextView mTvWatchNum;
    private TextView mBtnFollow;
    private RecyclerView mListFans;
    private LinearLayout mLayoutTop;

    private TextView mTvId;
    private LinearLayout mLayoutId;

    private ImageView mBtnNotice;
    private MarqueeView mTvNotice;
    private LinearLayout mLayoutNoticeBg;
    private RecyclerView mListMessage;
    private LinearLayout mLayoutNotice;

    private ImageView mBtnSendMessage;
    private ImageView mBtnBuyDiamonds;
    private ImageView mBtnShare;
    private ImageView mBtnReceiveMessage;
    private ImageView mBtnSendGift;
    private RelativeLayout mLayoutBottom;

    private ImageView mBtnExit;

    private RoomFansAdapter mFansAdapter;
    private RoomMessageAdapter mMessageAdapter;
    /**
     * 是否第一次进入房间
     */
    private boolean isFirstInRoom = true;
    private RoomBean mCurrBean;
    private AnchorBean mAnchorBean;

    private MemberBean mMemberBean;

    /**
     * 输入消息弹窗
     */
    private InputMessageDialog mInputDialog;
    /**
     * 购买钻石币弹窗
     */
    private BuyDiamondsPop mDiamondsPop;
    /**
     * 发送礼物弹窗
     */
    private SendGiftPop mGiftPop;
    /**
     * 粉丝信息弹窗
     */
    private FansInfoPop mFansPop;
    /**
     * 守护者弹窗
     */
    private GuarderPop mGuarderPop;
    /**
     * 消息列表弹窗
     */
    private MessageListPop mMessagePop;
    /**
     * 系统消息弹窗
     */
    private SystemMessagePop mSystemPop;
    /**
     * 粉丝消息弹窗
     */
    private FansMessagePop mFansMessagePop;
    /**
     * 分享弹窗
     */
    private SharePop mSharePop;
    /**
     * 粉丝管理弹窗
     */
    private FansManagerPop mManagerPop;
    /**
     * 分享工具
     */
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
        return R.layout.fragment_room;
    }

    @Override
    protected RoomControlPresenterImpl initPresenter() {
        return new RoomControlPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        initView();
        initClearLayout();

        initFansLayout();
        initMessageLayout();
        initMessagePop();

        initGiftControl();
    }

    private void initGiftControl() {
        smallGiftControl = new GiftControl(getActivity());
        smallGiftControl.setGiftLayout(giftParent, 2)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画
        smallGiftControl.setDisplayMode(GiftControl.FROM_TOP_TO_BOTTOM);
        smallGiftControl.setHideMode(true);

        bigGigtControl = new GiftControl(getActivity());
        bigGigtControl.setGiftLayout(BigGiftParent, 1)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());
        bigGigtControl.setDisplayMode(GiftControl.FROM_TOP_TO_BOTTOM);
        bigGigtControl.setHideMode(true);
    }

    @Override
    protected void beginLoad() {
        Timber.e("beginLoad()");
        if (isFirstInRoom) {
            isFirstInRoom = false;
        } else {
            refreshAndLoadData();
        }
    }

    /**
     * 刷新房间
     */
    public void refreshRoom(RoomBean bean) {
        Timber.e("refreshRoom()");
        mCurrBean = bean;
    }

    /**
     * 进入群聊，返回用户列表
     */
    private void intoRoom() {
        if (mCurrBean != null) {
            mPresenter.joinGroup(mCurrBean.getRoomIdStr());
        }
    }

    /**
     * 刷新和重新加载数据
     */
    private void refreshAndLoadData() {
        Timber.e("refreshAndLoadData()");
        if (mCurrBean == null) {
            return;
        }
        mAnchorBean = mCurrBean.getMember();
        refreshAnchor(mAnchorBean);

        if (mTvWatchNum != null) {
            mTvWatchNum.setText(mCurrBean.getWatchNumStr());
        }
        if (mCurrBean.isAttention()) {
            mBtnFollow.setVisibility(View.GONE);
        } else {
            mBtnFollow.setVisibility(View.VISIBLE);
        }

        intoRoom();
    }

    /**
     * 刷新主播员信息
     */
    private void refreshAnchor(AnchorBean bean) {
        if (bean == null) {
            return;
        }
        if (mUserHead != null) {
            mUserHead.loadImage(bean.getHeadImg(), bean.getIcon(), 80);
        }
        if (mTvName != null) {
            mTvName.setText(bean.getNickName());
        }
        if (mTvId != null) {
            mTvId.setText(bean.getIdAccount());
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mUserHead = mView.findViewById(R.id.layout_user);
        mTvName = mView.findViewById(R.id.tv_name);
        mTvWatchNum = mView.findViewById(R.id.tv_watch_num);
        mBtnFollow = mView.findViewById(R.id.btn_follow);
        mListFans = mView.findViewById(R.id.list_fans);
        mLayoutTop = mView.findViewById(R.id.layout_top);

        mTvId = mView.findViewById(R.id.tv_id);
        mLayoutId = mView.findViewById(R.id.layout_id);

        mBtnNotice = mView.findViewById(R.id.btn_notice);
        mTvNotice = mView.findViewById(R.id.tv_notice);
        mLayoutNoticeBg = mView.findViewById(R.id.layout_notice_bg);
        mListMessage = mView.findViewById(R.id.list_message);
        mLayoutNotice = mView.findViewById(R.id.layout_notice);

        mBtnSendMessage = mView.findViewById(R.id.btn_send_message);
        mBtnBuyDiamonds = mView.findViewById(R.id.btn_buy_diamonds);
        mBtnShare = mView.findViewById(R.id.btn_share);
        mBtnReceiveMessage = mView.findViewById(R.id.btn_receive_message);
        mBtnSendGift = mView.findViewById(R.id.btn_send_gift);
        mLayoutBottom = mView.findViewById(R.id.layout_bottom);

        mBtnExit = mView.findViewById(R.id.btn_exit);

        mUserHead.loadImage("", R.drawable.icon_anchor_level_4, 80);
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
     * 清屏布局
     */
    private void initClearLayout() {
        mClearRootLayout = (RelativeClearLayout) mView.findViewById(R.id.layout_root);
        mClearScreenHelper = new ClearScreenHelper(getActivity(), mClearRootLayout);
        mClearScreenHelper.bind(mLayoutTop, mLayoutId, mLayoutNotice, mLayoutBottom, giftParent);
        mClearScreenHelper.setIClearEvent(new IClearEvent() {
            @Override
            public void onClearEnd() {
                Timber.d("Clear End...");
            }

            @Override
            public void onRecovery() {
                Timber.d("Recovery Now...");
            }
        });
    }

    /**
     * 初始化输入消息弹窗
     */
    private void initInputDialog() {
        mInputDialog = new InputMessageDialog(getActivity());
        mInputDialog.setNoNotice();
        mInputDialog.setOnMessageSendListener((message, isNotice) -> {
            if (mCurrBean != null) {
                mPresenter.sendGroupMessage(mCurrBean.getRoomIdStr(), message, mMessageAdapter.getItemCount());
            }
        });

    }

    /**
     * 初始化购买钻石币弹窗
     */
    private void initDiamondsPop() {
        mDiamondsPop = new BuyDiamondsPop(mContext);
    }

    /**
     * 初始化发送礼物弹窗
     */
    private void initGiftPop() {
        if (mCurrBean != null && mAnchorBean != null) {
            mGiftPop = new SendGiftPop(mContext, mCurrBean, mAnchorBean);
            mGiftPop.setOnNoMoneyLinstener(new SendGiftPop.NoMoneyListener() {
                @Override
                public void hasNoMoney() {
                    noMoney(getResources().getString(R.string.tip_no_diamonds));
                    mGiftPop.dismiss();
                }

                @Override
                public void goToPay() {
                    onBuyDiamondsClick();
                    mGiftPop.dismiss();
                }
            });
        }
    }

    /**
     * 钻石币不够,要去充值
     */
    @Override
    public void noMoney(String tip) {
        TipsPop pop = new TipsPop(mContext);
        pop.setTips(tip);
        pop.showAtLocation(mLayoutBottom, Gravity.CENTER, 0, 0);
        pop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                onBuyDiamondsClick();
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
                mManagerPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
            } else if (view.getId() == R.id.btn_letter) {
                if (mFansMessagePop == null) {
                    initFansMessagePop();
                }
                mFansMessagePop.refreshMemberInfo(mFansPop.getCurrMember());
                mFansMessagePop.showAtLocation(mBtnReceiveMessage, Gravity.BOTTOM, 0, 0);
            } else {
                mFansPop.dismiss();
            }
        });
    }

    /**
     * 初始化管理弹窗
     */
    private void initFansManagerPop() {
        mManagerPop = new FansManagerPop(mContext);
        mManagerPop.noGuarder();
        mManagerPop.setOnHolderClick(view -> {
            switch (view.getId()) {
                case R.id.btn_set_guarder:
                    mPresenter.inOutGuard(mMemberBean.getId(), true);
                    break;
                case R.id.btn_my_guarder_list:
                    if (mGuarderPop == null) {
                        initGuarderPop();
                    }
                    mGuarderPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.btn_gag:
                    mPresenter.shutUp(mCurrBean.getRoomIdStr(), mMemberBean.getIdStr(), true);
                    break;
                case R.id.btn_add_blacklist:
                    mPresenter.submitAttention(StartFansEnum.Blacklist.getKey(), mMemberBean.getId(), true);
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
     * 初始化消息列表弹窗
     */
    private void initMessagePop() {
        mMessagePop = new MessageListPop(mContext);
        mMessagePop.setOnPopItemClick((position, memberBean) -> {
            if (mFansMessagePop == null) {
                initFansMessagePop();
            }
            mFansMessagePop.refreshMemberInfo(memberBean);
            mFansMessagePop.showAtLocation(mBtnReceiveMessage, Gravity.BOTTOM, 0, 0);
        });
    }

    /**
     * 系统消息弹窗
     */
    private void initSystemMessagePop() {
        mSystemPop = new SystemMessagePop(getActivity());
    }

    /**
     * 粉丝消息弹窗
     */
    private void initFansMessagePop() {
        mFansMessagePop = new FansMessagePop(getActivity());
    }

    /**
     * 初始化消息列表弹窗
     */
    private void initSharePop() {
        RoomBean room = mCurrBean;
        if (room != null && room.getRoomIdStr() != null && StringUtils.isNotEmptyString(room.getRoomIdStr())) {
            mSharePop = new SharePop(mContext);
            shareUtil = new ShareUtil(getActivity());
            mSharePop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
                @Override
                public void onViewClick(View view) {
                    switch (view.getId()) {
                        case R.id.btn_share_wechat:
                            shareUtil.shareWeb(SHARE_MEDIA.WEIXIN, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mAnchorBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    mAnchorBean.getHeadImg(),
                                    room.getTitle(), "AV8D，快来看我直播吧");
                            break;
                        case R.id.btn_share_wechat_circle:
                            shareUtil.shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mAnchorBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    mAnchorBean.getHeadImg(),
                                    room.getTitle(), "AV8D，快来看我直播吧");
                            break;
                        case R.id.btn_facebook:
                            shareUtil.shareWeb(SHARE_MEDIA.FACEBOOK, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mAnchorBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    mAnchorBean.getHeadImg(),
                                    room.getTitle(), "AV8D，快来看我直播吧");
                            break;
                    }
                }
            });
        }
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
            if (mFansPop == null) {
                initFansPop();
            }
            mFansPop.refreshData(bean);
            mFansPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
        });
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

    @Override
    public void upDateGroupMessageStatus(int position, int charStatus) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMessageAdapter.upDateMessageStatus(position, charStatus);
//                mMessageAdapter.notifyDataSetChanged();
            }
        });

    }

    @OnClick({R.id.btn_follow, R.id.btn_notice, R.id.btn_send_message, R.id.btn_buy_diamonds,
            R.id.btn_share, R.id.btn_receive_message, R.id.btn_send_gift, R.id.btn_exit})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onFollowClick() {
        if (mAnchorBean != null) {
            mPresenter.submitAttention(StartFansEnum.MyFans.getKey(), mAnchorBean.getId(), true);
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
    public void onBuyDiamondsClick() {
        if (mDiamondsPop == null) {
            initDiamondsPop();
        }
        mDiamondsPop.showAtLocation(mBtnBuyDiamonds, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onShareClick() {
        if (mSharePop == null) {
            initSharePop();
        }
        mSharePop.showAtLocation(mBtnShare, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onReceiveMessageClick() {
        if (mMessagePop == null) {
            initMessagePop();
        }
        mMessagePop.showAtLocation(mBtnReceiveMessage, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onSendGiftClick() {
        if (mGiftPop == null) {
            initGiftPop();
        }
        mGiftPop.showAtLocation(mBtnSendGift, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onExitClick() {
        getActivity().finish();
    }

    @Override
    public void onFollowRes(boolean isSuccess) {
        if (isInFragment) {
            if (isSuccess) {
                if (mBtnFollow != null) {
                    mBtnFollow.setVisibility(View.GONE);
                }
                ToastUtils.ToastMessage(getActivity(), R.string.toast_follow_success);
            } else {
                ToastUtils.ToastMessage(getActivity(), R.string.toast_follow_failure);
            }
        }
    }

    /**
     * 会话列表
     */
    @Override
    public void replaceChatList(List<MemberBean> beans) {
        if (isInFragment) {
            if (beans != null && mMessagePop != null) {
                mMessagePop.replaceList(beans);
            }
        }
    }

    /**
     * 添加看直播成员
     */
    @Override
    public void addMember(NettyRoomMemberBean bean) {
        if (isInFragment) {
            if (bean != null && bean.getUser() != null && mFansAdapter != null) {
                mFansAdapter.addListBeanAtStart(bean.getUser());
                mTvWatchNum.setText(String.valueOf(mFansAdapter.getItemCount()));
                mListFans.scrollToPosition(0);
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
                mListMessage.scrollToPosition(mMessageAdapter.getItemCount()-1);
            }
        }
    }

    /**
     * 私聊消息
     */
    @Override
    public void addPrivateChat(NettyReceivePrivateBean bean) {
        if (isInFragment) {
            if (bean != null && mFansMessagePop != null) {
                mFansMessagePop.addMessage(bean);
            }
        }
    }

    /**
     * 用户退出群聊
     */
    @Override
    public void userOutGroup(NettyNoticeBean bean) {
        if (isInFragment) {
            if (bean != null && mFansAdapter != null && bean.getUser() != null) {
                mFansAdapter.removeBeanByUserId(bean.getUser().getIdStr());
            }
        }
    }

    @Override
    public void backListSuccess() {
        ToastUtils.ToastMessage(mContext, getString(R.string.toast_add_blacklist_success));
    }

    @Override
    public void inOutGuardSuccess() {
        ToastUtils.ToastMessage(mContext, getString(R.string.toast_set_success));
    }

    @Override
    public void inOutGuardError(String msg) {
        ToastUtils.ToastMessage(mContext, msg);
    }

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

    @Override
    public void addMessage(MemberBean bean) {
        if (isInFragment) {
            if (isInFragment) {
                if (bean != null && mMessagePop != null) {
                    mMessagePop.add(bean);
                }
            }
        }
    }

    @Override
    public void upDateApater(String charId, int charStatus) {
        mFansMessagePop.upDateApater(charId, charStatus);
    }

    public static RoomFragment newInstance() {
        Bundle args = new Bundle();
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
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
        if (mDiamondsPop != null && mDiamondsPop.isShowing()) {
            mDiamondsPop.dismiss();
        }
        if (mGiftPop != null && mGiftPop.isShowing()) {
            mGiftPop.dismiss();
        }
        if (mMessagePop != null && mMessagePop.isShowing()) {
            mMessagePop.dismiss();
        }
        if (mSharePop != null && mSharePop.isShowing()) {
            mSharePop.dismiss();
        }
        if (mFansMessagePop != null && mFansMessagePop.isShowing()) {
            mFansMessagePop.dismiss();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.onDestroy();
        smallGiftControl.cleanAll();
        bigGigtControl.cleanAll();
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
