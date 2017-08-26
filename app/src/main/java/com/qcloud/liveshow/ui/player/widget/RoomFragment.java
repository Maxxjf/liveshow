package com.qcloud.liveshow.ui.player.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
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
import com.qcloud.liveshow.ui.player.presenter.impl.RoomControlPresenterImpl;
import com.qcloud.liveshow.ui.player.view.IRoomControlView;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.liveshow.widget.dialog.InputMessageDialog;
import com.qcloud.liveshow.widget.pop.TipsPop;
import com.qcloud.qclib.base.BasePopupWindow;
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

    private InputMessageDialog mInputDialog;

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
        initInputDialog();
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
     * 粉丝列表
     * */
    private void initFansLayout() {
        mFansAdapter = new RoomFansAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mListFans.setLayoutManager(manager);
        mListFans.setAdapter(mFansAdapter);
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
            } else {
                mTvNotice.setVisibility(View.VISIBLE);
                mTvNotice.reset();
            }
        }
    }

    @Override
    public void onSendMessageClick() {
        if (mInputDialog == null) {
            mInputDialog = new InputMessageDialog(getActivity());
        }
        mInputDialog.show();
    }

    @Override
    public void onBuyDiamondsClick() {

    }

    @Override
    public void onShareClick() {

    }

    @Override
    public void onReceiveMessageClick() {

    }

    @Override
    public void onSendGiftClick() {

    }

    @Override
    public void onExitClick() {
        final TipsPop pop = new TipsPop(getActivity());
        pop.setTips(R.string.tip_confirm_to_exit_room);
        pop.setCancelBtn(R.string.tip_cancel);
        pop.setOkBtn(R.string.tip_confirm);
        pop.setOnHolderClick(new BasePopupWindow.onPopWindowViewClick() {
            @Override
            public void onViewClick(View view) {
                if (view.getId() == R.id.btn_ok) {
                    getActivity().finish();
                } else {
                    pop.dismiss();
                }
            }
        });
        pop.showAtLocation(mBtnExit, Gravity.CENTER, 0, 0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
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
    }
}
