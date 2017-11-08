package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FansMessageAdapter;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.pullrefresh.PullRefreshView;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.ClearEditText;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 类说明：粉丝消息弹窗
 * Author: Kuzan
 * Date: 2017/9/11 11:48.
 */
public class FansMessagePop extends BasePopupWindow {
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.list_message)
    PullRefreshRecyclerView mListMessage;
    @Bind(R.id.et_message)
    ClearEditText mEtMessage;
    @Bind(R.id.btn_emoticon)
    ImageView mBtnEmoticon;

    private MemberBean currMember;
    private String mMessage;

    private FansMessageAdapter mAdapter;

    public FansMessagePop(Context context) {
        super(context);
    }

    @Override
    protected int getViewId() {
        return R.layout.pop_fans_message;
    }

    @Override
    protected int getAnimId() {
        return R.style.AnimationPopupWindow_bottom_to_up;
    }

    @Override
    protected void initAfterViews() {
        PullRefreshUtil.setRefresh(mListMessage, false, true);
        mListMessage.setOnPullUpRefreshListener(new PullRefreshView.OnPullUpRefreshListener() {
            @Override
            public void onRefresh() {
                mListMessage.refreshFinish();
            }
        });

        mListMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new FansMessageAdapter(mContext);
        mListMessage.setAdapter(mAdapter);

        //监听键盘
        mEtMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case KeyEvent.KEYCODE_ENDCALL:
                    case KeyEvent.KEYCODE_ENTER:
                        onSendClick();
                        return true;
                    case KeyEvent.KEYCODE_BACK:
                        dismiss();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        int height = (int) mContext.getResources().getDimension(R.dimen.margin_50);
        setHeight(height);
    }

    /**
     * 刷新用户信息
     * */
    public void refreshMemberInfo(MemberBean bean) {
        if (bean != null) {
            currMember = bean;
            mTvTitle.setText(bean.getNickName());
            mAdapter.refreshMember(bean);
            mAdapter.replaceList(null);
        }
    }

    /**
     * 刷新消息
     * */
    public void addMessage(NettyReceivePrivateBean bean) {
        if (mAdapter != null && bean != null) {
            mAdapter.addListBeanAtStart(bean);
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setPopWindowBg(1.0f);
    }

    @OnClick(R.id.btn_back)
    void onBackClick() {
        dismiss();
    }

    /**
     * 发送消息
     * */
    public void onSendClick() {
        if (currMember != null) {
            if (check()) {
                new IMModelImpl().sendPrivateChat(currMember.getIdStr(), mMessage);
            }
        }
    }

    private boolean check() {
        mMessage = mEtMessage.getText().toString();
        if (StringUtils.isEmptyString(mMessage)) {
            ToastUtils.ToastMessage(mContext, R.string.input_content_hint);
            return false;
        }
        return true;
    }
}
