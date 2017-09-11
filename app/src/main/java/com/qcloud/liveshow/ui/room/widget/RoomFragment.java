package com.qcloud.liveshow.ui.room.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.RoomFansAdapter;
import com.qcloud.liveshow.adapter.RoomMessageAdapter;
import com.qcloud.liveshow.base.BaseFragment;
import com.qcloud.liveshow.beans.LiveShowBean;
import com.qcloud.liveshow.ui.room.presenter.impl.RoomControlPresenterImpl;
import com.qcloud.liveshow.ui.room.view.IRoomControlView;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.liveshow.widget.dialog.InputMessageDialog;
import com.qcloud.liveshow.widget.pop.BuyDiamondsPop;
import com.qcloud.liveshow.widget.pop.FansInfoPop;
import com.qcloud.liveshow.widget.pop.FansMessagePop;
import com.qcloud.liveshow.widget.pop.MessageListPop;
import com.qcloud.liveshow.widget.pop.SendGiftPop;
import com.qcloud.liveshow.widget.pop.SharePop;
import com.qcloud.liveshow.widget.pop.SystemMessagePop;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.MarqueeView;
import com.qcloud.qclib.widget.customview.clearscreen.ClearScreenHelper;
import com.qcloud.qclib.widget.customview.clearscreen.IClearEvent;
import com.qcloud.qclib.widget.customview.clearscreen.IClearRootView;
import com.qcloud.qclib.widget.customview.clearscreen.view.RelativeClearLayout;

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
    private TextView mTvTitle;
    private TextView mTvFans;
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
    private LiveShowBean mCurrBean;

    /**输入消息弹窗*/
    private InputMessageDialog mInputDialog;
    /**购买钻石币弹窗*/
    private BuyDiamondsPop mDiamondsPop;
    /**发送礼物弹窗*/
    private SendGiftPop mGiftPop;
    /**粉丝信息弹窗*/
    private FansInfoPop mFansPop;
    /**消息列表弹窗*/
    private MessageListPop mMessagePop;
    /**系统消息弹窗*/
    private SystemMessagePop mSystemPop;
    /**粉丝消息弹窗*/
    private FansMessagePop mFansMessagePop;
    /**分享弹窗*/
    private SharePop mSharePop;

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
    public void refreshRoom(LiveShowBean bean) {
        Timber.e("refreshRoom()");
        mCurrBean = bean;
    }

    /**
     * 刷新和重新加载数据
     * */
    private void refreshAndLoadData() {
        Timber.e("refreshAndLoadData()");
        if (mCurrBean == null) {
            return;
        }
        if (mTvTitle != null) {
            mTvTitle.setText(mCurrBean.getName());
        }
        if (mTvFans != null) {
            mTvFans.setText(mCurrBean.getOnline_users()+"");
        }
        if (mTvId != null) {
            mTvId.setText(mCurrBean.getId()+"");
        }
        if (mTvNotice != null) {
            mTvNotice.stopScroll();
            mTvNotice.setText("我就是这么6......");
            resetNoticeWith();
        }
    }

    /**
     * 初始化控件
     * */
    private void initView() {
        mUserHead = (UserHeadImageView) mView.findViewById(R.id.layout_user);
        mTvTitle = (TextView) mView.findViewById(R.id.tv_title);
        mTvFans = (TextView) mView.findViewById(R.id.tv_fans);
        mBtnFollow = (TextView) mView.findViewById(R.id.btn_follow);
        mListFans = (RecyclerView) mView.findViewById(R.id.list_fans);
        mLayoutTop = (LinearLayout) mView.findViewById(R.id.layout_top);

        mTvId = (TextView) mView.findViewById(R.id.tv_id);
        mLayoutId = (LinearLayout) mView.findViewById(R.id.layout_id);

        mBtnNotice = (ImageView) mView.findViewById(R.id.btn_notice);
        mTvNotice = (MarqueeView) mView.findViewById(R.id.tv_notice);
        mLayoutNoticeBg = (LinearLayout) mView.findViewById(R.id.layout_notice_bg);
        mListMessage = (RecyclerView) mView.findViewById(R.id.list_message);
        mLayoutNotice = (LinearLayout) mView.findViewById(R.id.layout_notice);

        mBtnSendMessage = (ImageView) mView.findViewById(R.id.btn_send_message);
        mBtnBuyDiamonds = (ImageView) mView.findViewById(R.id.btn_buy_diamonds);
        mBtnShare = (ImageView) mView.findViewById(R.id.btn_share);
        mBtnReceiveMessage = (ImageView) mView.findViewById(R.id.btn_receive_message);
        mBtnSendGift = (ImageView) mView.findViewById(R.id.btn_send_gift);
        mLayoutBottom = (RelativeLayout) mView.findViewById(R.id.layout_bottom);

        mBtnExit = (ImageView) mView.findViewById(R.id.btn_exit);

        mUserHead.loadImage("", R.drawable.icon_anchor_level_4, 80);
    }

    /**
     * 设置公告背景宽度
     * */
    private void resetNoticeWith() {
        if (mTvNotice != null) {
            mTvNotice.post(new Runnable() {
                @Override
                public void run() {
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
                }
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
        mInputDialog.setOnMessageSendListener(new InputMessageDialog.OnMessageSendListener() {
            @Override
            public void onMessageSend(String message, boolean isNotice) {
                ToastUtils.ToastMessage(getActivity(), message);
            }
        });
        mInputDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    /**
     * 初始化购买钻石币弹窗
     * */
    private void initDiamondsPop() {
        mDiamondsPop = new BuyDiamondsPop(mContext);
        mDiamondsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    /**
     * 初始化发送礼物弹窗
     * */
    private void initGiftPop() {
        mGiftPop = new SendGiftPop(mContext);
        mGiftPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    /**
     * 初始化粉丝信息弹窗
     * */
    private void initFansPop() {
        mFansPop = new FansInfoPop(mContext);
        mFansPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    /**
     * 初始化消息列表弹窗
     * */
    private void initMessagePop() {
        mMessagePop = new MessageListPop(mContext);
        mMessagePop.setOnPopItemClick(new MessageListPop.onPopItemClick() {
            @Override
            public void onItemClick(int position, String item) {
                if (mFansMessagePop == null) {
                    initFansMessagePop();
                }
                mFansMessagePop.showAtLocation(mBtnReceiveMessage, Gravity.BOTTOM, 0, 0);
            }
        });
        mMessagePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
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
        mSharePop = new SharePop(mContext);
        mSharePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
    }

    /**
     * 粉丝列表
     * */
    private void initFansLayout() {
        mFansAdapter = new RoomFansAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mListFans.setLayoutManager(manager);
        mListFans.setAdapter(mFansAdapter);
        mFansAdapter.setOnHolderClick(new CommonRecyclerAdapter.ViewHolderClick<String>() {
            @Override
            public void onViewClick(View view, String s, int position) {
                if (mFansPop == null) {
                    initFansPop();
                }
                mFansPop.showAtLocation(mBtnExit, Gravity.BOTTOM, 0, 0);
            }
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
        ToastUtils.ToastMessage(getActivity(), "关注");
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
    }
}
