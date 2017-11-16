package com.qcloud.liveshow.ui.home.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FansMessageAdapter;
import com.qcloud.liveshow.base.SwipeBaseActivity;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.ui.home.presenter.impl.FansMessagePresenterImpl;
import com.qcloud.liveshow.ui.home.view.IFansMessageView;
import com.qcloud.liveshow.widget.customview.EmojiLayout;
import com.qcloud.liveshow.widget.customview.NoDataView;
import com.qcloud.liveshow.widget.toolbar.TitleBar;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.KeyBoardUtils;
import com.qcloud.qclib.utils.ScreenUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.ClearEditText;

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
    ClearEditText mEtMessage;
    @Bind(R.id.btn_emoticon)
    ImageView mBtnEmoticon;
    @Bind(R.id.layout_parent)
    LinearLayout mLayoutBottom;
    @Bind(R.id.layout_message)
    LinearLayout mLayoutMessage;
    @Bind(R.id.layout_emoji)
    EmojiLayout mLayoutEmoji;

    private NoDataView mEmptyView;

    private FansMessageAdapter mAdapter;
    private MemberBean mMemberBean;

    /**键盘和表情切换*/
    private final LayoutTransition mTransition = new LayoutTransition();
    /**表情布局高度*/
    private int emojiHeight;

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
        }
        setTitle(mMemberBean.getNickName());
    }

    private void initRefreshList() {
        PullRefreshUtil.setRefresh(mListMessage, false, false);
        mListMessage.setOnPullDownRefreshListener(() -> mListMessage.refreshFinish());

        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new FansMessageAdapter(mContext);
        mListMessage.setAdapter(mAdapter);

        mEmptyView = new NoDataView(this);
        mListMessage.setEmptyView(mEmptyView, Gravity.CENTER_HORIZONTAL);
    }

    private void initEditMessage() {
        //监听键盘
        //mEtMessage.postDelayed(() -> lockContainerHeight(ScreenUtils.getAppContentHeight(this)), 200);
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
                    //unlockContainerHeightDelayed();
                    return true;
                case KeyEvent.KEYCODE_BACK:

                    return false;
                default:
                    return false;
            }
        });
//        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
//            @Override
//            public void keyBoardShow(int height) {
//                Timber.e("显示输入框");
//            }
//
//            @Override
//            public void keyBoardHide(int height) {
//                Timber.e("隐藏输入框");
//                unlockContainerHeightDelayed();
//            }
//        });
    }

    private void initEmojiLayout() {
        mLayoutEmoji.initIndicator(getSupportFragmentManager());
    }

    @OnClick({R.id.btn_emoticon, R.id.layout_message})
    void onBtnClick(View view) {
        mPresenter.onBtnClick(view.getId());
    }

    @Override
    public void onEmojiClick() {
        if (mLayoutEmoji.isShown()) {
            hideEmojiLayout();
        } else {
            showEmojiLayout(KeyBoardUtils.isKeyBoardShow(this));
        }
    }

    /**
     * 显示表情输入框
     * */
    private void showEmojiLayout(boolean showAnimation) {
        if (showAnimation) {
            mTransition.setDuration(200);
        } else {
            mTransition.setDuration(0);
        }

        emojiHeight = KeyBoardUtils.getKeyboardHeight(this);

        KeyBoardUtils.hideKeybord(this, mEtMessage);
        mLayoutEmoji.getLayoutParams().height = emojiHeight;
        mLayoutEmoji.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //在5.0有navigationbar的手机，高度高了一个statusBar
        int lockHeight = ScreenUtils.getAppContentHeight(this);
        lockContainerHeight(lockHeight);
    }

    /**
     * 隐藏表情输入框
     * */
    private void hideEmojiLayout() {
        if (mLayoutEmoji.isShown()) {
            mLayoutEmoji.setVisibility(View.GONE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            unlockContainerHeightDelayed();
        }
    }

    private void lockContainerHeight(int paramInt) {
        LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams) mLayoutMessage.getLayoutParams();
        localLayoutParams.height = paramInt;
        localLayoutParams.weight = 0.0F;
    }

    public void unlockContainerHeightDelayed() {
        ((LinearLayout.LayoutParams) mLayoutMessage.getLayoutParams()).weight = 1.0F;
    }

    /**
     * 加并且刷新消息
     * */
    @Override
    public void addMessage(NettyReceivePrivateBean bean) {
        if (isRunning) {
            if (mAdapter != null && bean != null) {
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
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
