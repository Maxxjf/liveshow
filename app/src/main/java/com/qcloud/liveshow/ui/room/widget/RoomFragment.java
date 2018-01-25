package com.qcloud.liveshow.ui.room.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.qcloud.liveshow.beans.DiamondsBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyForbiddenBean;
import com.qcloud.liveshow.beans.NettyGiftBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyPayVipRoomReveice;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.PayResult;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.beans.UserStatusBean;
import com.qcloud.liveshow.constant.UrlConstants;
import com.qcloud.liveshow.enums.GiftTypeEnum;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.enums.UserIdentityEnum;
import com.qcloud.liveshow.ui.room.presenter.impl.RoomControlPresenterImpl;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.liveshow.utils.PayPalHelper;
import com.qcloud.liveshow.utils.ShareUtil;
import com.qcloud.liveshow.utils.UserInfoUtil;
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
import com.qcloud.qclib.FrameConfig;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.NetUtils;
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

    private MarqueeView mTvNotice;
    private LinearLayout mLayoutNoticeBg;
    private RecyclerView mListMessage;
    private LinearLayout mLayoutNotice;

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
    private DiamondsBean mCurrentDiamondsBean;//当前的充值Bean
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
    private TipsPop payPop;
    private Disposable payDisposable;
    private Disposable freeTimeDisposable;
    private PayPalHelper payPalHelper=PayPalHelper.getInstance();
    private UserIdentityEnum userIdentity;//当前人身份 0:观众 1:守护 2:主播
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
        payPalHelper.startPayPalService(getActivity());
        if (mCurrBean!=null){
            mPresenter.getUserIdentity(UserInfoUtil.mUser.getIdStr(),mCurrBean.getRoomIdStr());
        }else {
            ToastUtils.ToastMessage(getActivity(),"房间出错");
        }
    }

    /**
     * 免费观看时长，过了之后弹框，是否继续观看
     */
    private void initFreeTime() {
        if (mCurrBean != null&&mCurrBean.getFreeTime()>0 && mCurrBean.getType().equals(getResources().getString(R.string.tag_vip_room))) {//
           freeTimeDisposable= Observable.timer(mCurrBean.getFreeTime(), TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (isInFragment){
                                if (payPop == null) {
                                    initPayWindow();
                                }
                                payPop.showAtLocation(mLayoutNotice, Gravity.CENTER, 0, 0);
                            }
                        }
                    });
        }
    }


    private void initPayWindow() {
        payPop = new TipsPop(getActivity());
        payPop.setTips(getResources().getString(R.string.tip_pay_for_vip_room));
        payPop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_ok:
                        startTime();
                        break;
                    case R.id.btn_cancel:
                        payPop.dismiss();
                        break;
                }
            }
        });
    }
    //计费开始，一秒钟一次
    private void startTime() {
        if (payDisposable == null) {
            payDisposable = Observable.interval(60, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                    doOnDispose(new Action() {
                        @Override
                        public void run() throws Exception {
                            mPresenter.watchCalculate(mCurrBean.getRoomIdStr());
                        }
                    }).subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long aLong) throws Exception {
                    mPresenter.payVip(mCurrBean.getRoomIdStr());
                }
            });
        }
    }

    public void cancelPay() {
        if (payDisposable != null && !payDisposable.isDisposed()) {
            payDisposable.dispose();
        }
    }
    public void cancelStratTime() {
        if (freeTimeDisposable != null && !freeTimeDisposable.isDisposed()) {
            freeTimeDisposable.dispose();
        }
    }
    /**收到IM为17时*/
    @Override
    public void payVipRoom(NettyPayVipRoomReveice bean) {
        if (bean!=null){
            if (mGiftPop!=null){
                mGiftPop.setVirtualCoin(bean.getVirtualCoin());
            }
            if (!bean.isCanWatch()){
                cancelPay();
                noMoney();
            }
        }
    }

    @Override
    public void getUserIdentity(UserStatusBean userStatusBean) {
         userIdentity=UserIdentityEnum.valueOf(userStatusBean.getIdentity());
    }

    /**
     * 初始化Member的是否关注，是否被拉黑，是否被禁言。是否设置为守护
     * @param userStatusBean
     */
    @Override
    public void getUserIsAttention(UserStatusBean userStatusBean) {
        if (mMemberBean!=null){
            mMemberBean.setAttention(userStatusBean.isAttention());
            mMemberBean.setBlack(userStatusBean.isBlack());
            mMemberBean.setForbidden(userStatusBean.isForbidden());
            mMemberBean.setGuard(userStatusBean.isGuard());
        }
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
        initFreeTime();
    }

    @Override
    protected void beginLoad() {
        Timber.e("beginLoad()");
        if (isFirstInRoom) {
            isFirstInRoom = false;
        } else {
            cancelStratTime();//先取消免费时长
            initFreeTime();//再初始化免费时长
            refreshAndLoadData();
        }
    }

    /**
     * 刷新房间
     */
    public void refreshRoom(RoomBean bean) {
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
        mMessageAdapter.removeAll();
        mFansAdapter.removeAll();

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

        mTvNotice = mView.findViewById(R.id.tv_notice);
        mLayoutNoticeBg = mView.findViewById(R.id.layout_notice_bg);
        mListMessage = mView.findViewById(R.id.list_message);
        mLayoutNotice = mView.findViewById(R.id.layout_notice);

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
        mDiamondsPop.setOnPayLinstener(new BuyDiamondsPop.DiamodesPayLinstener() {
            @Override
            public void goToPay(DiamondsBean bean) {
                mCurrentDiamondsBean=bean;
                mCurrentDiamondsBean.setSelect(true);
               pay(bean);
            }
        });
    }

    private void pay(DiamondsBean bean){
        //服务器地址
        String address="";
        //检查服务器是否可用
        try {
            address= FrameConfig.server.split("/")[2];
            if (address.contains(":")){
                address=address.split(":")[0];
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        startLoadingDialog();
        NetUtils.isNetWorkAvailable(address, new Comparable<Boolean>() {
            @Override
            public int compareTo(@NonNull Boolean aBoolean) {
//                stopLoadingDialog();
                Timber.e("bean:"+bean);
                Timber.e("aBoolean:"+aBoolean);
                if (aBoolean){
                    if (bean!=null&&bean.isSelect()){
                        payPalHelper.doPayPalPay(getActivity(),bean.getName(),bean.getMoney(),bean.getIdStr());
                    }
                }else {
                    stopLoadingDialog();
                    ToastUtils.ToastMessage(getActivity(),"服务器出错，请重新试试");
                }
                return 0;
            }
        });
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
                    noMoney();
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
    public void noMoney() {
        TipsPop pop = new TipsPop(mContext);
        pop.setTips(getResources().getString(R.string.tip_no_diamonds));
        pop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                switch (view.getId()){
                    case R.id.btn_ok:
                        onBuyDiamondsClick();
                        break;
                    case R.id.btn_cancel:
                        pop.dismiss();
                        break;
                }

            }
        });
        pop.showAtLocation(mLayoutBottom, Gravity.CENTER, 0, 0);
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
            } else if(view.getId()==R.id.btn_follow){
                ToastUtils.ToastMessage(getActivity(),"点击了关注按钮");
            }else  {
                mFansPop.dismiss();
            }
        });
    }

    /**
     * 初始化管理弹窗
     */
    private void initFansManagerPop() {
        mManagerPop = new FansManagerPop(mContext);
        switch (userIdentity){
            case Audience:
                mManagerPop.noGuarder();//去掉守护列表
                mManagerPop.noSetGuarder();//去掉设置守护
                mManagerPop.noGag();//去掉禁言
                break;
            case Guard:
                mManagerPop.noGuarder();//去掉守护列表
                mManagerPop.noSetGuarder();//去掉设置守护
                break;
            case Anchor://不可能为直播的，所以这块没写
                break;
            default:
                break;
        }
        mManagerPop.setOnHolderClick(view -> {
            switch (view.getId()) {
                case R.id.btn_set_guarder:
                    mPresenter.inOutGuard(mMemberBean.getId(), !mMemberBean.isGuard());
                    break;
                case R.id.btn_my_guarder_list:
                    if (mGuarderPop == null) {
                        initGuarderPop();
                    }
                    mGuarderPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.btn_gag:
                    mPresenter.shutUp(mCurrBean.getRoomIdStr(), mMemberBean.getIdStr(), !mMemberBean.isForbidden());
                    break;
                case R.id.btn_add_blacklist:
                    mPresenter.submitAttention(StartFansEnum.Blacklist.getKey(), mMemberBean.getId(), !mMemberBean.isAttention());
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
                                    mCurrBean.getCover(),
                                    room.getTitle(), getResources().getString(R.string.tip_room_descrption));
                            break;
                        case R.id.btn_share_wechat_circle:
                            shareUtil.shareWeb(SHARE_MEDIA.WEIXIN_CIRCLE, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mAnchorBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    mCurrBean.getCover(),
                                    room.getTitle(), getResources().getString(R.string.tip_room_descrption));
                            break;
                        case R.id.btn_facebook:
                            shareUtil.shareWeb(SHARE_MEDIA.FACEBOOK, UrlConstants.SHARP_LIVR_URL +
                                            "?idAccount=" + mAnchorBean.getIdAccount() + "&roomId=" + room.getRoomIdStr(),
                                    mCurrBean.getCover(),
                                    room.getTitle(), getResources().getString(R.string.tip_room_descrption));
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
            mPresenter.getUserIsAttention(mMemberBean.getIdStr(),mCurrBean.getRoomIdStr());
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
        disposable = Observable.interval(60, TimeUnit.SECONDS).take(10).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
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
                mListMessage.scrollToPosition(mMessageAdapter.getItemCount() - 1);
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
                if (bean.getUser().getIdStr().equals(mAnchorBean.getIdStr())) {//主播退出房间
                    RoomFinishActivity.openActivity(mContext, mAnchorBean);
                    getActivity().finish();
                } else {
                    mFansAdapter.removeBeanByUserId(bean.getUser().getIdStr());
                }

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        payPalHelper.confirmPayResult(requestCode, resultCode, data,mCurrentDiamondsBean.getMoney(),mCurrentDiamondsBean.getIdStr(),
                new PayPalHelper.DoResult() {
                    @Override
                    public void confirmSuccess(PayResult param) {
                        stopLoadingDialog();
                        if (param.getState()==1){//成功
                            if (mGiftPop!=null){
                                mGiftPop.setVirtualCoin(param.getVirtualCoin());
                            }
                            ToastUtils.ToastMessage(getActivity(),getResources().getString(R.string.tip_diamonds_success));
                            Timber.e("confirmSuccess");
                        }else if (param.getState()==2){
                            ToastUtils.ToastMessage(getActivity(),getResources().getString(R.string.tip_diamonds_again));
                        }else {
                            ToastUtils.ToastMessage(getActivity(),getResources().getString(R.string.tip_diamonds_fail));
                        }
                    }

                    @Override
                    public void confirmNetWorkError() {
                        stopLoadingDialog();
                        Timber.e("confirmNetWorkError");
                    }

                    @Override
                    public void customerCanceled() {
                        stopLoadingDialog();
                        Timber.e("customerCanceled");
                    }

                    @Override
                    public void confirmFuturePayment() {
                        stopLoadingDialog();
                        Timber.e("confirmFuturePayment");
                    }

                    @Override
                    public void invalidPaymentConfiguration() {
                        stopLoadingDialog();
                        Timber.e("invalidPaymentConfiguration");
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelPay();
        payPalHelper.stopPayPalService(getActivity());
        if (mCurrBean != null) {
            mPresenter.outGroup(mCurrBean.getRoomIdStr());
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
