package com.qcloud.liveshow.widget.pop;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.qcloud.liveshow.R;
import com.qcloud.liveshow.adapter.FansMessageAdapter;
import com.qcloud.liveshow.beans.MemberBean;
import com.qcloud.liveshow.beans.NettyContentBean;
import com.qcloud.liveshow.beans.NettyReceivePrivateBean;
import com.qcloud.liveshow.model.impl.IMModelImpl;
import com.qcloud.liveshow.realm.RealmHelper;
import com.qcloud.liveshow.utils.UserInfoUtil;
import com.qcloud.qclib.base.BasePopupWindow;
import com.qcloud.qclib.pullrefresh.PullRefreshRecyclerView;
import com.qcloud.qclib.pullrefresh.PullRefreshUtil;
import com.qcloud.qclib.toast.ToastUtils;
import com.qcloud.qclib.utils.StringUtils;
import com.qcloud.qclib.widget.customview.ClearEditText;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.OnClick;
import timber.log.Timber;

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
    private RealmHelper realmHelper;

    public FansMessagePop(Context context) {
        super(context);
        realmHelper=new RealmHelper<NettyReceivePrivateBean>();

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
        mListMessage.setOnPullUpRefreshListener(() -> mListMessage.refreshFinish());

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
            initDate();
        }
    }
    //初始化所有私聊记录
    private void initDate() {
        if (currMember!=null){
            String fromUserId=currMember.getIdStr();
            List<NettyReceivePrivateBean> charList = (List<NettyReceivePrivateBean>) realmHelper.queryListById(NettyReceivePrivateBean.class,"from_user_id",fromUserId);
            Timber.e("charList:"+charList);
            replaceList(charList);
        }

    }
    public void replaceList(List<NettyReceivePrivateBean> beans) {
            if (mListMessage != null) {
                mListMessage.refreshFinish();
            }
            if (beans != null && beans.size() > 0) {
                if (mAdapter != null) {
                    mAdapter.replaceList(beans);
                }
            } else {
                Timber.e("私聊列表为空");
            }
    }

    /**
     * 刷新消息
     * */
    public void addMessage(NettyReceivePrivateBean bean) {
        Timber.e("NettyReceivePrivateBean:"+bean);
        Timber.e("mAdapter:"+mAdapter);
        if (mAdapter != null && bean != null) {
            if (bean.getContent() != null) {
                // 接收消息
                Timber.e(bean.toString());
            } else {
                // 发送消息
                NettyContentBean contentBean = new NettyContentBean(mMessage);
                bean.setFrom_user_id(UserInfoUtil.mUser.getIdStr());
                bean.setContent(contentBean);
            }
            mAdapter.addListBeanAtEnd(bean);
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
                NettyContentBean contentBean=new NettyContentBean();
                contentBean.setDate_time(System.currentTimeMillis());
                contentBean.setText(mMessage);
                NettyReceivePrivateBean nettyReceivePrivateBean=new NettyReceivePrivateBean();
                nettyReceivePrivateBean.setChat_id(""+ UUID.randomUUID());
                nettyReceivePrivateBean.setFrom_user_id(currMember.getIdStr());
                nettyReceivePrivateBean.setSend(true);
                nettyReceivePrivateBean.setContent(contentBean);
                realmHelper.addOrUpdateBean(nettyReceivePrivateBean);
                addMessage(nettyReceivePrivateBean);
                mEtMessage.setText("");
                hideInput();
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

    public void hideInput() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEtMessage.getWindowToken(), 0);
    }
}
