package com.qcloud.liveshow.ui.room.widget;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.RoomFansAdapter;
import com.qcloud.liveshow.adapter.RoomMessageAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.AnchorBean;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyLiveNoticeBean;
import com.qcloud.liveshow.beans.NettyNoticeBean;
import com.qcloud.liveshow.beans.NettyReceiveGroupBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.beans.NettyRoomMemberBean;
import com.qcloud.liveshow.beans.RoomBean;
import com.qcloud.liveshow.enums.StartFansEnum;
import com.qcloud.liveshow.ui.room.presenter.impl.RoomControlPresenterImpl;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.liveshow.widget.dialog.InputMessageDialog;
import com.qcloud.liveshow.widget.pop.BuyDiamondsPop;
import com.qcloud.liveshow.widget.pop.FansInfoPop;
import com.qcloud.liveshow.widget.pop.FansManagerPop;
import com.qcloud.liveshow.widget.pop.FansMessagePop;
import com.qcloud.liveshow.widget.pop.GuarderPop;
import com.qcloud.liveshow.widget.pop.MessageListPop;
import com.qcloud.liveshow.widget.pop.SendGiftPop;
import com.qcloud.liveshow.widget.pop.SharePop;
import com.qcloud.liveshow.widget.pop.SystemMessagePop;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.widget.customview.MarqueeView;
import com.qcloud.qclib.widget.customview.clearscreen.ClearScreenHelper;
import com.qcloud.qclib.widget.customview.clearscreen.IClearEvent;
import com.qcloud.qclib.widget.customview.clearscreen.IClearRootView;
import com.qcloud.qclib.widget.customview.clearscreen.view.RelativeClearLayout;

import java.util.List;

import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：直播间控制面板
 * Author: Kuzan
 * Date: 2017/8/22 18:54.
 */
public class RoomFragment extends BaseFragment<IRoomControlView, RoomControlPresenterImpl> implements IRoomControlView {
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
    /**是否第一次进入房间*/
    private boolean isFirstInRoom = true;
    private RoomBean mCurrBean;
    private AnchorBean mAnchorBean;

    private MemberBean mMemberBean;

    /**输入消息弹窗*/
    private InputMessageDialog mInputDialog;
    /**购买钻石币弹窗*/
    private BuyDiamondsPop mDiamondsPop;
    /**发送礼物弹窗*/
    private SendGiftPop mGiftPop;
    /**粉丝信息弹窗*/
    private FansInfoPop mFansPop;
    /**守护者弹窗*/
    private GuarderPop mGuarderPop;
    /**消息列表弹窗*/
    private MessageListPop mMessagePop;
    /**系统消息弹窗*/
    private SystemMessagePop mSystemPop;
    /**粉丝消息弹窗*/
    private FansMessagePop mFansMessagePop;
    /**分享弹窗*/
    private SharePop mSharePop;
    /**粉丝管理弹窗*/
    private FansManagerPop mManagerPop;

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
     * */
    public void refreshRoom(RoomBean bean) {
        Timber.e("refreshRoom()");
        mCurrBean = bean;
    }

    /**
     * 进入群聊，返回用户列表
     */
    private void intoRoom() {
        if (mCurrBean != null) {
            mPresenter.joinGroup( mCurrBean.getRoomIdStr());
        }
    }

