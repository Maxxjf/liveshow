package com.qcloud.liveshow.ui.anchor.widget;

import android.content.DialogInterface;
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
import com.qcloud.liveshow.ui.anchor.presenter.impl.AnchorControlPresenterImpl;
import com.qcloud.liveshow.ui.anchor.view.IAnchorControlView;
import com.qcloud.liveshow.widget.customview.UserHeadImageView;
import com.qcloud.liveshow.widget.dialog.InputMessageDialog;
import com.qcloud.liveshow.widget.pop.FansInfoPop;
import com.qcloud.liveshow.widget.pop.MessageListPop;
import com.qcloud.liveshow.widget.pop.SharePop;
import com.qcloud.qclib.adapter.recyclerview.CommonRecyclerAdapter;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.SystemBarUtil;
import com.qcloud.qclib.widget.customview.MarqueeView;
import com.qcloud.qclib.widget.customview.clearscreen.ClearScreenHelper;
import com.qcloud.qclib.widget.customview.clearscreen.IClearEvent;
import com.qcloud.qclib.widget.customview.clearscreen.IClearRootView;
import com.qcloud.qclib.widget.customview.clearscreen.view.RelativeClearLayout;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：直播间控制页面
 * Author: Kuzan
 * Date: 2017/9/2 15:50.
 */
public class AnchorFragment extends BaseFragment<IAnchorControlView, AnchorControlPresenterImpl> implements IAnchorControlView {

    @Bind(R.id.layout_user)
    UserHeadImageView mLayoutUser;
    @Bind(R.id.tv_time)
    TextView mTvTime;
    @Bind(R.id.tv_fans)
    TextView mTvFans;
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

    private IClearRootView mClearRootLayout;
    private ClearScreenHelper mClearScreenHelper;


    private RoomFansAdapter mFansAdapter;
    private RoomMessageAdapter mMessageAdapter;

    /**fragment点击事件回调*/
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor;
    }

    @Override
    protected AnchorControlPresenterImpl initPresenter() {
        return new AnchorControlPresenterImpl();
    }

    @Override
    protected void initViewAndData() {
        initClearLayout();
        initFansLayout();
        initMessageLayout();
    }

    @Override
    protected void beginLoad() {
        refreshAndLoadData();
    }

    /**
     * 刷新和重新加载数据
     * */
    private void refreshAndLoadData() {
        Timber.e("refreshAndLoadData()");

        if (mTvFans != null) {
            mTvFans.setText("1234");
        }
        if (mTvId != null) {
            mTvId.setText("3399366");
        }
        if (mTvNotice != null) {
            mTvNotice.stopScroll();
            mTvNotice.setText("看我直播，不要睡觉~");
            resetNoticeWith();
        }
    }

    /**
     * 设置公告背景宽度
     */
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
        mMessagePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                SystemBarUtil.hideNavBar(getActivity());
            }
        });
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
        AnchorFinishActivity.openActivity(getActivity());
        getActivity().finish();
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
    }

    /**
     * 点击事件回调
     * */
    public void setOnFragmentClickListener(OnFragmentClickListener listener) {
        this.mListener = listener;
    }

    public static AnchorFragment newInstance() {
        AnchorFragment fragment = new AnchorFragment();
        return fragment;
    }
}
