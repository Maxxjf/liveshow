package com.qcloud.liveshow.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FansMessageAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.constant.AppConstants;
import com.qcloud.liveshow.ui.home.presenter.impl.FansMessagePresenterImpl;
import com.qcloud.liveshow.ui.home.view.IFansMessageView;
import com.qcloud.liveshow.utils.EmojiClickManagerUtil;
import com.qcloud.liveshow.widget.customview.EmojiLayout;
import com.qcloud.liveshow.widget.customview.KeyBackEditText;
import com.qcloud.liveshow.widget.customview.NoDataView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.ConstantUtil;
import com.qcloud.qclib.utils.DensityUtils;
import com.qcloud.qclib.utils.KeyBoardUtils;
import com.qcloud.qclib.utils.StringUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * 类说明：粉丝消息
 * Author: Kuzan
 * Date: 2017/9/11 12:07.
 */
public class FansMessageActivity extends SwipeBaseActivity<IFansMessageView, FansMessagePresenterImpl> implements IFansMessageView {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.list_message)
    PullRefreshRecyclerView mListMessage;
    @Bind(R.id.et_message)
    KeyBackEditText mEtMessage;
    @Bind(R.id.btn_emoticon)
    ImageView mBtnEmoticon;
    @Bind(R.id.layout_parent)
    View mLayoutParent;
    @Bind(R.id.layout_emoji)
    EmojiLayout mLayoutEmoji;

    private NoDataView mEmptyView;

    private FansMessageAdapter mAdapter;
    private MemberBean mMemberBean;

    private Rect tmp = new Rect();
    private int mScreenHeight;

    /**是否显示表情*/
    private boolean isShowEmoji;

    private EmojiClickManagerUtil mClickManager;

    @Override
    protected int initLayout() {
        return R.layout.activity_fans_message;
    }

    @Override
    protected FansMessagePresenterImpl initPresenter() {
        return new FansMessagePresenterImpl();
    }

    @Override
    protected int setStatusBarColor() {
        return ContextCompat.getColor(this, R.color.white);
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    protected void initViewAndData() {
        initRefreshList();
        initEmojiLayout();

        initEditMessage();
        initData();
    }

    private void initData() {
         mMemberBean = (MemberBean) getIntent().getSerializableExtra("formUser");
        if (mMemberBean != null){
            mAdapter.refreshMember(mMemberBean);
            String fromUserId=mMemberBean.getIdStr();
            if (mPresenter!=null){
                mPresenter.getChars(fromUserId);//获取所有私聊列表
            }

            mTitleBar.setTitle(mMemberBean.getNickName());
        }
    }

    /**
     * 初始化刷新布局
     * */
    private void initRefreshList() {
        PullRefreshUtil.setRefresh(mListMessage, false, false);
        mListMessage.setOnPullDownRefreshListener(() -> mListMessage.refreshFinish());

        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new FansMessageAdapter(mContext);
        mListMessage.setAdapter(mAdapter);

        mEmptyView = new NoDataView(this);
        mListMessage.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
    }

    /**
     * 初始化表情布局
     * */
    private void initEmojiLayout() {
        mLayoutEmoji.initIndicator(getSupportFragmentManager());
        mLayoutEmoji.attachToEditText(mEtMessage);
        mClickManager = EmojiClickManagerUtil.getInstance();
        mClickManager.attachToEditText(mEtMessage);
    }

    /**
     * 初始化输入弹窗
     * */
    private void initEditMessage() {
        int defaultHeight = DensityUtils.dp2px(this, 240);
        int height = ConstantUtil.getInt(AppConstants.LAST_KEYBOARD_HEIGHT, defaultHeight);
        ViewGroup.LayoutParams params = mLayoutEmoji.getLayoutParams();
        if (params != null) {
            params.height = height;
            mLayoutEmoji.setLayoutParams(params);
        }

        //监听键盘
        mEtMessage.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case KeyEvent.KEYCODE_ENDCALL:
                case KeyEvent.KEYCODE_ENTER:
                    String context = mEtMessage.getText().toString();
                    if (StringUtils.isEmptyString(context)) {
                        ToastUtils.ToastMessage(mContext, R.string.input_content_hint);
                        return true;
                    }
                    mPresenter.sendMessage(mMemberBean.getIdStr(), context);
                    mEtMessage.setText("");
                    KeyBoardUtils.hideKeybord(mContext, mEtMessage);
                    return true;
                case KeyEvent.KEYCODE_BACK:

                    return false;
                default:
                    return false;
            }
        });

        mEtMessage.getViewTreeObserver().addOnPreDrawListener(() -> {
            if (isShowEmoji) {
                // 在设置isShowEmoji时已经调用了隐藏Keyboard的方法，直到Keyboard隐藏前都取消重给
                if (isKeyboardVisible()) {
                    ViewGroup.LayoutParams params1 = mLayoutEmoji.getLayoutParams();
                    int distance = getDistanceFromInputToBottom();
                    // 调整EmojiLayout高度
                    if (distance > AppConstants.DISTANCE_SLOP && distance != params1.height) {
                        params1.height = distance;
                        mLayoutEmoji.setLayoutParams(params1);
                        ConstantUtil.writeInt(AppConstants.LAST_KEYBOARD_HEIGHT, distance);
                    }
                    return false;
                } else { // 键盘已隐藏，显示EmojiLayout
                    showEmoji();
                    isShowEmoji = false;
                    return false;
                }
            } else {
                if (isEmojiVisible() && isKeyboardVisible()) {
                    hideEmoji();
                    return false;
                }
            }
            return true;
        });

        mEtMessage.setOnBackPressedListener(() -> {
            if (isEmojiVisible()) {
                hideEmoji();
                return true;
            } else if (isKeyboardVisible()) {
                KeyBoardUtils.hideKeybord(this, mEtMessage);
                return true;
            }
            return false;
        });
    }

    /**
     * 显示表情布局
     * */
    private void showEmoji() {
        if (mLayoutEmoji.getVisibility() != View.VISIBLE) {
            mLayoutEmoji.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏表情布局
     * */
    private void hideEmoji() {
        if (mLayoutEmoji.getVisibility() != View.GONE) {
            mLayoutEmoji.setVisibility(View.GONE);
        }
    }

    /**
     * 表情布局是否显示
     * */
    private boolean isEmojiVisible() {
        return mLayoutEmoji.getVisibility() == View.VISIBLE;
    }

    /**
     * 是否显示键盘
     * */
    private boolean isKeyboardVisible() {
        return (getDistanceFromInputToBottom() > AppConstants.DISTANCE_SLOP && !isEmojiVisible())
                || (getDistanceFromInputToBottom() > (mLayoutEmoji.getHeight() + AppConstants.DISTANCE_SLOP) && isEmojiVisible());
    }

    /**
     * 输入框的下边距离屏幕的距离
     */
    private int getDistanceFromInputToBottom() {
        return mScreenHeight - getInputBottom();
    }

    /**
     * 输入框下边的位置
     */
    private int getInputBottom() {
        mEtMessage.getGlobalVisibleRect(tmp);
        return tmp.bottom;
    }

    @OnClick({R.id.btn_emoticon})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onEmojiClick() {
        if (isEmojiVisible()) {
            hideEmoji();
        } else if (isKeyboardVisible()) {
            isShowEmoji = true;
            KeyBoardUtils.hideKeybord(this, mEtMessage);
        } else {
            showEmoji();
        }
    }

    /**
     * 加并且刷新消息
     * */
    @Override
    public void addMessage(NettyReceivePrivateBean bean) {
        if (isRunning) {
            if (mAdapter != null && bean != null && mMemberBean.getIdStr().equals(bean.getFrom_user_idStr())) {
                mAdapter.addListBeanAtEnd(bean);
                hideEmptyView();
            }
        }
    }

    @Override
    public void replaceList(List<NettyReceivePrivateBean> beans) {
        if (isRunning) {
            if (mListMessage != null) {
                mListMessage.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
            } else {
                showEmptyView(getResources().getString(R.string.tip_no_data));
            }
        }
    }

    @Override
    public void showEmptyView(String tip) {
        if (mListMessage != null) {
            mListMessage.showEmptyView();
        }
    }

    @Override
    public void hideEmptyView() {
        if (mListMessage != null) {
            mListMessage.hideEmptyView();
        }
    }
    @Override
    public void loadErr(boolean isShow, String errMsg) {
        if (isRunning) {
            if (isShow) {
                ToastUtils.ToastMessage(this, errMsg);
            } else {
                Timber.e(errMsg);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && mScreenHeight <= 0) {
            mLayoutParent.getGlobalVisibleRect(tmp);
            mScreenHeight = tmp.bottom;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        if (mClickManager != null) {
            mClickManager.unAttachToEditText();
        }
        if (mLayoutEmoji != null) {
            mLayoutEmoji.onDestroy();
        }
        super.onDestroy();
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, FansMessageActivity.class);
        context.startActivity(intent);
    }

    public static void openActivity(Context context, MemberBean fromUserId) {
        Intent intent = new Intent(context, FansMessageActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("formUser",fromUserId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