    /**
     * 刷新和重新加载数据
     * */
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
     * */
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
     * */
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
     * */
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
     * */
    private void initClearLayout() {
        mClearRootLayout = (RelativeClearLayout) mView.findViewById(R.id.layout_root);
        mClearScreenHelper = new ClearScreenHelper(getActivity(), mClearRootLayout);
        mClearScreenHelper.bind(mLayoutTop, mLayoutId, mLayoutNotice, mLayoutBottom);
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
     * */
    private void initInputDialog() {
        mInputDialog = new InputMessageDialog(getActivity());
        mInputDialog.setNoNotice();
        mInputDialog.setOnMessageSendListener((message, isNotice) -> {
            if (mCurrBean != null) {
                mPresenter.sendGroupMessage(mCurrBean.getRoomIdStr(), message);
            }
        });
    }

    /**
     * 初始化购买钻石币弹窗
     * */
    private void initDiamondsPop() {
        mDiamondsPop = new BuyDiamondsPop(mContext);
    }

    /**
     * 初始化发送礼物弹窗
     * */
    private void initGiftPop() {
        if (mCurrBean!=null&&mAnchorBean!=null){
            mGiftPop = new SendGiftPop(mContext,mCurrBean,mAnchorBean);
        }
    }

    /**
     * 初始化粉丝信息弹窗
     * */
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
            }
            else {
                mFansPop.dismiss();
            }
        });
    }
    /**
     * 初始化管理弹窗
     * */
    private void initFansManagerPop() {
        mManagerPop = new FansManagerPop(mContext);
        mManagerPop.setOnHolderClick(view -> {
            switch (view.getId()) {
                case R.id.btn_set_guarder:
                    mPresenter.inOutGuard(mMemberBean.getId(),true);
                    break;
                case R.id.btn_my_guarder_list:

                    if (mGuarderPop==null){
                        initGuarderPop();
                    }
                    mGuarderPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.btn_gag:
                    mPresenter.shutUp(mCurrBean.getRoomIdStr(),mMemberBean.getIdStr(),true);
                    break;
                case R.id.btn_add_blacklist:
                    mPresenter.submitAttention(StartFansEnum.Blacklist.getKey(), mMemberBean.getId(),true);
                    break;
            }
        });
    }
    /**
     * 初始化粉守护者弹窗
     * */
    private void initGuarderPop() {
        mGuarderPop = new GuarderPop(mContext);
    }

    /**
     * 初始化消息列表弹窗
     * */
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
     * */
    private void initSystemMessagePop() {
        mSystemPop = new SystemMessagePop(getActivity());
    }

    /**
     * 粉丝消息弹窗
     * */
    private void initFansMessagePop() {
        mFansMessagePop = new FansMessagePop(getActivity());
    }

    /**
     * 初始化消息列表弹窗
     * */
    private void initSharePop() {
        if (mCurrBean!=null){
            mSharePop = new SharePop(mContext,mCurrBean.getRoomIdStr());
        }
    }

    /**
     * 粉丝列表
     * */
    private void initFansLayout() {
        mFansAdapter = new RoomFansAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mListFans.setLayoutManager(manager);
        mListFans.setAdapter(mFansAdapter);
        mFansAdapter.setOnHolderClick((view, bean, position) -> {
            mMemberBean =bean;
            if (mFansPop == null) {
                initFansPop();
            }
            mFansPop.refreshData(bean);
            mFansPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
        });
    }

    /**
     * 消息列表
     * */
    private void initMessageLayout() {
        mMessageAdapter = new RoomMessageAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mListMessage.setLayoutManager(manager);
        mListMessage.setAdapter(mMessageAdapter);
    }

    @OnClick({R.id.btn_follow, R.id.btn_notice, R.id.btn_send_message, R.id.btn_buy_diamonds,
            R.id.btn_share, R.id.btn_receive_message, R.id.btn_send_gift, R.id.btn_exit})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onFollowClick() {
        if (mAnchorBean != null) {
            mPresenter.submitAttention(StartFansEnum.MyFans.getKey(),mAnchorBean.getId(), true);
        }
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
     * */
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
     * */
    @Override
    public void addMember(NettyRoomMemberBean bean) {
        if (isInFragment) {
            if (bean != null && bean.getUser() != null && mFansAdapter != null) {
                mFansAdapter.addListBeanAtStart(bean.getUser());
                mTvWatchNum.setText(String.valueOf(mFansAdapter.getItemCount()));
            }
        }
    }

    /**
     * 群聊消息
     * */
    @Override
    public void addGroupChat(NettyReceiveGroupBean bean) {
        if (isInFragment) {
            if (bean != null && mMessageAdapter != null) {
                mMessageAdapter.addListBeanAtStart(bean);
            }
        }
    }

    /**
     * 私聊消息
     * */
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
     * */
    @Override
    public void userOutGroup(NettyNoticeBean bean) {
        if (isInFragment) {
            if (bean != null && mFansAdapter != null && bean.getUser()!=null) {
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
        ToastUtils.ToastMessage(mContext,msg);
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
        mFansMessagePop.upDateApater(charId,charStatus);
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
    }
}
